package com.jusmore.trading.platform.customer.domain;

import com.jusmore.trading.platform.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
public class Payment extends BaseEntity<Long> {
  @Column(name = "transaction_id", nullable = false)
  private String transactionId;
  @Column(name = "amount", nullable = false)
  private double amount;
  @Column(name = "cvv", nullable = false)
  private String cvv;
  @Column(name = "credit_card_number", nullable = false)
  private String creditCardNumber;
}
