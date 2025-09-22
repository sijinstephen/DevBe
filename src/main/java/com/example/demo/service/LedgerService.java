package com.example.demo.service;

import com.example.demo.model.Account_group_v3;
import com.example.demo.model.Account_ledger_v3;
import com.example.demo.model.Account_transactions_v3;
import com.example.demo.repository.GroupServiceRepo;
import com.example.demo.repository.LedgerServiceRepo;
import com.example.demo.repository.TransactionServiceRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * Readability-first refactor:
 * - Constructor injection
 * - SLF4J logging
 * - Small helpers for safe parsing and group-name resolution
 * - Non-breaking: public APIs and field-encoding of totals/values are preserved
 */
@Service
public class LedgerService {

    private static final Logger log = LoggerFactory.getLogger(LedgerService.class);

    private final LedgerServiceRepo ledgerServiceRepo;
    private final GroupServiceRepo groupServiceRepo;
    private final TransactionServiceRepo transactionServiceRepo;

    public LedgerService(LedgerServiceRepo ledgerServiceRepo,
                         GroupServiceRepo groupServiceRepo,
                         TransactionServiceRepo transactionServiceRepo) {
        this.ledgerServiceRepo = ledgerServiceRepo;
        this.groupServiceRepo = groupServiceRepo;
        this.transactionServiceRepo = transactionServiceRepo;
    }

    // ---------------------------------------------------------------------
    // Helpers (local to this class)
    // ---------------------------------------------------------------------

    /** Parse a number from String safely: "", null, "Nil", "-" → 0. */
    private static double parseNum(String s) {
        if (s == null) return 0d;
        String t = s.trim();
        if (t.isEmpty()) return 0d;
        String lower = t.toLowerCase();
        if ("nil".equals(lower) || "-".equals(lower) || "undefined".equals(lower)) return 0d;
        try {
            return Double.parseDouble(t.replace(",", ""));
        } catch (NumberFormatException ex) {
            return 0d;
        }
    }

    /** Null Double → 0. */
    private static double nz(Double v) {
        return v == null ? 0d : v.doubleValue();
    }

    /** Double → String. */
    private static String ds(double v) {
        return Double.toString(v);
    }

    /** Resolve and write group names into the list (ac_group ← name). */
    private void applyGroupNames(List<Account_ledger_v3> ledgers) {
        if (ledgers == null) return;
        for (Account_ledger_v3 l : ledgers) {
            if (l == null) continue;
            try {
                String grpCode = l.getAc_group();
                String grpName = (String) groupServiceRepo.selectGroup(grpCode);
                l.setAc_group(grpName);
            } catch (Exception e) {
                log.debug("Group lookup failed for {}", l.getAc_group(), e);
            }
        }
    }

    // ---------------------------------------------------------------------
    // Basic CRUD / Queries
    // ---------------------------------------------------------------------

    public Account_ledger_v3 add_Ledgers(Account_ledger_v3 fp) {
        return ledgerServiceRepo.save(fp);
    }

    @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> last_idSearchs() {
        return (List<Account_ledger_v3>) ledgerServiceRepo.last_id_Search();
    }

    @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> ledger_name_searchs(String ledgerName) {
        log.debug("ledger_name_searchs: {}", ledgerName);
        return (List<Account_ledger_v3>) ledgerServiceRepo.ledger_name_search(ledgerName);
    }

