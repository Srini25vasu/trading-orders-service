package com.jusmore.trading.platform.iitc.payment.service;

import com.jusmore.trading.platform.iitc.payment.model.CreditCardRequest;
import com.jusmore.trading.platform.iitc.payment.model.PaymentResult;
import com.jusmore.trading.platform.iitc.payment.model.PaymentStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbstractPaymentStrategyTest {
  private final PrintStream originalOut = System.out;
  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

  @AfterEach
  void tearDown() {
    System.setOut(originalOut);
  }

  @Test
  void processShouldLogTransactionAndDelegateToDoProcess() {
    System.setOut(new PrintStream(outputStream));

    TestPaymentStrategy strategy = new TestPaymentStrategy();
    CreditCardRequest request = new CreditCardRequest(
          "txn-1",
          100.50,
          "1234567890123456",
          "2030-12-31",
          "123"
    );

    PaymentResult result = strategy.process(request);

    assertEquals(PaymentStatus.COMPLETED, result.status());
    assertEquals("txn-1", result.transactionId());
    assertEquals(1, strategy.processedCount);
    assertEquals(
          "Transaction ID: txn-1" + System.lineSeparator()
                + "Amount: 100.5" + System.lineSeparator(),
          outputStream.toString()
    );
  }

  private static final class TestPaymentStrategy extends AbstractPaymentStrategy<CreditCardRequest> {
    private int processedCount;

    @Override
    public PaymentResult doProcess(CreditCardRequest request) {
      processedCount++;
      return new PaymentResult(request.transactionId(), PaymentStatus.COMPLETED, "ok");
    }

    @Override
    public com.jusmore.trading.platform.iitc.payment.model.PaymentType getSupportedType() {
      return com.jusmore.trading.platform.iitc.payment.model.PaymentType.CREDIT_CARD;
    }
  }
}
