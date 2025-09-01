package com.carlgillis.hedera_factoring.controller;

import com.carlgillis.hedera_factoring.domain.Invoice;
import com.carlgillis.hedera_factoring.dto.DealResponseDto;
import com.carlgillis.hedera_factoring.dto.InvoiceDto;
import com.carlgillis.hedera_factoring.dto.InvoiceResponseDto;
import com.carlgillis.hedera_factoring.service.DealService;
import com.carlgillis.hedera_factoring.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    private final InvoiceService svc;
    private final DealService dealService;

    public InvoiceController(InvoiceService svc, DealService dealService) {
        this.svc = svc;
        this.dealService = dealService;
    }

    @GetMapping
    public List<InvoiceResponseDto> getAll() {
        return svc.getAllInvoices();
    }

    @PostMapping
    public ResponseEntity<Invoice> create(@Valid @RequestBody InvoiceDto dto) {
        return ResponseEntity.ok(svc.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(svc.getInvoiceById(id));
    }

    @GetMapping("/{id}/deals")
    public List<DealResponseDto> getDealsForInvoice(@PathVariable Long id) {
        return dealService.getDealsByInvoice(id);
    }
}
