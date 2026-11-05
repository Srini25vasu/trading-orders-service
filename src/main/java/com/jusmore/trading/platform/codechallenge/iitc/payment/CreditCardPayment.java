package com.jusmore.trading.platform.codechallenge.iitc.payment;

public class CreditCardPayment implements RefundPaymentStrategy{
  private final String cardNumberMasked;

  public CreditCardPayment(String cardNumber) {
    this.cardNumberMasked = maskCard(cardNumber);
  }
  @Override
  public boolean refund(double amount) {
    if(amount <= 0) {
      return false;
    }
    System.out.printf("Refunded %.2f to Credit card %s ",amount, cardNumberMasked);
    return true;
  }

  @Override
  public boolean pay(double amount) {
    if(amount <= 0) {
      return false;
    }
    System.out.printf("Charged %.2f to Credit card %s ",amount, cardNumberMasked);
    return true;
  }

  @Override
  public String getSupportedPaymentMethod() {
    return "Credit Card";
  }

  private String maskCard(String cardNumber) {
    if(cardNumber == null || cardNumber.length() < 4) {
      return "****";
    }
    return "**** **** **** "+cardNumber.substring(cardNumber.length() - 4);

  }
}
