package com.example.demo.controller.invoice;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.servlet.ServletContext;

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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.itextpdf.html2pdf.HtmlConverter;

import com.example.demo.fileService.FileResponse;
import com.example.demo.fileService.FileStorageService;
import com.example.demo.fileService.MediaTypeUtils;
import com.example.demo.model.Invoice;
import com.example.demo.model.Invoice_sub;
import com.example.demo.model.Invoice_template;
import com.example.demo.repository.InvoiceSubRepo;
import com.example.demo.repository.TemplateRepo;
import com.example.demo.service.InvoiceService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@CrossOrigin(origins = {"http://localhost:5173", "https://your-prod-domain.com"}, maxAge = 3600)
@RestController
@RequestMapping("")
public class InvoiceController {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    private final InvoiceService invoiceService;
    private final InvoiceSubRepo invoiceSubRepo;
    private final TemplateRepo templateRepo;
    private final FileStorageService fileStorageService;
    private final ServletContext servletContext;
    private final Configuration config;

    @Autowired
    public InvoiceController(
            InvoiceService invoiceService,
            InvoiceSubRepo invoiceSubRepo,
            TemplateRepo templateRepo,
            FileStorageService fileStorageService,
            ServletContext servletContext,
            Configuration config) {
        this.invoiceService = invoiceService;
        this.invoiceSubRepo = invoiceSubRepo;
        this.templateRepo = templateRepo;
        this.fileStorageService = fileStorageService;
        this.servletContext = servletContext;
        this.config = config;
    }

    @PostMapping("/add_invoice")
    public Invoice add_invoice(@RequestBody Invoice invoice) {
        return invoiceService.add_invoices(invoice);
    }

    @GetMapping("/invoiceData")
    public List<Invoice> invoiceDatas() {
        return invoiceService.invoiceDatas();
    }

    @GetMapping("/invoiceDataById")
    public List<Invoice> invoiceDataById(@RequestParam("invoiceId") String invoiceId) {
        return invoiceService.invoiceDataByIds(invoiceId);
    }

    @GetMapping("/invoiceDataOnDashBoard")
    public List<Invoice> invoiceDataOnDashBoards() {
        return invoiceService.invoiceDataOnDashBoards();
    }

    @GetMapping("/invoiceDataByTransactionId")
    public List<Invoice> invoiceDataByTransactionIds(@RequestParam("transactionId") String transactionId) {
        return invoiceService.invoiceDataByTransactionIds(transactionId);
    }

    @RequestMapping(value = "/invoiceDelete/{inv_id}", method = RequestMethod.DELETE)
    public String invoiceDeletes(@PathVariable int inv_id) {
        return invoiceService.invoiceDelete(inv_id);
    }

