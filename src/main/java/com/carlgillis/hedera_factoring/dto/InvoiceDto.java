package com.carlgillis.hedera_factoring.dto;

import com.carlgillis.hedera_factoring.domain.InvoiceStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceDto {
    @NotNull
    private Long customerId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    private String currency = "USD";

    private String dueDate;

    private String status = String.valueOf(InvoiceStatus.PENDING);
}
