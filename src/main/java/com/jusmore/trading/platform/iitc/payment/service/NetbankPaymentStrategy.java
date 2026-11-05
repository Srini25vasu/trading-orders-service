package com.jusmore.trading.platform.iitc.payment.service;

import com.jusmore.trading.platform.iitc.payment.model.NetbankingRequest;
import com.jusmore.trading.platform.iitc.payment.model.PaymentResult;
import com.jusmore.trading.platform.iitc.payment.model.PaymentStatus;
import com.jusmore.trading.platform.iitc.payment.model.PaymentType;

public class NetbankPaymentStrategy extends AbstractPaymentStrategy<NetbankingRequest> {

  @Override
  public PaymentResult doProcess(NetbankingRequest request) {
    // Implement the logic to process netbanking payment
    System.out.println("Processing netbanking payment for amount: " + request.amount());
    // Add your payment processing logic here
    return new PaymentResult("ashsh2", PaymentStatus.COMPLETED, "Netbanking payment processed successfully.");
  }

  @Override
  public PaymentType getSupportedType() {
    return PaymentType.NETBANKING;
  }
}
