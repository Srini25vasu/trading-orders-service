package com.jusmore.trading.platform.idempotency.repository;

import com.jusmore.trading.platform.idempotency.domain.IdempotencyKey;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdempotencyKeyRepository extends JpaRepository<IdempotencyKey, String> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")}) // 3 seconds
  Optional<IdempotencyKey> findByIdempotencyKeyAndUserId(String idempotencyKey, String userId);

}

