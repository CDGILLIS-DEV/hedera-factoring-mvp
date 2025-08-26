package com.carlgillis.hedera_factoring.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DealDto {
    @NotNull
    private Long invoiceId;

    @NotBlank
    private String purchaserAccountId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal purchasePrice;
}
