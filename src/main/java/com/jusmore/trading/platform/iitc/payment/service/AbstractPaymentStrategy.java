package com.jusmore.trading.platform.iitc.payment.service;

import com.jusmore.trading.platform.iitc.payment.model.PaymentRequest;
import com.jusmore.trading.platform.iitc.payment.model.PaymentResult;

public abstract class AbstractPaymentStrategy<T extends PaymentRequest> implements PaymentStrategy<T> {

  public abstract PaymentResult doProcess(T request);

  @Override
  public PaymentResult process(T request) {
    logTransaction(request);
    return doProcess(request);
  }

  public void logTransaction(T request) {
    System.out.println("Transaction ID: " + request.transactionId());
    System.out.println("Amount: " + request.amount());
  }
}
