package com.example.demo.controller;

import com.example.demo.api.dto.InvoicePayment;
import com.example.demo.validation.SchemaRegistry;
import com.example.demo.service.InvoiceTransactionsService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.ValidationMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private static final Logger log = LoggerFactory.getLogger(InvoicePaymentApi.class);

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

        // 🔹 Log incoming request JSON
        try {
            log.info("REQUEST JSON: {}", mapper.writeValueAsString(body));
        } catch (Exception e) {
            log.error("Failed to log request JSON", e);
        }

        // 🔹 JSON schema validation
        JsonNode node = mapper.valueToTree(body);
        Set<ValidationMessage> errors = registry.get("InvoicePayment").validate(node);
        if (!errors.isEmpty()) {
            log.warn("Validation errors: {}", errors);
            return ResponseEntity
                    .badRequest()
                    .body(errors.toString());
        }

        // --- Resolve debit / credit ledger IDs for the service ---

        // Debit ledger:
        //   - If mode = BANK  → use bank_ledger_id from request
        //   - If mode = CASH  → pass null so service uses default cash ledger from Defaults
        Long debitLedgerId =
            "BANK".equalsIgnoreCase(body.mode) ? body.bank_ledger_id : null;

        // Credit ledger:
        //   - Let the service resolve it based on the invoice's customer or default income account.
        Long creditLedgerId = null;

        // --- Call service layer ---
        // NOTE: amount is taken directly from the request body (body.amount).
        invoiceTransactionsService.invoicePaymentReceive(
            body.inv_id,
            debitLedgerId,
            creditLedgerId,
            body.amount,
            body.date != null ? LocalDate.parse(body.date) : null,
            body.reference,
            body.notes,
            "web",          // createdBy / source
            "default",      // company (adjust if you use something else)
            "main",         // branch  (adjust if you use something else)
            body.mode
        );

        // 🔹 Build response JSON to log (even though response body is empty)
        try {
            JsonNode responseJson = mapper.createObjectNode()
                .put("status", "ok")
                .put("path", "/api/v1/invoice-payments");
            log.info("RESPONSE JSON: {}", mapper.writeValueAsString(responseJson));
        } catch (Exception e) {
            log.error("Failed to log response JSON", e);
        }

        // You’re currently returning empty 200 OK (matches your logs)
        return ResponseEntity.ok().build();
    }
}
