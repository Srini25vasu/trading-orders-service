package com.jusmore.trading.platform.order.repository;

import com.jusmore.trading.platform.customer.domain.Customer;
import com.jusmore.trading.platform.order.domain.TradingOrder;
import com.jusmore.trading.platform.customer.domain.UserId;
import com.jusmore.trading.platform.customer.repository.CustomerRepository;
import com.jusmore.trading.platform.order.dto.response.TradeOrderSummary;
import com.jusmore.trading.platform.shared_kernel.CustomerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TradingOrderRepositoryGroupingTest {
  @Autowired
  private TradingOrderRepository tradingOrderRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private TestEntityManager testEntityManager;

  @BeforeEach
  void setup() {
    tradingOrderRepository.deleteAll();
    CustomerId userId = CustomerId.create();
    persistCustomer(userId,"John Doe", "sss@gmail.com", "1234567890");
    testEntityManager.flush();
    testEntityManager.clear();
    Optional<Customer> customer = customerRepository.findByEmail("sss@gmail.com");

    persistTradingOrder("VW", 89.0, 50000.0, "UP", customer.orElseThrow(() -> new AssertionError("Customer not found")));
    persistTradingOrder("TATA", 37.0, 50000.0, "DOWN", customer.orElseThrow(() -> new AssertionError("Customer not found")));
    persistTradingOrder("SUZUKI", 34, 4000.0, "UP", customer.orElseThrow(() -> new AssertionError("Customer not found")));
    persistTradingOrder("VW", 45.0, 500.0, "DOWN", customer.orElseThrow(() -> new AssertionError("Customer not found")));
    persistTradingOrder("VW", 34.0, 737, "UP", customer.orElseThrow(() -> new AssertionError("Customer not found")));

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

  private void persistCustomer(CustomerId userId, String name, String email, String phone) {
    Customer customer = new Customer();
    customer.setId(userId);
    customer.setName(name);
    customer.setEmail(email);
    customer.setPhone(phone);
    testEntityManager.persist(customer);
  }
  private void persistTradingOrder(String symbol, double quantity, double price, String side, Customer customer) {
    TradingOrder tradingOrder = new TradingOrder();
    tradingOrder.setSymbol(symbol);
    tradingOrder.setQuantity(quantity);
    tradingOrder.setPrice(price);
    tradingOrder.setSide(side);
    tradingOrder.setCustomer(customer);
    testEntityManager.persist(tradingOrder);
  }

  TradeOrderSummary findBySymbol(List<TradeOrderSummary>results, String symbol) {
    return results.stream()
          .filter(tradeOrder -> tradeOrder.symbol().equals(symbol))
          .findFirst()
          .orElseThrow(() -> new AssertionError("TradeOrderSummary not found for symbol: " + symbol));
  }
}