    @PutMapping("/invoice_update/{inv_id}")
    public ResponseEntity<Invoice> invoiceUpdate(@PathVariable int inv_id, @RequestBody Invoice invoice) {
        invoice.setInv_id(inv_id);
        Invoice saved = invoiceService.update_invoices(invoice);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/add_invoice_sub")
    public Invoice_sub add_invoice_sub(@RequestBody Invoice_sub invoiceSub) {
        return invoiceService.add_invoice_subs(invoiceSub);
    }

    @GetMapping("/invoiceSubDataById")
    public List<Invoice_sub> invoiceSubDataById(@RequestParam("invoiceId") String invoiceId) {
        return invoiceService.invoiceSubDataByIds(invoiceId);
    }

    @GetMapping("/invoiceSubDelete/{inv_id}")
    public String invoiceSubDeletes(@PathVariable String inv_id) {
        return invoiceService.invoiceSubDelete(inv_id);
    }

    @PutMapping("/invoice_sub/{inv_sub_id}")
    public ResponseEntity<Invoice_sub> updateInvoiceSub(@PathVariable int inv_sub_id, @RequestBody Invoice_sub body) {
        Invoice_sub existing = invoiceSubRepo.findById(inv_sub_id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "invoice_sub not found: " + inv_sub_id));

        existing.setInv_id(body.getInv_id() != null ? body.getInv_id() : existing.getInv_id());
        existing.setDescription(body.getDescription() != null ? body.getDescription() : existing.getDescription());
        existing.setHsn(body.getHsn() != null ? body.getHsn() : existing.getHsn());
        existing.setQty(body.getQty() != null ? body.getQty() : existing.getQty());
        existing.setAmount(body.getAmount() != null ? body.getAmount() : existing.getAmount());
        existing.setTax(body.getTax() != null && !body.getTax().isEmpty() ? body.getTax()
                : (existing.getTax() != null ? existing.getTax() : "0"));
        existing.setRemarks(body.getRemarks() != null ? body.getRemarks() : existing.getRemarks());

        if (body.getCreated_date() != null) {
            existing.setCreated_date(body.getCreated_date());
        }
        if (body.getCreated_time() != null) {
            existing.setCreated_time(body.getCreated_time());
        }

        Invoice_sub saved = invoiceSubRepo.save(existing);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/uploadTemplateFile")
    public ResponseEntity<FileResponse> uploadTemplateFiles(@RequestParam("file") @NonNull MultipartFile file)
            throws IOException {
        logger.info("Uploading template file: {}", file.getOriginalFilename());
        String originalName = file.getOriginalFilename();
        if (originalName == null) {
            logger.error("File has no original name");
            return ResponseEntity.badRequest().body(new FileResponse(null, null, null, 0));
        }

        String sanitizedName = sanitizeAssetName(originalName);
        MultipartFile sanitizedFile = new MultipartFileWrapper(file, sanitizedName);

        String fileName = fileStorageService.storeFile(sanitizedFile);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/image/")
                .path(fileName)
                .toUriString();

        byte[] bytes = file.getBytes();
        Path filePath = Paths.get("/image/").resolve(sanitizedName);
        Files.write(filePath, bytes);

        FileResponse response = new FileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        return new ResponseEntity<>(response, HttpStatus.OK);
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
    public ResponseEntity<Object> template_updates(@RequestBody Invoice_template invoiceTemplate,
                                                   @PathVariable int template_Id) {
        Optional<Invoice_template> existing = templateRepo.findById(template_Id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        templateRepo.save(invoiceTemplate);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/invoiceImgFetch")
    public ResponseEntity<InputStreamResource> invoiceImgFetchs(@RequestParam("fileName") String fileName)
            throws IOException {
        logger.info("Fetching invoice image: {}", fileName);
        String decodedName = URLDecoder.decode(fileName, StandardCharsets.UTF_8.name());
        String sanitizedName = sanitizeAssetName(decodedName);

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
            @RequestParam("invDate") String invDate,
            @RequestParam("invNo") String invNo,
            @RequestParam("gstNo") String gstNo,
            @RequestParam("billAddress") String billAddress,
            @RequestParam("place_of_supply") String place_of_supply,
            @RequestParam("igstAmnt") String igstAmnt,
            @RequestParam("cgstAmnt") String cgstAmnt,
            @RequestParam("sgstAmnt") String sgstAmnt,
            @RequestParam("totalTaxAmnt") String totalTaxAmnt,
            @RequestParam("totalAmnt") String totalAmnt,
            @RequestParam("hsn") String hsn) throws IOException, TemplateException {
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

        List<Invoice> invoices = invoiceService.invoiceDatas();
        if (!invoices.isEmpty()) {
            Invoice lastInvoice = invoices.get(invoices.size() - 1);
            List<Invoice_sub> subs = invoiceSubRepo.invoiceSubDataById(Integer.toString(lastInvoice.getInv_id()));
            if (!subs.isEmpty()) {
                model.put("invoiceSubData", subs);
            }
        }

        List<Invoice_template> templates = templateRepo.templateDatas();
        if (!templates.isEmpty()) {
            Invoice_template template = templates.get(0);
            String payTo = template.getTemplate_payTo();
            model.put("templateCompanyName", splitLines(template.getTemplate_companyName()));
            model.put("templateCompanyAddress", splitLines(template.getTemplate_companyAddress()));
            model.put("templateCompanyContact", splitLines(template.getTemplate_companyContact()));
            model.put("templateName", splitLines(template.getTemplate_Name()));
            model.put("templateData", payTo != null ? payTo.split("\\r?\\n") : new String[]{});
            model.put("templateLogo", sanitizeAssetName(template.getTemplate_logo()));
            model.put("templateSign", sanitizeAssetName(template.getTemplate_sig()));
        }

        Template freemarkerTemplate = config.getTemplate("email-template-new.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, model);
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
            @RequestParam("inv_id") int inv_id,
            @RequestParam("invDate") String invDate,
            @RequestParam("invNo") String invNo,
            @RequestParam("gstNo") String gstNo,
            @RequestParam("billAddress") String billAddress,
            @RequestParam("place_of_supply") String place_of_supply,
            @RequestParam("igstAmnt") String igstAmnt,
            @RequestParam("cgstAmnt") String cgstAmnt,
            @RequestParam("sgstAmnt") String sgstAmnt,
            @RequestParam("totalTaxAmnt") String totalTaxAmnt,
            @RequestParam("totalAmnt") String totalAmnt,
            @RequestParam("hsn") String hsn,
            @RequestParam("CompanyName") String companyName,
            @RequestParam("CustId") String custId,
            @RequestParam("ifsc") String ifsc,
            @RequestParam("swift_code") String swift_code,
            @RequestParam("gstId") String gstId) throws IOException, TemplateException {
        logger.info("Generating dashboard PDF for invoice: inv_id={}, CompanyName={}, CustId={}", inv_id, companyName, custId);
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

        List<Invoice_sub> subs = invoiceSubRepo.invoiceSubDataById(Integer.toString(inv_id));
        if (!subs.isEmpty()) {
            model.put("invoiceSubData", subs);
        }

        List<Invoice_template> templates = templateRepo.templateDatas();
        if (companyName != null) {
            templates = templates.stream()
                    .filter(t -> companyName.equals(t.getTemplate_companyName()))
                    .collect(Collectors.toList());
        }

        if (!templates.isEmpty()) {
            Invoice_template template = templates.get(0);
            String payTo = template.getTemplate_payTo();
            model.put("templateCompanyName", splitLines(template.getTemplate_companyName()));
            model.put("templateCompanyAddress", splitLines(template.getTemplate_companyAddress()));
            model.put("templateCompanyContact", splitLines(template.getTemplate_companyContact()));
            model.put("templateName", splitLines(template.getTemplate_Name()));
            model.put("templateData", payTo != null ? payTo.split("\\r?\\n") : new String[]{});
            model.put("templateLogo", sanitizeAssetName(template.getTemplate_logo()));
            model.put("templateSign", sanitizeAssetName(template.getTemplate_sig()));
        } else {
            logger.warn("No template found for CompanyName: {}", companyName);
        }

        Template freemarkerTemplate = config.getTemplate("email-template-new.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, model);
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
        } catch (Exception e) {
            logger.error("Error generating PDF: {}", e.getMessage(), e);
        }
        return baos;
    }

    private String[] splitLines(String value) {
        return value != null ? value.split("\\r?\\n") : new String[]{};
    }

    private String sanitizeAssetName(String value) {
        return value != null ? value.replaceAll("\\s+", "-")
                              .replaceAll("[^a-zA-Z0-9.-]", "")
                              .toLowerCase() : "";
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
}
