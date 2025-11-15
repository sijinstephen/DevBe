package com.example.demo.controller;

import com.example.demo.api.dto.InvoicePayment;
import com.example.demo.validation.SchemaRegistry;
import com.example.demo.service.InvoiceTransactionsService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.ValidationMessage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(
    origins = "http://localhost:5173",
    allowedHeaders = "*",
    methods = { RequestMethod.POST, RequestMethod.OPTIONS }
)
public class InvoicePaymentApi {

    private final ObjectMapper mapper;
    private final SchemaRegistry registry;
    private final InvoiceTransactionsService invoiceTransactionsService;

    public InvoicePaymentApi(ObjectMapper mapper,
                             SchemaRegistry registry,
                             InvoiceTransactionsService invoiceTransactionsService) {
        this.mapper = mapper;
        this.registry = registry;
        this.invoiceTransactionsService = invoiceTransactionsService;
    }

    @PostMapping(value = "/invoice-payments", consumes = "application/json")
    public ResponseEntity<?> receive(@RequestBody InvoicePayment body) {

        JsonNode node = mapper.valueToTree(body);
        Set<ValidationMessage> errors = registry.get("InvoicePayment").validate(node);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors.toString());
        }

        Long debitLedgerId =
            "BANK".equalsIgnoreCase(body.mode) ? body.bank_ledger_id : /* TODO: cash ledger id */ null;
        Long creditLedgerId = /* TODO: customer ledger id */ null;

        invoiceTransactionsService.invoicePaymentReceive(
            body.inv_id,
            debitLedgerId,
            creditLedgerId,
            body.amount,
            body.date != null ? LocalDate.parse(body.date) : null,
            body.reference,
            body.notes,
            "web",
            "default",
            "main",
            body.mode
        );

        return ResponseEntity.ok().build();
    }
}
