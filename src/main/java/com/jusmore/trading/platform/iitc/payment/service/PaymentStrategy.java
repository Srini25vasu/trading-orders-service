package com.jusmore.trading.platform.iitc.payment.service;

import com.jusmore.trading.platform.iitc.payment.model.PaymentRequest;
import com.jusmore.trading.platform.iitc.payment.model.PaymentResult;
import com.jusmore.trading.platform.iitc.payment.model.PaymentType;

public interface PaymentStrategy<T extends PaymentRequest> {
  PaymentResult process(T request);
  PaymentType getSupportedType();
}
