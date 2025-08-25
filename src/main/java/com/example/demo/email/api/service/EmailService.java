package com.example.demo.email.api.service;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import com.example.demo.email.api.dto.MailRequest;
import com.example.demo.email.api.dto.MailResponse;
import com.example.demo.model.Invoice;
import com.example.demo.model.Invoice_sub;
import com.example.demo.model.Invoice_template;
import com.example.demo.repository.InvoiceRepo;
import com.example.demo.repository.InvoiceSubRepo;
import com.example.demo.repository.TemplateRepo;
import com.example.demo.service.InvoiceService;
import com.example.demo.service.TemplateService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.itextpdf.html2pdf.HtmlConverter;
import jakarta.mail.util.ByteArrayDataSource;
@Service
public class EmailService {
	@Autowired
	private JavaMailSender sender;
	@Autowired
	private Configuration config;
	@Autowired
	private InvoiceRepo invoiceRepo;
	@Autowired
	private InvoiceSubRepo invoiceSubRepo;
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
    private TemplateService templateService;
	@Autowired
	private TemplateRepo templateRepo;
	public MailResponse sendEmail(MailRequest request, Map<String, Object> model) {
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
//		MailResponse response = new MailResponse();
//		MimeMessage message = sender.createMimeMessage();
//		try {
//			// set mediaType
//			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//					StandardCharsets.UTF_8.name());
//			// add attachment
////			helper.addAttachment("logo.png", new ClassPathResource("logo.png"));
//
//			Template t = config.getTemplate("email-template.ftl");
//			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
//			
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			baos = generatePdf(t.toString());
//			
//		
////			helper.addAttachment(html,baos); 
//			//helper.addAttachment("baos" ,(DataSource) new HttpEntity<byte[]>(baos.toByteArray()));
//			
//			//helper.addAttachment("Billing Software.pdf", new ClassPathResource("Billing Software.pdf"));
//
//			helper.setTo(request.getTo());
//			helper.setText(html, true);
//			helper.setSubject(request.getSubject());
//			helper.setFrom(request.getFrom());
//			sender.send(message);
//
//			response.setMessage("mail send to : " + request.getTo());
//			response.setStatus(Boolean.TRUE);
//
//		} catch (MessagingException | IOException | TemplateException e) {
//			response.setMessage("Mail Sending failure : "+e.getMessage());
//			response.setStatus(Boolean.FALSE);
//		}
		 String smtpHost = "smtp.gmail.com"; //replace this with a valid host
	        int smtpPort = 587; 
//		 String smtpHost = "localhost"; //replace this with a valid host
//	        int smtpPort = 25; 
//		 String smtpHost = "mail.intreact.tk"; //replace this with a valid host
//	        int smtpPort = 587; 
//		 String smtpHost = "smtpout.secureserver.net"; //replace this with a valid host
//	        int smtpPort = 465; 
//		 String smtpHost = "relay-hosting.secureserver.net"; //replace this with a valid host
//	        int smtpPort = 25; 
//		 String smtpHost = "intreact.tk"; //replace this with a valid host
//        int smtpPort = 465; 
		 Properties properties = new Properties();
		MailResponse response = new MailResponse();
		MimeMessage message = sender.createMimeMessage();
		 properties.put("mail.smtp.host", smtpHost);
	        properties.put("mail.smtp.port", smtpPort);     
	    Session session = Session.getDefaultInstance(properties, null);
//	    String content = "Hi"
//	    		+ " greetings from Intuisyz,"
//	    		+ "  Your invoice attached below "+"or you can access it by click the link : http://intreact.tk:8090/#/mail-preview-details/"+lastInvId;
	    String content = request.getName() +" you can access it by click the link : http://intreact.tk:8090/#/mail-preview-details/"+lastInvId;
	    try {
			// set mediaType
//			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//					StandardCharsets.UTF_8.name());
//			// add attachment
////			helper.addAttachment("logo.png", new ClassPathResource("logo.png"));
		    MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(content);
			Template t = config.getTemplate("email-template-new.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos = generatePdf(html);
			 byte[] bytes = baos.toByteArray();
			  DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
	            MimeBodyPart pdfBodyPart = new MimeBodyPart();
	            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
	            pdfBodyPart.setFileName("invoice.pdf");
////			helper.addAttachment(html,baos); 
//			//helper.addAttachment("baos" ,(DataSource) new HttpEntity<byte[]>(baos.toByteArray()));
//			
//			//helper.addAttachment("Billing Software.pdf", new ClassPathResource("Billing Software.pdf"));
	            MimeMultipart mimeMultipart = new MimeMultipart();
	            mimeMultipart.addBodyPart(textBodyPart);
	            mimeMultipart.addBodyPart(pdfBodyPart);
	            //create the sender/recipient addresses
	            InternetAddress iaSender = new InternetAddress(request.getFrom());
	            InternetAddress iaRecipient = new InternetAddress(request.getTo());
//	            InternetAddress iaSender = new InternetAddress("invoice@intreact.tk");
//	            InternetAddress iaRecipient = new InternetAddress("invoice@intreact.tk");
	            //construct the mime message
//	            MimeMessage mimeMessage =  sender.createMimeMessage();
	            message.setSender(iaSender);
	            message.setSubject(request.getSubject());
	            message.setRecipient(Message.RecipientType.TO, iaRecipient);
	            message.setContent(mimeMultipart);
	            //send off the email
	            sender.send(message);
//			helper.setTo(request.getTo());
//			helper.setText(html, true);
//			helper.setSubject(request.getSubject());
//			helper.setFrom(request.getFrom());
//			sender.send(message);
			response.setMessage("mail send to : " + request.getTo());
			response.setStatus(Boolean.TRUE);
		} catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Mail Sending failure : "+e.getMessage());
			response.setStatus(Boolean.FALSE);
		}
		return response;
	}
	public ByteArrayOutputStream generatePdf(String html) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(html, baos);
            System.out.println("PDF generated successfully");
            return baos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
	}
	public MailResponse sendEmailUpdates(MailRequest request, Map<String, Object> model) {
		System.out.print("Ready to mail");
		System.out.print("model val "+model);
//		List<Invoice> li=(List<Invoice>) invoiceRepo.invoiceData();
		int lastId = 0;
		int lastInvId=0;
//			System.out.println("li invoice data size "+li.size());
//			int i=li.size();
			 lastId=request.getInv_id();
			System.out.println("li invoice data previous size "+ lastId);
			lastInvId=lastId;
		List<Invoice_sub> li1=(List<Invoice_sub>) invoiceSubRepo.invoiceSubDataById(Integer.toString(lastId));
		System.out.print("li1 size "+li1.size());
		if(li1.size()>0)
	    {
			System.out.println("Invoice sub amount "+li1.get(0).getAmount());
			model.put("invoiceSubData", li1);
	    }
//		MailResponse response = new MailResponse();
//		MimeMessage message = sender.createMimeMessage();
//		try {
//			// set mediaType
//			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//					StandardCharsets.UTF_8.name());
//			// add attachment
////			helper.addAttachment("logo.png", new ClassPathResource("logo.png"));
//
//			Template t = config.getTemplate("email-template.ftl");
//			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
//			
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			baos = generatePdf(t.toString());
//			
//		
////			helper.addAttachment(html,baos); 
//			//helper.addAttachment("baos" ,(DataSource) new HttpEntity<byte[]>(baos.toByteArray()));
//			
//			//helper.addAttachment("Billing Software.pdf", new ClassPathResource("Billing Software.pdf"));
//
//			helper.setTo(request.getTo());
//			helper.setText(html, true);
//			helper.setSubject(request.getSubject());
//			helper.setFrom(request.getFrom());
//			sender.send(message);
//
//			response.setMessage("mail send to : " + request.getTo());
//			response.setStatus(Boolean.TRUE);
//
//		} catch (MessagingException | IOException | TemplateException e) {
//			response.setMessage("Mail Sending failure : "+e.getMessage());
//			response.setStatus(Boolean.FALSE);
//		}
//		 String smtpHost = "smtp.gmail.com"; //replace this with a valid host
//	        int smtpPort = 587; 
//		 String smtpHost = "localhost"; //replace this with a valid host
//	        int smtpPort = 25; 
		 String smtpHost = "mail.intreact.tk"; //replace this with a valid host
	        int smtpPort = 587; 
//		 String smtpHost = "smtpout.secureserver.net"; //replace this with a valid host
//	        int smtpPort = 465; 
//		 String smtpHost = "relay-hosting.secureserver.net"; //replace this with a valid host
//	        int smtpPort = 25; 
//		 String smtpHost = "intreact.tk"; //replace this with a valid host
//        int smtpPort = 465; 
		 Properties properties = new Properties();
		MailResponse response = new MailResponse();
		MimeMessage message = sender.createMimeMessage();
		 properties.put("mail.smtp.host", smtpHost);
	        properties.put("mail.smtp.port", smtpPort);     
	    Session session = Session.getDefaultInstance(properties, null);
//	    String content = "Hi"
//	    		+ " greetings from Intuisyz,"
//	    		+ "  Your invoice attached below "+"or you can access it by click the link : http://intreact.tk:8090/#/mail-preview-details/"+lastInvId;
	    String content = request.getName() +" you can access it by click the link : http://intreact.tk:8090/#/mail-preview-details/"+lastInvId;
	    try {
			// set mediaType
//			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//					StandardCharsets.UTF_8.name());
//			// add attachment
////			helper.addAttachment("logo.png", new ClassPathResource("logo.png"));
		    MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(content);
			Template t = config.getTemplate("email-template-new.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos = generatePdf(html);
			 byte[] bytes = baos.toByteArray();
			  DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
	            MimeBodyPart pdfBodyPart = new MimeBodyPart();
	            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
	            pdfBodyPart.setFileName("invoice.pdf");
////			helper.addAttachment(html,baos); 
//			//helper.addAttachment("baos" ,(DataSource) new HttpEntity<byte[]>(baos.toByteArray()));
//			
//			//helper.addAttachment("Billing Software.pdf", new ClassPathResource("Billing Software.pdf"));
	            MimeMultipart mimeMultipart = new MimeMultipart();
	            mimeMultipart.addBodyPart(textBodyPart);
	            mimeMultipart.addBodyPart(pdfBodyPart);
	            //create the sender/recipient addresses
	            InternetAddress iaSender = new InternetAddress(request.getFrom());
	            InternetAddress iaRecipient = new InternetAddress(request.getTo());
//	            InternetAddress iaSender = new InternetAddress("invoice@intreact.tk");
//	            InternetAddress iaRecipient = new InternetAddress("invoice@intreact.tk");
	            //construct the mime message
//	            MimeMessage mimeMessage =  sender.createMimeMessage();
	            message.setSender(iaSender);
	            message.setSubject(request.getSubject());
	            message.setRecipient(Message.RecipientType.TO, iaRecipient);
	            message.setContent(mimeMultipart);
	            //send off the email
	            sender.send(message);
//			helper.setTo(request.getTo());
//			helper.setText(html, true);
//			helper.setSubject(request.getSubject());
//			helper.setFrom(request.getFrom());
//			sender.send(message);
			response.setMessage("mail send to : " + request.getTo());
			response.setStatus(Boolean.TRUE);
		} catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Mail Sending failure : "+e.getMessage());
			response.setStatus(Boolean.FALSE);
		}
		return response;
	}
	//////////invoice pdf download/////////////
