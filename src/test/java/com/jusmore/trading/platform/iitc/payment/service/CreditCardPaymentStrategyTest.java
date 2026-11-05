package com.jusmore.trading.platform.iitc.payment.service;

import com.jusmore.trading.platform.iitc.payment.model.CreditCardRequest;
import com.jusmore.trading.platform.iitc.payment.model.PaymentResult;
import com.jusmore.trading.platform.iitc.payment.model.PaymentStatus;
import com.jusmore.trading.platform.iitc.payment.model.PaymentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardPaymentStrategyTest {

  @Test
  void processShouldReturnCompletedResult() {
    CreditCardPaymentStrategy paymentStrategy = new CreditCardPaymentStrategy();
    CreditCardRequest request = new CreditCardRequest(
          "txn-2",
          250.75,
          "1234567890123456",
          "2030-12-31",
          "321"
    );

    PaymentResult result = paymentStrategy.process(request);

    assertEquals("wzwzwz122", result.transactionId());
    assertEquals(PaymentStatus.COMPLETED, result.status());
    assertEquals("Credit card payment processed successfully.", result.confirmationCode());
  }

  @Test
  void getSupportedTypeShouldReturnCreditCard() {
    CreditCardPaymentStrategy paymentStrategy = new CreditCardPaymentStrategy();

    assertEquals(PaymentType.CREDIT_CARD, paymentStrategy.getSupportedType());
  }
}
