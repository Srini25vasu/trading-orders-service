package com.jusmore.trading.platform.iitc.payment.model;

public record NetbankingRequest(
      String transactionId,
      double amount
) implements PaymentRequest {
}