//	public HttpEntity<byte[]> invoicePdfDownload(MailRequest request, Map<String, Object> model)throws IOException, TemplateException {
//			
//		System.out.print("Ready to mail");
//		System.out.print("model val "+model);
//		
//		
//		List<Invoice> li=(List<Invoice>) invoiceRepo.invoiceData();
//		
//		int lastId = 0;
//		
//		int lastInvId=0;
//		
//		if(li.size()>0)
//	    {
//			System.out.println("li invoice data size "+li.size());
//			
//			int i=li.size();
//			
//			 lastId=i-1;
//			System.out.println("li invoice data previous size "+ lastId);
//			
//			System.out.println("last id  "+Integer.toString(li.get(li.size()-1).getInv_id())+"    ");
//		
//			lastInvId=li.get(lastId).getInv_id();
//			
//		List<Invoice_sub> li1=(List<Invoice_sub>) invoiceSubRepo.invoiceSubDataById(Integer.toString(li.get(lastId).getInv_id()));
//	    
//		System.out.print("li1 size "+li1.size());
//		
//		if(li1.size()>0)
//	    {
//		
//			System.out.println("Invoice sub amount "+li1.get(0).getAmount());
//			
//			model.put("invoiceSubData", li1);
//	    }
//		
//		
//	    }
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//	List<Invoice_template> li5=(List<Invoice_template>) templateRepo.templateDatas();
//		
//		if(li5.size()>0)
//	    {
//		
//			String payTo= li5.get(0).getTemplate_payTo();
//			
//			System.out.println("Pay to "+payTo);
//			
//			String[] payToArray=payTo.split("\\r?\\n");
//			
//			System.out.println("Pay to arr "+payToArray);
//			
//			System.out.println("Pay to arr of 0 "+payToArray[0]);
//			
//			
//			String cName=li5.get(0).getTemplate_companyName();
//			String cAddress=li5.get(0).getTemplate_companyAddress();
//			String cContact=li5.get(0).getTemplate_companyContact();
//			String Name=li5.get(0).getTemplate_Name();
//			String logoImg=li5.get(0).getTemplate_logo();
//			String signImg=li5.get(0).getTemplate_sig();
//			
//			String[] cNameArray=cName.split("\\r?\\n");
//			String[] cAddressArray=cAddress.split("\\r?\\n");
//			String[] cContactArray=cContact.split("\\r?\\n");
//			String[] nameArray=Name.split("\\r?\\n");
//			
//			model.put("templateCompanyName", cNameArray);
//			model.put("templateCompanyAddress", cAddressArray);
//			model.put("templateCompanyContact", cContactArray);
//			model.put("templateName", nameArray);
//			model.put("templateData", payToArray);
//			model.put("templateLogo", logoImg);
//			model.put("templateSign", signImg);
//			
//			System.out.println("templateLogo "+logoImg);
//	    }
//		
//		
//		
//
//
//			Template t = config.getTemplate("email-template-new.ftl");
//			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
//			
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			baos = generatePdf(html);
//		
//			String fileName="invoice";
//		
//	            HttpHeaders header = new HttpHeaders();
//	    	    header.setContentType(MediaType.APPLICATION_PDF);
//	    	    header.set(HttpHeaders.CONTENT_DISPOSITION,
//	    	                   "attachment; filename=" + fileName.replace(" ", "_"));
//	    	    header.setContentLength(baos.toByteArray().length);
//
//	    	    return new HttpEntity<byte[]>(baos.toByteArray(), header);
//	            	
//	
//	}
//	
}