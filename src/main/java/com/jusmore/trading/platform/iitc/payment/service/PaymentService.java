package com.jusmore.trading.platform.iitc.payment.service;

import com.jusmore.trading.platform.iitc.payment.model.*;

import java.util.Optional;

public class PaymentService {
  PaymentStrategyFactory paymentStrategyFactory;

  public PaymentService(PaymentStrategyFactory paymentStrategyFactory) {
    this.paymentStrategyFactory = paymentStrategyFactory;
  }

  public Optional<PaymentResult> processPayment(PaymentRequest paymentRequest) {
    return switch (paymentRequest) {
      case CreditCardRequest creditCardRequest -> processPayment(creditCardRequest, PaymentType.CREDIT_CARD);
      case CryptoRequest cryptoRequest -> processPayment(cryptoRequest, PaymentType.CRYPTO);
      case NetbankingRequest netbankRequest -> processPayment(netbankRequest, PaymentType.NETBANKING);
    };
  }

  private <T extends PaymentRequest> Optional<PaymentResult> processPayment(T paymentRequest, PaymentType paymentType) {
    PaymentStrategy<T> paymentStrategy = paymentStrategyFactory.getPaymentStrategy(paymentType);
    return Optional.of(paymentStrategy.process(paymentRequest));
  }
}
