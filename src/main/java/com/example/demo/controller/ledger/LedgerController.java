package com.example.demo.controller.ledger;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MissingServletRequestParameterException;

import com.example.demo.controller.report.ReportController;
import com.example.demo.email.api.service.EmailService;
import com.example.demo.fileService.FileStorageService;
import com.example.demo.model.Account_group_v3;
import com.example.demo.model.Account_ledger_v3;
import com.example.demo.model.Account_title_v3;
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

public class LedgerController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

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

     @GetMapping("/listAllAccounts")
    public List<Account_ledger_v3> listAllAccounts(){
        logger.info("Fetching all accounts");
        return ledgerService.ledger_listAll();
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
}
