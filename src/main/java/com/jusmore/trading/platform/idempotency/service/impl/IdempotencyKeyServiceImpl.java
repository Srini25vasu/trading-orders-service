package com.jusmore.trading.platform.idempotency.service.impl;

import com.jusmore.trading.platform.idempotency.domain.IdempotencyKey;
import com.jusmore.trading.platform.idempotency.dto.IdempotencyKeyDto;
import com.jusmore.trading.platform.idempotency.mapper.IdempotencyKeyMapper;
import com.jusmore.trading.platform.idempotency.repository.IdempotencyKeyRepository;
import com.jusmore.trading.platform.idempotency.service.IdempotencyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Implementierung des IdempotencyService.
 * Verwaltet die Erstellung und Verwaltung von IdempotencyKeys.
 *
 * @version 1.0*
 * @author Srinivas
 */
@Service
@Slf4j
@AllArgsConstructor
public class IdempotencyKeyServiceImpl implements IdempotencyService {
  public static final long IDEMPOTENCY_KEY_LOCK_TIMEOUT_IN_SECONDS = 10L;
  private final IdempotencyKeyRepository idempotencyKeyRepository;
  private final IdempotencyKeyMapper idempotencyKeyMapper;
  private final Clock clock;


  /**
   * Ruft den IdempotencyKey fÃ¼r die gegebene Anfrage ab oder erstellt ihn neu.
   * Wenn der Key bereits existiert, wird nur dann eine IllegalStateException geworfen,
   * wenn der Key aktuell noch gesperrt ist.
   * Bei Neuerstellung werden {@code createdAt} und {@code lockedAt} im Entity-{@code @PrePersist}
   * initialisiert.
   *
   * @param idempotencyKeyDto
   * @return der IdempotencyKey
   * @throws IllegalStateException wenn der bestehende IdempotencyKey gesperrt ist.
   */
  @Transactional
  @Override
  public IdempotencyKey getOrCreateIdempotencyKey(IdempotencyKeyDto idempotencyKeyDto) throws IllegalStateException {
    log.info("Abrufen oder erstellung des IdempotencyKeys for {}", idempotencyKeyDto);
    Optional<IdempotencyKey> optionalKey = idempotencyKeyRepository.findByIdempotencyKeyAndUserId(
          idempotencyKeyDto.idempotencyKey(), idempotencyKeyDto.userId());
    if (optionalKey.isPresent()) {
      return validateLockState(optionalKey.get());
    }

    IdempotencyKey idempotencyKey = new IdempotencyKey();
    idempotencyKeyMapper.toEntity(idempotencyKeyDto, idempotencyKey);

    try {
      // Force DB constraint check inside this method so race conflicts are handled deterministically.
      return idempotencyKeyRepository.saveAndFlush(idempotencyKey);
    } catch (DataIntegrityViolationException ex) {
      return idempotencyKeyRepository
            .findByIdempotencyKeyAndUserId(idempotencyKeyDto.idempotencyKey(), idempotencyKeyDto.userId())
            .map(this::validateLockState)
            .orElseThrow(() -> ex);
    }
  }

  private IdempotencyKey validateLockState(IdempotencyKey key) {
    if (isLocked(key)) {
      String msg = "IdempotencyKey: %s is gesperrt".formatted(key.getIdempotencyKey());
      log.error(msg);
      throw new IllegalStateException(msg);
    }
    return key;
  }

  protected boolean isLocked(IdempotencyKey idempotencyKey) {
    LocalDateTime lockedAt = idempotencyKey.getLockedAt();
    if (lockedAt == null) {
      log.warn("IdempotencyKey {} has null lockedAt, treating as unlocked", idempotencyKey.getIdempotencyKey());
      return false;
    }
    return lockedAt.isAfter(LocalDateTime.now(clock).minusSeconds(IDEMPOTENCY_KEY_LOCK_TIMEOUT_IN_SECONDS));
  }
}

