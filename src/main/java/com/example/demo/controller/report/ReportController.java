package com.example.demo.controller.report;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MissingServletRequestParameterException;

import com.example.demo.model.Account_ledger_v3;
import com.example.demo.model.Account_transactions_v3;
import com.example.demo.service.LedgerService;
import com.example.demo.service.TransactionService;

@CrossOrigin(origins = {"http://localhost:5173", "https://your-prod-domain.com"}, maxAge = 3600)
@RestController
@RequestMapping("")
public class ReportController {
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired private TransactionService transactionService;
    @Autowired private LedgerService ledgerService;

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleMissingParams(MissingServletRequestParameterException ex) {
        logger.error("Missing required parameter: {}", ex.getParameterName());
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Missing required parameter: " + ex.getParameterName());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("timestamp", LocalDate.now().toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/profit_loss")
    public List<Map<String, Object>> profitLoss(
            @RequestParam("title") String title,
            @RequestParam(value = "CompanyName", required = false) String companyName,
            @RequestParam(value = "CustId", required = false) String custId) {
        logger.info("Fetching profit/loss data for title: {}, CompanyName: {}, CustId: {}", title, companyName, custId);
        String category = mapTitleToCategory(title);
        List<Account_transactions_v3> transactions = transactionService.list_transactions();
        List<Account_ledger_v3> ledgers = ledgerService.list_ledgers();
        Map<String, String> ledgerTitles = ledgers.stream()
                .filter(l -> l.getAc_title() != null)
                .collect(Collectors.toMap(Account_ledger_v3::getLedger_name, Account_ledger_v3::getAc_title, (v1, v2) -> v1));

        Map<String, Map<String, Object>> aggregated = new HashMap<>();
        double totalAmount = 0.0;
        for (Account_transactions_v3 t : transactions) {
            String dbtTitle = ledgerTitles.getOrDefault(t.getDbt_ac(), "");
            String crdtTitle = ledgerTitles.getOrDefault(t.getCrdt_ac(), "");
            if (category.equals(dbtTitle) || category.equals(crdtTitle)) {
                String ledgerName = category.equals(dbtTitle) ? t.getDbt_ac() : t.getCrdt_ac();
                double amount = t.getAmount() != null ? Double.parseDouble(t.getAmount()) : 0.0;
                totalAmount += amount;
                aggregated.computeIfAbsent(ledgerName, k -> new HashMap<>()).put("id", t.getTranID());
                aggregated.get(ledgerName).put("ledger_name", ledgerName);
                aggregated.get(ledgerName).compute("amount", (k, v) -> v == null ? amount : ((Double) v) + amount);
            }
        }

        List<Map<String, Object>> result = aggregated.values().stream()
                .filter(m -> ((Double) m.get("amount")) != 0)
                .collect(Collectors.toList());

        if (!result.isEmpty()) {
            result.get(0).put("branch", totalAmount);
        } else {
            logger.warn("No transactions found for title: {}, CompanyName: {}, CustId: {}", title, companyName, custId);
            result.add(new HashMap<>(Map.of("id", "0", "ledger_name", "No Data", "amount", 0.0, "branch", 0.0)));
        }

        return result;
    }

