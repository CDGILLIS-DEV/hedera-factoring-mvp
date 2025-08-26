package com.carlgillis.hedera_factoring.service;

import com.carlgillis.hedera_factoring.domain.Customer;
import com.carlgillis.hedera_factoring.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public Customer create(Customer customer) {
        return repo.save(customer);
    }

    public Customer findById(Long id) {
        return  repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found: " + id));
    }
}
