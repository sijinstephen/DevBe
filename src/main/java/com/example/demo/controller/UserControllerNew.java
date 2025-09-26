package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.net.URLDecoder;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import com.itextpdf.html2pdf.HtmlConverter;

import com.example.demo.email.api.dto.MailRequest;
import com.example.demo.email.api.dto.MailResponse;
import com.example.demo.email.api.service.EmailService;
import com.example.demo.fileService.FileResponse;
import com.example.demo.fileService.FileStorageService;
import com.example.demo.fileService.MediaTypeUtils;
import com.example.demo.model.Account_group_v3;
import com.example.demo.model.Account_ledger_v3;
import com.example.demo.model.Account_title_v3;
import com.example.demo.model.Account_transactions_v3;
import com.example.demo.model.Account_user_v3;
import com.example.demo.model.Invoice;
import com.example.demo.model.Invoice_sub;
import com.example.demo.model.Invoice_template;
import com.example.demo.model.Profile;
import com.example.demo.repository.GroupServiceRepo;
import com.example.demo.repository.InvoiceSubRepo;
import com.example.demo.repository.LedgerServiceRepo;
import com.example.demo.repository.ProfileRepo;
import com.example.demo.repository.TemplateRepo;
import com.example.demo.repository.TransactionServiceRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.AcTitleService;
import com.example.demo.service.GroupService;
import com.example.demo.service.InvoiceService;
import com.example.demo.service.LedgerService;
import com.example.demo.service.ProfileService;
import com.example.demo.service.TransactionService;
import com.example.demo.service.UserService;

@CrossOrigin(origins = {"http://localhost:5173", "https://your-prod-domain.com"}, maxAge = 3600)
@RestController
@RequestMapping("")
public class UserControllerNew {
    private static final Logger logger = LoggerFactory.getLogger(UserControllerNew.class);

    @Autowired private EmailService service;
    @Autowired private AcTitleService acTitleService;
    @Autowired private GroupService groupService;
    @Autowired private LedgerService ledgerService;
    @Autowired private TransactionService transactionService;
    @Autowired private InvoiceService invoiceService;
    @Autowired private GroupServiceRepo groupServiceRepo;
    @Autowired private LedgerServiceRepo ledgerServiceRepo;
    @Autowired private TransactionServiceRepo transactionServiceRepo;
    @Autowired private FileStorageService fileStorageService;
    @Autowired private ServletContext servletContext;
    @Autowired private InvoiceSubRepo invoiceSubRepo;
    @Autowired private TemplateRepo templateRepo;
    @Autowired private Configuration config;
    @Autowired private ProfileRepo profileRepo;
    @Autowired private ProfileService profileService;
    @Autowired private UserRepo userRepo;
    @Autowired private UserService userService;

    // Inner class for login request body
    public static class LoginRequest {
        private String userName;
        private String password;

        public LoginRequest() {}