    @GetMapping("/profit_loss_bn_date")
    public List<Map<String, Object>> profitLossBnDate(
            @RequestParam("title") String title,
            @RequestParam(value = "CompanyName", required = false) String companyName,
            @RequestParam(value = "CustId", required = false) String custId,
            @RequestParam(value = "start", required = false, defaultValue = "2022-04-01") String start,
            @RequestParam(value = "end", required = false, defaultValue = "2025-09-17") String end) {
        logger.info("Fetching profit/loss data between dates for title: {}, CompanyName: {}, CustId: {}, start: {}, end: {}",
                title, companyName, custId, start, end);
        validateDate(start, "start");
        validateDate(end, "end");
        String category = mapTitleToCategory(title);
        List<Account_transactions_v3> transactions = transactionService.payment_bn_dates(start, end);
        List<Account_ledger_v3> ledgers = ledgerService.list_ledgers();
        Map<String, String> ledgerTitles = ledgers.stream()
                .filter(l -> l.getAc_title() != null)
                .collect(Collectors.toMap(Account_ledger_v3::getLedger_name, Account_ledger_v3::getAc_title, (v1, v2) -> v1));

        Map<String, Map<String, Object>> aggregated = new HashMap<>();
        double totalAmount = 0.0;
        for (Account_transactions_v3 t : transactions) {
            String dbtTitle = ledgerTitles.getOrDefault(t.getDbt_ac(), "");
            String crdtTitle = ledgerTitles.getOrDefault(t.getCrdt_ac(), "");
            if (category.equals(dbtTitle) || category.equals(crdtTitle)) {
                String ledgerName = category.equals(dbtTitle) ? t.getDbt_ac() : t.getCrdt_ac();
                double amount = t.getAmount() != null ? Double.parseDouble(t.getAmount()) : 0.0;
                totalAmount += amount;
                aggregated.computeIfAbsent(ledgerName, k -> new HashMap<>()).put("id", t.getTranID());
                aggregated.get(ledgerName).put("ledger_name", ledgerName);
                aggregated.get(ledgerName).compute("amount", (k, v) -> v == null ? amount : ((Double) v) + amount);
            }
        }

        List<Map<String, Object>> result = aggregated.values().stream()
                .filter(m -> ((Double) m.get("amount")) != 0)
                .collect(Collectors.toList());

        if (!result.isEmpty()) {
            result.get(0).put("branch", totalAmount);
        } else {
            logger.warn("No transactions found for title: {}, CompanyName: {}, CustId: {}, start: {}, end: {}",
                    title, companyName, custId, start, end);
            result.add(new HashMap<>(Map.of("id", "0", "ledger_name", "No Data", "amount", 0.0, "branch", 0.0)));
        }

        return result;
    }

    @GetMapping("/balanceSheetProfitLossDataBnDates")
    public List<Map<String, Object>> balanceSheetProfitLossDataBnDates(
            @RequestParam("title") String title,
            @RequestParam(value = "CompanyName", required = false) String companyName,
            @RequestParam(value = "CustId", required = false) String custId,
            @RequestParam(value = "start", required = false, defaultValue = "2022-04-01") String start,
            @RequestParam(value = "end", required = false, defaultValue = "2025-09-17") String end) {
        logger.info("Fetching balance sheet/P&L data between dates for title: {}, CompanyName: {}, CustId: {}, start: {}, end: {}",
                title, companyName, custId, start, end);
        validateDate(start, "start");
        validateDate(end, "end");
        String category = mapTitleToCategory(title);
        List<Account_transactions_v3> transactions = transactionService.payment_bn_dates(start, end);
        List<Account_ledger_v3> ledgers = ledgerService.list_ledgers();
        Map<String, String> ledgerTitles = ledgers.stream()
                .filter(l -> l.getAc_title() != null)
                .collect(Collectors.toMap(Account_ledger_v3::getLedger_name, Account_ledger_v3::getAc_title, (v1, v2) -> v1));

        Map<String, Map<String, Object>> aggregated = new HashMap<>();
        double totalAmount = 0.0;
        for (Account_transactions_v3 t : transactions) {
            String dbtTitle = ledgerTitles.getOrDefault(t.getDbt_ac(), "");
            String crdtTitle = ledgerTitles.getOrDefault(t.getCrdt_ac(), "");
            if (category.equals(dbtTitle) || category.equals(crdtTitle)) {
                String ledgerName = category.equals(dbtTitle) ? t.getDbt_ac() : t.getCrdt_ac();
                double amount = t.getAmount() != null ? Double.parseDouble(t.getAmount()) : 0.0;
                totalAmount += amount;
                aggregated.computeIfAbsent(ledgerName, k -> new HashMap<>()).put("id", t.getTranID());
                aggregated.get(ledgerName).put("ledger_name", ledgerName);
                aggregated.get(ledgerName).compute("amount", (k, v) -> v == null ? amount : ((Double) v) + amount);
            }
        }

        List<Map<String, Object>> result = aggregated.values().stream()
                .filter(m -> ((Double) m.get("amount")) != 0)
                .collect(Collectors.toList());

        if (!result.isEmpty()) {
            result.get(0).put("branch", totalAmount);
        } else {
            logger.warn("No transactions found for title: {}, CompanyName: {}, CustId: {}, start: {}, end: {}",
                    title, companyName, custId, start, end);
            result.add(new HashMap<>(Map.of("id", "0", "ledger_name", "No Data", "amount", 0.0, "branch", 0.0)));
        }

        return result;
    }

