package com.example.demo.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

//import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import freemarker.template.Template;
import freemarker.template.TemplateException;

//import com.sun.java.util.jar.pack.Package.File;
import com.example.demo.fileService.FileResponse;
import com.example.demo.fileService.FileStorageService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
 
import javax.servlet.ServletContext;
 
import com.example.demo.fileService.MediaTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.email.api.dto.MailRequest;
import com.example.demo.email.api.dto.MailResponse;
import com.example.demo.email.api.service.EmailService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.sun.istack.ByteArrayDataSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;




@CrossOrigin(origins="http://localhost:3000")

//@CrossOrigin(origins="http://intz.live:8080")


@RestController


public class UserControllerNew {

	@Autowired
	private EmailService service;
		
	@Autowired
	private AcTitleService acTitleService;
		
	@Autowired
	private GroupService groupService;
	
	
	@Autowired
	private LedgerService ledgerService;
	
	
	@Autowired
	private TransactionService transactionService;
	
	
	@Autowired
	private InvoiceService invoiceService;
	
	
	
	@Autowired
	private GroupServiceRepo groupServiceRepo;
	
	@Autowired
	private LedgerServiceRepo ledgerServiceRepo;
	
	@Autowired
	private TransactionServiceRepo transactionServiceRepo;
		
	@Autowired
	private FileStorageService fileStorageService;
	
    @Autowired
    private ServletContext servletContext;
	
    
    @Autowired
	private InvoiceRepo invoiceRepo;
    
    @Autowired
	private InvoiceSubRepo invoiceSubRepo;
    
    @Autowired
	private TemplateService templateService;
    
    @Autowired
	private TemplateRepo templateRepo;
    
    
	@Autowired
	private Configuration config;
	
	@Autowired
	private ProfileRepo profileRepo;
	
    @Autowired
    private ProfileService profileService;
    
    @Autowired
	private UserRepo userRepo;
    
    @Autowired
	private UserService userService;
    
    
    
