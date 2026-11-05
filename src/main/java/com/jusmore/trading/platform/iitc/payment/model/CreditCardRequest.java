package com.jusmore.trading.platform.iitc.payment.model;

public record CreditCardRequest(
      String transactionId,
      double amount,
      String cardNumber,
      String expiryDate,
      String cvv
) implements PaymentRequest {
  public CreditCardRequest {
    if(amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than zero");
    }
  }
  String maskedCardNumber() {
    return cardNumber.substring(0, 4) + "*" + cardNumber.substring(4, 12) + "*" + cardNumber.substring(12);
  }
}
