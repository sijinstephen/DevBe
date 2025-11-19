package com.example.demo.service;

import com.example.demo.model.Account_ledger_v3;
import com.example.demo.model.Account_transactions_v3;
import com.example.demo.model.Invoice;
import com.example.demo.model.Defaults;
import com.example.demo.repository.LedgerServiceRepo;
import com.example.demo.repository.InvoiceRepo;
import com.example.demo.repository.DefaultsRepo;
import com.example.demo.repository.TransactionServiceRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Standalone service for invoice payment postings (DR/CR).
 * Uses two fresh entities; does not touch legacy methods.
 */
@Service
public class InvoiceTransactionsService {

    // ✅ Fallback cash ledger id if defaults table not configured
    private static final long DEFAULT_CASH_LEDGER_ID_FALLBACK = 30L;
    private static final Logger logger = LoggerFactory.getLogger(InvoiceTransactionsService.class);

    @PersistenceContext
    private EntityManager entityManager;
    private final LedgerServiceRepo ledgerServiceRepo;
    private final InvoiceRepo invoiceRepo;
    private final DefaultsRepo defaultsRepo;
    private final TransactionServiceRepo transactionServiceRepo;

    public InvoiceTransactionsService(LedgerServiceRepo ledgerServiceRepo,
                                      InvoiceRepo invoiceRepo,
                                      DefaultsRepo defaultsRepo,
                                      TransactionServiceRepo transactionServiceRepo) {
        this.ledgerServiceRepo = ledgerServiceRepo;
        this.invoiceRepo = invoiceRepo;
        this.defaultsRepo = defaultsRepo;
        this.transactionServiceRepo = transactionServiceRepo;
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
        // --- Resolve CREDIT ledger (customer or default income) ---

        Account_ledger_v3 creditLedger;
        Long resolvedCreditLedgerId = creditLedgerId;
        Invoice invoice = null;

        if (resolvedCreditLedgerId == null) {
            if (invoiceId == null) {
                throw new IllegalArgumentException("invoice id is required to resolve credit ledger");
            }

            // InvoiceRepo<Invoice, Integer> → convert Long -> Integer
            invoice = invoiceRepo.findById(invoiceId.intValue())
                    .orElseThrow(() -> new IllegalArgumentException("Invoice not found: " + invoiceId));

            String custName = invoice.getCust_name();

            if (custName == null || custName.isBlank()) {
                // 🔹 Customer is blank → use default income account as credit ledger

                Defaults defaults = null;
                for (Defaults d : defaultsRepo.findAll()) {
                    defaults = d;
                    break;
                }

                if (defaults == null) {
                    throw new IllegalStateException("No defaults row found in database");
                }

                String defaultIncomeAccount = defaults.getDefault_income_account();
                if (defaultIncomeAccount == null || defaultIncomeAccount.isBlank()) {
                    throw new IllegalStateException("Default income account is not configured");
                }

                long incomeLedgerId;
                try {
                    incomeLedgerId = Long.parseLong(defaultIncomeAccount);
                } catch (NumberFormatException ex) {
                    throw new IllegalStateException(
                            "Default income account is not a valid ledger id: " + defaultIncomeAccount, ex);
                }

                creditLedger = requireLedger(incomeLedgerId, "credit");
                resolvedCreditLedgerId = incomeLedgerId;

            } else {
                // 🔹 Customer present → use customer ledger as credit
                List<Account_ledger_v3> customerLedgers = ledgerServiceRepo.ledger_name_search(custName);
                if (customerLedgers == null || customerLedgers.isEmpty()) {
                    throw new IllegalArgumentException("No ledger found for customer: " + custName);
                }

                creditLedger = customerLedgers.get(0);
                resolvedCreditLedgerId = Long.valueOf(creditLedger.getId());
            }

        } else {
            creditLedger = requireLedger(resolvedCreditLedgerId, "credit");
        }

        // --- Resolve DEBIT ledger (cash / bank) ---

        Account_ledger_v3 debitLedger;
        String debitAccountId;

        String normalizedMode = mode != null ? mode.toUpperCase() : "";

        if ("CASH".equals(normalizedMode)) {
            long cashLedgerId = resolveCashLedgerId();   // ✅ now dynamic
            debitLedger = requireLedger(cashLedgerId, "debit");
            debitAccountId = String.valueOf(cashLedgerId);
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

     //   String filepath = (invoiceId != null) ? "/invoices/" + invoiceId : null;
        String description = resolveDescription(invoiceId, reference, notes);

        // --- Build transaction entity ---

        Account_transactions_v3 tx = new Account_transactions_v3();
        tx.setCredit_blnc_bfore_txn(creditLedger.getAmount());
        tx.setDebit_blnc_bfore_txn(debitLedger.getAmount());

        tx.setCrdt_ac(String.valueOf(resolvedCreditLedgerId));
        tx.setDbt_ac(debitAccountId);
        tx.setTranID(nextTranId()); 
        tx.setMode(mode != null ? mode : "");
        tx.setAmount(amountStr);
        tx.setType("Journal");
        tx.setTran_gen("SYSTEM");
        tx.setTran_Date(txnDate.toString());
        tx.setDescription(description);
        tx.setStatus("paid");
        tx.setCreatedBy(createdBy);
        tx.setCreatedDate(createdDate.toString());
        tx.setCreatedTime(createdTime.toString());
        tx.setFilepath("Nil");
        tx.setFilename("Nil");
        tx.setBranch(branch);
        tx.setBank(company);
        tx.setTransactionID(invoiceId != null ? invoiceId.toString() : null);

        entityManager.persist(tx);

        // --- Update invoice received amount and status ---
        update_invoice_payment(invoice,amount);
    }

/**
 * Generate the next tranID manually because account_transactions_v3.tranid
 * is NOT auto-increment and has no primary key.
 * This avoids multiple rows with tranid = 0 which confuse JPA/Hibernate.
 *
 * This implementation queries the max(tranid) directly via a native query
 * so we don't depend on a repository method that may not exist.
 */
private int nextTranId() {
    try {
        Object result = entityManager.createNativeQuery("SELECT MAX(tranid) FROM account_transactions_v3")
                .getSingleResult();
        if (result == null) {
            return 1;
        }

        long max;
        if (result instanceof Number) {
            max = ((Number) result).longValue();
        } else {
            // fallback parsing if the JDBC driver returns a string
            max = new java.math.BigDecimal(result.toString()).longValue();
        }

        if (max < 0) {
            return 1;
        }
        long next = max + 1;
        // clamp to int range to match return type
        if (next > Integer.MAX_VALUE) {
            throw new IllegalStateException("tranid overflow");
        }
        return (int) next;
    } catch (Exception ex) {
        // safest fallback
        return 1;
    }
}


    // ✅ Resolve cash ledger id from defaults, fallback to 30
    private long resolveCashLedgerId() {
        Defaults defaults = null;
        for (Defaults d : defaultsRepo.findAll()) {
            defaults = d;
            break;
        }

        if (defaults != null) {
            String cash = defaults.getDefault_cash_account();
            if (cash != null && !cash.isBlank()) {
                try {
                    return Long.parseLong(cash);
                } catch (NumberFormatException ex) {
                    // fall through to fallback
                }
            }
        }

        // Fallback if no defaults or invalid config
        return DEFAULT_CASH_LEDGER_ID_FALLBACK;
    }

    private Account_ledger_v3 requireLedger(Long ledgerId, String role) {
        if (ledgerId == null) {
            throw new IllegalArgumentException(role + " ledger id is required");
        }
        List<Account_ledger_v3> found = ledgerServiceRepo.ledger_Search(ledgerId.intValue());
        if (found == null || found.isEmpty()) {
            throw new IllegalArgumentException(role + " ledger not found: " + ledgerId);
        }
        return found.get(0);
    }

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
    private void update_invoice_payment(Invoice invoice,BigDecimal paid_amount)
    {
        logger.info("Receiving invoice payment: {}, paid_amount={}", invoice, paid_amount);

        if (invoice == null) {
            throw new IllegalArgumentException("invoice is required");
        }

        // parse existing received amount safely
        BigDecimal existing = BigDecimal.ZERO;
        String existingStr = invoice.getAmount_received();
        if (existingStr != null && !existingStr.isBlank()) {
            try {
                existing = new BigDecimal(existingStr);
            } catch (NumberFormatException ex) {
                existing = BigDecimal.ZERO;
            }
        }

        BigDecimal paid = (paid_amount != null) ? paid_amount : BigDecimal.ZERO;
        BigDecimal totalReceived = existing.add(paid);

        invoice.setAmount_received(totalReceived.toPlainString());

        // compare with total amount (also parsed safely)
        String totalAmountStr = invoice.getTotal_amount();
        if (totalAmountStr != null && !totalAmountStr.isBlank()) {
            try {
                BigDecimal totalAmount = new BigDecimal(totalAmountStr);
                if (totalReceived.compareTo(totalAmount) >= 0) {
                    invoice.setStatus("paid");
                } else {
                    invoice.setStatus("partially_paid");
                }
            } catch (NumberFormatException ex) {
                invoice.setStatus("partially_paid");
            }
        } else {
            invoice.setStatus("partially_paid");
        }

        invoiceRepo.save(invoice);
    }
}