    @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> list_ledgers() {
        List<Account_ledger_v3> list = (List<Account_ledger_v3>) ledgerServiceRepo.selectData();
        applyGroupNames(list);
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> ledger_sorts(String field, String type) {
        log.debug("ledger_sorts field={} type={}", field, type);
        List<Account_ledger_v3> list = null;

        // Keep existing repo methods; just centralize branching
        if ("ledger_name".equals(field)) {
            list = "ASC".equalsIgnoreCase(type) ? ledgerServiceRepo.ledger_nameA() : ledgerServiceRepo.ledger_nameD();
        } else if ("ac_group".equals(field)) {
            list = "ASC".equalsIgnoreCase(type) ? ledgerServiceRepo.ac_groupA() : ledgerServiceRepo.ac_groupD();
        } else if ("open_balance".equals(field)) {
            list = "ASC".equalsIgnoreCase(type) ? ledgerServiceRepo.open_balanceA() : ledgerServiceRepo.open_balanceD();
        } else if ("mobile".equals(field)) {
            list = "ASC".equalsIgnoreCase(type) ? ledgerServiceRepo.mobileA() : ledgerServiceRepo.mobileD();
        } else if ("email".equals(field)) {
            list = "ASC".equalsIgnoreCase(type) ? ledgerServiceRepo.emailA() : ledgerServiceRepo.emailD();
        } else if ("bank".equals(field)) {
            list = "ASC".equalsIgnoreCase(type) ? ledgerServiceRepo.bankA() : ledgerServiceRepo.bankD();
        }

        applyGroupNames(list);
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> ledger_bn_dates(String start, String end) {
        log.debug("ledger_bn_dates start={} end={}", start, end);
        List<Account_ledger_v3> list = (List<Account_ledger_v3>) ledgerServiceRepo.selectDataBnDate(start, end);
        applyGroupNames(list);
        return list;
    }

    public String ledger_deletes(int id) {
        ledgerServiceRepo.deleteById(id);
        // transactionServiceRepo.transactionDelete(id); // kept as-is (commented)
        return "Deleted successfully";
    }

    @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> ledger_Searchs(int ledgerId) {
        log.debug("ledger_Searchs id={}", ledgerId);
        return (List<Account_ledger_v3>) ledgerServiceRepo.ledger_Search(ledgerId);
    }

    @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> bank_names() {
        return (List<Account_ledger_v3>) ledgerServiceRepo.selectBank();
    }

    @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> ledger_Searchs2(int ledgerId) {
        log.debug("ledger_Searchs2 id={}", ledgerId);
        return (List<Account_ledger_v3>) ledgerServiceRepo.ledger_Search2(ledgerId);
    }

    // ---------------------------------------------------------------------
    // Account Statement (current to date)
    // Preserves original field encodings in returned rows.
    // ---------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    public List<Account_transactions_v3> account_statementDatas(int ledgerId) {
        log.debug("account_statementDatas ledgerId={}", ledgerId);

        String ledgerIdStr = Integer.toString(ledgerId);
        List<Account_ledger_v3> ledgers = (List<Account_ledger_v3>) ledgerServiceRepo.ledger_Search2(ledgerId);

        double currentBalance = 0d;
        double debitTotal = 0d;
        double creditTotal = 0d;
        String balanceType = null;

        // Opening balance by ledger
        for (Account_ledger_v3 l : ledgers) {
            if (l == null) continue;
            balanceType = l.getBalance_type();
            double open = parseNum(l.getOpen_balance());
            if ("debit".equalsIgnoreCase(balanceType)) {
                debitTotal += open;
            } else if ("credit".equalsIgnoreCase(balanceType)) {
                creditTotal += open;
            }
            currentBalance += open;
        }

        // Seed transactions
        List<Account_transactions_v3> txs =
            (List<Account_transactions_v3>) transactionServiceRepo.selectAccStmtTransaction2(ledgerId, "ledger creation");

        // Merge filtered (legacy behavior preserved)
        List<Account_transactions_v3> filtered =
            (List<Account_transactions_v3>) transactionServiceRepo.transactionFilter(ledgerId, "ledger creation");
        if (filtered != null && filtered.size() > 1) {
            // remove last and append others
            filtered.remove(filtered.size() - 1);
            txs.addAll(filtered);
        }

        // Walk and compute running balance; encode fields as before
        String flag = null;
        for (Account_transactions_v3 t : txs) {
            if (t == null) continue;

            int particular;
            if (ledgerIdStr.equals(t.getDbt_ac())) {
                particular = parseIntSafe(t.getCrdt_ac(), 0);
            } else if (ledgerIdStr.equals(t.getCrdt_ac())) {
                particular = parseIntSafe(t.getDbt_ac(), 0);
            } else {
                continue;
            }

            String particularName = ledgerServiceRepo.ac_ledger_SearchName(particular);
            double amount = parseNum(t.getAmount());

            if (ledgerIdStr.equals(t.getDbt_ac())) {
                // debit to this ledger
                if ("debit".equalsIgnoreCase(balanceType)) currentBalance += amount;
                else if ("credit".equalsIgnoreCase(balanceType)) currentBalance -= amount;
                debitTotal += amount;
                flag = "debit";
            } else if (ledgerIdStr.equals(t.getCrdt_ac())) {
                // credit to this ledger
                if ("debit".equalsIgnoreCase(balanceType)) currentBalance -= amount;
                else if ("credit".equalsIgnoreCase(balanceType)) currentBalance += amount;
                creditTotal += amount;
                flag = "credit";
            }

            // encode into fields (legacy UI expectation)
            t.setFilename(particularName);
            t.setAmount(ds(amount));
            t.setBank(ds(currentBalance));   // running balance
            t.setBranch(flag);               // direction marker
        }

        // Totals into first row meta fields (legacy behavior)
        if (!txs.isEmpty()) {
            Account_transactions_v3 first = txs.get(0);
            first.setChq_no(ds(debitTotal));
            first.setChq_date(ds(creditTotal));

            double balance;
            if (debitTotal == 0 && creditTotal != 0) {
                balance = creditTotal;
            } else if (debitTotal != 0 && creditTotal == 0) {
                balance = debitTotal;
            } else {
                balance = debitTotal - creditTotal;
            }
            first.setCreatedTime(ds(balance));
        }

        return txs;
    }

    // ---------------------------------------------------------------------
    // Profit & Loss (unchanged public behavior)
    // ---------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> profit_losss(String title) {
        double total = 0d;
        List<Account_ledger_v3> list = (List<Account_ledger_v3>) ledgerServiceRepo.profit_loss(title);
        if (!list.isEmpty()) {
            for (Account_ledger_v3 l : list) {
                total += parseNum(l.getAmount());
            }
            list.get(0).setBranch(ds(total)); // legacy: store sum in first.branch
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> profit_loss_bn_dates(String title, String start, String end) {
        double grand = 0d;
        List<Account_ledger_v3> ledgers = (List<Account_ledger_v3>) ledgerServiceRepo.profit_loss_idSearchByTitle(title);
        if (!ledgers.isEmpty()) {
            for (Account_ledger_v3 l : ledgers) {
                int id = l.getId();
                double perLedger = 0d;
                List<Account_transactions_v3> txs =
                    (List<Account_transactions_v3>) transactionServiceRepo.profit_loss_bn_date(id, start, end);
                if (txs != null) {
                    for (Account_transactions_v3 t : txs) {
                        perLedger += parseNum(t.getAmount());
                    }
                }
                l.setAmount(ds(perLedger));
                grand += perLedger;
            }
            ledgers.get(0).setBranch(ds(grand)); // legacy: sum in first.branch
        }
        return ledgers;
    }

    // ---------------------------------------------------------------------
    // Account Statement (between dates)
    // ---------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    public List<Account_transactions_v3> account_statementDataBnDates(int ledgerId, String start, String end) {
        log.debug("account_statementDataBnDates ledgerId={} {}..{}", ledgerId, start, end);

        String ledgerIdStr = Integer.toString(ledgerId);
        List<Account_ledger_v3> ledgers = (List<Account_ledger_v3>) ledgerServiceRepo.ledger_Search2(ledgerId);

        double currentBalance = 0d;
        double debitTotal = 0d;
        double creditTotal = 0d;
        String balanceType = null;

        // Opening balance
        for (Account_ledger_v3 l : ledgers) {
            if (l == null) continue;
            balanceType = l.getBalance_type();
            double open = parseNum(l.getOpen_balance());
            if ("debit".equalsIgnoreCase(balanceType)) debitTotal += open;
            else if ("credit".equalsIgnoreCase(balanceType)) creditTotal += open;
            currentBalance += open;
        }

        List<Account_transactions_v3> txs =
            (List<Account_transactions_v3>) transactionServiceRepo
                .selectAccStmtTransaction2Bndates(ledgerId, "ledger creation", start, end);

        String flag = null;
        if (txs != null && !txs.isEmpty()) {
            for (Account_transactions_v3 t : txs) {
                if (t == null) continue;

                int particular;
                if (ledgerIdStr.equals(t.getDbt_ac())) {
                    particular = parseIntSafe(t.getCrdt_ac(), 0);
                } else if (ledgerIdStr.equals(t.getCrdt_ac())) {
                    particular = parseIntSafe(t.getDbt_ac(), 0);
                } else {
                    continue;
                }

                String particularName = ledgerServiceRepo.ac_ledger_SearchName(particular);
                double amount = parseNum(t.getAmount());

                if (ledgerIdStr.equals(t.getDbt_ac())) {
                    if ("debit".equalsIgnoreCase(balanceType)) currentBalance += amount;
                    else if ("credit".equalsIgnoreCase(balanceType)) currentBalance -= amount;
                    debitTotal += amount;
                    flag = "debit";
                } else if (ledgerIdStr.equals(t.getCrdt_ac())) {
                    if ("debit".equalsIgnoreCase(balanceType)) currentBalance -= amount;
                    else if ("credit".equalsIgnoreCase(balanceType)) currentBalance += amount;
                    creditTotal += amount;
                    flag = "credit";
                }

                t.setFilename(particularName);
                t.setAmount(ds(amount));
                t.setBank(ds(currentBalance));  // running balance
                t.setBranch(flag);
            }

            // Totals into first row meta fields (legacy)
            Account_transactions_v3 first = txs.get(0);
            first.setChq_no(ds(debitTotal));
            first.setChq_date(ds(creditTotal));

            double balance;
            if (debitTotal == 0 && creditTotal != 0) balance = creditTotal;
            else if (debitTotal != 0 && creditTotal == 0) balance = debitTotal;
            else balance = debitTotal - creditTotal;
            first.setCreatedTime(ds(balance));
        }

        return txs;
    }

    // ---------------------------------------------------------------------
    // Ledger balance utilities & updaters
    // ---------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    public String updateledgerbalance() {
        // type 1
        List<Account_ledger_v3> type1 = (List<Account_ledger_v3>) ledgerServiceRepo.ledgerLoad("1");
        for (Account_ledger_v3 l : type1) l.setAmount(ds(ledgerBalance(l.getId())));
        ledgerServiceRepo.saveAll(type1);

        // type 2
        List<Account_ledger_v3> type2 = (List<Account_ledger_v3>) ledgerServiceRepo.ledgerLoad("2");
        for (Account_ledger_v3 l : type2) l.setAmount(ds(ledgerBalanceforliabality(l.getId())));
        ledgerServiceRepo.saveAll(type2);

        // type 3
        List<Account_ledger_v3> type3 = (List<Account_ledger_v3>) ledgerServiceRepo.ledgerLoad("3");
        for (Account_ledger_v3 l : type3) l.setAmount(ds(ledgerBalanceforliabality(l.getId())));
        ledgerServiceRepo.saveAll(type3);

        // type 4
        List<Account_ledger_v3> type4 = (List<Account_ledger_v3>) ledgerServiceRepo.ledgerLoad("4");
        for (Account_ledger_v3 l : type4) l.setAmount(ds(ledgerBalance(l.getId())));
        ledgerServiceRepo.saveAll(type4);

        return "ledgerbalance updated";
    }

