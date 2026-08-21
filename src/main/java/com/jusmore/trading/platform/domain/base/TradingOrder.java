package com.jusmore.trading.platform.domain.base;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "trading_order")
@Getter
@Setter
@NoArgsConstructor
public class TradingOrder extends BaseEntity{
  @Column(name = "symbol")
  private String symbol;
  @Column(name = "quantity")
  private double quantity;
  @Column(name = "price")
  private double price;
  @Column(name = "side")
  private String side;
}