    @GetMapping("/balanceSheet")
    public List<Account_ledger_v3> balanceSheet(
            @RequestParam(value = "CompanyName", required = false) String companyName,
            @RequestParam(value = "CustId", required = false) String custId,
            @RequestParam(value = "start", required = false, defaultValue = "2022-04-01") String start,
            @RequestParam(value = "end", required = false, defaultValue = "2025-09-17") String end) {
        logger.info("Fetching balance sheet for CompanyName: {}, CustId: {}, start: {}, end: {}",
                companyName, custId, start, end);
        validateDate(start, "start");
        validateDate(end, "end");
        return ledgerService.balanceSheet(start, end);
    }

    @GetMapping("/trial_balance")
    public List<Account_ledger_v3> trialBalance(
            @RequestParam("acType") String acType,
            @RequestParam(value = "CompanyName", required = false) String companyName,
            @RequestParam(value = "CustId", required = false) String custId) {
        logger.info("Fetching trial balance data for acType: {}, CompanyName: {}, CustId: {}", acType, companyName, custId);
        return ledgerService.trial_balance(acType);
    }

    @GetMapping("/trial_balance_total")
    public String trialBalanceTotal(
            @RequestParam(value = "CompanyName", required = false) String companyName,
            @RequestParam(value = "CustId", required = false) String custId) {
        logger.info("Fetching trial balance total for CompanyName: {}, CustId: {}", companyName, custId);
        return ledgerService.trial_balance_total();
    }

    @GetMapping("/trial_balanceBnDates")
    public List<Account_ledger_v3> trialBalanceBnDates(
            @RequestParam("acType") String acType,
            @RequestParam(value = "CompanyName", required = false) String companyName,
            @RequestParam(value = "CustId", required = false) String custId,
            @RequestParam(value = "start", required = false, defaultValue = "2022-04-01") String start,
            @RequestParam(value = "end", required = false, defaultValue = "2025-09-17") String end) {
        logger.info("Fetching trial balance data for acType: {}, CompanyName: {}, CustId: {}, start: {}, end: {}",
                acType, companyName, custId, start, end);
        validateDate(start, "start");
        validateDate(end, "end");
        return ledgerService.trial_balanceBnDate(acType, start, end);
    }

    @GetMapping("/trial_balance_totalBnDates")
    public String trialBalanceTotalBnDates(
            @RequestParam(value = "CompanyName", required = false) String companyName,
            @RequestParam(value = "CustId", required = false) String custId,
            @RequestParam(value = "start", required = false, defaultValue = "2022-04-01") String start,
            @RequestParam(value = "end", required = false, defaultValue = "2025-09-17") String end) {
        logger.info("Fetching trial balance total between dates for CompanyName: {}, CustId: {}, start: {}, end: {}",
                companyName, custId, start, end);
        validateDate(start, "start");
        validateDate(end, "end");
        return ledgerService.trial_balance_totalBnDate(start, end);
    }

    private void validateDate(String date, String paramName) {
        if (date != null && !date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Invalid " + paramName + ": must be in YYYY-MM-DD format");
        }
    }

    private String mapTitleToCategory(String title) {
        switch (title) {
            case "6": return "6"; // Direct Income
            case "7": return "7"; // Primary Income
            case "8": return "8"; // Direct Expenses
            case "9": return "9"; // Primary Expenses
            default: return title;
        }
    }
}