    @PostMapping("/sendingEmail")
	public MailResponse sendEmail(@RequestBody MailRequest request) {
    	
    	System.out.println("Invoice date"+request.getInv_date());
    	System.out.println("Ready to mail");
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
    	
    	System.out.println("Invoice date"+request.getInv_date());
    	System.out.println("Ready to mail");
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
		public List<Account_title_v3> ac_Title()
		{
			
			return acTitleService.ac_Titles();
		}
		
		
		@GetMapping("/acTitle_search")
		public List<Account_title_v3>  acTitle_searchs(@RequestParam(value="acId") String acId) 
		{
			
					return acTitleService.acTitle_search(acId);
		}
		
		
		
		
		@RequestMapping(value="/add_group",method = RequestMethod.POST)
		public Account_group_v3 add_Group(@RequestBody Account_group_v3 fp)
		{
			
			return groupService.add_Groups(fp);
		}
		
		

		@GetMapping("/view_group")
		public List<Account_group_v3>  view_Group() 
		{
			
					return groupService.view_Groups();
		}
		
		
		@GetMapping("/grp_sorting")
		public List<Account_group_v3>  grp_sort(@RequestParam(value="field") String field,@RequestParam(value="type") String type) 
		{
					return groupService.grp_sorts(field,type);
					
		}
		
		
		@GetMapping("/grp_by_id")
		public List<Account_group_v3>  grp_idSearch(@RequestParam(value="grpId") String grpId) 
		{
			
					return groupService.grp_idSearchs(grpId);
					
		}
		
		
		@PutMapping("/grp_update/{grpId}")
		public ResponseEntity<Object> grp_updates(@RequestBody Account_group_v3 grp, @PathVariable int grpId) {

			System.out.println(grp);
			Optional<Account_group_v3> studentOptional = groupServiceRepo.findById(grpId);

			if (!studentOptional.isPresent())
				return ResponseEntity.notFound().build();

			groupServiceRepo.save(grp);

			return ResponseEntity.noContent().build();
		}
		
		
		
		@RequestMapping(value="/grp_delete/{id}",method = RequestMethod.DELETE)
		public String grp_delete(@PathVariable int id)
		{
			
			return groupService.grp_deletes(id);
		}
	
		
		@GetMapping("/grp_name_search")
		public List<Account_group_v3>  grp_nameSearch(@RequestParam(value="grpName") String grpName) 
		{
			
					return groupService.grp_nameSearchs(grpName);
					
		}
		
		
//		@GetMapping("/grp_id_search")
//		public List<Account_group_v3>  grp_id_Search (@RequestParam(value="grpId") String grpId) 
//		{
//			
//					return groupService.grp_idSearchs(grpName);
//					
//		}
		
		
		
		
		//////////////////////////// Ledger & Account Transaction ////////////////////////////////////////
		
		
		
		@RequestMapping(value="/add_ledger",method = RequestMethod.POST)
		public Account_ledger_v3 add_Ledger(@RequestBody Account_ledger_v3 fp)
		{
			
			return ledgerService.add_Ledgers(fp);
		}
		
		
		@GetMapping("/last_id_search")
		public List<Account_ledger_v3>  last_id_Search () 
		{
			
					return ledgerService.last_idSearchs();
					
		}
		
		
		@RequestMapping(value="/add_transaction",method = RequestMethod.POST)
		public Account_transactions_v3 add_Transaction(@RequestBody Account_transactions_v3 fp)
		{
			
			return transactionService.add_Transactions(fp);
		}
		
		
		@GetMapping("/ledger_name_search")
		public List<Account_ledger_v3>  ledger_name_search(@RequestParam(value="ledgerName") String ledgerName) 
		{
			
					return ledgerService.ledger_name_searchs(ledgerName);
					
		}
		
		@GetMapping("/transactionDate")
		public List<Account_transactions_v3>  transactionDate () 
		{
			
					return transactionService.transactionDates();
					
		}
		
		@GetMapping("/list_ledger")
		public List<Account_ledger_v3>  list_ledger() 
		{
			
					return ledgerService.list_ledgers();
		}
		
		
		@GetMapping("/ledger_sorting")
		public List<Account_ledger_v3>  ledger_sort(@RequestParam(value="field") String field,@RequestParam(value="type") String type) 
		{
					return ledgerService.ledger_sorts(field,type);
					
		}
		
		@GetMapping("/ledger_bn_date")
		public List<Account_ledger_v3>  ledger_bn_date(@RequestParam(value="start") String start,@RequestParam(value="end") String end) 
		{
			
					return ledgerService.ledger_bn_dates(start,end);
		}
		
		@RequestMapping(value="/ledger_delete/{id}",method = RequestMethod.DELETE)
		public String ledger_delete(@PathVariable int id)
		{
			
			return ledgerService.ledger_deletes(id);
		}
		
		@GetMapping("/ledger_search")
		public List<Account_ledger_v3>  ledger_Search(@RequestParam(value="ledgerId") int ledgerId) 
		{
			
					return ledgerService.ledger_Searchs(ledgerId);
					
		}
		
		
		@PutMapping("/ledger_update/{ledgerId}")
		public ResponseEntity<Object> ledger_updates(@RequestBody Account_ledger_v3 ledger, @PathVariable int ledgerId) {

			System.out.println(ledger);
			Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(ledgerId);

			if (!studentOptional.isPresent())
				return ResponseEntity.notFound().build();

			ledgerServiceRepo.save(ledger);

			return ResponseEntity.noContent().build();
		}
		
		
		@GetMapping("/ledger_transaction_search")
		public List<Account_transactions_v3>  ledger_transaction_search(@RequestParam(value="dbt_ac") String dbt_ac,@RequestParam(value="crdt_ac") String crdt_ac) 
		{
			
					return transactionService.ledger_transaction_searchs(dbt_ac,crdt_ac);
					
		}
		
		
	////////////////////////////Payments /////////////////////////
		
		
		@GetMapping("/bank_name")
		public List<Account_ledger_v3>  bank_name() 
		{
			
					return ledgerService.bank_names();
		}
		
		
		@GetMapping("/list_transaction")
		public List<Account_transactions_v3>  list_transaction() 
		{
			
					return transactionService.list_transactions();
		}
		
		@GetMapping("/transaction_sorting")
		public List<Account_transactions_v3>  transaction_sort(@RequestParam(value="field") String field,@RequestParam(value="type") String type) 
		{
					return transactionService.transaction_sorts(field,type);
					
		}
		
		@GetMapping("/payment_bn_date")
		public List<Account_transactions_v3>  payment_bn_date(@RequestParam(value="start") String start,@RequestParam(value="end") String end) 
		{
			
					return transactionService.payment_bn_dates(start,end);
		}
		
		@GetMapping("/transaction_search")
		public List<Account_transactions_v3>  transaction_search(@RequestParam(value="transactionId") String transactionId) 
		{
			
					return transactionService.transaction_searchs(transactionId);
					
		}
		
		@RequestMapping(value="/add_payment",method = RequestMethod.POST)
		public Account_transactions_v3 add_payment(@RequestBody Account_transactions_v3 fp)
		{
			
			return transactionService.add_payments(fp);
		}
		
		@RequestMapping(value="/payment_delete/{id}",method = RequestMethod.DELETE)
		public String payment_delete(@PathVariable int id)
		{
			
			return transactionService.payment_deletes(id);
		}
		
		
//		@PostMapping("/upload_image")
//		public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file)
//		{
//			System.out.println(file.getOriginalFilename());
//			System.out.println(file.getSize());
//			System.out.println(file.getContentType());
//			System.out.println(file.getName());
//			
//			
//			return ResponseEntity.ok("working");
//		}
		
		
		////////////////Journal/////////////
		
		
		@RequestMapping(value="/add_journalTransaction",method = RequestMethod.POST)
		public Account_transactions_v3 add_journalTransaction(@RequestBody Account_transactions_v3 fp)
		{
			
			return transactionService.add_journalTransactions(fp);
		}
		
		
		
		
		@GetMapping("/list_journal")
		public List<Account_transactions_v3>  list_journal() 
		{
			
					return transactionService.list_journals();
		}
		
		
		@GetMapping("/journal_sorting")
		public List<Account_transactions_v3>  journal_sort(@RequestParam(value="field") String field,@RequestParam(value="type") String type) 
		{
					return transactionService.journal_sorts(field,type);
					
		}
		
		@GetMapping("/journal_bn_date")
		public List<Account_transactions_v3>  journal_bn_date(@RequestParam(value="start") String start,@RequestParam(value="end") String end) 
		{
			
					return transactionService.journal_bn_dates(start,end);
		}
		
		@RequestMapping(value="/journal_delete/{id}",method = RequestMethod.DELETE)
		public String journal_delete(@PathVariable int id)
		{
			
			return transactionService.journal_deletes(id);
		}
		
		@GetMapping("/journal_search")
		public List<Account_transactions_v3>  journal_search(@RequestParam(value="tranId") String tranId) 
		{
			
					return transactionService.journal_searchs(tranId);
					
		}
		
		@PutMapping("/journal_update/{tranID}")
		public ResponseEntity<Object> journal_updates(@RequestBody Account_transactions_v3 journal, @PathVariable int tranID) {

			System.out.println(journal);
			Optional<Account_transactions_v3> studentOptional = transactionServiceRepo.findById(tranID);

			if (!studentOptional.isPresent())
				return ResponseEntity.notFound().build();

			transactionServiceRepo.save(journal);

			return ResponseEntity.noContent().build();
		}
		
		
		////////////////Receipt/////////////
		
		@GetMapping("/list_receipt")
		public List<Account_transactions_v3>  list_receipt() 
		{
			
					return transactionService.list_receipts();
		}
		
		
		@GetMapping("/receipt_sorting")
		public List<Account_transactions_v3>  receipt_sort(@RequestParam(value="field") String field,@RequestParam(value="type") String type) 
		{
					return transactionService.receipt_sorts(field,type);
					
		}
		
		@GetMapping("/receipt_bn_date")
		public List<Account_transactions_v3>  receipt_bn_date(@RequestParam(value="start") String start,@RequestParam(value="end") String end) 
		{
			
					return transactionService.receipt_bn_dates(start,end);
		}
		
		
		/////////////////account statement//////////
		
		@GetMapping("/list_account_statement")
		public List<Account_ledger_v3>  list_account_statement() 
		{
			
					return transactionService.list_account_statements();
		}
		
		@GetMapping("/list_acTitle")
		public String list_acTitle(@RequestParam(value="acId") String acId) 
		{
			
					return acTitleService.list_acTitles(acId);
		}
		
		@GetMapping("/list_account_statement_transaction")
		public List<Account_transactions_v3>  accStmtTransaction(@RequestParam(value="id") String id,@RequestParam(value="description") String description) 
		{
			
					return transactionService.accStmtTransactions(id,description);
		}
		
		
//		@GetMapping("/list_account_statement_transaction2")
//		public List<Account_transactions_v3>  accStmtTransaction2(@RequestParam(value="id") String id,@RequestParam(value="description") String description) 
//		{
//			
//					return transactionService.accStmtTransactions2(id,description);
//		}
		
		@GetMapping("/ledger_search2")
		public List<Account_ledger_v3>  ledger_Search2(@RequestParam(value="ledgerId") int ledgerId) 
		{
			
					return ledgerService.ledger_Searchs2(ledgerId);
					
		}
		
		
		@GetMapping("/account_statementData")
		public List<Account_transactions_v3>  account_statementData(@RequestParam(value="ledgerId") int ledgerId) 
		{
			
					return ledgerService.account_statementDatas(ledgerId);
					
		}
		
		
		@GetMapping("/profit_loss")
		public List<Account_ledger_v3>  profit_loss(@RequestParam(value="title") String title) 
		{
			
					return ledgerService.profit_losss(title);
					
		}
		
		
		@GetMapping("/profit_loss_bn_date")
		public List<Account_ledger_v3>  profit_loss_bn_date(@RequestParam(value="title") String title,  @RequestParam(value="start") String start,@RequestParam(value="end") String end) 
		{
			
					return ledgerService.profit_loss_bn_dates(title,start,end);
		}
		
		
		
		
		
		@GetMapping("/list_account_statement_transactionBnDates")
		public List<Account_transactions_v3>  accStmtTransactionBnDate(@RequestParam(value="id") String id,@RequestParam(value="description") String description,@RequestParam(value="start") String start,@RequestParam(value="end") String end) 
		{
			
					return transactionService.accStmtTransactionBnDates(id,description,start,end);
		}
		
		
		
		@GetMapping("/account_statementDataBnDates")
		public List<Account_transactions_v3>  account_statementDataBnDate(@RequestParam(value="ledgerId") int ledgerId,@RequestParam(value="start") String start,@RequestParam(value="end") String end) 
		{
			
					return ledgerService.account_statementDataBnDates(ledgerId,start,end);
					
		}
		
		
		////////////////Balance Sheet//////////////
		
//		@GetMapping("/balanceSheetDataBnDates")
//		public List<Account_ledger_v3>  balanceSheetDataBnDate(@RequestParam(value="title") String title,@RequestParam(value="end") String end) 
//		{
//			
//					return ledgerService.balanceSheetDataBnDates(title,end);
//					
//		}
		
		
		@GetMapping("/balanceSheetDataBnDates")
		public List<Account_ledger_v3>  balanceSheetDataBnDate(@RequestParam(value="title") String title, @RequestParam(value="start") String start,  @RequestParam(value="end") String end) 
		{
			
					return ledgerService.balanceSheetDataBnDates(title,start,end);
					
		}
		
		
//		@GetMapping("/balanceSheetProfitLossDataBnDates")
//		public List<Account_ledger_v3>  balanceSheetProfitLossDataBnDate(@RequestParam(value="title") String title,@RequestParam(value="end") String end) 
//		{
//			
//					return ledgerService.balanceSheetProfitLossDataBnDates(title,end);
//					
//		}
		
		
		@GetMapping("/balanceSheetProfitLossDataBnDates")
		public List<Account_ledger_v3>  balanceSheetProfitLossDataBnDate(@RequestParam(value="title") String title,  @RequestParam(value="start") String start,@RequestParam(value="end") String end) 
		{
			
					return ledgerService.balanceSheetProfitLossDataBnDates(title,start ,end);
					
		}
		
		
		
		@GetMapping("/updateledgerbalance")
		public String  updateledgerbalances() 
		{
			
					return ledgerService.updateledgerbalance();
					
		}
		
		
		//////////////trial balance/////////
		
		@GetMapping("/trial_balance")
		public List<Account_ledger_v3>  trial_balances(@RequestParam(value="acType") String acType) 
		{
			
					return ledgerService.trial_balance(acType);
					
		}
		
		@GetMapping("/trial_balance_total")
		public String  trial_balance_totals() 
		{
			
					return ledgerService.trial_balance_total();
					
		}
		
		
		@GetMapping("/trial_balanceBnDates")
		public List<Account_ledger_v3>  trial_balanceBnDates(@RequestParam(value="acType") String acType,@RequestParam(value="start") String start,@RequestParam(value="end") String end) 
		{
			
					return ledgerService.trial_balanceBnDate(acType,start,end);
					
		}
		
		@GetMapping("/trial_balance_totalBnDates")
		public String  trial_balance_totalBnDates(@RequestParam(value="start") String start,@RequestParam(value="end") String end) 
		{
			
					return ledgerService.trial_balance_totalBnDate(start,end);
					
		}
		
		
		///////////////File Upload//////////
		
		
		@PutMapping("/files")
		public ResponseEntity<FileResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException{
			System.out.println(file.getOriginalFilename());
			String fileName = fileStorageService.storeFile(file);
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//					.path("C:\\Users\\SHERIN\\git\\JavaAccounts\\Intuisyz_Accounts_Java\\src\\main\\resources\\image")
//				.path("C:\\Users\\SHERIN\\Documents\\workspace-spring-tool-suite-4-4.8.1.RELEASE\\Intuisyz_Accounts_Java\\src\\main\\resources\\image")
					.path("\\images")
					.path(fileName)
					.toUriString();
			
			//String folder="C:\\Users\\SHERIN\\git\\JavaAccounts\\Intuisyz_Accounts_Java\\frontend\\src\\Image" + "/";
			
//			String folder="C:\\Users\\SHERIN\\intuisyz_accounts_app\\src\\Image"+"/";
//			byte[] bytes=file.getBytes();
//			Path path=Paths.get(folder+file.getOriginalFilename());
//			Files.write(path, bytes);
			
			
//			String fileLocation = new File("src\\main\\resources\\static\\uploads").getAbsolutePath() + "\\" + fileName;
//
//			FileOutputStream output = new FileOutputStream(fileLocation);
//
//			output.write(f.getBytes());
//
//			output.close();
			
			FileResponse fileResponse = new FileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
			return new ResponseEntity<FileResponse>(fileResponse,HttpStatus.OK);
		}
		
//		@GetMapping("/fileDownload")
//		public ResponseEntity<Resource> downloadFile(@RequestParam(value="fileName") String fileName,HttpServletRequest request){
//			
//			fileName="C:\\\\Users\\\\SHERIN\\\\Documents\\\\workspace-spring-tool-suite-4-4.8.1.RELEASE\\\\Intuisyz_Accounts_Java\\\\src\\\\main\\\\resources\\\\image\\\\"+fileName;
//			
////			fileName="\\images\\"+fileName;
//			
//			System.out.println("fileName "+fileName);
//			
//			Resource resource = fileStorageService.loadFileAsResource(fileName);
//			
//			String contentType = null;
//			
//			try {
//				contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//			}catch(IOException ex) {
//				System.out.println("Could not determine fileType");
//			}
//			
//			if(contentType==null) {
//				contentType = "application/octet-stream";
//			}
//			
//			return ResponseEntity.ok()
//					.contentType(MediaType.parseMediaType(contentType))
//					.body(resource);
//		}
		
		
		@GetMapping("/fileDownload")
	    public ResponseEntity<InputStreamResource> downloadFile1(
	    		@RequestParam(value="fileName") String fileName) throws IOException {
		
	 
	        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
	        System.out.println("fileName: " + fileName);
	        System.out.println("mediaType: " + mediaType);
	 
	    //    File file = new File("C:\\\\Users\\\\SHERIN\\\\git\\\\JavaAccounts\\\\Intuisyz_Accounts_Java\\\\src\\\\main\\\\resources\\\\image"+ "/" + fileName);
	        
	   //     File file = new File("C:\\\\Users\\\\SHERIN\\\\Documents\\\\workspace-spring-tool-suite-4-4.8.1.RELEASE\\\\Intuisyz_Accounts_Java\\\\src\\\\main\\\\resources\\\\image" + "/" + fileName);
	        
	        File file = new File("\\images" + "/" + fileName);
	        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
	 
	        return ResponseEntity.ok()
	                // Content-Disposition
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
	                // Content-Type
	                .contentType(mediaType)
	                // Contet-Length
	                .contentLength(file.length()) //
	                .body(resource);
	    }
		
		
		////////////////////////Cash Book//////////////////
		
		//////Old code///////
		
//		@GetMapping("/cashBookData")
//		public List<Account_transactions_v3>  cashBookDatas() 
//		{
//			
//					return transactionService.cashBookData();
//					
//		}
//		
		@GetMapping("/cashBookOpeningBalanceData")
		public List<Account_transactions_v3>  cashBookOpeningBalanceDatas() 
		{
			
					return transactionService.cashBookOpeningBalanceData();
					
		}
		
		
		@GetMapping("/cashBookAccount_statementDatas")
		public List<Account_transactions_v3>  cashBookAccount_statementData(@RequestParam(value="ledgerId") int ledgerId) 
		{
			
					return transactionService.cashBookAccount_statementDatas(ledgerId);
					
		}
		
		
		@GetMapping("/cashbook_sorting")
		public List<Account_transactions_v3>  cashbook_sort(@RequestParam(value="field") String field,@RequestParam(value="type") String type) 
		{
					return transactionService.cashbook_sorts(field,type);
					
		}
		
		
		@GetMapping("/cashBookBnDates")
		public List<Account_transactions_v3>  cashBookBnDate(@RequestParam(value="start") String start,@RequestParam(value="end") String end) 
		{
			
					return transactionService.cashBookBnDates(start,end);
					
		}
		
		@GetMapping("/cashBookOpeningBalanceBnDates")
		public List<Account_transactions_v3>  cashBookOpeningBalanceBnDate(@RequestParam(value="start") String start,@RequestParam(value="end") String end) 
		{
			
					return transactionService.cashBookOpeningBalanceBnDates(start,end);
					
		}
		
		
		
		
		@GetMapping("/cashBookDelete")
		public List<Account_transactions_v3>  cashBookDeletes(@RequestParam(value="tranId") String tranId) 
		{
			
					return transactionService.cashBookDelete(tranId);
					
					
					
		}
		
		//////////////////Bank Book///////////////
		

		@GetMapping("/bankBookData")
		public List<Account_transactions_v3>  bankBookDatas() 
		{
			
					return transactionService.bankBookData();
					
		}
		
		@GetMapping("/bankData")
		public List<Account_ledger_v3>  bankData() 
		{
			
					return ledgerService.bankDatas();
					
		}
		
		@GetMapping("/bankBookDataB")
		public List<Account_transactions_v3>  bankBookDatasB(@RequestParam(value="ledgerId") int ledgerId,@RequestParam(value="start") String start,@RequestParam(value="end") String end) 
		{
			
					//return transactionService.bankBookDataB(Integer.toString(bankId),start,end);
					
			return transactionService.bankBookDataB(ledgerId,start,end);
			
		}
		
		
		@GetMapping("/bankBookOpenBalanceDataB")
		public List<Account_transactions_v3>  bankBookOpenBalanceDataB(@RequestParam(value="id") String id,@RequestParam(value="description") String description,@RequestParam(value="start") String start,@RequestParam(value="end") String end) 
		{
			
					//return transactionService.bankBookDataB(Integer.toString(bankId),start,end);
					
			return transactionService.bankBookOpenBalanceDataB(id,description,start,end);
			
		}
		
		
		
		
		
		
		
		@GetMapping("/bankBookDelete")
		public List<Account_transactions_v3>  bankBookDeletes(@RequestParam(value="tranId") String tranId) 
		{
			
					return transactionService.bankBookDelete(tranId);
					
					
					
		}
		
		
		@GetMapping("/bankbook_sorting")
		public List<Account_transactions_v3>  bankbook_sort(@RequestParam(value="field") String field,@RequestParam(value="type") String type) 
		{
					return transactionService.bankbook_sorts(field,type);
					
		}
		
		
		
		

		/////////////Day Book/////////
		
		@GetMapping("/dayBookData")
		public List<Account_transactions_v3>  dayBookDatas() 
		{
			
					return transactionService.dayBookData();
					
		}
		
		@GetMapping("/debitAcData")
		public List<Account_transactions_v3>  debitAcDatas() 
		{
			
					return transactionService.debitAcData();
					
		}
		
		@GetMapping("/creditAcData")
		public List<Account_transactions_v3>  creditAcDatas() 
		{
			
					return transactionService.creditAcData();
					
		}
		
		@GetMapping("/dayBookDataBnDate")
		public List<Account_transactions_v3>  dayBookDataBnDates(@RequestParam(value="start") String start,@RequestParam(value="end") String end,@RequestParam(value="debit") String debit,@RequestParam(value="credit") String credit) 
		{
			
					return transactionService.dayBookDataBnDate(start,end,debit,credit);
					
		}
		
		@GetMapping("/dayBookDelete")
		public List<Account_transactions_v3>  dayBookDeletes(@RequestParam(value="tranId") String tranId) 
		{
			
					return transactionService.dayBookDelete(tranId);
					
					
		}
		
		
		@GetMapping("/daybook_sorting")
		public List<Account_transactions_v3>  daybook_sort(@RequestParam(value="field") String field,@RequestParam(value="type") String type) 
		{
					return transactionService.daybook_sorts(field,type);
					
		}
		
		
//		//////////////Transaction History////////////
		
		@GetMapping("/transactionHistory_searching")
		public List<Account_transactions_v3>  transactionHistory_search(@RequestParam(value="start") String start,@RequestParam(value="end") String end,@RequestParam(value="debit") String debit,@RequestParam(value="credit") String credit,@RequestParam(value="field") String field,@RequestParam(value="val") String val) 
		{
					return transactionService.transactionHistory_searchs(start,end,debit,credit,field,val);
					
		}
		
		
		//////////////////invoice////////////////////
		
		@RequestMapping(value="/add_invoice",method = RequestMethod.POST)
		public Invoice add_invoice(@RequestBody Invoice fp)
		{
			
			return invoiceService.add_invoices(fp);
		}
		
		
		@GetMapping("/invoiceData")
		public List<Invoice>  invoiceData() 
		{
			
					return invoiceService.invoiceDatas();
					
		}
		
		
		
		@GetMapping("/invoiceDataOnDashBoard")
		public List<Invoice>  invoiceDataOnDashBoard() 
		{
			
					return invoiceService.invoiceDataOnDashBoards();
					
		}
		
		
		
		
		
		@GetMapping("/invoiceDataById")
		public List<Invoice>  invoiceDataById(@RequestParam(value="invoiceId") String invoiceId) 
		{
			
					return invoiceService.invoiceDataByIds(invoiceId);
					
		}
		
		
		@PutMapping("/invoice_update/{invoiceId}")
		public ResponseEntity<Object> invoice_updates(@RequestBody Invoice invoice, @PathVariable int invoiceId) {

			System.out.println(invoice);
			Optional<Invoice> studentOptional = invoiceRepo.findById(invoiceId);

			if (!studentOptional.isPresent())
				return ResponseEntity.notFound().build();

			invoiceRepo.save(invoice);

			return ResponseEntity.noContent().build();
		}
		
		
		
		@GetMapping("/customer_name")
		public List<Account_ledger_v3>  selectCustomer() 
		{
			
					return ledgerService.selectCustomers();
		}
		
		@GetMapping("/service_name")
		public List<Account_ledger_v3>  selectService() 
		{
			
					return ledgerService.selectServices();
		}
		
		
		
		@GetMapping("/journal_searchInvoice")
		public List<Account_transactions_v3>  journal_searchInvoice(@RequestParam(value="tranId") String tranId,@RequestParam(value="creditAc") String creditAc,@RequestParam(value="debitAc") String debitAc) 
		{
			
					return transactionService.journal_searchInvoices(tranId,creditAc,debitAc);
					
		}
		
		@GetMapping("/invoiceDataByTransactionId")
		public List<Invoice>  invoiceDataByTransactionId(@RequestParam(value="transactionId") String transactionId) 
		{
			
					return invoiceService.invoiceDataByTransactionIds(transactionId);
					
		}
		
		
		
		@GetMapping("/tran_gen_Search")
		public List<Account_transactions_v3>  tran_gen_Searchs(@RequestParam(value="tran_gen_id") String tran_gen_id) 
		{
			
					return transactionService.tran_gen_Search(tran_gen_id);
		}
		
		
		
		@RequestMapping(value="/invoiceDelete/{inv_id}",method = RequestMethod.DELETE)
		public String invoiceDeletes(@PathVariable int inv_id)
		{
			
			return invoiceService.invoiceDelete(inv_id);
		}
		
		
		
         //////////////////invoice_sub////////////////////
		
		@RequestMapping(value="/add_invoice_sub",method = RequestMethod.POST)
		public Invoice_sub add_invoice_sub(@RequestBody Invoice_sub fp)
		{
			
			return invoiceService.add_invoice_subs(fp);
		}
		
		@GetMapping("/invoiceSubDataById")
		public List<Invoice_sub>  invoiceSubDataById(@RequestParam(value="invoiceId") String invoiceId) 
		{
			
					return invoiceService.invoiceSubDataByIds(invoiceId);
					
		}
		
		
		
		
		
		@RequestMapping(value="/invoiceSubDelete/{inv_id}",method = RequestMethod.GET)
		public String invoiceSubDeletes(@PathVariable String inv_id)
		{
			
			return invoiceService.invoiceSubDelete(inv_id);
		}
		
		
		////////////////////////////////ac_dashboard//////////////////////////////

		@GetMapping("/ac_dashboardCashData")
		public List<Account_ledger_v3>  ac_dashboardCashDatas() 
		{
			
					return ledgerService.ac_dashboardCashData();
					
		}
		
		
		@GetMapping("/ac_dashboardBankData")
		public List<Account_ledger_v3>  ac_dashboardBankDatas() 
		{
			
					return ledgerService.ac_dashboardBankData();
					
		}
		
		//////////////////////index page/////////////////////////
		
		@GetMapping("/index_customer_vendorApi")
		public List<Account_ledger_v3>  index_customer_vendorApis(@RequestParam(value="grp") String grp) 
		{
					return ledgerService.index_customer_vendorApi(grp);
					
		}
		
		//////////////Migration Date/////////
		
		@GetMapping("/migrationDateAdd")
		public String  migrationDateAdds(@RequestParam(value="mgrDate") String mgrDate) 
		{
					return ledgerService.migrationDateAdd(mgrDate);
					
		}
		
		
		//////////edit template file upload/////////
		
		@PutMapping("/uploadTemplateFile")
		public ResponseEntity<FileResponse> uploadTemplateFiles(@RequestParam("file") MultipartFile file) throws IOException{
			System.out.println(file.getOriginalFilename());
			String fileName = fileStorageService.storeFile(file);
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("C:\\Users\\SHERIN\\git\\JavaAccounts\\Intuisyz_Accounts_Java\\src\\main\\resources\\image")
//				.path("C:\\Users\\SHERIN\\Documents\\workspace-spring-tool-suite-4-4.8.1.RELEASE\\Intuisyz_Accounts_Java\\src\\main\\resources\\image")
					//.path("\\images")
					.path(fileName)
					.toUriString();
			
			String folder="C:\\Users\\SHERIN\\git\\JavaAccounts\\Intuisyz_Accounts_Java\\frontend\\public\\assets\\images" + "/";
			
			//String folder="C:\\Users\\SHERIN\\intuisyz_accounts_app\\src\\Image"+"/";
			
			//String folder="C:\\Users\\SHERIN\\intuisyz_accounts_app\\public\\assets\\images"+"/";
			
			byte[] bytes=file.getBytes();
			Path path=Paths.get(folder+file.getOriginalFilename());
			Files.write(path, bytes);
			
			
//			String fileLocation = new File("src\\main\\resources\\static\\uploads").getAbsolutePath() + "\\" + fileName;
//
//			FileOutputStream output = new FileOutputStream(fileLocation);
//
//			output.write(f.getBytes());
//
//			output.close();
			
			FileResponse fileResponse = new FileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
			return new ResponseEntity<FileResponse>(fileResponse,HttpStatus.OK);
		}
		
		
		
		
		@GetMapping("/templateData")
		public List<Invoice_template>  templateDatas() 
		{
			
					return templateService.templateData();
					
		}
		
		
		@PutMapping("/template_update/{template_Id}")
		public ResponseEntity<Object> template_updates(@RequestBody Invoice_template invoice_template, @PathVariable int template_Id) {

			System.out.println(invoice_template);
			Optional<Invoice_template> studentOptional = templateRepo.findById(template_Id);

			if (!studentOptional.isPresent())
				return ResponseEntity.notFound().build();

			templateRepo.save(invoice_template);

			return ResponseEntity.noContent().build();
		}
		
		
		
		
		@GetMapping("/invoiceImgFetch")
	    public ResponseEntity<InputStreamResource> invoiceImgFetchs(
	    		@RequestParam(value="fileName") String fileName) throws IOException {
		
	 
	        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
	        System.out.println("fileName: " + fileName);
	        System.out.println("mediaType: " + mediaType);
	 
	        File file = new File("C:\\\\Users\\\\SHERIN\\\\git\\\\JavaAccounts\\\\Intuisyz_Accounts_Java\\\\src\\\\main\\\\resources\\\\image"+ "/" + fileName);
	        
	   //     File file = new File("C:\\\\Users\\\\SHERIN\\\\Documents\\\\workspace-spring-tool-suite-4-4.8.1.RELEASE\\\\Intuisyz_Accounts_Java\\\\src\\\\main\\\\resources\\\\image" + "/" + fileName);
	        
	    //    File file = new File("\\images" + "/" + fileName);
	        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
	 
	        return ResponseEntity.ok()
	                // Content-Disposition
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
	                // Content-Type
	                .contentType(mediaType)
	                // Contet-Length
	                .contentLength(file.length()) //
	                .body(resource);
	    }
		
		
		
		
		 @GetMapping("/invoicePdfDownload")
			public HttpEntity<byte[]> invoicePdfDownloads(@RequestParam(value="invDate") String invDate,@RequestParam(value="invNo") String invNo,
					@RequestParam(value="gstNo") String gstNo,@RequestParam(value="billAddress") String billAddress,@RequestParam(value="place_of_supply") String place_of_supply,
					@RequestParam(value="igstAmnt") String igstAmnt,@RequestParam(value="cgstAmnt") String cgstAmnt,@RequestParam(value="sgstAmnt") String sgstAmnt,
					@RequestParam(value="totalTaxAmnt") String totalTaxAmnt,@RequestParam(value="totalAmnt") String totalAmnt,@RequestParam(value="hsn") String hsn
					
					) throws IOException, TemplateException {
		    	
//		    	System.out.println("Invoice date"+request.getInv_date());
		    	System.out.println("Ready to mail");
				Map<String, Object> model = new HashMap<>();
			
				model.put("invDate", invDate);
				model.put("invNo", invNo);
				model.put("gstNo",gstNo);
				model.put("billAddress", billAddress);
				model.put("place_of_supply", place_of_supply);
				model.put("igstAmnt", igstAmnt);
				model.put("cgstAmnt", cgstAmnt);
				model.put("sgstAmnt",sgstAmnt);
				model.put("totalTaxAmnt", totalTaxAmnt);
				model.put("totalAmnt", totalAmnt);
				model.put("hsn", hsn);
				
				
					System.out.print("Ready to mail");
					System.out.print("model val "+model);
					
					
					List<Invoice> li=(List<Invoice>) invoiceRepo.invoiceData();
					
					int lastId = 0;
					
					int lastInvId=0;
					
					if(li.size()>0)
				    {
						System.out.println("li invoice data size "+li.size());
						
						int i=li.size();
						
						 lastId=i-1;
						System.out.println("li invoice data previous size "+ lastId);
						
						System.out.println("last id  "+Integer.toString(li.get(li.size()-1).getInv_id())+"    ");
					
						lastInvId=li.get(lastId).getInv_id();
						
					List<Invoice_sub> li1=(List<Invoice_sub>) invoiceSubRepo.invoiceSubDataById(Integer.toString(li.get(lastId).getInv_id()));
				    
					System.out.print("li1 size "+li1.size());
					
					if(li1.size()>0)
				    {
					
						System.out.println("Invoice sub amount "+li1.get(0).getAmount());
						
						model.put("invoiceSubData", li1);
				    }
					
					
				    }
					
					
		
					
				List<Invoice_template> li5=(List<Invoice_template>) templateRepo.templateDatas();
					
					if(li5.size()>0)
				    {
					
						String payTo= li5.get(0).getTemplate_payTo();
						
						System.out.println("Pay to "+payTo);
						
						String[] payToArray=payTo.split("\\r?\\n");
						
						System.out.println("Pay to arr "+payToArray);
						
						System.out.println("Pay to arr of 0 "+payToArray[0]);
						
						
						String cName=li5.get(0).getTemplate_companyName();
						String cAddress=li5.get(0).getTemplate_companyAddress();
						String cContact=li5.get(0).getTemplate_companyContact();
						String Name=li5.get(0).getTemplate_Name();
						String logoImg=li5.get(0).getTemplate_logo();
						String signImg=li5.get(0).getTemplate_sig();
						
						String[] cNameArray=cName.split("\\r?\\n");
						String[] cAddressArray=cAddress.split("\\r?\\n");
						String[] cContactArray=cContact.split("\\r?\\n");
						String[] nameArray=Name.split("\\r?\\n");
						
						model.put("templateCompanyName", cNameArray);
						model.put("templateCompanyAddress", cAddressArray);
						model.put("templateCompanyContact", cContactArray);
						model.put("templateName", nameArray);
						model.put("templateData", payToArray);
						model.put("templateLogo", logoImg);
						model.put("templateSign", signImg);
						
						System.out.println("templateLogo "+logoImg);
				    }
					
					
					


						Template t = config.getTemplate("email-template-new.ftl");
						String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
						
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						baos = generatePdf(html);
					
						String fileName="invoice.pdf";
					
				            HttpHeaders header = new HttpHeaders();
				    	    header.setContentType(MediaType.APPLICATION_PDF);
				    	    header.set(HttpHeaders.CONTENT_DISPOSITION,
				    	                   "attachment; filename=" + fileName.replace(" ", "_"));
				    	    header.setContentLength(baos.toByteArray().length);

				    	    return new HttpEntity<byte[]>(baos.toByteArray(), header);
				            	
	

			}
		
			
			public ByteArrayOutputStream generatePdf(String html) {

				String pdfFilePath = "";
				PdfWriter pdfWriter = null;

				// create a new document
				Document document = new Document();
				try {

//					document = new Document();
//					// document header attributes
//					document.addAuthor("Kinns");
//					document.addAuthor("Kinns123");
//					document.addCreationDate();
//					document.addProducer();
//					document.addCreator("kinns123.github.io");
//					document.addTitle("HTML to PDF using itext");
//					document.setPageSize(PageSize.LETTER);

					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					PdfWriter.getInstance(document, baos);

					// open document
					document.open();

					XMLWorkerHelper xmlWorkerHelper = XMLWorkerHelper.getInstance();
					xmlWorkerHelper.getDefaultCssResolver(true);
					xmlWorkerHelper.parseXHtml(pdfWriter, document, new StringReader(
							html));
					// close the document
					document.close();
					System.out.println("PDF generated successfully");

					return baos;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}

			}
			
			
			
			
			 @GetMapping("/invoicePdfDownloadDashboard")
				public HttpEntity<byte[]> invoicePdfDownloadsDashboard(@RequestParam(value="inv_id") int inv_id,@RequestParam(value="invDate") String invDate,@RequestParam(value="invNo") String invNo,
						@RequestParam(value="gstNo") String gstNo,@RequestParam(value="billAddress") String billAddress,@RequestParam(value="place_of_supply") String place_of_supply,
						@RequestParam(value="igstAmnt") String igstAmnt,@RequestParam(value="cgstAmnt") String cgstAmnt,@RequestParam(value="sgstAmnt") String sgstAmnt,
						@RequestParam(value="totalTaxAmnt") String totalTaxAmnt,@RequestParam(value="totalAmnt") String totalAmnt,@RequestParam(value="hsn") String hsn
						
						) throws IOException, TemplateException {
			    	
//			    	System.out.println("Invoice date"+request.getInv_date());
			    	System.out.println("Ready to mail");
					Map<String, Object> model = new HashMap<>();
				
					model.put("invDate", invDate);
					model.put("invNo", invNo);
					model.put("gstNo",gstNo);
					model.put("billAddress", billAddress);
					model.put("place_of_supply", place_of_supply);
					model.put("igstAmnt", igstAmnt);
					model.put("cgstAmnt", cgstAmnt);
					model.put("sgstAmnt",sgstAmnt);
					model.put("totalTaxAmnt", totalTaxAmnt);
					model.put("totalAmnt", totalAmnt);
					model.put("hsn", hsn);
					
					
						System.out.print("Ready to mail");
						System.out.print("model val "+model);
						
							
						List<Invoice_sub> li1=(List<Invoice_sub>) invoiceSubRepo.invoiceSubDataById(Integer.toString(inv_id));
					    
						System.out.print("li1 size "+li1.size());
						
						if(li1.size()>0)
					    {
						
							System.out.println("Invoice sub amount "+li1.get(0).getAmount());
							
							model.put("invoiceSubData", li1);
					    }
						
					
						
					List<Invoice_template> li5=(List<Invoice_template>) templateRepo.templateDatas();
						
						if(li5.size()>0)
					    {
						
							String payTo= li5.get(0).getTemplate_payTo();
							
							System.out.println("Pay to "+payTo);
							
							String[] payToArray=payTo.split("\\r?\\n");
							
							System.out.println("Pay to arr "+payToArray);
							
							System.out.println("Pay to arr of 0 "+payToArray[0]);
							
							
							String cName=li5.get(0).getTemplate_companyName();
							String cAddress=li5.get(0).getTemplate_companyAddress();
							String cContact=li5.get(0).getTemplate_companyContact();
							String Name=li5.get(0).getTemplate_Name();
							String logoImg=li5.get(0).getTemplate_logo();
							String signImg=li5.get(0).getTemplate_sig();
							
							String[] cNameArray=cName.split("\\r?\\n");
							String[] cAddressArray=cAddress.split("\\r?\\n");
							String[] cContactArray=cContact.split("\\r?\\n");
							String[] nameArray=Name.split("\\r?\\n");
							
							model.put("templateCompanyName", cNameArray);
							model.put("templateCompanyAddress", cAddressArray);
							model.put("templateCompanyContact", cContactArray);
							model.put("templateName", nameArray);
							model.put("templateData", payToArray);
							model.put("templateLogo", logoImg);
							model.put("templateSign", signImg);
							
							System.out.println("templateLogo "+logoImg);
					    }
						
						
						


							Template t = config.getTemplate("email-template-new.ftl");
							String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
							
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							baos = generatePdf(html);
						
							String fileName="invoice.pdf";
						
					            HttpHeaders header = new HttpHeaders();
					    	    header.setContentType(MediaType.APPLICATION_PDF);
					    	    header.set(HttpHeaders.CONTENT_DISPOSITION,
					    	                   "attachment; filename=" + fileName.replace(" ", "_"));
					    	    header.setContentLength(baos.toByteArray().length);

					    	    return new HttpEntity<byte[]>(baos.toByteArray(), header);
					            	
		

				}

			
			
			
			
			
			
			
		
//			 @PostMapping("/invoicePdfDownload/{fileName}")
//				public HttpEntity<byte[]> invoicePdfDownloads(@RequestBody MailRequest request,@RequestParam(value="fileName") String fileName) throws IOException, TemplateException {
//			    	
//			    	System.out.println("Invoice date"+request.getInv_date());
//			    	System.out.println("Ready to mail");
//					Map<String, Object> model = new HashMap<>();
//				
//					model.put("invDate", request.getInv_date());
//					model.put("invNo", request.getInv_no());
//					model.put("gstNo", request.getGst_no());
//					model.put("billAddress", request.getBill_address());
//					model.put("place_of_supply", request.getPlace_of_supply());
//					model.put("igstAmnt", request.getIgstAmnt());
//					model.put("cgstAmnt", request.getCgstAmnt());
//					model.put("sgstAmnt", request.getSgstAmnt());
//					model.put("totalTaxAmnt", request.getTotalTaxAmnt());
//					model.put("totalAmnt", request.getTotalAmnt());
//					
//					return service.invoicePdfDownload(request, model,fileName);
//
//				}
				
				
				///////////////Profile page //////////////
				
				@RequestMapping(value="/add_profile",method = RequestMethod.POST)
				public Profile add_Profile(@RequestBody Profile fp)
				{
					
					return profileService.add_Profiles(fp);
				}
				
		
				@GetMapping("/profileData")
				public List<Profile>  profileDatas() 
				{
					
							return profileService.profileData();
							
							
				}
		
				
				@PutMapping("/profile_update/{organization_id}")
				public ResponseEntity<Object> profile_updates(@RequestBody Profile profile, @PathVariable int organization_id) {

					System.out.println(profile);
					Optional<Profile> studentOptional = profileRepo.findById(organization_id);

					if (!studentOptional.isPresent())
						return ResponseEntity.notFound().build();

					profileRepo.save(profile);

					return ResponseEntity.noContent().build();
				}
				
				
				////////////Login/////////////
				
				
				@GetMapping("/login")
				public String  logins(@RequestParam(value="userName") String userName,@RequestParam(value="password") String password) 
				{
					
							return userService.login(userName,password);
							
							
				}
				
				
				@GetMapping("/userSearch")
				public List<Account_user_v3>  userSearch(@RequestParam(value="userName") String userName) 
				{
					
							return userService.userSearchs(userName);
							
							
				}
				
				
				
				
				@PutMapping("/forgot_password/{id}")
				public ResponseEntity<Object> forgot_password(@RequestBody Account_user_v3 account_user_v3, @PathVariable int id) {

					System.out.println(account_user_v3);
					Optional<Account_user_v3> studentOptional = userRepo.findById(id);

					if (!studentOptional.isPresent())
						return ResponseEntity.notFound().build();

					userRepo.save(account_user_v3);

					return ResponseEntity.noContent().build();
				}
				
				
				@GetMapping("/userList")
				public List<Account_user_v3>  userList() 
				{
					
							return userService.userLists();
							
							
				}
				
				
				
				@RequestMapping(value="/addUser",method = RequestMethod.POST)
				public Account_user_v3 addUser(@RequestBody Account_user_v3 fp)
				{
					
					return userService.addUsers(fp);
				}
				
				
				@PutMapping("/editUser/{id}")
				public ResponseEntity<Object> editUser(@RequestBody Account_user_v3 account_user_v3, @PathVariable int id) {

					System.out.println(account_user_v3);
					Optional<Account_user_v3> studentOptional = userRepo.findById(id);

					if (!studentOptional.isPresent())
						return ResponseEntity.notFound().build();

					userRepo.save(account_user_v3);

					return ResponseEntity.noContent().build();
				}
				
				@RequestMapping(value="/userDelete/{id}",method = RequestMethod.DELETE)
				public String userDelete(@PathVariable int id)
				{
					
					return userService.userDeletes(id);
				}
				
				
				@GetMapping("/userSearchById")
				public List<Account_user_v3>  userSearchById(@RequestParam(value="id") int id) 
				{
					
							return userService.userSearchByIds(id);
							
							
				}
}
