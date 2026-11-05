package com.jusmore.trading.platform.iitc.pyament;

import com.jusmore.trading.platform.iitc.payment.model.*;
import com.jusmore.trading.platform.iitc.payment.service.PaymentService;
import com.jusmore.trading.platform.iitc.payment.service.PaymentStrategy;
import com.jusmore.trading.platform.iitc.payment.service.CreditCardPaymentStrategy;
import com.jusmore.trading.platform.iitc.payment.service.PaymentStrategyFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
  @Mock
  PaymentStrategyFactory paymentStrategyFactory;

  @InjectMocks
  PaymentService paymentService;

  @Test
  void testProcessPayment() {
    PaymentStrategy<? extends PaymentRequest> paymentStrategy = new CreditCardPaymentStrategy();
    when(paymentStrategyFactory.getPaymentStrategy(PaymentType.CREDIT_CARD)).thenReturn((PaymentStrategy<PaymentRequest>) paymentStrategy);

    PaymentResult paymentResult = paymentService.processPayment(new CreditCardRequest(
          "asgsh1",
          2626.23,
          "1234567890",
          "2020-20-09",
          "097"
    )).orElseThrow();

    assertEquals(PaymentStatus.COMPLETED, paymentResult.status());
    assertEquals("wzwzwz122", paymentResult.transactionId());
    verify(paymentStrategyFactory).getPaymentStrategy(PaymentType.CREDIT_CARD);
  }
}
