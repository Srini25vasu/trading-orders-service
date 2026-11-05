package com.jusmore.trading.platform.steadforce.message;

public record Message(
      String id,
      MessageType messageType,
      String message
) {
}
