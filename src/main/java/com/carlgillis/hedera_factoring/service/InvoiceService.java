package com.carlgillis.hedera_factoring.service;

import com.carlgillis.hedera_factoring.domain.*;
import com.carlgillis.hedera_factoring.dto.InvoiceDto;
import com.carlgillis.hedera_factoring.dto.InvoiceResponseDto;
import com.carlgillis.hedera_factoring.repository.CustomerRepository;
import com.carlgillis.hedera_factoring.repository.DealRepository;
import com.carlgillis.hedera_factoring.repository.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepo;
    private final CustomerRepository customerRepo;
    private final   DealRepository dealRepo;

    public  InvoiceService(InvoiceRepository invoiceRepo, CustomerRepository customerRepo, DealRepository dealRepo) {
        this.invoiceRepo = invoiceRepo;
        this.customerRepo = customerRepo;
        this.dealRepo = dealRepo;
    }

    public List<InvoiceResponseDto> getAllInvoices() {
        return invoiceRepo.findAll().stream()
                .map(i -> InvoiceResponseDto.builder()
                        .id(i.getId())
                        .customerId(i.getCustomer().getId())
                        .amount(i.getAmount())
                        .currency(i.getCurrency())
                        .dueDate(LocalDate.parse(i.getDueDate()))
                        .build())
                .toList();


    }

    public InvoiceResponseDto getInvoiceById(Long id) {
        var i = invoiceRepo.findById(id).orElseThrow();
        return InvoiceResponseDto.builder()
                .id(i.getId())
                .customerId(i.getCustomer().getId())
                .amount(i.getAmount())
                .currency(i.getCurrency())
                .dueDate(LocalDate.parse(i.getDueDate()))
                .status(i.getStatus())
                .build();
    }

    public List<InvoiceResponseDto> getInvoicesByCustomer(Long customerId) {
        return invoiceRepo.findByCustomerId(customerId).stream()
                .map(inv -> InvoiceResponseDto.builder()
                        .id(inv.getId())
                        .customerId(inv.getCustomer().getId())
                        .amount(inv.getAmount())
                        .dueDate(LocalDate.parse(inv.getDueDate()))
                        .status(inv.getStatus())
                        .build()
                ).toList();

    }

    @Transactional
    public Invoice create(InvoiceDto dto) {
        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Invoice invoice = Invoice.builder()
                .customer(customer)
                .amount(dto.getAmount())
                .currency(dto.getCurrency())
                .dueDate(String.valueOf(dto.getDueDate()))
                .status(InvoiceStatus.OPEN)
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

    @Transactional
    public InvoiceResponseDto markAsPaid(Long invoiceId) {
        // 1. Find the invoice
        Invoice invoice = invoiceRepo.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        // 2. Update invoice status
        invoice.setStatus(InvoiceStatus.PAID);
        Invoice savedInvoice = invoiceRepo.save(invoice);

        // 3. Update all related deals to SETTLED
        List<Deal> deals = dealRepo.findByInvoiceId(invoiceId);
        deals.forEach(deal -> {
            deal.setStatus(DealStatus.SETTLED);
            dealRepo.save(deal);
        });

        return InvoiceResponseDto.builder()
                .id(savedInvoice.getId())
                .customerId(savedInvoice.getId())
                .amount(savedInvoice.getAmount())
                .currency(savedInvoice.getCurrency())
                .dueDate(LocalDate.parse(savedInvoice.getDueDate()))
                .status(savedInvoice.getStatus())
                .build();
    }
}
