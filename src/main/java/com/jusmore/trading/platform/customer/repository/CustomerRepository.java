package com.jusmore.trading.platform.customer.repository;

import com.jusmore.trading.platform.customer.domain.Customer;
import com.jusmore.trading.platform.customer.domain.UserId;
import com.jusmore.trading.platform.shared_kernel.CustomerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, CustomerId> {
  public Optional<Customer> findByEmail(String email);

  public Optional<Customer> findByPhone(String phone);

  public Optional<Customer> findByName(String name);

  public Customer findByEmailAndPhone(String email, String phone);
}

