package com.jusmore.trading.platform.iitc.payment.service;

import com.jusmore.trading.platform.iitc.payment.model.PaymentRequest;
import com.jusmore.trading.platform.iitc.payment.model.PaymentType;

import java.util.HashMap;
import java.util.Map;

public class PaymentStrategyFactory {
  Map<PaymentType, PaymentStrategy<? extends PaymentRequest>> paymentStrategies = new HashMap<>();

  public PaymentStrategyFactory() {

  }

  @SuppressWarnings("unchecked")
  public <T extends PaymentRequest> PaymentStrategy<T> getPaymentStrategy(PaymentType paymentType) {
    return (PaymentStrategy<T>) paymentStrategies.get(paymentType);
  }
  public void addPaymentStrategy(PaymentStrategy<? extends PaymentRequest> paymentStrategy) {
    paymentStrategies.put(paymentStrategy.getSupportedType(), paymentStrategy);
  }
}
