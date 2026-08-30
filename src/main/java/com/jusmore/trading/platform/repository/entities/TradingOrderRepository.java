package com.jusmore.trading.platform.repository.entities;

import com.jusmore.trading.platform.domain.base.TradingOrder;
import com.jusmore.trading.platform.dto.response.TradeOrderSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradingOrderRepository extends JpaRepository<TradingOrder, Long> {

  @Query("SELECT t FROM trading_order t WHERE t.symbol = :symbol AND t.side = :side GROUP BY :groupby")
  List<TradingOrder> findOrders(@Param("symbol") String symbol, @Param("side") String side, @Param("groupby") String groupby);
  //List<TradingOrder> findBySymbolAndSide(String symbol, String side);

  @Query(
        """
              SELECT new com.jusmore.trading.platform.dto.response.TradeOrderSummary(
                  t.symbol,
                  COUNT(t.id),
                  SUM(t.quantity),
                  SUM(t.price * t.quantity)
              ) FROM trading_order t GROUP BY t.symbol
              """
  )
  List<TradeOrderSummary> findTradeOrderSummaries(@Param("side") String side);

}
