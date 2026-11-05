package com.jusmore.trading.platform.iitc.payment.service;

import com.jusmore.trading.platform.iitc.payment.model.CryptoRequest;
import com.jusmore.trading.platform.iitc.payment.model.PaymentResult;
import com.jusmore.trading.platform.iitc.payment.model.PaymentStatus;
import com.jusmore.trading.platform.iitc.payment.model.PaymentType;

public class CryptoPaymentStrategy extends AbstractPaymentStrategy<CryptoRequest> {
  @Override
  public PaymentResult doProcess(CryptoRequest request) {
    System.out.println("request = " + request);
    return new PaymentResult("ashsh2", PaymentStatus.COMPLETED, "Crypto payment processed successfully.");
  }
  @Override
  public PaymentType getSupportedType() {
    return PaymentType.CRYPTO;
  }
}
