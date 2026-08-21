package com.jusmore.trading.platform.repository.entities;

import com.jusmore.trading.platform.domain.base.TradingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradingOrderRepository extends JpaRepository<TradingOrder, Long> {
}
