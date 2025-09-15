package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
import com.example.demo.repository.InvoiceRepo;
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
import com.example.demo.service.TemplateService;
import com.example.demo.service.TransactionService;
import com.example.demo.service.UserService;

@CrossOrigin(origins = {"http://localhost:5173", "https://your-prod-domain.com"})
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
    @Autowired private InvoiceRepo invoiceRepo;
    @Autowired private InvoiceSubRepo invoiceSubRepo;
    @Autowired private TemplateService templateService;
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
        return groupService.grp_idSearchSearch(grpId);
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

    // Ledger & Account Transaction
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

    // Payments
    @GetMapping("/bank_name")
    public List<Account_ledger_v3> bank_name() {
        return ledgerService.bank_names();
    }

    @GetMapping("/list_transaction")
    public List<Account_transactions_v3> list_transaction() {
        return transactionService.list_transactions();
    }

    @GetMapping("/transaction_sorting")
    public List<Account_transactions_v3> transaction_sort(@RequestParam(value = "field") String field,
                                                          @RequestParam(value = "type") String type) {
        return transactionService.transaction_sorts(field, type);
    }

    @GetMapping("/payment_bn_date")
    public List<Account_transactions_v3> payment_bn_date(@RequestParam(value = "start") String start,
                                                         @RequestParam(value = "end") String end) {
        return transactionService.payment_bn_dates(start, end);
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

    // Journal
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

    // Invoice
    @PostMapping("/add_invoice")
    public Invoice add_invoice(@RequestBody Invoice fp) {
        return invoiceService.add_invoices(fp);
    }

    @GetMapping("/invoiceData")
    public List<Invoice> invoiceDatas() {
        return invoiceService.invoiceData();
    }

    @GetMapping("/invoiceDataById")
    public List<Invoice> invoiceDataById(@RequestParam(value = "invoiceId") String invoiceId) {
        return invoiceService.invoiceDataByIds(invoiceId);
    }

    @GetMapping("/invoiceDataOnDashBoard")
    public List<Invoice> invoiceDataOnDashBoards() {
        return invoiceService.invoiceDataOnDashBoard();
    }

    @GetMapping("/invoiceDataByTransactionId")
    public List<Invoice> invoiceDataByTransactionIds(@RequestParam(value = "transactionId") String transactionId) {
        return invoiceService.invoiceDataByTransactionId(transactionId);
    }

    @GetMapping("/tran_gen_Search")
    public List<Account_transactions_v3> tran_gen_Searchs(@RequestParam(value = "tran_gen_id") String tran_gen_id) {
        return transactionService.tran_gen_Search(tran_gen_id);
    }

    @RequestMapping(value = "/invoiceDelete/{inv_id}", method = RequestMethod.DELETE)
    public String invoiceDeletes(@PathVariable int inv_id) {
        return invoiceService.invoiceDelete(inv_id);
    }

    // Invoice Sub
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

    // Dashboard
    @GetMapping("/ac_dashboardCashData")
    public List<Account_ledger_v3> ac_dashboardCashDatas() {
        return ledgerService.ac_dashboardCashData();
    }

    @GetMapping("/ac_dashboardBankData")
    public List<Account_ledger_v3> ac_dashboardBankDatas() {
        return ledgerService.ac_dashboardBankData();
    }

    // Index Page
    @GetMapping("/index_customer_vendorApi")
    public List<Account_ledger_v3> index_customer_vendorApis(@RequestParam(value = "grp") String grp) {
        return ledgerService.index_customer_vendorApi(grp);
    }

    // Migration Date
    @GetMapping("/migrationDateAdd")
    public String migrationDateAdds(@RequestParam(value = "mgrDate") String mgrDate) {
        return ledgerService.migrationDateAdd(mgrDate);
    }

    // Template File Upload
    @PutMapping("/uploadTemplateFile")
    public ResponseEntity<FileResponse> uploadTemplateFiles(@RequestParam("file") MultipartFile file)
            throws IOException {
        logger.info("Uploading template file: {}", file.getOriginalFilename());
        String originalName = file.getOriginalFilename();
        String sanitizedName = originalName.replaceAll("\\s+", "-")
                                           .replaceAll("[^a-zA-Z0-9.-]", "")
                                           .toLowerCase();
        MultipartFile sanitizedFile = new MultipartFileWrapper(file, sanitizedName); // Wrapper to override filename
        String fileName = fileStorageService.storeFile(sanitizedFile);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/image")
                .path(fileName)
                .toUriString();
        String folder = "/frontend/public/assets/images/";
        byte[] bytes = file.getBytes();
        Path filePath = Paths.get(folder + sanitizedName);
        Files.write(filePath, bytes);
        FileResponse fileResponse = new FileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        return new ResponseEntity<>(fileResponse, HttpStatus.OK);
    }

    // Wrapper class to override MultipartFile filename
    private static class MultipartFileWrapper implements MultipartFile {
        private final MultipartFile delegate;
        private final String filename;

        MultipartFileWrapper(MultipartFile delegate, String filename) {
            this.delegate = delegate;
            this.filename = filename;
        }

        @Override
        public String getName() {
            return delegate.getName();
        }

        @Override
        public String getOriginalFilename() {
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
        public byte[] getBytes() throws IOException {
            return delegate.getBytes();
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return delegate.getInputStream();
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            delegate.transferTo(dest);
        }
    }

    @GetMapping("/templateData")
    public List<Invoice_template> templateDatas() {
        return templateService.templateData();
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
            return ResponseEntity.notFound().build();
        }
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }

    @GetMapping("/invoicePdfDownload")
    public HttpEntity<byte[]> invoicePdfDownloads(@RequestParam(value = "invDate") String invDate,
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

        List<Invoice> li = invoiceRepo.invoiceData();
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
            String[] payToArray = payTo.split("\\r?\\n");
            String cName = li5.get(0).getTemplate_companyName();
            String cAddress = li5.get(0).getTemplate_companyAddress();
            String cContact = li5.get(0).getTemplate_companyContact();
            String name = li5.get(0).getTemplate_Name();
            String logoImg = li5.get(0).getTemplate_logo();
            String signImg = li5.get(0).getTemplate_sig();
            model.put("templateCompanyName", cName.split("\\r?\\n"));
            model.put("templateCompanyAddress", cAddress.split("\\r?\\n"));
            model.put("templateCompanyContact", cContact.split("\\r?\\n"));
            model.put("templateName", name.split("\\r?\\n"));
            model.put("templateData", payToArray);
            model.put("templateLogo", logoImg);
            model.put("templateSign", signImg);
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
    public HttpEntity<byte[]> invoicePdfDownloadsDashboard(@RequestParam(value = "inv_id") int inv_id,
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
        logger.info("Generating PDF for dashboard invoice: inv_id={}", inv_id);
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

        List<Invoice_sub> li1 = invoiceSubRepo.invoiceSubDataById(Integer.toString(inv_id));
        if (li1.size() > 0) {
            model.put("invoiceSubData", li1);
        }

        List<Invoice_template> li5 = templateRepo.templateDatas();
        if (li5.size() > 0) {
            String payTo = li5.get(0).getTemplate_payTo();
            String[] payToArray = payTo.split("\\r?\\n");
            String cName = li5.get(0).getTemplate_companyName();
            String cAddress = li5.get(0).getTemplate_companyAddress();
            String cContact = li5.get(0).getTemplate_companyContact();
            String name = li5.get(0).getTemplate_Name();
            String logoImg = li5.get(0).getTemplate_logo();
            String signImg = li5.get(0).getTemplate_sig();
            model.put("templateCompanyName", cName.split("\\r?\\n"));
            model.put("templateCompanyAddress", cAddress.split("\\r?\\n"));
            model.put("templateCompanyContact", cContact.split("\\r?\\n"));
            model.put("templateName", name.split("\\r?\\n"));
            model.put("templateData", payToArray);
            model.put("templateLogo", logoImg);
            model.put("templateSign", signImg);
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

    // Helper: HTML -> PDF using iText 7 html2pdf
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

    // Profile Page
    @PostMapping("/add_profile")
    public Profile add_Profile(@RequestBody Profile fp) {
        return profileService.add_Profiles(fp);
    }

    @GetMapping("/profileData")
    public List<Profile> profileDatas() {
        return profileService.profileData();
    }

    @PutMapping("/profile_update/{organization_id}")
    public ResponseEntity<Object> profile_updates(@RequestBody Profile profile, @PathVariable int organization_id) {
        logger.debug("Updating profile: {}", profile);
        Optional<Profile> studentOptional = profileRepo.findById(organization_id);
        if (!studentOptional.isPresent()) return ResponseEntity.notFound().build();
        profileRepo.save(profile);
        return ResponseEntity.noContent().build();
    }

    // User Management
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
}