package com.jusmore.trading.platform.codechallenge.iitc.payment;

/**
 * Open/Closed Principle: OrderService is closed for modification but open for extension.
 * We can add new payment methods without modifying the existing code.
 * a new payment method can just implement this interface.OrderService
 * never needs if-else or switch cases over payment types.
 *
 * Interface Segregation Principle: This interface has only what every method
 * must support . Refunds are here, because not all payment methods support refunds.
 *
 */
public interface PaymentStrategy {
  boolean pay(double amount);
  String getSupportedPaymentMethod();
}