    @SuppressWarnings("unchecked")
    public Double ledgerBalance(int id) {
        double dbtTot = 0d;
        double crdTot = 0d;

        List<Account_transactions_v3> dbts = (List<Account_transactions_v3>) transactionServiceRepo.dbtAcLoad(id);
        for (Account_transactions_v3 t : dbts) dbtTot += parseNum(t.getAmount());

        List<Account_transactions_v3> crds = (List<Account_transactions_v3>) transactionServiceRepo.crdtAcLoad(id);
        for (Account_transactions_v3 t : crds) crdTot += parseNum(t.getAmount());

        return dbtTot - crdTot;
    }

    @SuppressWarnings("unchecked")
    public Double ledgerBalanceforliabality(int id) {
        double dbtTot = 0d;
        double crdTot = 0d;

        List<Account_transactions_v3> dbts = (List<Account_transactions_v3>) transactionServiceRepo.dbtAcLoad(id);
        for (Account_transactions_v3 t : dbts) dbtTot += parseNum(t.getAmount());

        List<Account_transactions_v3> crds = (List<Account_transactions_v3>) transactionServiceRepo.crdtAcLoad(id);
        for (Account_transactions_v3 t : crds) crdTot += parseNum(t.getAmount());

        return crdTot - dbtTot;
    }

