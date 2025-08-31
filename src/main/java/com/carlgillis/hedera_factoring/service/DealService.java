package com.carlgillis.hedera_factoring.service;

import com.carlgillis.hedera_factoring.domain.Deal;
import com.carlgillis.hedera_factoring.domain.DealStatus;
import com.carlgillis.hedera_factoring.domain.Invoice;
import com.carlgillis.hedera_factoring.domain.InvoiceStatus;
import com.carlgillis.hedera_factoring.dto.CustomerResponseDto;
import com.carlgillis.hedera_factoring.dto.DealDto;
import com.carlgillis.hedera_factoring.dto.DealResponseDto;
import com.carlgillis.hedera_factoring.repository.DealRepository;
import com.carlgillis.hedera_factoring.repository.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class DealService {
    private final DealRepository dealRepo;
    private final InvoiceRepository invoiceRepo;

    public DealService(DealRepository dealRepo, InvoiceRepository invoiceRepo) {
        this.dealRepo = dealRepo;
        this.invoiceRepo = invoiceRepo;
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
    public Deal create(DealDto dto) {
        Invoice invoice = invoiceRepo.findById(dto.getInvoiceId())
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        if (invoice.getStatus() != InvoiceStatus.PENDING) {
            throw new RuntimeException("Invoice not available for factoring");
        }

        // create deal
        Deal deal = Deal.builder()
                .invoice(invoice)
                .purchaserAccountId(dto.getPurchaserAccountId())
                .purchasePrice(dto.getPurchasePrice())
                .status(DealStatus.INITIATED)
                .build();

        return dealRepo.save(deal);
    }

    public Optional<Deal> findById(Long id) {
        return dealRepo.findById(id);
    }
}
