package com.carlgillis.hedera_factoring.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;

@Data
public class CustomerDto {
    @NotBlank
    private String name;

    private String email;
    private String accountId; // optional Hedera account string
}
