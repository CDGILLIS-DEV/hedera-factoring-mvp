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

//    @PostMapping("/debug")
//    public ResponseEntity<String> debug(@RequestBody String raw) {
//        return ResponseEntity.ok("Got: " + raw);
//    }
//    @PostMapping("/raw")
//    public ResponseEntity<String> echo(@RequestBody String body) {
//        return ResponseEntity.ok(body);
//    }
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CustomerDto dto) {
        Customer c = Customer.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .accountId(dto.getAccountId())
                .build();
        Customer saved = svc. create(c);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.ok(svc.findById(id));
    }
}
