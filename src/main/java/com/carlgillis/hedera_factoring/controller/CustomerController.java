package com.carlgillis.hedera_factoring.controller;

import com.carlgillis.hedera_factoring.dto.CustomerDto;
import com.carlgillis.hedera_factoring.dto.CustomerResponseDto;
import com.carlgillis.hedera_factoring.dto.InvoiceResponseDto;
import com.carlgillis.hedera_factoring.service.CustomerService;
import com.carlgillis.hedera_factoring.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService svc;
    private final InvoiceService invoiceService;

    public CustomerController(CustomerService svc, InvoiceService invoiceService) {
        this.svc = svc;
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CustomerDto dto) {
        return ResponseEntity.ok(svc.create(dto));
    }

    @GetMapping
    public List<CustomerResponseDto> getAll() {
        return svc.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(svc.getCustomerById(id));
    }

    @GetMapping("/{id}/invoices")
    public List<InvoiceResponseDto> getInvoiceForCustomer(@PathVariable Long id) {
        return invoiceService.getInvoicesByCustomer(id);
    }
}
