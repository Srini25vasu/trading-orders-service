package com.jusmore.trading.platform.iitc.payment.service;

import com.jusmore.trading.platform.iitc.payment.model.CreditCardRequest;
import com.jusmore.trading.platform.iitc.payment.model.PaymentResult;
import com.jusmore.trading.platform.iitc.payment.model.PaymentStatus;
import com.jusmore.trading.platform.iitc.payment.model.PaymentType;

public class CreditCardPaymentStrategy extends AbstractPaymentStrategy<CreditCardRequest> {

  @Override
  public PaymentResult doProcess(CreditCardRequest request) {
    // Implement credit card payment processing logic here
    System.out.println("Processing credit card payment for amount: " + request.amount());
    // Add more logic as needed
    return new PaymentResult("wzwzwz122", PaymentStatus.COMPLETED, "Credit card payment processed successfully.");
  }

  @Override
  public PaymentType getSupportedType() {
    return PaymentType.CREDIT_CARD;
  }
}
