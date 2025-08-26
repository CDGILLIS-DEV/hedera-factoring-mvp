package com.carlgillis.hedera_factoring.controller;

import com.carlgillis.hedera_factoring.domain.Invoice;
import com.carlgillis.hedera_factoring.dto.InvoiceDto;
import com.carlgillis.hedera_factoring.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    private final InvoiceService svc;

    public InvoiceController(InvoiceService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody InvoiceDto dto) {
        Invoice invoice = Invoice.builder()
                .amount(dto.getAmount())
                .currency(dto.getCurrency() == null ? "USD" : dto.getCurrency())
                .dueDate(dto.getDueDate())
                .build();
        Invoice saved = svc.create(invoice, dto.getCustomerId());
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.ok(svc.findById(id));
    }
}
