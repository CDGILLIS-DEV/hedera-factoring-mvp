package com.carlgillis.hedera_factoring.service;

import com.carlgillis.hedera_factoring.domain.Customer;
import com.carlgillis.hedera_factoring.dto.CustomerDto;
import com.carlgillis.hedera_factoring.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public Customer create(CustomerDto dto) {
        Customer c = Customer.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .accountId(dto.getAccountId())
                .build();
        return repo.save(c);
    }

    public Optional<Customer> findById(Long id) {
        return  repo.findById(id);
    }
}
