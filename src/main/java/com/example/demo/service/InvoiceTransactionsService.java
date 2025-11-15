package com.example.demo.service;

import com.example.demo.model.Account_ledger_v3;
import com.example.demo.model.Account_transactions_v3;
import com.example.demo.repository.LedgerServiceRepo;
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

    @PersistenceContext
    private EntityManager entityManager;

    private final LedgerServiceRepo ledgerServiceRepo;

    public InvoiceTransactionsService(LedgerServiceRepo ledgerServiceRepo) {
        this.ledgerServiceRepo = ledgerServiceRepo;
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
        Account_ledger_v3 creditLedger = requireLedger(creditLedgerId, "credit");
        Account_ledger_v3 debitLedger = requireLedger(debitLedgerId, "debit");

        LocalDate txnDate = date != null ? date : LocalDate.now();
        LocalDate createdDate = LocalDate.now();
        LocalTime createdTime = LocalTime.now();
        String amountStr = amount != null ? amount.toPlainString() : BigDecimal.ZERO.toPlainString();
        String filepath = invoiceId != null ? "/invoices/" + invoiceId : null;
        String description = resolveDescription(invoiceId, reference, notes);

        Account_transactions_v3 tx = new Account_transactions_v3();
        tx.setCredit_blnc_bfore_txn(creditLedger.getAmount());
        tx.setDebit_blnc_bfore_txn(debitLedger.getAmount());
        tx.setCrdt_ac(String.valueOf(creditLedgerId));
        tx.setDbt_ac(String.valueOf(debitLedgerId));
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

    private static String resolveDescription(Long invoiceId, String reference, String notes) {
        if (notes != null && !notes.isBlank()) {
            return notes;
        }
        if (reference != null && !reference.isBlank()) {
            return reference;
        }
        return invoiceId != null ? "Invoice " + invoiceId : "Invoice payment";
    }
}
