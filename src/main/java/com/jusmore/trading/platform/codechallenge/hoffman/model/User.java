package com.jusmore.trading.platform.codechallenge.hoffman.model;

import java.util.UUID;

public record User(
      UUID id,
      String name,
      String email
) {
}
