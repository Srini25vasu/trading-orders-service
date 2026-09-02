package com.jusmore.trading.platform.idempotency.service;

import com.jusmore.trading.platform.idempotency.domain.IdempotencyKey;
import com.jusmore.trading.platform.idempotency.dto.IdempotencyKeyDto;

public interface IdempotencyService {
  IdempotencyKey getOrCreateIdempotencyKey(IdempotencyKeyDto idempotencyKeyDto);
}

