package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Account_ledger_v3;
import com.example.demo.model.Account_transactions_v3;
import com.example.demo.model.Invoice;
import com.example.demo.model.Invoice_sub;
import com.example.demo.repository.InvoiceRepo;
import com.example.demo.repository.InvoiceSubRepo;
import com.example.demo.repository.LedgerServiceRepo;

@Service
public class InvoiceService {
	
	
	@Autowired
	private InvoiceRepo invoiceRepo;
	
	@Autowired
	private InvoiceSubRepo invoiceSubRepo;
	
	
	@Autowired
	private InvoiceService invoiceService;
	
	
	@Autowired
	private LedgerServiceRepo ledgerServiceRepo;
	
	@Autowired
	private LedgerService ledgerService;
	
	
	
    public Invoice add_invoices(Invoice fp) {
			
    	    invoiceRepo.save(fp);
		   	    
			return null;
		   }
    
    public Invoice_sub add_invoice_subs(Invoice_sub fp) {
		
	    invoiceSubRepo.save(fp);
	   	    
		return null;
	   }
	
	
    public List<Invoice> invoiceDatas()  {
    	
  	  
    	List<Invoice> li=(List<Invoice>) invoiceRepo.invoiceData();
    	
    	  System.out.println("li "+ li.size());
    	  
    	  return li;
    }
    
    
    public List<Invoice> invoiceDataOnDashBoards()  {
    	
    	  List<Account_ledger_v3> li1 = new ArrayList<Account_ledger_v3>();	
 		 List<Account_ledger_v3> li2 = new ArrayList<Account_ledger_v3>() ;
    	
    	  
    	List<Invoice> li=(List<Invoice>) invoiceRepo.invoiceData();
    	
    	  System.out.println("li "+ li.size());
    	  
    	  
    	  if(li.size()>0)
   		{
   			
   		for (int i = 0; i < li.size(); i++)   
   		{ 
   		
   		 li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(i).getCust_name()));
   			
   		 li2=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(i).getService()));
    		
   		 
   		 
   		 if(li1.size()>0)
		    {
         	
         	
       	li.get(i).setCust_name(li1.get(0).getLedger_name());
     	
     	
		    }
         
//         System.out.println("li2 size"+ li2.size()+" li2"+li2 );
        
         
         if(li2.size()>0)
		    {
         	
       	li.get(i).setService(li2.get(0).getLedger_name());
     	
     	
		    }
   		 
   		 
   		}
   		
   		}
    	  
    	  
    	  
    	  
    	  
    	  return li;
    }
    
    
    
    
    
    
    
    
    public List<Invoice> invoiceDataByIds(String invoiceId)  {
    	
    	  
    	List<Invoice> li=(List<Invoice>) invoiceRepo.invoiceDataById(Integer.parseInt(invoiceId));
    	
    	  System.out.println("li "+ li.size());
    	  
    	  return li;
    } 
    
    
    public List<Invoice_sub> invoiceSubDataByIds(String invoiceId)  {
    	
  	  
    	List<Invoice_sub> li=(List<Invoice_sub>) invoiceSubRepo.invoiceSubDataById(invoiceId);
    	
    	  System.out.println("li "+ li.size());
    	  
    	  return li;
    } 
    
    
    public String invoiceSubDelete(String inv_id) {
    	
    	

    	List<Invoice_sub> li=(List<Invoice_sub>) invoiceSubRepo.invoiceSubDataById(inv_id);
    	
    	  System.out.println("li "+ li.size());
    	
    	  for(int i=0;i<li.size();i++)
    	  {
    		  
    		  invoiceSubRepo.deleteById(li.get(i).getInv_sub_id());
    	  }
    	

    	return "Deleted successfully";
    }
    
    
    public List<Invoice> invoiceDataByTransactionIds(String transactionId)  {
    	
  	  
    	List<Invoice> li=(List<Invoice>) invoiceRepo.invoiceDataByTransactionId(transactionId);
    	
    	  System.out.println("li "+ li.size());
    	  
    	  return li;
    } 
    
    
    
    public String invoiceDelete(int inv_id) {
		
    	invoiceRepo.deleteById(inv_id);
		// TODO Auto-generated method stub
		return "Deleted successfully";
	}
	

    
    
}
