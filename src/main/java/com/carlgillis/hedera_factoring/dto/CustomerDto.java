package com.carlgillis.hedera_factoring.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerDto {
    @NotBlank
    private String name;

    private String email;
    private String accountId; // optional Hedera account string
}
