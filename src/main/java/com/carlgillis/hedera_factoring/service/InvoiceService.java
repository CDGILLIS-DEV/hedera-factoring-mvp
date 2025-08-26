package com.carlgillis.hedera_factoring.service;

import com.carlgillis.hedera_factoring.domain.Customer;
import com.carlgillis.hedera_factoring.domain.Invoice;
import com.carlgillis.hedera_factoring.domain.InvoiceStatus;
import com.carlgillis.hedera_factoring.repository.CustomerRepository;
import com.carlgillis.hedera_factoring.repository.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class InvoiceService {
    private final InvoiceRepository invoiceRepo;
    private final CustomerRepository customerRepo;

    public  InvoiceService(InvoiceRepository invoiceRepo, CustomerRepository customerRepo) {
        this.invoiceRepo = invoiceRepo;
        this.customerRepo = customerRepo;
    }

    public Invoice create(Invoice invoice, Long customerId) {
        Customer cust = customerRepo.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: "  + customerId));
        invoice.setCustomer(cust);
        invoice.setCreatedAt(Instant.now());
        invoice.setStatus(InvoiceStatus.PENDING);
        return invoiceRepo.save(invoice);
    }

    public Invoice findById(Long id) {
        return invoiceRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invoice not found: " + id));
    }

    public List<Invoice> listByCustomer(Long customerId) {
        return invoiceRepo.findByCustomerId(customerId);
    }

    public Invoice markFactored(Invoice invoice) {
        invoice.setStatus(InvoiceStatus.FACTORED);
        return invoiceRepo.save(invoice);
    }
}
