package com.example.demo.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Invoice;
import com.example.demo.model.Invoice_template;
import com.example.demo.repository.TemplateRepo;
@Service
public class TemplateService {
	@Autowired
	private TemplateRepo templateRepo;
	 public List<Invoice_template> templateData()  {
	    	List<Invoice_template> li=(List<Invoice_template>) templateRepo.templateDatas();
	    	  System.out.println("li "+ li.size());
	    	  return li;
	    }
}
