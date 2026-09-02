package com.jusmore.trading.platform.customer.domain;

import com.jusmore.trading.platform.order.domain.TradingOrder;
import com.jusmore.trading.platform.shared_kernel.CustomerId;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Customer {
  @EmbeddedId
  @EqualsAndHashCode.Include
  private CustomerId id; // Only this field is used for equals/hashCode
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "email", nullable = false)
  private String email;
  @Column(name = "phone", nullable = false)
  private String phone;
  @OneToMany(mappedBy = "customer", orphanRemoval = true, fetch = FetchType.LAZY)
  private List<TradingOrder> tradingOrders;
}
