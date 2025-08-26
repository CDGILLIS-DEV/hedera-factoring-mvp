package com.carlgillis.hedera_factoring.service;

import com.carlgillis.hedera_factoring.domain.Customer;
import com.carlgillis.hedera_factoring.domain.Invoice;
import com.carlgillis.hedera_factoring.domain.InvoiceStatus;
import com.carlgillis.hedera_factoring.dto.InvoiceDto;
import com.carlgillis.hedera_factoring.repository.CustomerRepository;
import com.carlgillis.hedera_factoring.repository.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepo;
    private final CustomerRepository customerRepo;

    public  InvoiceService(InvoiceRepository invoiceRepo, CustomerRepository customerRepo) {
        this.invoiceRepo = invoiceRepo;
        this.customerRepo = customerRepo;
    }
    @Transactional
    public Invoice create(InvoiceDto dto) {
        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Invoice invoice = Invoice.builder()
                .customer(customer)
                .amount(dto.getAmount())
                .currency(dto.getCurrency())
                .dueDate(dto.getDueDate())
                .status(InvoiceStatus.PENDING)
                .build();

        return invoiceRepo.save(invoice);
    }

    public Optional<Invoice> findById(Long id) {
        return invoiceRepo.findById(id);
    }

    public List<Invoice> listByCustomer(Long customerId) {
        return invoiceRepo.findByCustomerId(customerId);
    }

    public Invoice markFactored(Invoice invoice) {
        invoice.setStatus(InvoiceStatus.FACTORED);
        return invoiceRepo.save(invoice);
    }
}
