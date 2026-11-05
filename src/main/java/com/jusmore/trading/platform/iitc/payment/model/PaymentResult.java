package com.jusmore.trading.platform.iitc.payment.model;

public record PaymentResult(
      String transactionId,
      PaymentStatus status,
      String confirmationCode
) {
}
