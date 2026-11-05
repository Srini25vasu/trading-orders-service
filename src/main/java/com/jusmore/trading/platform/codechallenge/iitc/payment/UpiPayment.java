package com.jusmore.trading.platform.codechallenge.iitc.payment;

public class UpiPayment implements PaymentStrategy{
  private final String upiId;

  public UpiPayment(String upiId) {
    this.upiId = upiId;
  }

  @Override
  public boolean pay(double amount) {
    if(amount <= 0) {
      return false;
    }
    System.out.printf("amount = %.2f UpiId = %s%n",amount, upiId);
    return true;
  }

  @Override
  public String getSupportedPaymentMethod() {
    return "Credit Card";
  }
}
