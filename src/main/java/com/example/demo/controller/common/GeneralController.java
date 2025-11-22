package com.example.demo.controller.common;
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
import com.example.demo.dto.MessageProfile;
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
import com.example.demo.model.Defaults;
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
import com.example.demo.dto.MessageProfile;
import com.example.demo.model.Profile;

@CrossOrigin(origins = {"http://localhost:5173", "https://your-prod-domain.com"}, maxAge = 3600)
@RestController
@RequestMapping("")
public class GeneralController {
    private static final Logger logger = LoggerFactory.getLogger(GeneralController.class);

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
            logger.info("Profle data found. Attempting to fetch company profile data.",profiles);
                        return profiles.stream()
                           .filter(p -> companyName.equals(p.getOrganization_name()) && custId.equals(p.getCompany_id()))
                           .collect(Collectors.toList());

        }
        if (profiles.isEmpty()|| profiles==null) 
        {
            logger.info("No profile data found. Attempting to fetch default profile data.");
            profiles = profileService.defaultProfileData();
            logger.info("Default Profile data found. Attempting to fetch default profile data.",profiles.size());
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

    @GetMapping("/profileDataNew")
public MessageProfile getProfileData(
        @RequestParam("CompanyName") String companyName,
        @RequestParam("CustId") String custId) {

    // ✅ Typed list instead of Object
    List<Profile> list = profileService.getProfileData(companyName, custId);

    MessageProfile msg = MessageProfile.newReadMessage();
    MessageProfile.Payload payload = msg.payload;

    // If nothing found, return empty-but-valid message
    if (list == null || list.isEmpty()) {
        return msg;
    }

    Profile p = list.get(0);  // ✅ Profile, not Object

    // 🔹 Map entity → JSON-schema payload
// ------------ ORGANIZATION ------------
payload.organization.organization_name = p.getOrganization_name();
payload.organization.industry           = p.getIndustry();
payload.organization.street1            = p.getStreet1();
payload.organization.street2            = p.getStreet2();
payload.organization.city               = p.getCity();
payload.organization.state              = p.getState();
payload.organization.zip                = p.getZip();

// ------------ BANKING ------------
payload.banking.ifsc   = p.getIfsc();
payload.banking.acc_no = p.getAcc_no();
payload.banking.bank   = p.getBank();
payload.banking.branch = p.getBranch();

// ------------ GST ------------
payload.gst.gst_id = p.getGst_id();

// ------------ CONTACT ------------
payload.contact.phone   = p.getPhone();
payload.contact.website = p.getWebsite();
payload.contact.gmail   = p.getGmail();

// ------------ META ------------
payload.meta.signatory_name        = p.getSignatory_name();
payload.meta.signatory_designation = p.getSignatory_designation();
payload.meta.company_name          = p.getCompany_id();      // ✔ correct field
payload.meta.cust_id               = String.valueOf(p.getOrganization_id()); // ✔ correct field
payload.meta.fiscal_year           = p.getFiscal_year();
payload.meta.date_format           = p.getDate_format();

    return msg;
}



}
