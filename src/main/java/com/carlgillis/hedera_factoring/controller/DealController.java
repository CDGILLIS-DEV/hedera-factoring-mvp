package com.carlgillis.hedera_factoring.controller;

import com.carlgillis.hedera_factoring.domain.Deal;
import com.carlgillis.hedera_factoring.dto.DealDto;
import com.carlgillis.hedera_factoring.service.DealService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deals")
public class DealController {
    private final DealService svc;

    public  DealController(DealService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DealDto dto) {
        Deal deal = Deal.builder()
                .purchaserAccountId((dto.getPurchaserAccountId()))
                .purchasePrice(dto.getPurchasePrice())
                .build();

        Deal saved = svc.initiateDeal(dto.getInvoiceId(), deal);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.ok(svc.findById(id));
    }
}