    // ---------------------------------------------------------------------
    // Balance Sheet helpers
    // ---------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> balanceSheet(String Start, String end) {
        log.debug("balanceSheet start={} end={}", Start, end);
        String start = Start;

        double fixedLiabilityTotal = 0d;
        List<Account_ledger_v3> ledgersAssets = (List<Account_ledger_v3>) ledgerServiceRepo.trial_balances("1");
        List<Account_ledger_v3> ledgersLiabilities = (List<Account_ledger_v3>) ledgerServiceRepo.trial_balances("2");
        List   <Account_ledger_v3> ledgers = ledgersAssets;
        ledgers.addAll(ledgersLiabilities);
        if (!ledgers.isEmpty()) {
            for (Account_ledger_v3 l : ledgers) {
                double debitTotal = 0d;
                double creditTotal = 0d;
                double balance = 0d;

                if (!"0".equals(l.getAmount()) && !"".equals(l.getAmount())) {
                    int id = l.getId();
                    String idStr = Integer.toString(id);

                    @SuppressWarnings("unchecked")
                    List<Account_transactions_v3> txs =
                        (List<Account_transactions_v3>) transactionServiceRepo.selectBalanceSheetDataBnDates(id, start, end);

                    for (Account_transactions_v3 t : txs) {
                        if (idStr.equals(t.getDbt_ac())) {
                            debitTotal += parseNum(t.getAmount());
                        }
                        if (idStr.equals(t.getCrdt_ac())) {
                            creditTotal += parseNum(t.getAmount());
                        }
                    }

                    balance = Math.abs(debitTotal - creditTotal);
                    fixedLiabilityTotal += balance;
                    l.setAmount(ds(balance));
                }
            }
            ledgers.get(0).setBranch(ds(fixedLiabilityTotal)); // legacy: sum in first.branch
        }

