package com.example.demo.service;

import com.example.demo.model.Account_ledger_v3;
import com.example.demo.model.Account_transactions_v3;
import com.example.demo.model.Invoice;
import com.example.demo.model.Defaults;
import com.example.demo.repository.LedgerServiceRepo;
import com.example.demo.repository.InvoiceRepo;
import com.example.demo.repository.DefaultsRepo;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Standalone service for invoice payment postings (DR/CR).
 * Uses two fresh entities; does not touch legacy methods.
 */
@Service
public class InvoiceTransactionsService {

    // Cash ledger id constant
    private static final long CASH_LEDGER_ID = 30L;

    @PersistenceContext
    private EntityManager entityManager;

    private final LedgerServiceRepo ledgerServiceRepo;
    private final InvoiceRepo invoiceRepo;
    private final DefaultsRepo defaultsRepo;;


    public InvoiceTransactionsService(LedgerServiceRepo ledgerServiceRepo, InvoiceRepo invoiceRepo, DefaultsRepo defaultsRepo) {
        this.ledgerServiceRepo = ledgerServiceRepo;
        this.invoiceRepo = invoiceRepo;
        this.defaultsRepo = defaultsRepo;
    }

    @Transactional
    public void invoicePaymentReceive(
            Long invoiceId,
            Long debitLedgerId,
            Long creditLedgerId,
            BigDecimal amount,
            LocalDate date,
            String reference,
            String notes,
            String createdBy,
            String company,
            String branch
    ) {
        invoicePaymentReceive(
                invoiceId,
                debitLedgerId,
                creditLedgerId,
                amount,
                date,
                reference,
                notes,
                createdBy,
                company,
                branch,
                null
        );
    }

    @Transactional
    public void invoicePaymentReceive(
            Long invoiceId,
            Long debitLedgerId,
            Long creditLedgerId,
            BigDecimal amount,
            LocalDate date,
            String reference,
            String notes,
            String createdBy,
            String company,
            String branch,
            String mode
    ) {
        // --- Resolve CREDIT ledger (customer) ---
       DefaultsRepo
       
        Account_ledger_v3 creditLedger;
        Long resolvedCreditLedgerId = creditLedgerId;

        if (resolvedCreditLedgerId == null) {
            // Derive customer ledger from invoice
            if (invoiceId == null) {
                throw new IllegalArgumentException("invoice id is required to resolve customer (credit) ledger");
            }

            // If InvoiceRepo<Invoice, Integer>, convert Long -> Integer
            Invoice invoice = invoiceRepo.findById(Math.toIntExact(invoiceId))
                    .orElseThrow(() -> new IllegalArgumentException("Invoice not found: " + invoiceId));

            String custName = invoice.getCust_name();
            if (custName == null || custName.isBlank()) {
                throw new IllegalArgumentException("Invoice " + invoiceId + " has no customer name");
            }

            // Assumes this method exists in LedgerServiceRepo
            List<Account_ledger_v3> customerLedgers = ledgerServiceRepo.ledger_name_search(custName);
            if (customerLedgers == null || customerLedgers.isEmpty()) {
                throw new IllegalArgumentException("No ledger found for customer: " + custName);
            }

            creditLedger = customerLedgers.get(0);
            // Assumes Account_ledger_v3 has getId()
            resolvedCreditLedgerId = Long.valueOf(creditLedger.getId());
        } else {
            creditLedger = requireLedger(resolvedCreditLedgerId, "credit");
        }

        // --- Resolve DEBIT ledger (cash / bank) ---

        Account_ledger_v3 debitLedger;
        String debitAccountId;

        String normalizedMode = mode != null ? mode.toUpperCase() : "";

        if ("CASH".equals(normalizedMode)) {
            debitLedger = requireLedger(CASH_LEDGER_ID, "debit");
            debitAccountId = String.valueOf(CASH_LEDGER_ID);
        } else if ("BANK".equals(normalizedMode)) {
            debitLedger = requireLedger(debitLedgerId, "debit");
            debitAccountId = (debitLedgerId != null) ? debitLedgerId.toString() : null;
        } else {
            throw new IllegalArgumentException("Unsupported payment mode: " + mode);
        }

        // --- Common transaction fields ---

        LocalDate txnDate = (date != null) ? date : LocalDate.now();
        LocalDate createdDate = LocalDate.now();
        LocalTime createdTime = LocalTime.now();

        String amountStr = (amount != null)
                ? amount.toPlainString()
                : BigDecimal.ZERO.toPlainString();

        String filepath = (invoiceId != null) ? "/invoices/" + invoiceId : null;
        String description = resolveDescription(invoiceId, reference, notes);

        // --- Build transaction entity ---

        Account_transactions_v3 tx = new Account_transactions_v3();
        tx.setCredit_blnc_bfore_txn(creditLedger.getAmount());
        tx.setDebit_blnc_bfore_txn(debitLedger.getAmount());

        tx.setCrdt_ac(String.valueOf(resolvedCreditLedgerId));
        tx.setDbt_ac(debitAccountId);

        tx.setMode(mode != null ? mode : "");
        tx.setAmount(amountStr);
        tx.setType("INVOICE_PAYMENT");
        tx.setTran_gen("SYSTEM");
        tx.setTran_Date(txnDate.toString());
        tx.setDescription(description);
        tx.setStatus("COMPLETED");
        tx.setCreatedBy(createdBy);
        tx.setCreatedDate(createdDate.toString());
        tx.setCreatedTime(createdTime.toString());
        tx.setFilepath(filepath);
        tx.setFilename(reference);
        tx.setBranch(branch);
        tx.setBank(company);
        tx.setTransactionID(invoiceId != null ? invoiceId.toString() : null);

        entityManager.persist(tx);
    }

    private Account_ledger_v3 requireLedger(Long ledgerId, String role) {
        if (ledgerId == null) {
            throw new IllegalArgumentException(role + " ledger id is required");
        }
        List<Account_ledger_v3> found = ledgerServiceRepo.ledger_Search(Math.toIntExact(ledgerId));
        if (found == null || found.isEmpty()) {
            throw new IllegalArgumentException(role + " ledger not found: " + ledgerId);
        }
        return found.get(0);
    }

    // Overload to allow passing the constant CASH_LEDGER_ID as long
    private Account_ledger_v3 requireLedger(long ledgerId, String role) {
        return requireLedger(Long.valueOf(ledgerId), role);
    }

    private static String resolveDescription(Long invoiceId, String reference, String notes) {
        if (notes != null && !notes.isBlank()) {
            return notes;
        }
        if (reference != null && !reference.isBlank()) {
            return reference;
        }
        return (invoiceId != null) ? "Invoice " + invoiceId : "Invoice payment";
    }
}
