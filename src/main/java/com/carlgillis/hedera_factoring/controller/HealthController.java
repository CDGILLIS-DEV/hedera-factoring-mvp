package com.carlgillis.hedera_factoring.controller;

import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.proto.AccountID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}
