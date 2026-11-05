package com.jusmore.trading.platform.codechallenge.iitc.payment;

public interface RefundPaymentStrategy extends PaymentStrategy{
  boolean refund(double amount);
}
