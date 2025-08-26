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
    public ResponseEntity<Invoice> create(@Valid @RequestBody InvoiceDto dto) {
        return ResponseEntity.ok(svc.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> get(@PathVariable Long id) {
        return svc.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