        return ledgers;
    }

     @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> balanceSheetDataBnDates(String title, String Start, String end) {
        log.debug("balanceSheetDataBnDates title={} start={} end={}", title, Start, end);
        String start = Start;

        double fixedLiabilityTotal = 0d;
        List<Account_ledger_v3> ledgers = (List<Account_ledger_v3>) ledgerServiceRepo.profit_loss(title);

        if (!ledgers.isEmpty()) {
            for (Account_ledger_v3 l : ledgers) {
                double debitTotal = 0d;
                double creditTotal = 0d;
                double balance = 0d;

                if (!"0".equals(l.getAmount()) && !"".equals(l.getAmount())) {
                    int id = l.getId();
                    String idStr = Integer.toString(id);

                    @SuppressWarnings("unchecked")
                    List<Account_transactions_v3> txs =
                        (List<Account_transactions_v3>) transactionServiceRepo.selectBalanceSheetDataBnDates(id, start, end);

                    for (Account_transactions_v3 t : txs) {
                        if (idStr.equals(t.getDbt_ac())) {
                            debitTotal += parseNum(t.getAmount());
                        }
                        if (idStr.equals(t.getCrdt_ac())) {
                            creditTotal += parseNum(t.getAmount());
                        }
                    }

                    balance = Math.abs(debitTotal - creditTotal);
                    fixedLiabilityTotal += balance;
                    l.setAmount(ds(balance));
                }
            }
            ledgers.get(0).setBranch(ds(fixedLiabilityTotal)); // legacy: sum in first.branch
        }

        return ledgers;
    }


    @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> balanceSheetProfitLossDataBnDates(String title, String start, String end) {
        log.debug("balanceSheetProfitLossDataBnDates title={} {}..{}", title, start, end);
        double total = 0d;
        List<Account_ledger_v3> list =
            (List<Account_ledger_v3>) ledgerServiceRepo.balanceSheetProfitLossDataBnDate(title, start, end);

        if (!list.isEmpty()) {
            for (Account_ledger_v3 l : list) {
                total += parseNum(l.getAmount());
            }
            list.get(0).setBranch(ds(total)); // legacy: sum in first.branch
        }

        return list;
    }

    // ---------------------------------------------------------------------
    // Trial Balance (non-breaking; safer null handling)
    // ---------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> trial_balance(String acType) {
        double totalDebit = 0d;
        double totalCredit = 0d;
        double totalBalance = 0d;

        List<Account_ledger_v3> list = (List<Account_ledger_v3>) ledgerServiceRepo.trial_balances(acType);
        if (!list.isEmpty()) {
            for (Account_ledger_v3 l : list) {
                int id = l.getId();

                double dbt = nz(transactionServiceRepo.selectTrialBalanceDbt(id));
                double crd = nz(transactionServiceRepo.selectTrialBalanceCrdt(id));
                double dbtWO = nz(transactionServiceRepo.selectTrialBalanceDbtWithoutOpenBalance(id));
                double crdWO = nz(transactionServiceRepo.selectTrialBalanceCrdtWithoutOpenBalance(id));

                totalDebit += dbt;
                totalCredit += crd;

                double absDiff = Math.abs(dbt - crd);
                double signedDiff = dbt - crd;
                totalBalance += absDiff;

                l.setAddress(ds(dbt));     // debit
                l.setEmail(ds(crd));       // credit
                l.setBank(ds(absDiff));    // abs diff
                l.setMobile(ds(signedDiff));
                l.setIfsc_code(ds(dbtWO)); // debit w/o opening balance
                l.setState(ds(crdWO));     // credit w/o opening balance
            }
            list.get(0).setContact(ds(totalBalance)); // legacy: total in first.contact
        }
        return list;
    }

    public String trial_balance_total() {
        double totalDbt = nz(transactionServiceRepo.selectTotalDbtAmnt());
        double totalCrd = nz(transactionServiceRepo.selectTotalCrdAmnt());
        return ds(totalDbt) + "," + ds(totalCrd);
    }

    @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> trial_balanceBnDate(String acType, String start, String end) {
        double totalDebit = 0d;
        double totalCredit = 0d;
        double totalBalance = 0d;

        List<Account_ledger_v3> list = (List<Account_ledger_v3>) ledgerServiceRepo.trial_balances(acType);
        if (!list.isEmpty()) {
            for (Account_ledger_v3 l : list) {
                int id = l.getId();

                double dbt = nz(transactionServiceRepo.selectTrialBalanceDbtBnDates(id, start, end));
                double crd = nz(transactionServiceRepo.selectTrialBalanceCrdtBnDates(id, start, end));

                totalDebit += dbt;
                totalCredit += crd;

                double absDiff = Math.abs(dbt - crd);
                double signedDiff = dbt - crd;
                totalBalance += absDiff;

                l.setAddress(ds(dbt));
                l.setEmail(ds(crd));
                l.setBank(ds(absDiff));
                l.setMobile(ds(signedDiff));
            }
            list.get(0).setContact(ds(totalBalance));
        }
        return list;
    }

    public String trial_balance_totalBnDate(String start, String end) {
        double totalDbt = nz(transactionServiceRepo.selectTotalDbtAmntBnDates(start, end));
        double totalCrd = nz(transactionServiceRepo.selectTotalCrdAmntBnDates(start, end));
        return ds(totalDbt) + "," + ds(totalCrd);
    }

    // ---------------------------------------------------------------------
    // Misc lists
    // ---------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> bankDatas() {
        return (List<Account_ledger_v3>) ledgerServiceRepo.bankData();
    }

    @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> selectCustomers() {
        return (List<Account_ledger_v3>) ledgerServiceRepo.selectCustomer();
    }

    @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> selectServices() {
        return (List<Account_ledger_v3>) ledgerServiceRepo.selectService();
    }

    @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> ac_dashboardCashData() {
        return (List<Account_ledger_v3>) ledgerServiceRepo.ac_dashboardCashDatas();
    }

    @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> ac_dashboardBankData() {
        return (List<Account_ledger_v3>) ledgerServiceRepo.ac_dashboardBankDatas();
    }

    @SuppressWarnings("unchecked")
    public List<Account_ledger_v3> index_customer_vendorApi(String grp) {
        return (List<Account_ledger_v3>) ledgerServiceRepo.index_customer_vendorApis(grp);
    }

    @SuppressWarnings("unchecked")
    public String migrationDateAdd(String mgrDate) {
        List<Account_ledger_v3> list = (List<Account_ledger_v3>) ledgerServiceRepo.last_id_Search();
        for (Account_ledger_v3 l : list) {
            Account_ledger_v3 upd = ledgerServiceRepo.ledger_Search_MigrationDate(l.getId());
            upd.setLedger_date(mgrDate);
            ledgerServiceRepo.save(upd);
        }
        return "Added";
    }

    // small util
    private static int parseIntSafe(String s, int fallback) {
        try { return Integer.parseInt(s); } catch (Exception e) { return fallback; }
    }
}
