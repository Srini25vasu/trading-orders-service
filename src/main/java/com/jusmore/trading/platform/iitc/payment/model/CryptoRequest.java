package com.jusmore.trading.platform.iitc.payment.model;

public record CryptoRequest(
      String transactionId,
      double amount,
      String cryptoAddress
) implements PaymentRequest {
  public CryptoRequest {
    if(amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than zero");
    }
  }

}