        public LoginRequest(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    // Global Exception Handler
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

    // Manual date validation
    private void validateDate(String date, String paramName) {
        if (date != null && !date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Invalid " + paramName + ": must be in YYYY-MM-DD format");
        }
    }

    // Map title values to category IDs
    private String mapTitleToCategory(String title) {
        switch (title) {
            case "6": return "6"; // Direct Income
            case "7": return "7"; // Primary Income
            case "8": return "8"; // Direct Expenses
            case "9": return "9"; // Primary Expenses
            default: return title;
        }
    }

    @PostMapping("/login")
    public List<Account_user_v3> logins(@RequestBody LoginRequest loginRequest) {
        logger.info("Login attempt for userName: {}", loginRequest.getUserName());
        List<Account_user_v3> users = userService.login(loginRequest.getUserName(), loginRequest.getPassword());
        logger.info("Login response size: {}", users.size());
        return users;
    }

    @PostMapping("/sendingEmail")
    public MailResponse sendEmail(@RequestBody MailRequest request) {
        logger.info("Sending email for invoice date: {}", request.getInv_date());
        Map<String, Object> model = new HashMap<>();
        model.put("invDate", request.getInv_date());
        model.put("invNo", request.getInv_no());
        model.put("gstNo", request.getGst_no());
        model.put("billAddress", request.getBill_address());
        model.put("place_of_supply", request.getPlace_of_supply());
        model.put("igstAmnt", request.getIgstAmnt());
        model.put("cgstAmnt", request.getCgstAmnt());
        model.put("sgstAmnt", request.getSgstAmnt());
        model.put("totalTaxAmnt", request.getTotalTaxAmnt());
        model.put("totalAmnt", request.getTotalAmnt());
        return service.sendEmail(request, model);
    }

    @PostMapping("/sendingEmailUpdate")
    public MailResponse sendEmailUpdate(@RequestBody MailRequest request) {
        logger.info("Sending email update for invoice date: {}", request.getInv_date());
        Map<String, Object> model = new HashMap<>();
        model.put("invDate", request.getInv_date());
        model.put("invNo", request.getInv_no());
        model.put("gstNo", request.getGst_no());
        model.put("billAddress", request.getBill_address());
        model.put("place_of_supply", request.getPlace_of_supply());
        model.put("igstAmnt", request.getIgstAmnt());
        model.put("cgstAmnt", request.getCgstAmnt());
        model.put("sgstAmnt", request.getSgstAmnt());
        model.put("totalTaxAmnt", request.getTotalTaxAmnt());
        model.put("totalAmnt", request.getTotalAmnt());
        return service.sendEmailUpdates(request, model);
    }

    @GetMapping("/ac_title")
    public List<Account_title_v3> ac_Title() {
        return acTitleService.ac_Titles();
    }

    @GetMapping("/acTitle_search")
    public List<Account_title_v3> acTitle_searchs(@RequestParam(value = "acId") String acId) {
        return acTitleService.acTitle_search(acId);
    }

    @PostMapping("/add_group")
    public Account_group_v3 add_Group(@RequestBody Account_group_v3 fp) {
        return groupService.add_Groups(fp);
    }

    @GetMapping("/view_group")
    public List<Account_group_v3> view_Group() {
        return groupService.view_Groups();
    }

    @GetMapping("/grp_sorting")
    public List<Account_group_v3> grp_sort(@RequestParam(value = "field") String field,
                                           @RequestParam(value = "type") String type) {
        return groupService.grp_sorts(field, type);
    }

    @GetMapping("/grp_by_id")
    public List<Account_group_v3> grp_idSearch(@RequestParam(value = "grpId") String grpId) {
        return groupService.grp_idSearchs(grpId);
    }

    @PutMapping("/grp_update/{grpId}")
    public ResponseEntity<Object> grp_updates(@RequestBody Account_group_v3 grp, @PathVariable int grpId) {
        logger.debug("Updating group: {}", grp);
        Optional<Account_group_v3> studentOptional = groupServiceRepo.findById(grpId);
        if (!studentOptional.isPresent()) return ResponseEntity.notFound().build();
        groupServiceRepo.save(grp);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/grp_delete/{id}", method = RequestMethod.DELETE)
    public String grp_delete(@PathVariable int id) {
        return groupService.grp_deletes(id);
    }

    @GetMapping("/grp_name_search")
    public List<Account_group_v3> grp_nameSearch(@RequestParam(value = "grpName") String grpName) {
        return groupService.grp_nameSearchs(grpName);
    }

    @PostMapping("/add_ledger")
    public Account_ledger_v3 add_Ledger(@RequestBody Account_ledger_v3 fp) {
        return ledgerService.add_Ledgers(fp);
    }

    @GetMapping("/last_id_search")
    public List<Account_ledger_v3> last_id_Search() {
        return ledgerService.last_idSearchs();
    }

    @PostMapping("/add_transaction")
    public Account_transactions_v3 add_Transaction(@RequestBody Account_transactions_v3 fp) {
        return transactionService.add_Transactions(fp);
    }

    @GetMapping("/ledger_name_search")
    public List<Account_ledger_v3> ledger_name_search(@RequestParam(value = "ledgerName") String ledgerName) {
        return ledgerService.ledger_name_searchs(ledgerName);
    }

    @GetMapping("/transactionDate")
    public List<Account_transactions_v3> transactionDate() {
        return transactionService.transactionDates();
    }

    @GetMapping("/list_ledger")
    public List<Account_ledger_v3> list_ledger() {
        return ledgerService.list_ledgers();
    }

    @GetMapping("/ledger_sorting")
    public List<Account_ledger_v3> ledger_sort(@RequestParam(value = "field") String field,
                                               @RequestParam(value = "type") String type) {
        return ledgerService.ledger_sorts(field, type);
    }

    @GetMapping("/ledger_bn_date")
    public List<Account_ledger_v3> ledger_bn_date(@RequestParam(value = "start") String start,
                                                  @RequestParam(value = "end") String end) {
        validateDate(start, "start");
        validateDate(end, "end");
        return ledgerService.ledger_bn_dates(start, end);
    }

    @RequestMapping(value = "/ledger_delete/{id}", method = RequestMethod.DELETE)
    public String ledger_delete(@PathVariable int id) {
        return ledgerService.ledger_deletes(id);
    }

    @GetMapping("/ledger_search")
    public List<Account_ledger_v3> ledger_Search(@RequestParam(value = "ledgerId") int ledgerId) {
        return ledgerService.ledger_Searchs(ledgerId);
    }

    @PutMapping("/ledger_update/{ledgerId}")
    public ResponseEntity<Object> ledger_updates(@RequestBody Account_ledger_v3 ledger, @PathVariable int ledgerId) {
        logger.debug("Updating ledger: {}", ledger);
        Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(ledgerId);
        if (!studentOptional.isPresent()) return ResponseEntity.notFound().build();
        ledgerServiceRepo.save(ledger);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ledger_transaction_search")
    public List<Account_transactions_v3> ledger_transaction_search(@RequestParam(value = "dbt_ac") String dbt_ac,
                                                                   @RequestParam(value = "crdt_ac") String crdt_ac) {
        return transactionService.ledger_transaction_searchs(dbt_ac, crdt_ac);
    }

    @GetMapping("/bank_name")
    public List<Account_ledger_v3> bank_name() {
        return ledgerService.bank_names();
    }

    @GetMapping("/list_transaction")
    public List<Account_transactions_v3> list_transaction() {
        return transactionService.list_transactions();
    }

    @GetMapping("/list_receipt")
    public List<Account_transactions_v3> list_receipt() {
        logger.info("Fetching receipt transactions");
        return transactionService.list_receipts();
    }

    @GetMapping("/transaction_sorting")
    public List<Account_transactions_v3> transaction_sort(@RequestParam(value = "field") String field,
                                                          @RequestParam(value = "type") String type) {
        return transactionService.transaction_sorts(field, type);
    }

    @GetMapping("/payment_bn_date")
    public List<Account_transactions_v3> payment_bn_date(@RequestParam(value = "start") String start,
                                                         @RequestParam(value = "end") String end) {
        validateDate(start, "start");
        validateDate(end, "end");
        return transactionService.payment_bn_dates(start, end);
    }

    @GetMapping("/transactions_account_bn_date")
    public List<Account_transactions_v3> transactions_account_bn_date
         (
        @RequestParam(value = "ledger") int ledger,
        @RequestParam(value = "start") String start,
        @RequestParam(value = "end") String end) 
        {
        validateDate(start, "start");
        validateDate(end, "end");
        return transactionService.View_Account_Statement(ledger,start, end);
    }


    @GetMapping("/transaction_search")
    public List<Account_transactions_v3> transaction_search(@RequestParam(value = "transactionId") String transactionId) {
        return transactionService.transaction_searchs(transactionId);
    }

    @PostMapping("/add_payment")
    public Account_transactions_v3 add_payment(@RequestBody Account_transactions_v3 fp) {
        return transactionService.add_payments(fp);
    }

    @RequestMapping(value = "/payment_delete/{id}", method = RequestMethod.DELETE)
    public String payment_delete(@PathVariable int id) {
        return transactionService.payment_deletes(id);
    }

    @PostMapping("/add_journalTransaction")
    public Account_transactions_v3 add_journalTransaction(@RequestBody Account_transactions_v3 fp) {
        return transactionService.add_journalTransactions(fp);
    }

    @GetMapping("/list_journal")
    public List<Account_transactions_v3> list_journal() {
        return transactionService.list_journals();
    }

    @GetMapping("/journal_sorting")
    public List<Account_transactions_v3> journal_sort(@RequestParam(value = "field") String field,
                                                      @RequestParam(value = "type") String type) {
        return transactionService.journal_sorts(field, type);
    }

    @GetMapping("/journal_bn_date")
    public List<Account_transactions_v3> journal_bn_date(@RequestParam(value = "start") String start,
                                                         @RequestParam(value = "end") String end) {
        validateDate(start, "start");
        validateDate(end, "end");
        return transactionService.journal_bn_dates(start, end);
    }

    @RequestMapping(value = "/journal_delete/{id}", method = RequestMethod.DELETE)
    public String journal_delete(@PathVariable int id) {
        return transactionService.journal_deletes(id);
    }

    @GetMapping("/journal_search")
    public List<Account_transactions_v3> journal_search(@RequestParam(value = "journalId") String journalId) {
        return transactionService.journal_searchs(journalId);
    }

    @PutMapping("/journal_update/{journalId}")
    public ResponseEntity<Object> journal_updates(@RequestBody Account_transactions_v3 journal, @PathVariable int journalId) {
        logger.debug("Updating journal: {}", journal);
        Optional<Account_transactions_v3> studentOptional = transactionServiceRepo.findById(journalId);
        if (!studentOptional.isPresent()) return ResponseEntity.notFound().build();
        transactionServiceRepo.save(journal);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add_invoice")
    public Invoice add_invoice(@RequestBody Invoice fp) {
        return invoiceService.add_invoices(fp);
    }

    @GetMapping("/invoiceData")
    public List<Invoice> invoiceDatas() {
        return invoiceService.invoiceDatas();
    }

    @GetMapping("/invoiceDataById")
    public List<Invoice> invoiceDataById(@RequestParam(value = "invoiceId") String invoiceId) {
        return invoiceService.invoiceDataByIds(invoiceId);
    }

    @GetMapping("/invoiceDataOnDashBoard")
    public List<Invoice> invoiceDataOnDashBoards() {
        return invoiceService.invoiceDataOnDashBoards();
    }

    @GetMapping("/invoiceDataByTransactionId")
    public List<Invoice> invoiceDataByTransactionIds(@RequestParam(value = "transactionId") String transactionId) {
        return invoiceService.invoiceDataByTransactionIds(transactionId);
    }

    @GetMapping("/tran_gen_Search")
    public List<Account_transactions_v3> tran_gen_Searchs(@RequestParam(value = "tran_gen_id") String tran_gen_id) {
        return transactionService.tran_gen_Search(tran_gen_id);
    }

    @RequestMapping(value = "/invoiceDelete/{inv_id}", method = RequestMethod.DELETE)
    public String invoiceDeletes(@PathVariable int inv_id) {
        return invoiceService.invoiceDelete(inv_id);
    }

    @PostMapping("/add_invoice_sub")
    public Invoice_sub add_invoice_sub(@RequestBody Invoice_sub fp) {
        return invoiceService.add_invoice_subs(fp);
    }

    @GetMapping("/invoiceSubDataById")
    public List<Invoice_sub> invoiceSubDataById(@RequestParam(value = "invoiceId") String invoiceId) {
        return invoiceService.invoiceSubDataByIds(invoiceId);
    }

    @GetMapping("/invoiceSubDelete/{inv_id}")
    public String invoiceSubDeletes(@PathVariable String inv_id) {
        return invoiceService.invoiceSubDelete(inv_id);
    }

    @GetMapping("/ac_dashboardCashData")
    public List<Account_ledger_v3> ac_dashboardCashDatas() {
        return ledgerService.ac_dashboardCashData();
    }

    @GetMapping("/ac_dashboardBankData")
    public List<Account_ledger_v3> ac_dashboardBankDatas() {
        return ledgerService.ac_dashboardBankData();
    }

    @GetMapping("/profit_loss")
    public List<Map<String, Object>> profitLoss(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "CompanyName", required = false) String companyName,
            @RequestParam(value = "CustId", required = false) String custId) {
        logger.info("Fetching profit/loss data for title: {}, CompanyName: {}, CustId: {}", title, companyName, custId);
        String category = mapTitleToCategory(title);
        List<Account_transactions_v3> transactions = transactionService.list_transactions();
        List<Account_ledger_v3> ledgers = ledgerService.list_ledgers();
        Map<String, String> ledgerTitles = ledgers.stream()
                .filter(l -> l.getAc_title() != null)
                .collect(Collectors.toMap(Account_ledger_v3::getLedger_name, Account_ledger_v3::getAc_title, (v1, v2) -> v1));
        
        // Aggregate transactions by ledger_name
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
                aggregated.get(ledgerName).compute("amount", (k, v) -> v == null ? amount : ((Double)v) + amount);
            }
        }

        List<Map<String, Object>> result = aggregated.values().stream()
                .filter(m -> ((Double)m.get("amount")) != 0)
                .collect(Collectors.toList());
        
        // Add total as branch field in the first item
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
            @RequestParam(value = "title") String title,
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
        
        // Aggregate transactions by ledger_name
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
                aggregated.get(ledgerName).compute("amount", (k, v) -> v == null ? amount : ((Double)v) + amount);
            }
        }

