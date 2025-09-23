package com.example.demo.service;

import com.example.demo.model.Account_group_v3;
import com.example.demo.model.Account_ledger_v3;
import com.example.demo.model.Account_title_v3;
import com.example.demo.model.Account_transactions_v3;
import com.example.demo.repository.AcTitleRepo;
import com.example.demo.repository.GroupServiceRepo;
import com.example.demo.repository.LedgerServiceRepo;
import com.example.demo.repository.TransactionServiceRepo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Readability-first refactor:
 * - Constructor injection + SLF4J logging (replaces System.out.println)
 * - Helpers for safe parsing (numbers, ints), null/default handling
 * - Centralized constants for magic strings (e.g., "30", "Nil", "ledger creation")
 * - Keeps the original method signatures and side-effects (including the "overloaded fields" convention)
 */
@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    // Common constants
    private static final String CASH_LEDGER_ID = "30";
    private static final String NIL = "Nil";
    private static final String DESC_LEDGER_CREATION = "ledger creation";

    private final TransactionServiceRepo transactionServiceRepo;
    private final LedgerService ledgerService;            // used in a few places
    private final LedgerServiceRepo ledgerServiceRepo;
    private final GroupServiceRepo groupServiceRepo;
    private final AcTitleRepo acTitleRepo;

    public TransactionService(TransactionServiceRepo transactionServiceRepo,
                              LedgerService ledgerService,
                              LedgerServiceRepo ledgerServiceRepo,
                              GroupServiceRepo groupServiceRepo,
                              AcTitleRepo acTitleRepo) {
        this.transactionServiceRepo = transactionServiceRepo;
        this.ledgerService = ledgerService;
        this.ledgerServiceRepo = ledgerServiceRepo;
        this.groupServiceRepo = groupServiceRepo;
        this.acTitleRepo = acTitleRepo;
    }

    // ---------------------------------------------------------------------
    // Small helpers
    // ---------------------------------------------------------------------

    private static double parseNum(String s) {
        if (s == null) return 0d;
        String t = s.trim();
        if (t.isEmpty()) return 0d;
        if (NIL.equalsIgnoreCase(t) || "-".equals(t)) return 0d;
        try { return Double.parseDouble(t.replace(",", "")); } catch (NumberFormatException e) { return 0d; }
    }

    private static float parseNumF(String s) {
        return (float) parseNum(s);
    }

    private static double nz(Double v) { return v == null ? 0d : v; }
    private static String ds(double v) { return Double.toString(v); }
    private static String fs(float v)  { return Float.toString(v);  }

    private static int toInt(String s, int fallback) {
        try { return Integer.parseInt(s); } catch (Exception e) { return fallback; }
    }

    private static boolean isEmpty(String s) { return s == null || s.trim().isEmpty(); }

    private String ledgerNameById(int id) {
        List<Account_ledger_v3> ls = ledgerServiceRepo.getLedgers(id);
        return (ls != null && !ls.isEmpty()) ? ls.get(0).getLedger_name() : "";
    }

    // ---------------------------------------------------------------------
    // Create / Update
    // ---------------------------------------------------------------------

    public Account_transactions_v3 add_Transactions(Account_transactions_v3 fp) {
        transactionServiceRepo.save(fp);
        // update_Transactions(fp); // kept commented to preserve original behavior
        return null;
    }

    public void update_Transactions(Account_transactions_v3 fp) {
        log.debug("update_Transactions: crdt={} dbt={}", fp.getCrdt_ac(), fp.getDbt_ac());

        List<Account_ledger_v3> creditor = getLedgerID(fp.getCrdt_ac());
        List<Account_ledger_v3> debitor  = getLedgerID(fp.getDbt_ac());

        String crType = null;
        for (Account_ledger_v3 l : creditor) { crType = l.getAc_type(); break; }
        String dbType = null;
        for (Account_ledger_v3 l : debitor)  { dbType = l.getAc_type(); break; }

        String createDate = fp.getCreatedDate();
        String createTime = fp.getCreatedTime();

        if ("2".equals(dbType) || "3".equals(dbType)) {
            increaseAmount(fp.getDbt_ac(), fp.getAmount(), createDate, createTime);
        } else {
            decreaseAmount(fp.getDbt_ac(), fp.getAmount(), createDate, createTime);
        }

        if ("2".equals(crType) || "3".equals(crType)) {
            increaseAmount(fp.getCrdt_ac(), fp.getAmount(), createDate, createTime);
        } else {
            decreaseAmount(fp.getCrdt_ac(), fp.getAmount(), createDate, createTime);
        }
    }

    // ---------------------------------------------------------------------
    // Lookups
    // ---------------------------------------------------------------------

    public List<Account_transactions_v3> transactionDates() {
        return transactionServiceRepo.transactionDate();
    }

    public List<Account_ledger_v3> getLedgerID(String ledgerIdStr) {
        return ledgerServiceRepo.ledger(ledgerIdStr);
    }

    public ResponseEntity<Object> increaseAmount(String ledgerID, String ledgerAmount, String createdDate, String time) {
        List<Account_ledger_v3> ledger = getLedgerID(ledgerID);
        int currentAmount = 0;
        int updatedAmount = 0;

        for (Account_ledger_v3 ob : ledger) {
            currentAmount = isEmpty(ob.getAmount()) ? 0 : toInt(ob.getAmount(), 0);
            log.debug("increaseAmount currentAmount={}", currentAmount);
            updatedAmount = currentAmount + toInt(ledgerAmount, 0);
            ob.setAmount(Integer.toString(updatedAmount));
        }

        log.debug("increaseAmount updatedAmount={} ledgerID={}", updatedAmount, ledgerID);
        // NOTE: persisting was commented in original code; left as-is to preserve behavior
        // ledgerServiceRepo.saveAll(ledger);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Object> decreaseAmount(String ledgerID, String ledgerAmount, String createdDate, String time) {
        List<Account_ledger_v3> ledger = getLedgerID(ledgerID);
        int currentAmount = 0;
        int updatedAmount = 0;

        for (Account_ledger_v3 ob : ledger) {
            currentAmount = isEmpty(ob.getAmount()) ? 0 : toInt(ob.getAmount(), 0);
            log.debug("decreaseAmount currentAmount={}", currentAmount);
            updatedAmount = currentAmount - toInt(ledgerAmount, 0);
            ob.setAmount(Integer.toString(updatedAmount));
        }

        log.debug("decreaseAmount updatedAmount={} ledgerID={}", updatedAmount, ledgerID);
        // NOTE: persisting was commented in original code; left as-is to preserve behavior
        // ledgerServiceRepo.saveAll(ledger);
        return ResponseEntity.noContent().build();
    }

    // ---------------------------------------------------------------------
    // Payments / Transactions list
    // ---------------------------------------------------------------------

    public List<Account_transactions_v3> list_transactions() {
        List<Account_transactions_v3> li = transactionServiceRepo.selectData();
        for (Account_transactions_v3 t : li) {
            t.setTran_gen(t.getDbt_ac()); // store original dbt id
            String ledgerName = ledgerServiceRepo.getLedger(toInt(t.getDbt_ac(), 0));
            t.setDbt_ac(ledgerName);
        }
        return li;
    }

    public List<Account_transactions_v3> transaction_sorts(String field, String type) {
        log.debug("transaction_sorts {} {}", field, type);
        List<Account_transactions_v3> li = null;

        if ("tran_Date".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.tran_DateA()
                                              : transactionServiceRepo.tran_DateD();
        } else if ("dbt_ac".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.dbt_acA()
                                              : transactionServiceRepo.dbt_acD();
        } else if ("mode".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.modeA()
                                              : transactionServiceRepo.modeD();
        } else if ("amount".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.amountA()
                                              : transactionServiceRepo.amountD();
        } else if ("description".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.descriptionA()
                                              : transactionServiceRepo.descriptionD();
        }

        for (Account_transactions_v3 t : li) {
            String ledgerName = ledgerServiceRepo.getLedger(toInt(t.getDbt_ac(), 0));
            t.setDbt_ac(ledgerName);
        }
        return li;
    }

    public List<Account_transactions_v3> payment_bn_dates(String start, String end) {
        log.debug("payment_bn_dates {}..{}", start, end);
        List<Account_transactions_v3> li = transactionServiceRepo.selectDataBnDate(start, end);
        for (Account_transactions_v3 t : li) {
            String ledgerName = ledgerServiceRepo.getLedger(toInt(t.getDbt_ac(), 0));
            t.setDbt_ac(ledgerName);
        }
        return li;
    }

    public List<Account_transactions_v3> transaction_searchs(String transactionId) {
        log.debug("transaction_searchs {}", transactionId);
        List<Account_transactions_v3> li = transactionServiceRepo.transaction_Search(transactionId);
        for (Account_transactions_v3 t : li) {
            t.setDebit_blnc_bfore_txn(t.getDbt_ac());
            t.setCredit_blnc_bfore_txn(t.getCrdt_ac());

            List<Account_ledger_v3> ledgerlist = ledgerServiceRepo.getLedgers(toInt(t.getDbt_ac(), 0));
            for (Account_ledger_v3 l : ledgerlist) {
                t.setStatus(t.getFilepath());
                t.setDbt_ac(l.getLedger_name());
                t.setTran_gen(l.getPin());
                t.setCreatedDate(l.getContact());
                t.setCreatedTime(l.getFax());
                t.setFilename(l.getState());
                t.setFilepath(l.getAddress());
                break;
            }
        }
        return li;
    }

    public Account_transactions_v3 add_payments(Account_transactions_v3 fp) {
        return transactionServiceRepo.save(fp);
    }

    public String payment_deletes(int id) {
        transactionServiceRepo.deleteById(id);
        return "Deleted successfully";
    }

    // ---------------------------------------------------------------------
    // Journal
    // ---------------------------------------------------------------------

    public Account_transactions_v3 add_journalTransactions(Account_transactions_v3 fp) {
        transactionServiceRepo.save(fp);
        return null;
    }

    public List<Account_transactions_v3> list_journals() {
        List<Account_transactions_v3> li = transactionServiceRepo.selectDataJournal();
        for (Account_transactions_v3 t : li) {
            t.setCreatedTime(t.getDbt_ac());
            String debit = ledgerServiceRepo.getLedger(toInt(t.getDbt_ac(), 0));
            String credit = ledgerServiceRepo.getLedger(toInt(t.getCrdt_ac(), 0));
            t.setDbt_ac(debit);
            t.setCrdt_ac(credit);
        }
        return li;
    }

    public List<Account_transactions_v3> journal_sorts(String field, String type) {
        log.debug("journal_sorts {} {}", field, type);
        List<Account_transactions_v3> li = null;

        if ("tran_Date".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.tran_Date_journalA()
                                              : transactionServiceRepo.tran_Date_journalD();
        } else if ("dbt_ac".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.dbt_ac_journalA()
                                              : transactionServiceRepo.dbt_ac_journalD();
        } else if ("crdt_ac".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.crdt_ac_journalA()
                                              : transactionServiceRepo.crdt_ac_journalD();
        } else if ("amount".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.amount_journalA()
                                              : transactionServiceRepo.amount_journalD();
        } else if ("description".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.description_journalA()
                                              : transactionServiceRepo.description_journalD();
        }

        for (Account_transactions_v3 t : li) {
            t.setCreatedTime(t.getDbt_ac());
            String debit = ledgerServiceRepo.getLedger(toInt(t.getDbt_ac(), 0));
            String credit = ledgerServiceRepo.getLedger(toInt(t.getCrdt_ac(), 0));
            t.setDbt_ac(debit);
            t.setCrdt_ac(credit);
        }
        return li;
    }

    public List<Account_transactions_v3> journal_bn_dates(String start, String end) {
        log.debug("journal_bn_dates {}..{}", start, end);
        List<Account_transactions_v3> li = transactionServiceRepo.selectJournalDataBnDate(start, end);
        for (Account_transactions_v3 t : li) {
            t.setCreatedTime(t.getDbt_ac());
            String debit = ledgerServiceRepo.getLedger(toInt(t.getDbt_ac(), 0));
            String credit = ledgerServiceRepo.getLedger(toInt(t.getCrdt_ac(), 0));
            t.setDbt_ac(debit);
            t.setCrdt_ac(credit);
        }
        return li;
    }

    public String journal_deletes(int id) {
        transactionServiceRepo.deleteById(id);
        return "Deleted successfully";
    }

    public List<Account_transactions_v3> journal_searchs(String tranId) {
        log.debug("journal_searchs {}", tranId);
        return transactionServiceRepo.journal_Search(tranId);
    }

    public List<Account_transactions_v3> journal_searchInvoices(String tranId, String creditAc, String debitAc) {
        log.debug("journal_searchInvoices tran_gen={} credit={} debit={}", tranId, creditAc, debitAc);
        return transactionServiceRepo.journal_searchInvoice(tranId, creditAc, debitAc);
    }

    public List<Account_transactions_v3> ledger_transaction_searchs(String dbt_ac, String crdt_ac) {
        return transactionServiceRepo.ledger_transaction_search(dbt_ac, crdt_ac);
    }

    // ---------------------------------------------------------------------
    // Receipt
    // ---------------------------------------------------------------------

    public List<Account_transactions_v3> list_receipts() {
        List<Account_transactions_v3> li = transactionServiceRepo.selectDataReceipt();
        for (Account_transactions_v3 t : li) {
            t.setCreatedBy(t.getCrdt_ac());
            String credit = ledgerServiceRepo.getLedger(toInt(t.getCrdt_ac(), 0));
            t.setCrdt_ac(credit);
        }
        return li;
    }

    public List<Account_transactions_v3> receipt_sorts(String field, String type) {
        log.debug("receipt_sorts {} {}", field, type);
        List<Account_transactions_v3> li = null;

        if ("tran_Date".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.tran_DateReceiptA()
                                              : transactionServiceRepo.tran_DateReceiptD();
        } else if ("dbt_ac".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.dbt_acReceiptA()
                                              : transactionServiceRepo.dbt_acReceiptD();
        } else if ("mode".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.modeReceiptA()
                                              : transactionServiceRepo.modeReceiptD();
        } else if ("amount".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.amountReceiptA()
                                              : transactionServiceRepo.amountReceiptD();
        } else if ("description".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.descriptionReceiptA()
                                              : transactionServiceRepo.descriptionReceiptD();
        }

        for (Account_transactions_v3 t : li) {
            t.setCreatedBy(t.getCrdt_ac());
            String credit = ledgerServiceRepo.getLedger(toInt(t.getCrdt_ac(), 0));
            t.setCrdt_ac(credit);
        }
        return li;
    }

    public List<Account_transactions_v3> receipt_bn_dates(String start, String end) {
        log.debug("receipt_bn_dates {}..{}", start, end);
        List<Account_transactions_v3> li = transactionServiceRepo.selectReceiptDataBnDate(start, end);
        for (Account_transactions_v3 t : li) {
            t.setCreatedBy(t.getCrdt_ac());
            String credit = ledgerServiceRepo.getLedger(toInt(t.getCrdt_ac(), 0));
            t.setCrdt_ac(credit);
        }
        return li;
    }

    // ---------------------------------------------------------------------
    // Account statement (list of ledgers that appear in txns)
    // ---------------------------------------------------------------------

    public List<Account_ledger_v3> list_account_statements() {
        List<String> dbt = transactionServiceRepo.selectDbt_account_statements();
        List<String> crd = transactionServiceRepo.selectCrdt_account_statements();

        List<String> ids = new ArrayList<>();
        ids.addAll(dbt);
        ids.addAll(crd);

        // de-duplicate preserving order
        for (int i = 1; i < ids.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (ids.get(i).equals(ids.get(j))) { ids.remove(i); i--; break; }
            }
        }

        List<Account_ledger_v3> finalList = new ArrayList<>();
        for (int k = 1; k < ids.size(); k++) {
            List<Account_ledger_v3> one = ledgerServiceRepo.getLedgers(toInt(ids.get(k), 0));
            if (!one.isEmpty()) {
                List<Account_group_v3> grp = groupServiceRepo.grp_id_Search(one.get(0).getAc_group());
                if (!grp.isEmpty()) {
                    one.get(0).setAc_group(grp.get(0).getGroup_name());
                    List<Account_title_v3> titles = acTitleRepo.acTitle_searchs(grp.get(0).getAc_title());
                    if (!titles.isEmpty()) {
                        one.get(0).setAc_title(titles.get(0).getAc_title());
                    }
                }
            }
            finalList.addAll(one);
        }
        return finalList;
    }

    public List<Account_transactions_v3> accStmtTransactions(String id, String description) {
        return transactionServiceRepo.selectAccStmtTransaction(id, description);
    }

    public List<Account_transactions_v3> accStmtTransactionBnDates(String id, String description, String start, String end) {
        return transactionServiceRepo.selectAccStmtTransactionBndates(id, description, start, end);
    }

    // ---------------------------------------------------------------------
    // Cash Book (account statement style for cash account)
    // ---------------------------------------------------------------------

    public List<Account_transactions_v3> cashBookAccount_statementDatas(int ledgerId) {
        log.debug("cashBookAccount_statementDatas ledgerId={}", ledgerId);

        String ledger_id = Integer.toString(ledgerId);
        List<Account_ledger_v3> ledgers = ledgerServiceRepo.ledger_Search2(ledgerId);

        float curBalance = 0f, debitTotal = 0f, creditTotal = 0f;
        String balanceType = null;
        float opening = 0f;

        // Opening balance
        for (Account_ledger_v3 l : ledgers) {
            balanceType = l.getBalance_type();
            float ob = parseNumF(l.getOpen_balance());
            if ("debit".equalsIgnoreCase(balanceType)) debitTotal += ob;
            else if ("credit".equalsIgnoreCase(balanceType)) creditTotal += ob;
            opening = ob;
        }
        curBalance += opening;

        // Transactions
        List<Account_transactions_v3> txs =
            transactionServiceRepo.selectAccStmtTransaction2(ledgerId, DESC_LEDGER_CREATION);

        if (!txs.isEmpty()) {
            String flag = null;
            for (Account_transactions_v3 t : txs) {
                int particular;
                // normalize "Nil" vs. real ids
                if (ledger_id.equals(t.getDbt_ac()) && !ledger_id.equals(t.getCrdt_ac())) {
                    particular = toInt(t.getCrdt_ac(), ledgerId);
                } else if (NIL.equalsIgnoreCase(t.getDbt_ac()) || NIL.equalsIgnoreCase(t.getCrdt_ac())) {
                    particular = ledgerId;
                } else {
                    particular = toInt(t.getDbt_ac(), ledgerId);
                }

                String particularName = ledgerServiceRepo.ac_ledger_SearchName(particular);
                float amount = parseNumF(t.getAmount());

                if (ledger_id.equals(t.getDbt_ac())) {
                    curBalance += "debit".equalsIgnoreCase(balanceType) ? amount : -amount;
                    debitTotal += amount;
                    flag = "debit";
                }
                if (ledger_id.equals(t.getCrdt_ac())) {
                    curBalance += "debit".equalsIgnoreCase(balanceType) ? -amount : amount;
                    creditTotal += amount;
                    flag = "credit";
                }

                t.setFilename(particularName);
                t.setAmount(fs(amount));
                t.setBank(fs(curBalance)); // running balance
                t.setBranch(flag);
            }

            // Totals to first row meta fields
            Account_transactions_v3 first = txs.get(0);
            first.setChq_no(fs(debitTotal));
            first.setChq_date(fs(creditTotal));

            float balance;
            if (debitTotal == 0 && creditTotal != 0) balance = creditTotal;
            else if (debitTotal != 0 && creditTotal == 0) balance = debitTotal;
            else balance = debitTotal - creditTotal;
            first.setCreatedTime(fs(balance));
        }

        // (kept) json scaffolding for parity with old method
        JSONObject json = new JSONObject();
        JSONArray arr = new JSONArray();
        json.put("contact", arr);

        return txs;
    }

    public List<Account_transactions_v3> cashBookOpeningBalanceData() {
        Double totalDebit = 0d, totalCredit = 0d, totalDebitContra = 0d, tot = 0d;
        List<Account_transactions_v3> li = transactionServiceRepo.cashBookOpenBalanceDataFetch();

        if (!li.isEmpty()) {
            for (Account_transactions_v3 t : li) {
                String ledger;
                if (CASH_LEDGER_ID.equals(t.getDbt_ac()) && !CASH_LEDGER_ID.equals(t.getCrdt_ac())) {
                    ledger = t.getCrdt_ac();
                } else if (NIL.equalsIgnoreCase(t.getDbt_ac()) || NIL.equalsIgnoreCase(t.getCrdt_ac())) {
                    ledger = CASH_LEDGER_ID;
                } else {
                    ledger = t.getDbt_ac();
                }

                List<Account_ledger_v3> ls = ledgerServiceRepo.getLedgers(toInt(ledger, 0));
                if (!ls.isEmpty()) {
                    t.setBranch(ls.get(0).getLedger_name());
                    t.setTran_Date(ls.get(0).getLedger_date());
                }
                if (CASH_LEDGER_ID.equals(t.getDbt_ac())) totalDebit += parseNum(t.getAmount());
                if (CASH_LEDGER_ID.equals(t.getCrdt_ac())) tot += parseNum(t.getAmount());

                t.setTran_gen("Yes");
            }
            totalCredit = totalDebitContra + tot;
            li.get(0).setAc_no(ds(totalDebit));
            li.get(0).setBank(ds(totalCredit));
        }
        return li;
    }

    public List<Account_transactions_v3> cashbook_sorts(String field, String type) {
        log.debug("cashbook_sorts {} {}", field, type);
        List<Account_transactions_v3> li = null;

        if ("tran_Date".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.tran_DateCashBookA()
                                              : transactionServiceRepo.tran_DateCashBookD();
        } else if ("ledger_name".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.ledgerCashBookA()
                                              : transactionServiceRepo.ledgerCashBookD();
        } else if ("type".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.typeCashBookA()
                                              : transactionServiceRepo.typeCashBookD();
        } else if ("typeWithNo".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.typeWithNoCashBookA()
                                              : transactionServiceRepo.typeWithNoCashBookD();
        } else if ("description".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.descriptionCashBookA()
                                              : transactionServiceRepo.descriptionCashBookD();
        } else if ("amount".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.amountCashBookA()
                                              : transactionServiceRepo.amountCashBookD();
        }

        Double totalDebit = 0d, totalCredit = 0d, totalDebitContra = 0d, tot = 0d;

        if (!li.isEmpty()) {
            for (Account_transactions_v3 t : li) {
                String ledger;
                if (CASH_LEDGER_ID.equals(t.getDbt_ac()) && !CASH_LEDGER_ID.equals(t.getCrdt_ac())) {
                    ledger = t.getCrdt_ac();
                } else if (NIL.equalsIgnoreCase(t.getDbt_ac()) || NIL.equalsIgnoreCase(t.getCrdt_ac())) {
                    ledger = CASH_LEDGER_ID;
                } else {
                    ledger = t.getDbt_ac();
                }

                List<Account_ledger_v3> ls = ledgerServiceRepo.getLedgers(toInt(ledger, 0));
                if (!ls.isEmpty()) t.setBranch(ls.get(0).getLedger_name());

                if (CASH_LEDGER_ID.equals(t.getDbt_ac())) totalDebit += parseNum(t.getAmount());
                if (CASH_LEDGER_ID.equals(t.getCrdt_ac())) tot += parseNum(t.getAmount());
            }
            totalCredit = totalDebitContra + tot;
            li.get(0).setAc_no(ds(totalDebit));
            li.get(0).setBank(ds(totalCredit));
        }
        return li;
    }

    public List<Account_transactions_v3> cashBookOpeningBalanceBnDates(String start, String end) {
        Double totalDebit = 0d, totalCredit = 0d, totalDebitContra = 0d, tot = 0d;
        List<Account_transactions_v3> li = transactionServiceRepo.cashBookOpenBalanceDataFetch();

        if (!li.isEmpty()) {
            for (Account_transactions_v3 t : li) {
                List<Account_ledger_v3> cb = ledgerServiceRepo.cashBookOpenBalanceDataBnDate(Integer.parseInt(CASH_LEDGER_ID), start, end);
                if (!cb.isEmpty()) {
                    String ledger;
                    if (CASH_LEDGER_ID.equals(t.getDbt_ac()) && !CASH_LEDGER_ID.equals(t.getCrdt_ac())) {
                        ledger = t.getCrdt_ac();
                    } else if (NIL.equalsIgnoreCase(t.getDbt_ac()) || NIL.equalsIgnoreCase(t.getCrdt_ac())) {
                        ledger = CASH_LEDGER_ID;
                    } else {
                        ledger = t.getDbt_ac();
                    }

                    List<Account_ledger_v3> ls = ledgerServiceRepo.getLedgers(toInt(ledger, 0));
                    if (!ls.isEmpty()) {
                        t.setBranch(ls.get(0).getLedger_name());
                        t.setTran_Date(ls.get(0).getLedger_date());
                    }

                    if (CASH_LEDGER_ID.equals(t.getDbt_ac())) totalDebit += parseNum(t.getAmount());
                    if (CASH_LEDGER_ID.equals(t.getCrdt_ac())) tot += parseNum(t.getAmount());

                    t.setTran_gen("Yes");
                }
            }
            totalCredit = totalDebitContra + tot;
            li.get(0).setAc_no(ds(totalDebit));
            li.get(0).setBank(ds(totalCredit));
        }
        return li;
    }

    public List<Account_transactions_v3> cashBookBnDates(String start, String end) {
        Double totalDebit = 0d, totalCredit = 0d, totalDebitContra = 0d, tot = 0d;

        // second calculation
        List<Account_transactions_v3> li4 = transactionServiceRepo.cashBookBnDate(start, end);
        if (!li4.isEmpty()) {
            for (int i = 0; i < li4.size(); i++) {
                Account_transactions_v3 t = li4.get(i);

                if (!DESC_LEDGER_CREATION.equals(t.getDescription()) && !NIL.equalsIgnoreCase(t.getMode())) {
                    String ledger;
                    if (CASH_LEDGER_ID.equals(t.getDbt_ac()) && !CASH_LEDGER_ID.equals(t.getCrdt_ac())) {
                        ledger = t.getCrdt_ac();
                    } else if (NIL.equalsIgnoreCase(t.getDbt_ac()) || NIL.equalsIgnoreCase(t.getCrdt_ac())) {
                        ledger = CASH_LEDGER_ID;
                    } else {
                        ledger = t.getDbt_ac();
                    }

                    List<Account_ledger_v3> ls = ledgerServiceRepo.getLedgers(toInt(ledger, 0));
                    if (!ls.isEmpty()) t.setBranch(ls.get(0).getLedger_name());

                    if (CASH_LEDGER_ID.equals(t.getDbt_ac())) totalDebit += parseNum(t.getAmount());
                    if (CASH_LEDGER_ID.equals(t.getCrdt_ac())) tot += parseNum(t.getAmount());
                } else {
                    li4.remove(i);
                    i--;
                }

                totalCredit = totalDebitContra + tot;
                if (!li4.isEmpty()) {
                    li4.get(0).setAc_no(ds(totalDebit));
                    li4.get(0).setBank(ds(totalCredit));
                }
            }
        }

        // opening balance rows inside range
        List<Account_transactions_v3> li = transactionServiceRepo.cashBookOpenBalanceDataFetch();
        if (!li.isEmpty()) {
            for (Account_transactions_v3 t : li) {
                List<Account_ledger_v3> cb = ledgerServiceRepo.cashBookOpenBalanceDataBnDate(Integer.parseInt(CASH_LEDGER_ID), start, end);
                if (!cb.isEmpty()) {
                    String ledger;
                    if (CASH_LEDGER_ID.equals(t.getDbt_ac()) && !CASH_LEDGER_ID.equals(t.getCrdt_ac())) {
                        ledger = t.getCrdt_ac();
                    } else if (NIL.equalsIgnoreCase(t.getDbt_ac()) || NIL.equalsIgnoreCase(t.getCrdt_ac())) {
                        ledger = CASH_LEDGER_ID;
                    } else {
                        ledger = t.getDbt_ac();
                    }

                    List<Account_ledger_v3> ls = ledgerServiceRepo.getLedgers(toInt(ledger, 0));
                    if (!ls.isEmpty()) {
                        t.setBranch(ls.get(0).getLedger_name());
                        t.setTran_Date(ls.get(0).getLedger_date());
                    }

                    if (CASH_LEDGER_ID.equals(t.getDbt_ac())) totalDebit += parseNum(t.getAmount());
                    if (CASH_LEDGER_ID.equals(t.getCrdt_ac())) tot += parseNum(t.getAmount());

                    t.setTran_gen("Yes");
                }
            }
            totalCredit = totalDebitContra + tot;
            if (!li4.isEmpty()) {
                li4.get(0).setAc_no(ds(totalDebit));
                li4.get(0).setBank(ds(totalCredit));
            }
        }

        return li4;
    }

    // ---------------------------------------------------------------------
    // Bank Book
    // ---------------------------------------------------------------------

    public List<Account_transactions_v3> bankBookData() {
        List<Account_transactions_v3> li = transactionServiceRepo.bankBookDatas();
        if (!li.isEmpty()) {
            for (Account_transactions_v3 t : li) {
                List<Account_ledger_v3> ls = ledgerServiceRepo.getLedgers(toInt(t.getDbt_ac(), 0));
                if (!ls.isEmpty()) {
                    t.setBranch(ls.get(0).getLedger_name());
                    if (DESC_LEDGER_CREATION.equals(t.getDescription())) {
                        t.setTran_Date(ls.get(0).getLedger_date());
                    }
                }
                t.setAmount("-"); // legacy UI
            }
        }
        return li;
    }

    public List<Account_transactions_v3> bankbook_sorts(String field, String type) {
        log.debug("bankbook_sorts {} {}", field, type);
        List<Account_transactions_v3> li = null;

        if ("tran_Date".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.tran_DateBankBookA()
                                              : transactionServiceRepo.tran_DateBankBookD();
        } else if ("ledger_name".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.ledgerBankBookA()
                                              : transactionServiceRepo.ledgerBankBookD();
        } else if ("type".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.typeBankBookA()
                                              : transactionServiceRepo.typeBankBookD();
        } else if ("typeWithNo".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.typeWithNoBankBookA()
                                              : transactionServiceRepo.typeWithNoBankBookD();
        } else if ("description".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.descriptionBankBookA()
                                              : transactionServiceRepo.descriptionBankBookD();
        } else if ("amount".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.amountBankBookA()
                                              : transactionServiceRepo.amountBankBookD();
        }

        if (!li.isEmpty()) {
            for (Account_transactions_v3 t : li) {
                List<Account_ledger_v3> ls = ledgerServiceRepo.getLedgers(toInt(t.getDbt_ac(), 0));
                if (!ls.isEmpty()) t.setBranch(ls.get(0).getLedger_name());
                t.setAmount("-"); // legacy UI
            }
        }
        return li;
    }

    public List<Account_transactions_v3> bankBookOpenBalanceDataB(String id, String description, String start, String end) {
        return transactionServiceRepo.selectAccStmtTransactionBndates(id, description, start, end);
    }

    public List<Account_transactions_v3> bankBookDataB(int ledgerId, String start, String end) {
        log.debug("bankBookDataB ledgerId={} {}..{}", ledgerId, start, end);

        String ledger_id = Integer.toString(ledgerId);
        List<Account_ledger_v3> ledgers = ledgerServiceRepo.ledger_Search2(ledgerId);

        float curBalance = 0f, debitTotal = 0f, creditTotal = 0f;
        String balanceType = null;
        float opening = 0f;

        for (Account_ledger_v3 l : ledgers) {
            balanceType = l.getBalance_type();
            float ob = parseNumF(l.getOpen_balance());
            if ("debit".equalsIgnoreCase(balanceType)) debitTotal += ob;
            else if ("credit".equalsIgnoreCase(balanceType)) creditTotal += ob;
            opening = ob;
        }
        curBalance += opening;

        List<Account_transactions_v3> txs =
            transactionServiceRepo.selectAccStmtTransaction2Bndates(ledgerId, DESC_LEDGER_CREATION, start, end);

        if (!txs.isEmpty()) {
            String flag = null;
            for (Account_transactions_v3 t : txs) {
                int particular;
                if (ledger_id.equals(t.getDbt_ac()) && !ledger_id.equals(t.getCrdt_ac())) {
                    particular = toInt(t.getCrdt_ac(), ledgerId);
                } else if (NIL.equalsIgnoreCase(t.getDbt_ac()) || NIL.equalsIgnoreCase(t.getCrdt_ac())) {
                    particular = ledgerId;
                } else {
                    particular = toInt(t.getDbt_ac(), ledgerId);
                }

                String particularName = ledgerServiceRepo.ac_ledger_SearchName(particular);
                float amount = parseNumF(t.getAmount());

                if (ledger_id.equals(t.getDbt_ac())) {
                    curBalance += "debit".equalsIgnoreCase(balanceType) ? amount : -amount;
                    debitTotal += amount;
                    flag = "debit";
                }
                if (ledger_id.equals(t.getCrdt_ac())) {
                    curBalance += "debit".equalsIgnoreCase(balanceType) ? -amount : amount;
                    creditTotal += amount;
                    flag = "credit";
                }

                t.setFilename(particularName);
                t.setAmount(fs(amount));
                t.setBank(fs(curBalance));
                t.setBranch(flag);
            }

            Account_transactions_v3 first = txs.get(0);
            first.setChq_no(fs(debitTotal));
            first.setChq_date(fs(creditTotal));

            float balance;
            if (debitTotal == 0 && creditTotal != 0) balance = creditTotal;
            else if (debitTotal != 0 && creditTotal == 0) balance = debitTotal;
            else balance = debitTotal - creditTotal;
            first.setCreatedTime(fs(balance));
        }

        // (kept) json scaffolding for parity with old method
        JSONObject json = new JSONObject();
        JSONArray arr = new JSONArray();
        json.put("contact", arr);

        return txs;
    }

    // ---------------------------------------------------------------------
    // Day Book
    // ---------------------------------------------------------------------

    public List<Account_transactions_v3> dayBookData() {
        List<Account_transactions_v3> li = transactionServiceRepo.dayBookDatas();
        if (!li.isEmpty()) {
            for (Account_transactions_v3 t : li) {
                List<Account_ledger_v3> li1 = new ArrayList<>();
                List<Account_ledger_v3> li2 = new ArrayList<>();

                if (!NIL.equalsIgnoreCase(t.getDbt_ac()) && !isEmpty(t.getDbt_ac())) {
                    li1 = ledgerServiceRepo.getLedgers(toInt(t.getDbt_ac(), 0));
                }
                if (!NIL.equalsIgnoreCase(t.getCrdt_ac()) && !isEmpty(t.getCrdt_ac())) {
                    li2 = ledgerServiceRepo.getLedgers(toInt(t.getCrdt_ac(), 0));
                }

                if (!li1.isEmpty()) {
                    t.setBranch(li1.get(0).getLedger_name());
                    if (DESC_LEDGER_CREATION.equals(t.getDescription())) {
                        t.setTran_Date(li1.get(0).getLedger_date());
                    }
                }
                if (!li2.isEmpty()) {
                    t.setChq_date(li2.get(0).getLedger_name());
                    if (DESC_LEDGER_CREATION.equals(t.getDescription())) {
                        t.setTran_Date(li2.get(0).getLedger_date());
                    }
                }
            }
        }
        return li;
    }

    public List<Account_transactions_v3> debitAcData() {
        List<Account_transactions_v3> li = transactionServiceRepo.debitAcDatas();
        if (!li.isEmpty()) {
            for (Account_transactions_v3 t : li) {
                if (!NIL.equalsIgnoreCase(t.getDbt_ac()) && !isEmpty(t.getDbt_ac())) {
                    List<Account_ledger_v3> ls = ledgerServiceRepo.getLedgers(toInt(t.getDbt_ac(), 0));
                    t.setBranch(!ls.isEmpty() ? ls.get(0).getLedger_name() : "");
                }
            }
        }
        return li;
    }

    public List<Account_transactions_v3> creditAcData() {
        List<Account_transactions_v3> li = transactionServiceRepo.creditAcDatas();
        if (!li.isEmpty()) {
            for (Account_transactions_v3 t : li) {
                if (!NIL.equalsIgnoreCase(t.getCrdt_ac()) && !isEmpty(t.getCrdt_ac())) {
                    List<Account_ledger_v3> ls = ledgerServiceRepo.getLedgers(toInt(t.getCrdt_ac(), 0));
                    t.setBranch(!ls.isEmpty() ? ls.get(0).getLedger_name() : "");
                }
            }
        }
        return li;
    }

    public List<Account_transactions_v3> daybook_sorts(String field, String type) {
        log.debug("daybook_sorts {} {}", field, type);
        List<Account_transactions_v3> li;

        if ("tran_Date".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.tran_DateDayBookA()
                                              : transactionServiceRepo.tran_DateDayBookD();
        } else if ("description".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.descriptionDayBookA()
                                              : transactionServiceRepo.descriptionDayBookD();
        } else if ("amount".equals(field)) {
            li = "ASC".equalsIgnoreCase(type) ? transactionServiceRepo.amountDayBookA()
                                              : transactionServiceRepo.amountDayBookD();
        } else {
            li = transactionServiceRepo.dayBookDatas();
        }

        List<Account_ledger_v3> li1 = new ArrayList<>();
        List<Account_ledger_v3> li2 = new ArrayList<>();

        for (Account_transactions_v3 t : li) {
            if (!NIL.equalsIgnoreCase(t.getDbt_ac()) && !isEmpty(t.getDbt_ac())) {
                li1 = ledgerServiceRepo.getLedgers(toInt(t.getDbt_ac(), 0));
            }
            if (!NIL.equalsIgnoreCase(t.getCrdt_ac()) && !isEmpty(t.getCrdt_ac())) {
                li2 = ledgerServiceRepo.getLedgers(toInt(t.getCrdt_ac(), 0));
            }
            if (!li1.isEmpty()) {
                t.setBranch(li1.get(0).getLedger_name());
                if (DESC_LEDGER_CREATION.equals(t.getDescription())) {
                    t.setTran_Date(li1.get(0).getLedger_date());
                }
            }
            if (!li2.isEmpty()) {
                t.setChq_date(li2.get(0).getLedger_name());
                if (DESC_LEDGER_CREATION.equals(t.getDescription())) {
                    t.setTran_Date(li2.get(0).getLedger_date());
                }
            }
        }
        return li;
    }

    public List<Account_transactions_v3> dayBookDataBnDate(String start, String end, String debit, String credit) {
        log.debug("dayBookDataBnDate debit={} credit={}", debit, credit);
        List<Account_transactions_v3> li;

        if (!"null".equals(debit) && !"null".equals(credit) && !debit.equals(credit)) {
            li = transactionServiceRepo.dayBookDataBnDate1(start, end, debit, credit);
        } else if (!"null".equals(debit) && !"null".equals(credit)) {
            li = transactionServiceRepo.dayBookDataBnDate2(start, end, debit, credit);
        } else if (!"null".equals(debit)) {
            li = transactionServiceRepo.dayBookDataBnDate3(start, end, debit);
        } else if (!"null".equals(credit)) {
            li = transactionServiceRepo.dayBookDataBnDate4(start, end, credit);
        } else {
            li = transactionServiceRepo.dayBookDataBnDate5(start, end);
        }

        List<Account_ledger_v3> li1 = new ArrayList<>();
        List<Account_ledger_v3> li2 = new ArrayList<>();

        for (Account_transactions_v3 t : li) {
            if (!NIL.equalsIgnoreCase(t.getDbt_ac()) && !isEmpty(t.getDbt_ac())) {
                li1 = ledgerServiceRepo.getLedgers(toInt(t.getDbt_ac(), 0));
            }
            if (!NIL.equalsIgnoreCase(t.getCrdt_ac()) && !isEmpty(t.getCrdt_ac())) {
                li2 = ledgerServiceRepo.getLedgers(toInt(t.getCrdt_ac(), 0));
            }
            if (!li1.isEmpty()) {
                t.setBranch(li1.get(0).getLedger_name());
                if (DESC_LEDGER_CREATION.equals(t.getDescription())) {
                    t.setTran_Date(li1.get(0).getLedger_date());
                }
            }
            if (!li2.isEmpty()) {
                t.setChq_date(li2.get(0).getLedger_name());
                if (DESC_LEDGER_CREATION.equals(t.getDescription())) {
                    t.setTran_Date(li2.get(0).getLedger_date());
                }
            }
        }
        return li;
    }

    // ---------------------------------------------------------------------
    // Delete helpers (Day Book / Cash Book / Bank Book)
    // ---------------------------------------------------------------------

    public List<Account_transactions_v3> dayBookDelete(String tranId) {
        Double amount = 0d, finalAmount;
        List<Account_transactions_v3> li = transactionServiceRepo.journal_Search(tranId);

        if (!li.isEmpty()) {
            Account_transactions_v3 t = li.get(0);
            if ("Voucher".equals(t.getType())) {
                amount = parseNum(t.getAmount());
                if ("cash".equalsIgnoreCase(t.getMode())) {
                    // cash ledger +
                    List<Account_ledger_v3> cash = ledgerServiceRepo.getLedgers(Integer.parseInt(CASH_LEDGER_ID));
                    if (!cash.isEmpty()) {
                        finalAmount = parseNum(cash.get(0).getAmount()) + amount;
                        cash.get(0).setAmount(ds(finalAmount));
                        ledgerServiceRepo.saveAll(cash);
                    }
                    // debit ledger -
                    List<Account_ledger_v3> db = ledgerServiceRepo.getLedgers(toInt(t.getDbt_ac(), 0));
                    if (!db.isEmpty()) {
                        finalAmount = parseNum(db.get(0).getAmount()) - amount;
                        db.get(0).setAmount(ds(finalAmount));
                        ledgerServiceRepo.saveAll(db);
                    }
                } else {
                    // bank ledger +
                    List<Account_ledger_v3> bank = ledgerServiceRepo.getLedgers(toInt(t.getBank(), 0));
                    if (!bank.isEmpty()) {
                        finalAmount = parseNum(bank.get(0).getAmount()) + amount;
                        bank.get(0).setAmount(ds(finalAmount));
                        ledgerServiceRepo.saveAll(bank);
                    }
                    // debit ledger -
                    List<Account_ledger_v3> db = ledgerServiceRepo.getLedgers(toInt(t.getDbt_ac(), 0));
                    if (!db.isEmpty()) {
                        finalAmount = parseNum(db.get(0).getAmount()) - amount;
                        db.get(0).setAmount(ds(finalAmount));
                        ledgerServiceRepo.saveAll(db);
                    }
                }
                transactionServiceRepo.deleteById(Integer.parseInt(tranId));
            }

            if ("Receipt".equals(t.getType())) {
                if ("Recieve".equalsIgnoreCase(t.getStatus()) || "1".equals(t.getStatus())) {
                    amount = parseNum(t.getAmount());
                    if ("cash".equalsIgnoreCase(t.getMode())) {
                        // cash ledger -
                        List<Account_ledger_v3> cash = ledgerServiceRepo.getLedgers(Integer.parseInt(CASH_LEDGER_ID));
                        if (!cash.isEmpty()) {
                            finalAmount = parseNum(cash.get(0).getAmount()) - amount;
                            cash.get(0).setAmount(ds(finalAmount));
                            ledgerServiceRepo.saveAll(cash);
                        }
                        // credit ledger -
                        List<Account_ledger_v3> cr = ledgerServiceRepo.getLedgers(toInt(t.getCrdt_ac(), 0));
                        if (!cr.isEmpty()) {
                            finalAmount = parseNum(cr.get(0).getAmount()) - amount;
                            cr.get(0).setAmount(ds(finalAmount));
                            ledgerServiceRepo.saveAll(cr);
                        }
                    } else {
                        // bank ledger -
                        List<Account_ledger_v3> bank = ledgerServiceRepo.getLedgers(toInt(t.getBank(), 0));
                        if (!bank.isEmpty()) {
                            finalAmount = parseNum(bank.get(0).getAmount()) - amount;
                            bank.get(0).setAmount(ds(finalAmount));
                            ledgerServiceRepo.saveAll(bank);
                        }
                        // credit ledger -
                        List<Account_ledger_v3> cr = ledgerServiceRepo.getLedgers(toInt(t.getCrdt_ac(), 0));
                        if (!cr.isEmpty()) {
                            finalAmount = parseNum(cr.get(0).getAmount()) - amount;
                            cr.get(0).setAmount(ds(finalAmount));
                            ledgerServiceRepo.saveAll(cr);
                        }
                    }
                    transactionServiceRepo.deleteById(Integer.parseInt(tranId));
                } else if ("Not_now".equalsIgnoreCase(t.getStatus()) || "2".equals(t.getStatus())) {
                    transactionServiceRepo.deleteById(Integer.parseInt(tranId));
                }
            }

            if ("Contra".equals(t.getType())) {
                amount = parseNum(t.getAmount());
                // dbt ledger -
                List<Account_ledger_v3> db = ledgerServiceRepo.getLedgers(toInt(t.getDbt_ac(), 0));
                if (!db.isEmpty()) {
                    finalAmount = parseNum(db.get(0).getAmount()) - amount;
                    db.get(0).setAmount(ds(finalAmount));
                    ledgerServiceRepo.saveAll(db);
                }
                // crdt ledger +/- by type
                List<Account_ledger_v3> cr = ledgerServiceRepo.getLedgers(toInt(t.getCrdt_ac(), 0));
                if (!cr.isEmpty()) {
                    boolean liabOrIncome = "2".equals(cr.get(0).getAc_type()) || "3".equals(cr.get(0).getAc_type());
                    finalAmount = parseNum(cr.get(0).getAmount()) + (liabOrIncome ? -amount : amount);
                    cr.get(0).setAmount(ds(finalAmount));
                    ledgerServiceRepo.saveAll(cr);
                }
                transactionServiceRepo.deleteById(Integer.parseInt(tranId));
            }

            if (!"Contra".equals(t.getType()) && !"Receipt".equals(t.getType()) && !"Voucher".equals(t.getType())) {
                t.setUser_bank("can't");
            }
        }

        return li;
    }

    public List<Account_transactions_v3> cashBookDelete(String tranId) {
        // Same logic as dayBookDelete but always targets cash (kept identical to original)
        return dayBookDelete(tranId);
    }

    public List<Account_transactions_v3> bankBookDelete(String tranId) {
        Double amount = 0d, finalAmount;
        List<Account_transactions_v3> li = transactionServiceRepo.journal_Search(tranId);

        if (!li.isEmpty()) {
            Account_transactions_v3 t = li.get(0);
            if ("Voucher".equals(t.getType())) {
                amount = parseNum(t.getAmount());
                // bank +
                List<Account_ledger_v3> bank = ledgerServiceRepo.getLedgers(toInt(t.getBank(), 0));
                if (!bank.isEmpty()) {
                    finalAmount = parseNum(bank.get(0).getAmount()) + amount;
                    bank.get(0).setAmount(ds(finalAmount));
                    ledgerServiceRepo.saveAll(bank);
                }
                // debit -
                List<Account_ledger_v3> db = ledgerServiceRepo.getLedgers(toInt(t.getDbt_ac(), 0));
                if (!db.isEmpty()) {
                    finalAmount = parseNum(db.get(0).getAmount()) - amount;
                    db.get(0).setAmount(ds(finalAmount));
                    ledgerServiceRepo.saveAll(db);
                }
                transactionServiceRepo.deleteById(Integer.parseInt(tranId));
            }

            if ("Receipt".equals(t.getType())) {
                if ("Recieve".equalsIgnoreCase(t.getStatus()) || "1".equals(t.getStatus())) {
                    amount = parseNum(t.getAmount());
                    // bank -
                    List<Account_ledger_v3> bank = ledgerServiceRepo.getLedgers(toInt(t.getBank(), 0));
                    if (!bank.isEmpty()) {
                        finalAmount = parseNum(bank.get(0).getAmount()) - amount;
                        bank.get(0).setAmount(ds(finalAmount));
                        ledgerServiceRepo.saveAll(bank);
                    }
                    // credit -
                    List<Account_ledger_v3> cr = ledgerServiceRepo.getLedgers(toInt(t.getCrdt_ac(), 0));
                    if (!cr.isEmpty()) {
                        finalAmount = parseNum(cr.get(0).getAmount()) - amount;
                        cr.get(0).setAmount(ds(finalAmount));
                        ledgerServiceRepo.saveAll(cr);
                    }
                    transactionServiceRepo.deleteById(Integer.parseInt(tranId));
                } else if ("Not_now".equalsIgnoreCase(t.getStatus()) || "2".equals(t.getStatus())) {
                    transactionServiceRepo.deleteById(Integer.parseInt(tranId));
                }
            }

            if ("Contra".equals(t.getType())) {
                amount = parseNum(t.getAmount());
                // dbt -
                List<Account_ledger_v3> db = ledgerServiceRepo.getLedgers(toInt(t.getDbt_ac(), 0));
                if (!db.isEmpty()) {
                    finalAmount = parseNum(db.get(0).getAmount()) - amount;
                    db.get(0).setAmount(ds(finalAmount));
                    ledgerServiceRepo.saveAll(db);
                }
                // crdt +/- by type
                List<Account_ledger_v3> cr = ledgerServiceRepo.getLedgers(toInt(t.getCrdt_ac(), 0));
                if (!cr.isEmpty()) {
                    boolean liabOrIncome = "2".equals(cr.get(0).getAc_type()) || "3".equals(cr.get(0).getAc_type());
                    finalAmount = parseNum(cr.get(0).getAmount()) + (liabOrIncome ? -amount : amount);
                    cr.get(0).setAmount(ds(finalAmount));
                    ledgerServiceRepo.saveAll(cr);
                }
                transactionServiceRepo.deleteById(Integer.parseInt(tranId));
            }

            if (!"Contra".equals(t.getType()) && !"Receipt".equals(t.getType()) && !"Voucher".equals(t.getType())) {
                t.setUser_bank("can't");
            }
        }

        return li;
    }

    // ---------------------------------------------------------------------
    // Transaction History (filters)
    // ---------------------------------------------------------------------

    public List<Account_transactions_v3> transactionHistory_searchs(String start, String end,
                                                                    String debit, String credit,
                                                                    String field, String val) {
        log.debug("txnHistory start={} end={} debit={} credit={} field={} val={}",
                start, end, debit, credit, field, val);

        List<Account_transactions_v3> li = null;
        boolean noField = isEmpty(field) || "undefined".equals(field);

        if (noField) {
            if (!"null".equals(debit) && !"null".equals(credit) && !debit.equals(credit)) {
                li = transactionServiceRepo.dayBookDataBnDate1(start, end, debit, credit);
            } else if (!"null".equals(debit) && !"null".equals(credit)) {
                li = transactionServiceRepo.dayBookDataBnDate2(start, end, debit, credit);
            } else if (!"null".equals(debit)) {
                li = transactionServiceRepo.dayBookDataBnDate3(start, end, debit);
            } else if (!"null".equals(credit)) {
                li = transactionServiceRepo.dayBookDataBnDate4(start, end, credit);
            } else {
                li = transactionServiceRepo.dayBookDataBnDate5(start, end);
            }
        }

        if ("narration".equals(field)) {
            if (!"null".equals(debit) && !"null".equals(credit) && !debit.equals(credit)) {
                li = transactionServiceRepo.transactionHistoryNarrationSearch1(start, end, debit, credit, val);
            } else if (!"null".equals(debit) && !"null".equals(credit)) {
                li = transactionServiceRepo.transactionHistoryNarrationSearch2(start, end, debit, credit, val);
            } else if (!"null".equals(debit)) {
                li = transactionServiceRepo.transactionHistoryNarrationSearch3(start, end, debit, val);
            } else if (!"null".equals(credit)) {
                li = transactionServiceRepo.transactionHistoryNarrationSearch4(start, end, credit, val);
            } else {
                li = transactionServiceRepo.transactionHistoryNarrationSearch5(start, end, val);
            }
        }

        if ("amount".equals(field)) {
            String[] range = (val == null ? "" : val).split(",", -1);
            String lo = range.length > 0 ? range[0] : "";
            String hi = range.length > 1 ? range[1] : "";
            if (!"null".equals(debit) && !"null".equals(credit) && !debit.equals(credit)) {
                li = transactionServiceRepo.transactionHistoryAmountSearch1(start, end, debit, credit, lo, hi);
            } else if (!"null".equals(debit) && !"null".equals(credit)) {
                li = transactionServiceRepo.transactionHistoryAmountSearch2(start, end, debit, credit, lo, hi);
            } else if (!"null".equals(debit)) {
                li = transactionServiceRepo.transactionHistoryAmountSearch3(start, end, debit, lo, hi);
            } else if (!"null".equals(credit)) {
                li = transactionServiceRepo.transactionHistoryAmountSearch4(start, end, credit, lo, hi);
            } else {
                li = transactionServiceRepo.transactionHistoryAmountSearch5(start, end, lo, hi);
            }
        }

        if ("group".equals(field)) {
            if (!"null".equals(debit) && !"null".equals(credit) && !debit.equals(credit)) {
                li = transactionServiceRepo.transactionHistoryGroupSearch1(start, end, debit, credit, val);
            } else if (!"null".equals(debit) && !"null".equals(credit)) {
                li = transactionServiceRepo.transactionHistoryGroupSearch2(start, end, debit, credit, val);
            } else if (!"null".equals(debit)) {
                li = transactionServiceRepo.transactionHistoryGroupSearch3(start, end, debit, val);
            } else if (!"null".equals(credit)) {
                li = transactionServiceRepo.transactionHistoryGroupSearch4(start, end, credit, val);
            } else {
                li = transactionServiceRepo.transactionHistoryGroupSearch5(start, end, val);
            }
        }

        if (!li.isEmpty()) {
            List<Account_ledger_v3> li1 = new ArrayList<>();
            List<Account_ledger_v3> li2 = new ArrayList<>();
            for (Account_transactions_v3 t : li) {
                if (!NIL.equalsIgnoreCase(t.getDbt_ac()) && !isEmpty(t.getDbt_ac())) {
                    li1 = ledgerServiceRepo.getLedgers(toInt(t.getDbt_ac(), 0));
                }
                if (!NIL.equalsIgnoreCase(t.getCrdt_ac()) && !isEmpty(t.getCrdt_ac())) {
                    li2 = ledgerServiceRepo.getLedgers(toInt(t.getCrdt_ac(), 0));
                }
                if (!li1.isEmpty()) {
                    t.setBranch(li1.get(0).getLedger_name());
                    if (DESC_LEDGER_CREATION.equals(t.getDescription())) {
                        t.setTran_Date(li1.get(0).getLedger_date());
                    }
                }
                if (!li2.isEmpty()) {
                    t.setChq_date(li2.get(0).getLedger_name());
                    if (DESC_LEDGER_CREATION.equals(t.getDescription())) {
                        t.setTran_Date(li2.get(0).getLedger_date());
                    }
                }
            }
        }

        return li;
    }

    // ---------------------------------------------------------------------
    // Misc & Invoices
    // ---------------------------------------------------------------------

    public List<Account_transactions_v3> tran_gen_Search(String tran_gen_id) {
        return transactionServiceRepo.tran_gen_Search(tran_gen_id);
    }

    public List<Account_transactions_v3> View_Account_Statement(int ledgerId, String start, String end) {
        log.debug("View Statement ledgerId={} {}..{}", ledgerId, start, end);
        List<Account_transactions_v3> txs =
            transactionServiceRepo.selectAccStmtTransaction2BndatesNoDesc(ledgerId, start, end);
        return txs;
    }

}
 