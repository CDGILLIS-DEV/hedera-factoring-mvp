package com.carlgillis.hedera_factoring.service;

import com.carlgillis.hedera_factoring.domain.Deal;
import com.carlgillis.hedera_factoring.domain.DealStatus;
import com.carlgillis.hedera_factoring.domain.Invoice;
import com.carlgillis.hedera_factoring.domain.InvoiceStatus;
import com.carlgillis.hedera_factoring.dto.DealDto;
import com.carlgillis.hedera_factoring.dto.DealResponseDto;
import com.carlgillis.hedera_factoring.repository.DealRepository;
import com.carlgillis.hedera_factoring.repository.InvoiceRepository;
import com.hedera.hashgraph.sdk.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

@Service
public class DealService {
    private final DealRepository dealRepo;
    private final InvoiceRepository invoiceRepo;
    private final Client hederaClient;

    public DealService(DealRepository dealRepo, InvoiceRepository invoiceRepo, Client hederaClient) {
        this.dealRepo = dealRepo;
        this.invoiceRepo = invoiceRepo;
        this.hederaClient = hederaClient;
    }


    public List<DealResponseDto> getAllDeals() {
        return dealRepo.findAll().stream()
                .map(d -> DealResponseDto.builder()
                        .id(d.getId())
                        .invoiceId(d.getInvoice().getId())
                        .purchaserAccountId(d.getPurchaserAccountId())
                        .purchasePrice(d.getPurchasePrice())
                        .status(String.valueOf(d.getStatus()))
                        .build())
                .toList();
    }

    public DealResponseDto getDealById(Long id) {
        var d = dealRepo.findById(id).orElseThrow();
        return DealResponseDto.builder()
                .id(d.getId())
                .invoiceId(d.getInvoice().getId())
                .purchaserAccountId(d.getPurchaserAccountId())
                .purchasePrice(d.getPurchasePrice())
                .status(String.valueOf(d.getStatus()))
                .build();
    }


    /**
     * Initiates a deal: validates invoice available, creates Deal, and marks invoice as factored.
     * For MVP, we do not perform on-chain settlement here; that will be added once contract/wallet flows exist.
     */
    @Transactional
    public DealResponseDto create(DealDto dto) throws Exception {
        // 1. Load invoice
        Invoice invoice = invoiceRepo.findById(dto.getInvoiceId())
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        // 2. Build deal object
        Deal deal = Deal.builder()
                .invoice(invoice)
                .purchaserAccountId(dto.getPurchaserAccountId())
                .purchasePrice(dto.getPurchasePrice())
                .status(DealStatus.INITIATED)
                .createdAt(Instant.now())
                .transactionId(dto.getTransactionId())
                .build();

        // 3. Execute Hedera transfer (tiny test transaction)
        TransactionResponse tx = new TransferTransaction()
                .addHbarTransfer(
                        hederaClient.getOperatorAccountId(),
                        Hbar.fromTinybars(-1_000) // send 1000 tinybars (0.00001 HBAR)
                )
                .addHbarTransfer(
                        AccountId.fromString(dto.getPurchaserAccountId()),
                        Hbar.fromTinybars(1_000)
                )
                .execute(hederaClient);
        // 4. Wait for receipt
        TransactionReceipt receipt = tx.getReceipt(hederaClient);

        // 5. Store transaction in Deal
        deal.setTransactionId(tx.transactionId.toString());

        // 6. Save deal
        Deal saved = dealRepo.save(deal);

        // 7. Return DTO
        return DealResponseDto.builder()
                .id(saved.getId())
                .invoiceId(saved.getInvoice().getId())
                .purchaserAccountId(saved.getPurchaserAccountId())
                .status(String.valueOf(saved.getStatus()))
                .createdAt(saved.getCreatedAt())
                .transactionId(saved.getTransactionId())
                .build();

    }

    public Optional<Deal> findById(Long id) {
        return dealRepo.findById(id);
    }
}
