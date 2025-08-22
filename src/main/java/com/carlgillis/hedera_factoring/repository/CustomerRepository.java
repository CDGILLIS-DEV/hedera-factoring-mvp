package com.carlgillis.hedera_factoring.repository;

import com.carlgillis.hedera_factoring.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
