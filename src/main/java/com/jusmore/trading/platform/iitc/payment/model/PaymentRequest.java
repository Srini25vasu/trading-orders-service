package com.jusmore.trading.platform.iitc.payment.model;

public sealed interface PaymentRequest permits CreditCardRequest, CryptoRequest, NetbankingRequest  {
  String transactionId();
  double amount();
}
