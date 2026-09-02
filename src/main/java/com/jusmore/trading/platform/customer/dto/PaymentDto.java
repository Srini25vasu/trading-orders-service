package com.jusmore.trading.platform.customer.dto;

public record PaymentDto(
  String transactionId,
  double amount,

  //@Redact // Will serialize as "[REDACTED]"
  String cvv,

  //@Redact(mask = "XXXX-XXXX-XXXX-####") // Custom mask pattern
  String creditCardNumber ){

}