        List<Map<String, Object>> result = aggregated.values().stream()
                .filter(m -> ((Double)m.get("amount")) != 0)
                .collect(Collectors.toList());
        
        // Add total as branch field in the first item
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
            @RequestParam(value = "title") String title,
            @RequestParam(value = "CompanyName", required = false) String companyName,
            @RequestParam(value = "CustId", required = false) String custId,
            @RequestParam(value = "start", required = false, defaultValue = "2022-04-01") String start,
            @RequestParam(value = "end", required = false, defaultValue = "2025-09-17") String end) {
        logger.info("Fetching balance sheet and P&L data between dates for title: {}, CompanyName: {}, CustId: {}, start: {}, end: {}", 
                    title, companyName, custId, start, end);
        validateDate(start, "start");
        validateDate(end, "end");
        String category = mapTitleToCategory(title);
        List<Account_transactions_v3> transactions = transactionService.payment_bn_dates(start, end);
        List<Account_ledger_v3> ledgers = ledgerService.list_ledgers();
        Map<String, String> ledgerTitles = ledgers.stream()
                .filter(l -> l.getAc_title() != null)
                .collect(Collectors.toMap(Account_ledger_v3::getLedger_name, Account_ledger_v3::getAc_title, (v1, v2) -> v1));
        
        // Aggregate transactions by ledger_name
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
                aggregated.get(ledgerName).compute("amount", (k, v) -> v == null ? amount : ((Double)v) + amount);
            }
        }

        List<Map<String, Object>> result = aggregated.values().stream()
                .filter(m -> ((Double)m.get("amount")) != 0)
                .collect(Collectors.toList());
        
        // Add total as branch field in the first item
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
            @RequestParam(value = "acType") String acType,
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
            @RequestParam(value = "acType") String acType,
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

    @GetMapping("/updateledgerbalance")
    public ResponseEntity<String> updateLedgerBalance() {
        logger.info("Updating ledger balances");
        try {
            String result = ledgerService.updateledgerbalance();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error updating ledger balances: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update ledger balances: " + e.getMessage());
        }
    }

    @GetMapping("/debitAcData")
    public List<Account_ledger_v3> debitAcData(
            @RequestParam(value = "CompanyName") String companyName,
            @RequestParam(value = "CustId") String custId) {
        logger.info("Fetching debit account data for CompanyName: {}, CustId: {}", companyName, custId);
        return ledgerService.ledger_name_searchs(companyName);
    }

    @GetMapping("/creditAcData")
    public List<Account_ledger_v3> creditAcData(
            @RequestParam(value = "CompanyName") String companyName,
            @RequestParam(value = "CustId") String custId) {
        logger.info("Fetching credit account data for CompanyName: {}, CustId: {}", companyName, custId);
        return ledgerService.ledger_name_searchs(companyName);
    }

    @GetMapping("/dayBookData")
    public List<Account_transactions_v3> dayBookData(
            @RequestParam(value = "CompanyName") String companyName,
            @RequestParam(value = "CustId") String custId) {
        logger.info("Fetching day book data for CompanyName: {}, CustId: {}", companyName, custId);
        return transactionService.list_transactions();
    }

    
    @GetMapping("/index_customer_vendorApi")
    public List<Account_ledger_v3> index_customer_vendorApis(@RequestParam(value = "grp") String grp) {
        return ledgerService.index_customer_vendorApi(grp);
    }

    @GetMapping("/migrationDateAdd")
    public String migrationDateAdds(@RequestParam(value = "mgrDate") String mgrDate) {
        return ledgerService.migrationDateAdd(mgrDate);
    }

    @PutMapping("/uploadTemplateFile")
    public ResponseEntity<FileResponse> uploadTemplateFiles(@RequestParam("file") @NonNull MultipartFile file)
            throws IOException {
        logger.info("Uploading template file: {}", file.getOriginalFilename());
        String originalName = file.getOriginalFilename();
        if (originalName == null) {
            logger.error("File has no name");
            return ResponseEntity.badRequest().body(new FileResponse(null, null, null, 0));
        }
        String sanitizedName = originalName.replaceAll("\\s+", "-")
                                           .replaceAll("[^a-zA-Z0-9.-]", "")
                                           .toLowerCase();
        logger.info("Sanitized filename: {}", sanitizedName);
        MultipartFile sanitizedFile = new MultipartFileWrapper(file, sanitizedName);
        String fileName = fileStorageService.storeFile(sanitizedFile);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/image/")
                .path(fileName)
                .toUriString();
        String folder = "/image/";
        byte[] bytes = file.getBytes();
        Path filePath = Paths.get(folder + sanitizedName);
        Files.write(filePath, bytes);
        FileResponse fileResponse = new FileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        return new ResponseEntity<>(fileResponse, HttpStatus.OK);
    }

    private static class MultipartFileWrapper implements MultipartFile {
        private final @NonNull MultipartFile delegate;
        private final @NonNull String filename;

        MultipartFileWrapper(@NonNull MultipartFile delegate, @NonNull String filename) {
            this.delegate = delegate;
            this.filename = filename;
        }

        @Override
        public @NonNull String getName() {
            return delegate.getName();
        }

        @Override
        public @NonNull String getOriginalFilename() {
            return filename;
        }

        @Override
        public String getContentType() {
            return delegate.getContentType();
        }

        @Override
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        @Override
        public long getSize() {
            return delegate.getSize();
        }

        @Override
        public @NonNull byte[] getBytes() throws IOException {
            return delegate.getBytes();
        }

        @Override
        public @NonNull InputStream getInputStream() throws IOException {
            return delegate.getInputStream();
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            delegate.transferTo(dest);
        }
    }

    @GetMapping("/templateData")
    public List<Invoice_template> templateDatas(
            @RequestParam(value = "CompanyName", required = false) String companyName,
            @RequestParam(value = "CustId", required = false) String custId) {
        logger.info("Fetching template data for CompanyName: {}, CustId: {}", companyName, custId);
        List<Invoice_template> templates = templateRepo.templateDatas();
        if (companyName != null) {
            return templates.stream()
                    .filter(t -> companyName.equals(t.getTemplate_companyName()))
                    .collect(Collectors.toList());
        }
        return templates;
    }

    @PutMapping("/template_update/{template_Id}")
    public ResponseEntity<Object> template_updates(@RequestBody Invoice_template invoice_template,
                                                   @PathVariable int template_Id) {
        logger.debug("Updating template: {}", invoice_template);
        Optional<Invoice_template> studentOptional = templateRepo.findById(template_Id);
        if (!studentOptional.isPresent()) return ResponseEntity.notFound().build();
        templateRepo.save(invoice_template);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/invoiceImgFetch")
    public ResponseEntity<InputStreamResource> invoiceImgFetchs(@RequestParam(value = "fileName") String fileName)
            throws IOException {
        logger.info("Fetching invoice image: {}", fileName);
        String decodedName = URLDecoder.decode(fileName, StandardCharsets.UTF_8.name());
        String sanitizedName = decodedName.replaceAll("\\s+", "-")
                                          .replaceAll("[^a-zA-Z0-9.-]", "")
                                          .toLowerCase();
        logger.info("Sanitized filename: {}", sanitizedName);
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, sanitizedName);
        File file = new File("/image/" + sanitizedName);
        if (!file.exists()) {
            logger.warn("File not found: /image/{}", sanitizedName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }

    @GetMapping("/invoicePdfDownload")
    public HttpEntity<byte[]> invoicePdfDownloads(
            @RequestParam(value = "invDate") String invDate,
            @RequestParam(value = "invNo") String invNo,
            @RequestParam(value = "gstNo") String gstNo,
            @RequestParam(value = "billAddress") String billAddress,
            @RequestParam(value = "place_of_supply") String place_of_supply,
            @RequestParam(value = "igstAmnt") String igstAmnt,
            @RequestParam(value = "cgstAmnt") String cgstAmnt,
            @RequestParam(value = "sgstAmnt") String sgstAmnt,
            @RequestParam(value = "totalTaxAmnt") String totalTaxAmnt,
            @RequestParam(value = "totalAmnt") String totalAmnt,
            @RequestParam(value = "hsn") String hsn)
            throws IOException, TemplateException {
        logger.info("Generating PDF for invoice: invNo={}", invNo);
        Map<String, Object> model = new HashMap<>();
        model.put("invDate", invDate);
        model.put("invNo", invNo);
        model.put("gstNo", gstNo);
        model.put("billAddress", billAddress);
        model.put("place_of_supply", place_of_supply);
        model.put("igstAmnt", igstAmnt);
        model.put("cgstAmnt", cgstAmnt);
        model.put("sgstAmnt", sgstAmnt);
        model.put("totalTaxAmnt", totalTaxAmnt);
        model.put("totalAmnt", totalAmnt);
        model.put("hsn", hsn);

        List<Invoice> li = invoiceService.invoiceDatas();
        int lastInvId = 0;
        if (li.size() > 0) {
            int lastId = li.size() - 1;
            lastInvId = li.get(lastId).getInv_id();
            List<Invoice_sub> li1 = invoiceSubRepo.invoiceSubDataById(Integer.toString(lastInvId));
            if (li1.size() > 0) {
                model.put("invoiceSubData", li1);
            }
        }

        List<Invoice_template> li5 = templateRepo.templateDatas();
        if (li5.size() > 0) {
            String payTo = li5.get(0).getTemplate_payTo();
            String[] payToArray = payTo != null ? payTo.split("\\r?\\n") : new String[]{};
            String cName = li5.get(0).getTemplate_companyName();
            String cAddress = li5.get(0).getTemplate_companyAddress();
            String cContact = li5.get(0).getTemplate_companyContact();
            String name = li5.get(0).getTemplate_Name();
            String logoImg = li5.get(0).getTemplate_logo();
            String signImg = li5.get(0).getTemplate_sig();
            model.put("templateCompanyName", cName != null ? cName.split("\\r?\\n") : new String[]{});
            model.put("templateCompanyAddress", cAddress != null ? cAddress.split("\\r?\\n") : new String[]{});
            model.put("templateCompanyContact", cContact != null ? cContact.split("\\r?\\n") : new String[]{});
            model.put("templateName", name != null ? name.split("\\r?\\n") : new String[]{});
            model.put("templateData", payToArray);
            model.put("templateLogo", logoImg != null ? logoImg.replaceAll("\\s+", "-").replaceAll("[^a-zA-Z0-9.-]", "").toLowerCase() : "");
            model.put("templateSign", signImg != null ? signImg.replaceAll("\\s+", "-").replaceAll("[^a-zA-Z0-9.-]", "").toLowerCase() : "");
        }

        Template t = config.getTemplate("email-template-new.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        ByteArrayOutputStream baos = generatePdf(html);

        String fileName = "invoice.pdf";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName.replace(" ", "_"));
        header.setContentLength(baos.toByteArray().length);
        return new HttpEntity<>(baos.toByteArray(), header);
    }

    @GetMapping("/invoicePdfDownloadDashboard")
    public HttpEntity<byte[]> invoicePdfDownloadsDashboard(
            @RequestParam(value = "inv_id") int inv_id,
            @RequestParam(value = "invDate") String invDate,
            @RequestParam(value = "invNo") String invNo,
            @RequestParam(value = "gstNo") String gstNo,
            @RequestParam(value = "billAddress") String billAddress,
            @RequestParam(value = "place_of_supply") String place_of_supply,
            @RequestParam(value = "igstAmnt") String igstAmnt,
            @RequestParam(value = "cgstAmnt") String cgstAmnt,
            @RequestParam(value = "sgstAmnt") String sgstAmnt,
            @RequestParam(value = "totalTaxAmnt") String totalTaxAmnt,
            @RequestParam(value = "totalAmnt") String totalAmnt,
            @RequestParam(value = "hsn") String hsn,
            @RequestParam(value = "CompanyName") String companyName,
            @RequestParam(value = "CustId") String custId,
            @RequestParam(value = "ifsc") String ifsc,
            @RequestParam(value = "swift_code") String swift_code,
            @RequestParam(value = "gstId") String gstId)
            throws IOException, TemplateException {
        logger.info("Generating PDF for dashboard invoice: inv_id={}, CompanyName={}, CustId={}", inv_id, companyName, custId);
        Map<String, Object> model = new HashMap<>();
        model.put("invDate", invDate);
        model.put("invNo", invNo);
        model.put("gstNo", gstNo);
        model.put("billAddress", billAddress);
        model.put("place_of_supply", place_of_supply);
        model.put("igstAmnt", igstAmnt);
        model.put("cgstAmnt", cgstAmnt);
        model.put("sgstAmnt", sgstAmnt);
        model.put("totalTaxAmnt", totalTaxAmnt);
        model.put("totalAmnt", totalAmnt);
        model.put("hsn", hsn);
        model.put("ifsc", ifsc);
        model.put("swift_code", swift_code);
        model.put("gstId", gstId);

        List<Invoice_sub> li1 = invoiceSubRepo.invoiceSubDataById(Integer.toString(inv_id));
        if (li1.size() > 0) {
            model.put("invoiceSubData", li1);
        }

        List<Invoice_template> li5 = templateRepo.templateDatas();
        if (companyName != null) {
            li5 = li5.stream()
                     .filter(t -> companyName.equals(t.getTemplate_companyName()))
                     .collect(Collectors.toList());
        }
        if (li5.size() > 0) {
            String payTo = li5.get(0).getTemplate_payTo();
            String[] payToArray = payTo != null ? payTo.split("\\r?\\n") : new String[]{};
            String cName = li5.get(0).getTemplate_companyName();
            String cAddress = li5.get(0).getTemplate_companyAddress();
            String cContact = li5.get(0).getTemplate_companyContact();
            String name = li5.get(0).getTemplate_Name();
            String logoImg = li5.get(0).getTemplate_logo();
            String signImg = li5.get(0).getTemplate_sig();
            model.put("templateCompanyName", cName != null ? cName.split("\\r?\\n") : new String[]{});
            model.put("templateCompanyAddress", cAddress != null ? cAddress.split("\\r?\\n") : new String[]{});
            model.put("templateCompanyContact", cContact != null ? cContact.split("\\r?\\n") : new String[]{});
            model.put("templateName", name != null ? name.split("\\r?\\n") : new String[]{});
            model.put("templateData", payToArray);
            model.put("templateLogo", logoImg != null ? logoImg.replaceAll("\\s+", "-").replaceAll("[^a-zA-Z0-9.-]", "").toLowerCase() : "");
            model.put("templateSign", signImg != null ? signImg.replaceAll("\\s+", "-").replaceAll("[^a-zA-Z0-9.-]", "").toLowerCase() : "");
        } else {
            logger.warn("No template found for CompanyName: {}", companyName);
        }

        Template t = config.getTemplate("email-template-new.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        ByteArrayOutputStream baos = generatePdf(html);

        String fileName = "invoice.pdf";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName.replace(" ", "_"));
        header.setContentLength(baos.toByteArray().length);
        return new HttpEntity<>(baos.toByteArray(), header);
    }

    private ByteArrayOutputStream generatePdf(String html) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            HtmlConverter.convertToPdf(html, baos);
            return baos;
        } catch (Exception e) {
            logger.error("Error generating PDF: {}", e.getMessage(), e);
            return baos;
        }
    }

    @PostMapping("/add_profile")
    public Profile add_Profile(@RequestBody Profile fp) {
        return profileService.add_Profiles(fp);
    }

    @GetMapping("/profileData")
    public List<Profile> profileDatas(
            @RequestParam(value = "CompanyName", required = false) String companyName,
            @RequestParam(value = "CustId", required = false) String custId) {
        logger.info("Fetching profile data for CompanyName: {}, CustId: {}", companyName, custId);
        List<Profile> profiles = profileService.profileData();
        if (companyName != null && custId != null) {
            return profiles.stream()
                           .filter(p -> companyName.equals(p.getOrganization_name()) && custId.equals(p.getCompany_id()))
                           .collect(Collectors.toList());
        }
        return profiles;
    }

    @PutMapping("/profile_update/{organization_id}")
    public ResponseEntity<Object> profile_updates(@RequestBody Profile profile, @PathVariable int organization_id) {
        logger.debug("Updating profile: {}", profile);
        Optional<Profile> studentOptional = profileRepo.findById(organization_id);
        if (!studentOptional.isPresent()) return ResponseEntity.notFound().build();
        profileRepo.save(profile);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userSearch")
    public List<Account_user_v3> userSearch(@RequestParam(value = "userName") String userName) {
        return userService.userSearchs(userName);
    }

    @PutMapping("/forgot_password/{id}")
    public ResponseEntity<Object> forgot_password(@RequestBody Account_user_v3 account_user_v3, @PathVariable int id) {
        logger.debug("Updating user password for id: {}", id);
        Optional<Account_user_v3> studentOptional = userRepo.findById(id);
        if (!studentOptional.isPresent()) return ResponseEntity.notFound().build();
        userRepo.save(account_user_v3);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userList")
    public List<Account_user_v3> userList() {
        return userService.userLists();
    }

    @PostMapping("/addUser")
    public Account_user_v3 addUser(@RequestBody Account_user_v3 fp) {
        return userService.addUsers(fp);
    }

    @PutMapping("/editUser/{id}")
    public ResponseEntity<Object> editUser(@RequestBody Account_user_v3 account_user_v3, @PathVariable int id) {
        logger.debug("Editing user: {}", account_user_v3);
        Optional<Account_user_v3> studentOptional = userRepo.findById(id);
        if (!studentOptional.isPresent()) return ResponseEntity.notFound().build();
        userRepo.save(account_user_v3);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/userDelete/{id}", method = RequestMethod.DELETE)
    public String userDelete(@PathVariable int id) {
        return userService.userDeletes(id);
    }

    @GetMapping("/userSearchById")
    public List<Account_user_v3> userSearchById(@RequestParam(value = "id") int id) {
        return userService.userSearchByIds(id);
    }
// New API to update invoice and return updated object with 200 status

    @PutMapping("/invoice_update/{inv_id}")
public ResponseEntity<Invoice> invoiceUpdate(
        @PathVariable int inv_id,
        @RequestBody Invoice body
) {
    // ensure path param wins
    body.setInv_id(inv_id);

    // do the update
    Invoice saved = invoiceService.update_invoices(body);

    // return 200 with the updated header
    return ResponseEntity.ok(saved);
}
// Invoice sub update and delete APIs

@PutMapping("/invoice_sub/{inv_sub_id}")
public ResponseEntity<Invoice_sub> updateInvoiceSub(
        @PathVariable int inv_sub_id,
        @RequestBody Invoice_sub body
) {
    // load existing; 404 if not found
    Invoice_sub existing = invoiceSubRepo.findById(inv_sub_id)
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND,
                "invoice_sub not found: " + inv_sub_id));

    // update mutable fields only (keep original created_date/time if you prefer)
    existing.setInv_id(body.getInv_id() != null ? body.getInv_id() : existing.getInv_id());
    existing.setDescription(body.getDescription() != null ? body.getDescription() : existing.getDescription());
    existing.setHsn(body.getHsn() != null ? body.getHsn() : existing.getHsn());
    existing.setQty(body.getQty() != null ? body.getQty() : existing.getQty());
    existing.setAmount(body.getAmount() != null ? body.getAmount() : existing.getAmount());
    existing.setTax(body.getTax() != null && !body.getTax().isEmpty() ? body.getTax() : (existing.getTax() != null ? existing.getTax() : "0"));
    existing.setRemarks(body.getRemarks() != null ? body.getRemarks() : existing.getRemarks());

    // OPTIONAL: only if you truly want to overwrite timestamps
    if (body.getCreated_date() != null) existing.setCreated_date(body.getCreated_date());
    if (body.getCreated_time() != null) existing.setCreated_time(body.getCreated_time());

    Invoice_sub saved = invoiceSubRepo.save(existing);
    return ResponseEntity.ok(saved);
}



}