package com.carlgillis.hedera_factoring.service;

import com.carlgillis.hedera_factoring.domain.Deal;
import com.carlgillis.hedera_factoring.domain.DealStatus;
import com.carlgillis.hedera_factoring.domain.Invoice;
import com.carlgillis.hedera_factoring.domain.InvoiceStatus;
import com.carlgillis.hedera_factoring.repository.DealRepository;
import com.carlgillis.hedera_factoring.repository.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
public class DealService {
    private final DealRepository dealRepo;
    private final InvoiceRepository invoiceRepo;
    private final InvoiceService invoiceService;

    public DealService(DealRepository dealRepo, InvoiceRepository invoiceRepo, InvoiceService invoiceService) {
        this.dealRepo = dealRepo;
        this.invoiceRepo = invoiceRepo;
        this.invoiceService = invoiceService;
    }

    /**
     * Initiates a deal: validates invoice available, creates Deal, and marks invoice as factored.
     * For MVP, we do not perform on-chain settlement here; that will be added once contract/wallet flows exist.
     */
    public Deal initiateDeal(Long invoiceId, Deal dealTemplate) {
        Invoice invoice = invoiceRepo.findById(invoiceId)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found: " + invoiceId));

        if (invoice.getStatus() != InvoiceStatus.PENDING) {
            throw new IllegalStateException("Invoice not available for factoring: " + invoice.getStatus());
        }

        // create deal
        dealTemplate.setInvoice(invoice);
        dealTemplate.setCreatedAt(Instant.now());
        dealTemplate.setStatus(DealStatus.INITIATED);
        Deal saved = dealRepo.save(dealTemplate);

        // mark invoice
        invoice.setStatus(InvoiceStatus.FACTORED);
        invoiceRepo.save(invoice);

        return saved;
    }

    public Deal findById(Long id) {
        return dealRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Deal not found: " + id));
    }
}
