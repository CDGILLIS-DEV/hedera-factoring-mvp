package com.carlgillis.hedera_factoring.controller;

import com.carlgillis.hedera_factoring.domain.Customer;
import com.carlgillis.hedera_factoring.dto.CustomerDto;
import com.carlgillis.hedera_factoring.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService svc;

    public CustomerController(CustomerService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CustomerDto dto) {
        return ResponseEntity.ok(svc.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return svc.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
