package com.example.demo.controller.transaction;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.report.ReportController;
import com.example.demo.email.api.service.EmailService;
import com.example.demo.fileService.FileStorageService;
import com.example.demo.model.Account_ledger_v3;
import com.example.demo.model.Account_transactions_v3;
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

import freemarker.template.Configuration;
import jakarta.servlet.ServletContext;

@CrossOrigin(origins = {"http://localhost:5173", "https://your-prod-domain.com"}, maxAge = 3600)
@RestController
@RequestMapping("")

public class TransactionController {

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
    
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

 
    @GetMapping("/ledger_transaction_search")
    public List<Account_transactions_v3> ledger_transaction_search(@RequestParam(value = "dbt_ac") String dbt_ac,
                                                                   @RequestParam(value = "crdt_ac") String crdt_ac) {
        return transactionService.ledger_transaction_searchs(dbt_ac, crdt_ac);
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








        @GetMapping("/tran_gen_Search")
    public List<Account_transactions_v3> tran_gen_Searchs(@RequestParam(value = "tran_gen_id") String tran_gen_id) {
        return transactionService.tran_gen_Search(tran_gen_id);
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
    
           @GetMapping("/transactionDate")
        public List<Account_transactions_v3> transactionDate() {
            return transactionService.transactionDates();
        }
                @GetMapping("/txHistoryFull")
        public List<Account_transactions_v3> txHistoryFull() {
            return transactionService.transactionHistory_full();
        }

}

