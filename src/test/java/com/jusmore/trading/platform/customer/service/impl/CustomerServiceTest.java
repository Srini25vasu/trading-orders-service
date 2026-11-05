package com.jusmore.trading.platform.customer.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jusmore.trading.platform.customer.domain.Customer;
import com.jusmore.trading.platform.customer.dto.response.CustomerResponse;
import com.jusmore.trading.platform.customer.mapper.CustomerMapper;
import com.jusmore.trading.platform.customer.repository.CustomerRepository;
import com.jusmore.trading.platform.shared_kernel.CustomerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
  @Mock
  private CustomerRepository customerRepository;

  @Mock
  private CustomerMapper customerMapper;

  @InjectMocks
  private CustomerServiceImpl customerService;

  private final ObjectMapper objectMapper = new ObjectMapper();
  private List<Customer> customers;
  private Customer customer;
  private CustomerResponse customerResponse;

  @BeforeEach
  void setup() throws IOException {
    customer = objectMapper.readValue(getClass()
          .getResourceAsStream("/customer/customer.json"), Customer.class);
    customer.setId(CustomerId.create());
    customerResponse = objectMapper.readValue(getClass()
          .getResourceAsStream("/customer/customer-response.json"), CustomerResponse.class);
  }

  /*@Test
  void testGetCustomerById() {
    when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
    when(customerMapper.toCustomerResponse(customer)).thenReturn(customerResponse);
    CustomerResponse result = customerService.getCustomerById(customer.getId().toString());
    assertEquals(customerResponse, result);
  }*/

  @Test
  void testGetAllCustomersFetchesOrdersInSingleRepositoryCall() {
    customers = List.of(customer);
    when(customerRepository.findAllWithTradingOrders()).thenReturn(customers);
    when(customerMapper.toCustomerResponse(customer)).thenReturn(customerResponse);

    List<CustomerResponse> result = customerService.getAllCustomers();

    assertEquals(List.of(customerResponse), result);
    verify(customerRepository).findAllWithTradingOrders();
    verify(customerMapper).toCustomerResponse(customer);
  }

}
