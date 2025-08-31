package com.carlgillis.hedera_factoring.service;

import com.carlgillis.hedera_factoring.domain.Customer;
import com.carlgillis.hedera_factoring.dto.CustomerDto;
import com.carlgillis.hedera_factoring.dto.CustomerResponseDto;
import com.carlgillis.hedera_factoring.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public List<CustomerResponseDto> getAllCustomers() {
        return repo.findAll().stream()
                .map(c -> CustomerResponseDto.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .email(c.getEmail())
                        .accountId(c.getAccountId())
                        .build())
                .toList();
    }

    public CustomerResponseDto getCustomerById(Long id) {
        var c = repo.findById(id).orElseThrow();
        return CustomerResponseDto.builder()
                .id(c.getId())
                .name(c.getName())
                .email(c.getEmail())
                .accountId(c.getAccountId())
                .build();
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
