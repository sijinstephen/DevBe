package com.example.demo.controller.dashboard;
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
public class DashboardController {

    @Autowired
    private LedgerService ledgerService;

    @GetMapping("/ac_dashboardCashData")
    public List<Account_ledger_v3> ac_dashboardCashDatas() {
        return ledgerService.ac_dashboardCashData();
    }

    @GetMapping("/ac_dashboardBankData")
    public List<Account_ledger_v3> ac_dashboardBankDatas() {
        return ledgerService.ac_dashboardBankData();
    }

    
    
}
