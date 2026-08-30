package com.jusmore.trading.platform.repository;

import com.jusmore.trading.platform.domain.base.TradingOrder;
import com.jusmore.trading.platform.dto.response.TradeOrderSummary;
import com.jusmore.trading.platform.repository.entities.TradingOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TradingOrderRepositoryGroupingTest {
  @Autowired
  private TradingOrderRepository tradingOrderRepository;

  @Autowired
  private TestEntityManager testEntityManager;

  @BeforeEach
  void setup() {
    tradingOrderRepository.deleteAll();
    persistTradingOrder("VW", 89.0, 50000.0, "UP");
    persistTradingOrder("TATA", 37.0, 50000.0, "DOWN");
    persistTradingOrder("SUZUKI", 34, 4000.0, "UP");
    persistTradingOrder("VW", 45.0, 500.0, "DOWN");
    persistTradingOrder("VW", 34.0, 737, "UP");

    testEntityManager.flush();
    testEntityManager.clear();
  }
  @Test
  void findTradingOrderSummaries_shouldGroupBySymbol() {
    String slide = "DOWN";
    List<TradeOrderSummary> tradeOrderSummaries = tradingOrderRepository.findTradeOrderSummaries(slide);
    assertEquals(3, tradeOrderSummaries.size());

    // TATA trading order summary: total orders: 1, total quantity: 37, total amount: 50000.0
    TradeOrderSummary tataOrderSummaries = findBySymbol(tradeOrderSummaries, "TATA");
    assertEquals(1, tataOrderSummaries.totalOrders());
    assertEquals(1850000.0, tataOrderSummaries.totalAmount());
    assertEquals(37, tataOrderSummaries.totalQuantity());

    // VW trading order summary: total orders: 3,
    TradeOrderSummary vwOrderSummaries = findBySymbol(tradeOrderSummaries, "VW");
    assertEquals(3, vwOrderSummaries.totalOrders());
    assertEquals(4497558.0, vwOrderSummaries.totalAmount());
    assertEquals(168.0, vwOrderSummaries.totalQuantity());
  }

  private void persistTradingOrder(String symbol, double quantity, double price, String side) {
    TradingOrder tradingOrder = new TradingOrder();
    tradingOrder.setSymbol(symbol);
    tradingOrder.setQuantity(quantity);
    tradingOrder.setPrice(price);
    tradingOrder.setSide(side);
    testEntityManager.persist(tradingOrder);
  }

  TradeOrderSummary findBySymbol(List<TradeOrderSummary>results, String symbol) {
    return results.stream()
          .filter(tradeOrder -> tradeOrder.symbol().equals(symbol))
          .findFirst()
          .orElseThrow(() -> new AssertionError("TradeOrderSummary not found for symbol: " + symbol));
  }
}
