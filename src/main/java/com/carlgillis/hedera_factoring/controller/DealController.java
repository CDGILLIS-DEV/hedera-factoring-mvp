package com.carlgillis.hedera_factoring.controller;

import com.carlgillis.hedera_factoring.domain.Deal;
import com.carlgillis.hedera_factoring.dto.DealDto;
import com.carlgillis.hedera_factoring.dto.DealResponseDto;
import com.carlgillis.hedera_factoring.service.DealService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deals")
public class DealController {
    private final DealService svc;

    public  DealController(DealService svc) {
        this.svc = svc;
    }


    @GetMapping
    public List<DealResponseDto> getAll() {
        return  svc.getAllDeals();
    }

    @PostMapping
    public ResponseEntity<Deal> create(@Valid @RequestBody DealDto dto) {
        return ResponseEntity.ok(svc.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(svc.getDealById(id));
    }
}
