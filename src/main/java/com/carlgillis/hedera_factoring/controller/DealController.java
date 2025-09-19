package com.carlgillis.hedera_factoring.controller;

import com.carlgillis.hedera_factoring.domain.Invoice;
import com.carlgillis.hedera_factoring.dto.DealDto;
import com.carlgillis.hedera_factoring.dto.DealResponseDto;
import com.carlgillis.hedera_factoring.dto.InvoiceDto;
import com.carlgillis.hedera_factoring.dto.InvoiceResponseDto;
import com.carlgillis.hedera_factoring.repository.InvoiceRepository;
import com.carlgillis.hedera_factoring.service.DealService;
import com.carlgillis.hedera_factoring.service.InvoiceService;
import com.hedera.hashgraph.sdk.PrecheckStatusException;
import com.hedera.hashgraph.sdk.ReceiptStatusException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/deals")
public class DealController {
    private final DealService svc;

    public  DealController(DealService svc, InvoiceService invSrv) { this.svc = svc; }



    @GetMapping
    public List<DealResponseDto> getAll() {
        return  svc.getAllDeals();
    }

    @PostMapping
    public ResponseEntity<DealResponseDto> create(@Valid @RequestBody DealDto dto) throws Exception {
        return ResponseEntity.ok(svc.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(svc.getDealById(id));
    }

    @GetMapping("/invoice/{id}")
    public List<DealResponseDto> getDealsForInvoice(@PathVariable Long id) { return svc.getDealsByInvoice(id); }
}
