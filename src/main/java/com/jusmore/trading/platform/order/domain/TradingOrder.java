package com.jusmore.trading.platform.order.domain;

import com.jusmore.trading.platform.common.domain.BaseEntity;
import com.jusmore.trading.platform.customer.domain.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "trading_order")
@Getter
@Setter
@NoArgsConstructor
public class TradingOrder extends BaseEntity {
  @Column(name = "symbol")
  private String symbol;
  @Column(name = "quantity")
  private double quantity;
  @Column(name = "price")
  private double price;
  @Column(name = "side")
  private String side;
  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;
}
