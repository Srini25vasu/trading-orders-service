package com.jusmore.trading.platform.iitc.payment;

import com.jusmore.trading.platform.iitc.payment.model.CreditCardRequest;
import com.jusmore.trading.platform.iitc.payment.model.CryptoRequest;
import com.jusmore.trading.platform.iitc.payment.model.NetbankingRequest;
import com.jusmore.trading.platform.iitc.payment.service.PaymentService;
import com.jusmore.trading.platform.iitc.payment.service.PaymentStrategyFactory;

import java.util.Arrays;

public class PaymentController {

  static void main(String[] args) {
    PaymentService paymentService = new PaymentService(new PaymentStrategyFactory());
    //Credit card payment
    paymentService.processPayment(new CreditCardRequest("asgsh1",
          2626.23,
          "1234567890",
          "2020-20-09",
          "097")).map(paymentResult -> {
      System.out.println(paymentResult);
      return paymentResult;
    }).orElseThrow(() ->new RuntimeException("Payment failed"));

    //Crypto payment

    paymentService.processPayment(new CryptoRequest(
          "asjsj",
          2772.29,
          "ajajaj"
    )).map(paymentResult -> {
      System.out.println("args = " + paymentResult);
      return paymentResult;
    }).orElseThrow(() ->new RuntimeException("Payment failed"));

    //netbank payment
    paymentService.processPayment(new NetbankingRequest(
          "aahha",
          363636.23
    )).map(paymentResult -> {
      System.out.println("args = " + paymentResult);
      return paymentResult;
    }).orElseThrow(() ->new RuntimeException("Payment failed"));
  }
}
