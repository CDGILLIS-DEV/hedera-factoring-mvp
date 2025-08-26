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
    public ResponseEntity<Deal> create(@Valid @RequestBody DealDto dto) {
        return ResponseEntity.ok(svc.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deal> get(@PathVariable Long id) {
        return svc.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
