package com.jusmore.trading.platform.customer.repository;

import com.jusmore.trading.platform.customer.domain.Customer;
import com.jusmore.trading.platform.customer.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UserId> {
  public Customer findByEmail(String email);
  public Customer findByPhone(String phone);
  public Customer findByName(String name);
  public Customer findByEmailAndPhone(String email, String phone);
}

