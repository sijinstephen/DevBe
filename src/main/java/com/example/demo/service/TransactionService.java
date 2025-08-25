package com.example.demo.service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.json.JSONObject;
import org.json.JSONArray;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.model.Account_group_v3;
import com.example.demo.model.Account_ledger_v3;
import com.example.demo.model.Account_title_v3;
import com.example.demo.model.Account_transactions_v3;
import com.example.demo.repository.AcTitleRepo;
import com.example.demo.repository.GroupServiceRepo;
import com.example.demo.repository.LedgerServiceRepo;
import com.example.demo.repository.TransactionServiceRepo;
//import jdk.nashorn.internal.runtime.Undefined;
@Service
public class TransactionService {
	@Autowired
	private TransactionServiceRepo transactionServiceRepo;
	@Autowired
	private LedgerService ledgerService;
	@Autowired
	private LedgerServiceRepo ledgerServiceRepo;
	@Autowired
	private GroupServiceRepo groupServiceRepo;
	@Autowired
	private AcTitleRepo acTitleRepo;
   public Account_transactions_v3 add_Transactions(Account_transactions_v3 fp) {
	   transactionServiceRepo.save(fp);
//	   update_Transactions(fp);
		return null;
	}
   public void update_Transactions(Account_transactions_v3 fp)
   {
	   System.out.println("creditor  "+fp.getCrdt_ac());
	   System.out.println("debitor  "+fp.getDbt_ac());
	   List<Account_ledger_v3>   ledgerdetails= getLedgerID(fp.getCrdt_ac());
	   List<Account_ledger_v3>   ledgerdetails1= getLedgerID(fp.getDbt_ac());
	   Iterator<Account_ledger_v3> it = ledgerdetails.iterator(); 
	   String type = null, type1 = null,createDate,createTime;
	    while (it.hasNext()) { 
	    	Account_ledger_v3 ob = it.next(); 
	    	 System.out.println("creditor type "+ob.getAc_type());
	    	 type=ob.getAc_type();
	    }
	    Iterator<Account_ledger_v3> it1 = ledgerdetails1.iterator(); 
	    while (it1.hasNext()) { 
	    	Account_ledger_v3 ob = it1.next(); 
	    	 System.out.println("debitor type "+ob.getAc_type());
	    	 type1=ob.getAc_type();
	    }
	    createDate = fp.getCreatedDate();
	    createTime=fp.getCreatedTime();
	    if(type1.equals("2")||type1.equals("3"))
	    {
	    	increaseAmount(fp.getDbt_ac(),fp.getAmount(),createDate,createTime);
	    }
	    else
	    {
	    	decreaseAmount(fp.getDbt_ac(),fp.getAmount(),createDate,createTime);
	    }
	    if(type.equals("2")||type.equals("3"))
	    {
	    	increaseAmount(fp.getCrdt_ac(),fp.getAmount(),createDate,createTime);
	    }
	    else
	    {
	    	decreaseAmount(fp.getCrdt_ac(),fp.getAmount(),createDate,createTime);
	    }
   }
   public List<Account_transactions_v3> transactionDates()  {
	List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.transactionDate();
	return li;
}
   public List<Account_ledger_v3> getLedgerID(String j)
   {
	   List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.ledger(j);
	   return li;
   }
 public ResponseEntity<Object> increaseAmount(String ledgerID,String ledgeramount,String created_date,String time)
 {
	 List<Account_ledger_v3>   ledger =	getLedgerID(ledgerID);
	 int currentAmount=0;
	  int updatedAmount=0;
	 Iterator<Account_ledger_v3> it1 = ledger.iterator(); 
	    while (it1.hasNext()) { 
	    	Account_ledger_v3 ob = it1.next(); 
	    	if(ob.getAmount().equals(""))
	    	{
	    		currentAmount=0;
	    	}
	    	else
	    	{
	    	  currentAmount =Integer.parseInt(ob.getAmount());
	    	}
	   	 System.out.println("currentAmount  "+currentAmount);
	   	 updatedAmount=currentAmount+Integer.parseInt(ledgeramount);
	   	 String s=String.valueOf(updatedAmount);
	   	 ob.setAmount(s);
	    }
	     System.out.println("updatedAmount  "+updatedAmount);
	    System.out.println("ledgerID  "+ledgerID);
//	    Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt(ledgerID));
//
//		if (!studentOptional.isPresent())
//			return ResponseEntity.notFound().build();
//
//		ledgerServiceRepo.saveAll(ledger);
		return ResponseEntity.noContent().build();
////	    String updatedAmount1=String.pa updatedAmount;
//	    List<Account_ledger_v3>   oo=  ledgerServiceRepo.ledgerAmountUpdate(Integer.parseInt(ledgerID),s,created_date,time);
//	 
	 }
 public ResponseEntity<Object> decreaseAmount(String ledgerID,String ledgeramount,String created_date,String time)
 {
     List<Account_ledger_v3>   ledger =	getLedgerID(ledgerID);
	 int currentAmount=0;
	 int updatedAmount=0;
	 Iterator<Account_ledger_v3> it1 = ledger.iterator(); 
	    while (it1.hasNext()) { 
	    	Account_ledger_v3 ob = it1.next(); 
	    	if(ob.getAmount().equals(""))
	    	{
	    		currentAmount=0;
	    	}
	    	else
	    	{
	    	  currentAmount =Integer.parseInt(ob.getAmount());
	    	}
	   	 System.out.println("currentAmount  "+currentAmount);
	     updatedAmount=currentAmount-Integer.parseInt(ledgeramount);
		    String s=String.valueOf(updatedAmount);
		    ob.setAmount(s);
	    }
	    System.out.println("updatedAmount  "+updatedAmount);
	    System.out.println("ledgerID  "+ledgerID);
//	    Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt(ledgerID));
//
//		if (!studentOptional.isPresent())
//			return ResponseEntity.notFound().build();
//
//		ledgerServiceRepo.saveAll(ledger);
		return ResponseEntity.noContent().build();
//	    List<Account_ledger_v3>   oo=  ledgerServiceRepo.ledgerAmountUpdate(Integer.parseInt(ledgerID),s,created_date,time);
//	  
 }
    public List<Account_transactions_v3> list_transactions()  {
		List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.selectData();
		Iterator<Account_transactions_v3> it = li.iterator(); 
	    while (it.hasNext()) { 
	    	Account_transactions_v3 ob = it.next(); 
	    	ob.setTran_gen(ob.getDbt_ac());
	    	String ledgerName = (String) ledgerServiceRepo.getLedger(Integer.parseInt(ob.getDbt_ac()) );
	    	System.out.println(ledgerName);
	    	ob.setDbt_ac(ledgerName);
//	    	
//	    	ob.setAc_group(grpName);
//	    	
//	    	ob.setAc_title(acTitle);
//	    	
//	    	String acType = (String) acTypeRepo.selectType(ob.getAc_type());
//	    	
//	    	ob.setAc_type(acType);
	    }
		return li;
	}
  public List<Account_transactions_v3> transaction_sorts(String field, String type)  {
		System.out.println(field+' '+type);
		List<Account_transactions_v3> li = null;
		if(field.equals("tran_Date")&&type.equals("ASC"))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.tran_DateA();
		}
		if(field.equals("tran_Date")&&type.equals("DESC"))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.tran_DateD();
		}
		if(field.equals("dbt_ac")&&type.equals("ASC"))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.dbt_acA();
		}
		if(field.equals("dbt_ac")&&type.equals("DESC"))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.dbt_acD();
		}
		if(field.equals("mode")&&type.equals("ASC"))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.modeA();
		}
		if(field.equals("mode")&&type.equals("DESC"))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.modeD();
		}
		if(field.equals("amount")&&type.equals("ASC"))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.amountA();
		}
		if(field.equals("amount")&&type.equals("DESC"))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.amountD();
		}
		if(field.equals("description")&&type.equals("ASC"))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.descriptionA();
		}
		if(field.equals("description")&&type.equals("DESC"))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.descriptionD();
		}
		Iterator<Account_transactions_v3> it = li.iterator(); 
		  while (it.hasNext()) { 
		    	Account_transactions_v3 ob = it.next(); 
		    	String ledgerName = (String) ledgerServiceRepo.getLedger(Integer.parseInt(ob.getDbt_ac()) );
		    	System.out.println(ledgerName);
		    	ob.setDbt_ac(ledgerName);
	    }
		return li;
    }
    public List<Account_transactions_v3> payment_bn_dates(String start, String end)  {
		System.out.println("start"+start+"end"+end);
		List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.selectDataBnDate(start,end);
		Iterator<Account_transactions_v3> it = li.iterator(); 
		 while (it.hasNext()) { 
		    	Account_transactions_v3 ob = it.next(); 
		    	String ledgerName = (String) ledgerServiceRepo.getLedger(Integer.parseInt(ob.getDbt_ac()) );
		    	System.out.println(ledgerName);
		    	ob.setDbt_ac(ledgerName);
	    }
	    return li;
	   }
    public List<Account_transactions_v3> transaction_searchs(String transactionId)  {
		System.out.println(transactionId);
		List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.transaction_Search(transactionId);
		Iterator<Account_transactions_v3> it = li.iterator(); 
		 while (it.hasNext()) { 
		    	Account_transactions_v3 ob = it.next(); 
		    	ob.setDebit_blnc_bfore_txn(ob.getDbt_ac());
		    	ob.setCredit_blnc_bfore_txn(ob.getCrdt_ac());
		 List<Account_ledger_v3> ledgerlist = (List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(ob.getDbt_ac()) );
		 Iterator<Account_ledger_v3> it1 = ledgerlist.iterator(); 
		 while (it1.hasNext()) { 
			 Account_ledger_v3 ob1 = it1.next(); 
			    ob.setStatus(ob.getFilepath());
		    	ob.setDbt_ac(ob1.getLedger_name());
		    	ob.setTran_gen(ob1.getPin());
		    	ob.setCreatedDate(ob1.getContact());
		    	ob.setCreatedTime(ob1.getFax());
		    	ob.setFilename(ob1.getState());
		    	ob.setFilepath(ob1.getAddress());
		 }
	    }
		return li;
	}
public Account_transactions_v3 add_payments(Account_transactions_v3 fp) {
		return transactionServiceRepo.save(fp);
	}
public String payment_deletes(int id) {
	transactionServiceRepo.deleteById(id);
//	transactionServiceRepo.transactionDelete(id);
	// TODO Auto-generated method stub
	return "Deleted successfully";
}
       ///////////////////////Journal//////////////
     public Account_transactions_v3 add_journalTransactions(Account_transactions_v3 fp) {
	   transactionServiceRepo.save(fp);
		return null;
	   }
public List<Account_transactions_v3> list_journals()  {
		List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.selectDataJournal();
		Iterator<Account_transactions_v3> it = li.iterator(); 
	    while (it.hasNext()) { 
	    	Account_transactions_v3 ob = it.next(); 
	    	ob.setCreatedTime(ob.getDbt_ac());
	    	String debitaccount = (String) ledgerServiceRepo.getLedger(Integer.parseInt(ob.getDbt_ac()) );
	    	String creditaccount = (String) ledgerServiceRepo.getLedger(Integer.parseInt(ob.getCrdt_ac()) );
	    	ob.setDbt_ac(debitaccount);
	    	ob.setCrdt_ac(creditaccount);
//	    	
//	    	ob.setAc_group(grpName);
//	    	
//	    	ob.setAc_title(acTitle);
//	    	
//	    	String acType = (String) acTypeRepo.selectType(ob.getAc_type());
//	    	
//	    	ob.setAc_type(acType);
	    }
		return li;
	}
public List<Account_transactions_v3> journal_sorts(String field, String type)  {
	System.out.println(field+' '+type);
	List<Account_transactions_v3> li = null;
	if(field.equals("tran_Date")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.tran_Date_journalA();
	}
	if(field.equals("tran_Date")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.tran_Date_journalD();
	}
	if(field.equals("dbt_ac")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.dbt_ac_journalA();
	}
	if(field.equals("dbt_ac")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.dbt_ac_journalD();
	}
	if(field.equals("crdt_ac")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.crdt_ac_journalA();
	}
	if(field.equals("crdt_ac")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.crdt_ac_journalD();
	}
	if(field.equals("amount")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.amount_journalA();
	}
	if(field.equals("amount")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.amount_journalD();
	}
	if(field.equals("description")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.description_journalA();
	}
	if(field.equals("description")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.description_journalD();
	}
	Iterator<Account_transactions_v3> it = li.iterator(); 
    while (it.hasNext()) { 
    	Account_transactions_v3 ob = it.next(); 
    	ob.setCreatedTime(ob.getDbt_ac());
    	String debitaccount = (String) ledgerServiceRepo.getLedger(Integer.parseInt(ob.getDbt_ac()) );
    	String creditaccount = (String) ledgerServiceRepo.getLedger(Integer.parseInt(ob.getCrdt_ac()) );
    	ob.setDbt_ac(debitaccount);
    	ob.setCrdt_ac(creditaccount);
       }
	return li;
}
public List<Account_transactions_v3> journal_bn_dates(String start, String end)  {
	System.out.println("start"+start+"end"+end);
	List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.selectJournalDataBnDate(start,end);
	Iterator<Account_transactions_v3> it = li.iterator(); 
	  while (it.hasNext()) { 
	    	Account_transactions_v3 ob = it.next(); 
	    	ob.setCreatedTime(ob.getDbt_ac());
	    	String debitaccount = (String) ledgerServiceRepo.getLedger(Integer.parseInt(ob.getDbt_ac()) );
	    	String creditaccount = (String) ledgerServiceRepo.getLedger(Integer.parseInt(ob.getCrdt_ac()) );
	    	ob.setDbt_ac(debitaccount);
	    	ob.setCrdt_ac(creditaccount);
    }
    return li;
   }
public String journal_deletes(int id) {
	transactionServiceRepo.deleteById(id);
//	transactionServiceRepo.transactionDelete(id);
	// TODO Auto-generated method stub
	return "Deleted successfully";
}
public List<Account_transactions_v3> journal_searchs(String tranId)  {
	System.out.println(tranId);
	List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.journal_Search(tranId);
	return li;
  }
public List<Account_transactions_v3> journal_searchInvoices(String tranId,String creditAc,String debitAc)  {
	System.out.println("tran gen value "+ tranId);
	List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.journal_searchInvoice(tranId,creditAc,debitAc);
	return li;
  }
public List<Account_transactions_v3> ledger_transaction_searchs(String dbt_ac,String crdt_ac)  {
	List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.ledger_transaction_search(dbt_ac,crdt_ac);
	return li;
}
    ///////////Receipt////////////
public List<Account_transactions_v3> list_receipts()  {
	List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.selectDataReceipt();
	Iterator<Account_transactions_v3> it = li.iterator(); 
    while (it.hasNext()) { 
    	Account_transactions_v3 ob = it.next(); 
    	ob.setCreatedBy(ob.getCrdt_ac());
    	String creditaccount = (String) ledgerServiceRepo.getLedger(Integer.parseInt(ob.getCrdt_ac()) );
       ob.setCrdt_ac(creditaccount);
//    	
//    	ob.setAc_group(grpName);
//    	
//    	ob.setAc_title(acTitle);
//    	
//    	String acType = (String) acTypeRepo.selectType(ob.getAc_type());
//    	
//    	ob.setAc_type(acType);
    }
	return li;
}
public List<Account_transactions_v3> receipt_sorts(String field, String type)  {
	System.out.println(field+' '+type);
	List<Account_transactions_v3> li = null;
	if(field.equals("tran_Date")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.tran_DateReceiptA();
	}
	if(field.equals("tran_Date")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.tran_DateReceiptD();
	}
	if(field.equals("dbt_ac")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.dbt_acReceiptA();
	}
	if(field.equals("dbt_ac")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.dbt_acReceiptD();
	}
	if(field.equals("mode")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.modeReceiptA();
	}
	if(field.equals("mode")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.modeReceiptD();
	}
	if(field.equals("amount")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.amountReceiptA();
	}
	if(field.equals("amount")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.amountReceiptD();
	}
	if(field.equals("description")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.descriptionReceiptA();
	}
	if(field.equals("description")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.descriptionReceiptD();
	}
	Iterator<Account_transactions_v3> it = li.iterator(); 
	  while (it.hasNext()) { 
	    	Account_transactions_v3 ob = it.next(); 
	    	ob.setCreatedBy(ob.getCrdt_ac());
	    	String creditaccount = (String) ledgerServiceRepo.getLedger(Integer.parseInt(ob.getCrdt_ac()) );
	        ob.setCrdt_ac(creditaccount);
    }
	return li;
}
public List<Account_transactions_v3> receipt_bn_dates(String start, String end)  {
	System.out.println("start"+start+"end"+end);
	List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.selectReceiptDataBnDate(start,end);
	Iterator<Account_transactions_v3> it = li.iterator(); 
	 while (it.hasNext()) { 
	    	Account_transactions_v3 ob = it.next(); 
	    	ob.setCreatedBy(ob.getCrdt_ac());
         String creditaccount = (String) ledgerServiceRepo.getLedger(Integer.parseInt(ob.getCrdt_ac()) );
	        ob.setCrdt_ac(creditaccount);
    }
    return li;
   }
     ///////////account statement///////////
public List<Account_ledger_v3> list_account_statements()  {
	List<String> li1=(List<String>) transactionServiceRepo.selectDbt_account_statements();
	System.out.println("transaction dbt"+li1);
	List<String> li2=(List<String>) transactionServiceRepo.selectCrdt_account_statements();
	System.out.println("transaction crdt"+li2);
	List<String> li= new ArrayList<String>();
    li.addAll(li1);
    li.addAll(li2);
    System.out.print("li size before filtering"+li.size());
    for(int i=1; i<li.size(); i++) {
        for(int j=0;j<i;j++) {
            if(li.get(i).equals(li.get(j))) {
                li.remove(i);
                i--;
                break;
            }
        }
    }
    System.out.print("li size after filtering"+li.size());
    List<Account_ledger_v3> finalList= new ArrayList<Account_ledger_v3>();
    for(int k=1; k<li.size(); k++) {
    List<Account_ledger_v3> li3=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(k)));
    if(li3.size()>0)
    {
         List<Account_group_v3> li4=(List<Account_group_v3>) groupServiceRepo.grp_id_Search(li3.get(0).getAc_group());
         if(li4.size()>0)
         {
            li3.get(0).setAc_group(li4.get(0).getGroup_name());
            List<Account_title_v3> li5=(List<Account_title_v3>) acTitleRepo.acTitle_searchs(li4.get(0).getAc_title());
            if(li5.size()>0)
             {
              li3.get(0).setAc_title(li5.get(0).getAc_title());
             }
         }
    }
    finalList.addAll(li3);
    }
//	Iterator<Account_transactions_v3> it = li.iterator(); 
//	  
//    while (it.hasNext()) { 
//    	
//    	Account_transactions_v3 ob = it.next(); 
//    	
//    	ob.setTran_gen(ob.getDbt_ac());
//    	
//    	String ledgerName = (String) ledgerServiceRepo.getLedger(Integer.parseInt(ob.getDbt_ac()) );
//    	
//    	System.out.println(ledgerName);
//    	ob.setDbt_ac(ledgerName);
//    	
//    	
//    	ob.setAc_group(grpName);
//    	
//    	ob.setAc_title(acTitle);
//    	
//    	String acType = (String) acTypeRepo.selectType(ob.getAc_type());
//    	
//    	ob.setAc_type(acType);
//    }
	return finalList;
}
public List<Account_transactions_v3> accStmtTransactions(String id, String description)  {
	List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.selectAccStmtTransaction(id,description);
	return li;
}
public List<Account_transactions_v3> accStmtTransactionBnDates(String id, String description,String start, String end)  {
	List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.selectAccStmtTransactionBndates(id,description,start,end);
	return li;
}
    /////////////////cash Book///////////
//////////////////////new code//////////////////
public List<Account_transactions_v3> cashBookAccount_statementDatas(int ledgerId)  {
		System.out.println(ledgerId);
		String ledger_id=Integer.toString(ledgerId);
		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.ledger_Search2(ledgerId);
		System.out.println(li.size());
		Float cur_amount,cur_balance1 = (float) 0 , debit_total1 = (float) 0 ,credit_total1 =(float) 0 ;
		String  balance_type = null , description = "ledger creation" ,particularname=null,created_date=null,tran_description=null;
		Float  Amount=(float)0,tran_open_balance=(float)0;
	    Integer particular=0;
	    Float   balance=(float)0;
		for (int i = 0; i < li.size(); i++)   
		{  
			System.out.println(li.get(i).getAc_group());
			balance_type=li.get(i).getBalance_type();
			 if (balance_type.equals("debit")) {
			    if(li.get(i).getOpen_balance().equals(""))
	 	 	      {
			    	 debit_total1 +=(float) 0;
	 	 	      }
	 	 	      else
	 	 	      {
	 	          debit_total1 += Float.parseFloat(li.get(i).getOpen_balance());   
	 	 	      }
	 	        }
	 	        if (balance_type.equals("credit")){
	 	          if(li.get(i).getOpen_balance().equals(""))
	 	 	      {
	 	        	credit_total1 +=(float) 0;
	 	 	      }
	 	 	      else
	 	 	      {
	 	          credit_total1 += Float.parseFloat(li.get(i).getOpen_balance()); 
	 	 	      }
	 	        }
	 	      System.out.println(debit_total1+"  "+credit_total1);
	 	      if(li.get(i).getOpen_balance().equals(""))
	 	      {
	 	    	tran_open_balance=(float) 0;
	 	      }
	 	      else
	 	      {
	 	        tran_open_balance=Float.parseFloat(li.get(i).getOpen_balance());
	 	      }
		}
		 System.out.println(" balance_type  "+balance_type);
		 JSONObject jsonObject = new JSONObject();
	      //Inserting key-value pairs into the json object
//	      jsonObject.put("ID", "1");
//	      jsonObject.put("First_Name", "Krishna Kasyap");
//	      jsonObject.put("Last_Name", "Bhagavatula");
//	      jsonObject.put("Date_Of_Birth", "1989-09-26");
//	      jsonObject.put("Place_Of_Birth", "Vishakhapatnam");
//	      jsonObject.put("Country", "25000");
	      //Creating a json array
	      JSONArray array = new JSONArray();
//	      array.put("e-mail: krishna_kasyap@gmail.com");
	      //Adding array to the json object
	     cur_balance1=cur_balance1+tran_open_balance;
List<Account_transactions_v3> li1=(List<Account_transactions_v3>) transactionServiceRepo.selectAccStmtTransaction2(ledgerId,description);
//Iterator<Account_transactions_v3> it = li1.iterator(); 
//while (it.hasNext()) { 
//
//Account_transactions_v3 ob = it.next(); 
//
//System.out.println("fgdfgdf"+ob.getType());
//
//}
String flag=null;
System.out.println(li1.size());
if(li1.size()>0)
{
for (int i = 0; i < li1.size(); i++)   
{ 
// if (li1.get(i).getDbt_ac().equals( ledger_id)) {
//     
//	  particular = Integer.parseInt(li1.get(i).getCrdt_ac());
//	  
// }
// 
//
// if (li1.get(i).getCrdt_ac().equals(ledger_id)) {
//	  
//	  particular = Integer.parseInt(li1.get(i).getDbt_ac());
// 
//
// }
 if(li1.get(i).getDbt_ac().equals(ledgerId) && !li1.get(i).getCrdt_ac().equals(ledgerId)){
		particular=Integer.parseInt(li1.get(i).getCrdt_ac());
}
  if(li1.get(i).getDbt_ac().equals("Nil") || li1.get(i).getCrdt_ac().equals("Nil")){
 	 particular=ledgerId;
  }
		else{
			particular=Integer.parseInt(li1.get(i).getDbt_ac());
 }	
particularname= ledgerServiceRepo.ac_ledger_SearchName(particular);
System.out.println("particularname  "+ particularname);
//System.out.println("balance_type  "+ balance_type);
//balance_type=li1.get(i).get
   Amount =Float.parseFloat(li1.get(i).getAmount()) ;
//   System.out.println("Amount  "+ Amount);
//   System.out.println("Dbt_ac  "+ li1.get(i).getDbt_ac());
//   
//   System.out.println("Crdt_ac  "+ li1.get(i).getCrdt_ac());
               if (li1.get(i).getDbt_ac().equals( ledger_id)) {
   	                if (balance_type.equals("debit")) {
   	                  cur_balance1 =
   	                  cur_balance1 + Amount;
   	                } else if (balance_type.equals("credit")) {
   	                  cur_balance1 =
   	                    cur_balance1 - Amount;
   	                }
   	                debit_total1 =
   	                  debit_total1 + Amount;
   	                flag="debit";
   	              }
               if (li1.get(i).getCrdt_ac().equals(ledger_id)) {
	                if (balance_type.equals("debit")) {
	                  cur_balance1 =
	                   cur_balance1 - Amount;
	                } else if (balance_type.equals("credit")) {
	                  cur_balance1 =
	                    cur_balance1 + Amount;
	             }
	                credit_total1 =
	                 credit_total1 + Amount;
	             flag="credit";
	               }
               created_date=li1.get(i).getCreatedDate();
               tran_description=li1.get(i).getDescription();
               System.out.println("cur_balance1  "+ cur_balance1);
               array.put("e-mail:"+cur_balance1);
               array.put("particularname "+particularname);
               array.put("flag "+flag);
//               cur_balance1=(float) 0;
//               Amount=(float) 0;
               li1.get(i).setFilename(particularname);
   			li1.get(i).setAmount(Float.toString(Amount));
   			li1.get(i).setCreatedDate(created_date);
   			li1.get(i).setDescription(tran_description);
   		li1.get(i).setBank(Float.toString(cur_balance1));
   		li1.get(i).setBranch(flag);
       }
li1.get(0).setChq_no(Float.toString( debit_total1));
li1.get(0).setChq_date(Float.toString( credit_total1));
if(debit_total1==0&&credit_total1!=0)
{
 balance=credit_total1; 
}
if(debit_total1!=0&&credit_total1==0)
{
 balance=debit_total1;  
}
if(debit_total1!=0&&credit_total1!=0)
{
balance=debit_total1-credit_total1;
}
li1.get(0).setCreatedTime(Float.toString( balance));
  System.out.println("debit_total1  "+ debit_total1+"  "+credit_total1);
  jsonObject.put("contact",array);
  System.out.println("array  "+ array);
}	
		return li1;
}
//////////////Old code/////////////
//public List<Account_transactions_v3> cashBookData()  {
//	
//	  Long totalDebit =(long) 0;
//      Long totalCredit = (long) 0;
//      Long totalDebitcontra =(long) 0;
//      Long tot =(long) 0;
//	
//      
//    Float cur_amount,cur_balance1 = (float) 0 , debit_total1 = (float) 0 ,credit_total1 =(float) 0 ;
//	String  balance_type = null , description = "ledger creation" ,particularname=null,created_date=null,tran_description=null;
//	Float  Amount=(float)0,tran_open_balance=(float)0;
//	Integer particular=0;
//	Float   balance=(float)0;
//	String flag=null;   
//      
//      
//      
//      
////      
////  	String ledger_id=Integer.toString(ledgerId);
////		
////		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.ledger_Search2(ledgerId);
////		
////		System.out.println(li.size());
////		
////		
////		for (int i = 0; i < li.size(); i++)   
////		{  
////			
////			System.out.println(li.get(i).getAc_group());
////			
////			balance_type=li.get(i).getBalance_type();
////			
////			 if (balance_type.equals("debit")) {
////				 
////			    if(li.get(i).getOpen_balance().equals(""))
////	 	 	      {
////			    	 debit_total1 +=(float) 0;
////	 	 	      }
////	 	 	      else
////	 	 	      {
////				 
////	 	          debit_total1 += Float.parseFloat(li.get(i).getOpen_balance());   
////	 	          
////	 	 	      }
////	 	          
////	 	          
////	 	        }
////	 	        if (balance_type.equals("credit")){
////	 	        	
////	 	          if(li.get(i).getOpen_balance().equals(""))
////	 	 	      {
////	 	        	credit_total1 +=(float) 0;
////	 	 	      }
////	 	 	      else
////	 	 	      {
////	 	        	
////	 	          credit_total1 += Float.parseFloat(li.get(i).getOpen_balance()); 
////	 	          
////	 	 	      }
////	 	        }
////
////	 	      System.out.println(debit_total1+"  "+credit_total1);
////	 	      if(li.get(i).getOpen_balance().equals(""))
////	 	      {
////	 	    	tran_open_balance=(float) 0;
////	 	      }
////	 	      else
////	 	      {
////	 	        tran_open_balance=Float.parseFloat(li.get(i).getOpen_balance());
////	 	      }
////			
////		}
////		
////		 System.out.println(" balance_type  "+balance_type);
////		
////	
////		 
////		 
////
////		 
////	     cur_balance1=cur_balance1+tran_open_balance;
////		 
////		 
////	    	
////List<Account_transactions_v3> li1=(List<Account_transactions_v3>) transactionServiceRepo.selectAccStmtTransaction2(ledgerId,description);
////		
////
////
////
////String flag=null;
////		
////System.out.println(li1.size());
////
////if(li1.size()>0)
////{
////
////for (int i = 0; i < li1.size(); i++)   
////{ 
////
////
//// if (li1.get(i).getDbt_ac().equals( ledger_id)) {
////     
////	  particular = Integer.parseInt(li1.get(i).getCrdt_ac());
////	  
//// }
//// 
////
//// if (li1.get(i).getCrdt_ac().equals(ledger_id)) {
////	  
////	  particular = Integer.parseInt(li1.get(i).getDbt_ac());
//// 
////
//// }
//// 
////particularname= ledgerServiceRepo.ac_ledger_SearchName(particular);
////
////
////System.out.println("particularname  "+ particularname);
////
////   Amount =Float.parseFloat(li1.get(i).getAmount()) ;
////   
////
////               if (li1.get(i).getDbt_ac().equals( ledger_id)) {
////   	            	  
////   	                if (balance_type.equals("debit")) {
////   	                
////   	                  cur_balance1 =
////   	                  cur_balance1 + Amount;
////   	                  
////   	                } else if (balance_type.equals("credit")) {
////   	                 
////   	                  cur_balance1 =
////   	                    cur_balance1 - Amount;
////   	                  
////   	                }
////   	                debit_total1 =
////   	                  debit_total1 + Amount;
////   	                
////   	                flag="debit";
////   	                
////   	              }
////  
////
////               if (li1.get(i).getCrdt_ac().equals(ledger_id)) {
////	               
////	                if (balance_type.equals("debit")) {
////	                 
////	                  cur_balance1 =
////	                   cur_balance1 - Amount;
////	                 
////	                } else if (balance_type.equals("credit")) {
////	                
////	                  cur_balance1 =
////	                    cur_balance1 + Amount;
////	                
////	             }
////	                
////	                credit_total1 =
////	                 credit_total1 + Amount;
////	                
////	             flag="credit";
////	               }
////               
////               
////               created_date=li1.get(i).getCreatedDate();
////               tran_description=li1.get(i).getDescription();
////               
////               
////               
////               System.out.println("cur_balance1  "+ cur_balance1);
////               
////              
////               
////               li1.get(i).setFilename(particularname);
////   			li1.get(i).setAmount(Float.toString(Amount));
////   			li1.get(i).setCreatedDate(created_date);
////   			li1.get(i).setDescription(tran_description);
////   		li1.get(i).setBank(Float.toString(cur_balance1));
////   		li1.get(i).setBranch(flag);
////   		
////   		
////       }
////
////
////li1.get(0).setChq_no(Float.toString( debit_total1));
////li1.get(0).setChq_date(Float.toString( credit_total1));
////
////
////
////if(debit_total1==0&&credit_total1!=0)
////{
//// balance=credit_total1; 
////}
////
////if(debit_total1!=0&&credit_total1==0)
////{
//// balance=debit_total1;  
////}
////
////if(debit_total1!=0&&credit_total1!=0)
////{
////balance=debit_total1-credit_total1;
////}
////  
////
////li1.get(0).setCreatedTime(Float.toString( balance));
////
////  System.out.println("debit_total1  "+ debit_total1+"  "+credit_total1);
////	       
//// 
////
////}	
//
//      
//	
//	
//List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.cashBookOpenBalanceDataFetch();
//
////System.out.println("li "+ li.size());
//
//
//if(li.size()>0)
//{
//	String ledger;
//
//for (int i = 0; i < li.size(); i++)   
//{ 
//	
//	
//	
//	if(li.get(i).getDbt_ac().equals("30") && !li.get(i).getCrdt_ac().equals("30")){
//		
//		ledger=li.get(i).getCrdt_ac();
// }
//	
// if(li.get(i).getDbt_ac().equals("Nil") || li.get(i).getCrdt_ac().equals("Nil")){
//	   
//	ledger="30";
// }
// else{
//	   
//	   ledger=li.get(i).getDbt_ac();
// }	
//	
// 
//List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(ledger));
// 
////System.out.println("li1 "+ li1.size());
//	 
//if(li1.size()>0)
//{
//	li.get(i).setBranch(li1.get(0).getLedger_name());
//	
//	li.get(i).setTran_Date(li1.get(0).getLedger_date());
//	
//	
//  Amount =Float.parseFloat(li.get(i).getAmount()) ;
//	  
//	  balance_type=li1.get(0).getBalance_type();
//	
//	
//	 ////////////new code///////////////
//    
//    if (li.get(i).getDbt_ac().equals("30")) {
//     	  
//           if (balance_type.equals("debit")) {
//           
//             cur_balance1 =
//             cur_balance1 + Amount;
//             
//           } else if (balance_type.equals("credit")) {
//            
//             cur_balance1 =
//               cur_balance1 - Amount;
//             
//           }
//           
////           debit_total1 =
////             debit_total1 + Amount;
//           
//           flag="debit";
//           
//         }
//
//
//   if (li.get(i).getCrdt_ac().equals("30")) {
//       
//        if (balance_type.equals("debit")) {
//         
//          cur_balance1 =
//           cur_balance1 - Amount;
//         
//        } else if (balance_type.equals("credit")) {
//        
//          cur_balance1 =
//            cur_balance1 + Amount;
//        
//     }
//        
////        credit_total1 =
////         credit_total1 + Amount;
//        
//     flag="credit";
//       }
//
//   
//   
//
//   
//   
//   
//   
//   ////////////////////////////////////////////////////
//
//	
//	
//	
//	
//	
//}
//
//
//if(li.get(i).getDbt_ac().equals("30"))
//{
//
//totalDebit=totalDebit+Long.parseLong(li.get(i).getAmount()); 
//}
//
//
//if(li.get(i).getCrdt_ac().equals("30"))
//{
//
//	tot=tot+Long.parseLong(li.get(i).getAmount()); 
//}
//
////System.out.println("totalDebit  "+ totalDebit);
////System.out.println("tot  "+ tot);
//
//li.get(i).setTran_gen("Yes");
//	
//  
//	 
//	
//}
//
//totalCredit=totalDebitcontra+tot;
//
////li4.get(0).setAc_no(Long.toString(totalDebit));
////li4.get(0).setBank(Long.toString(totalCredit));
//
//}
//
//      
//      
//
//      
//      
//      
//
////////////////second calculation////////////
//      
//       
//	
//	
//	List<Account_transactions_v3> li4=(List<Account_transactions_v3>) transactionServiceRepo.cashBookDatas();
//	
//	 // System.out.println("li4 "+ li4.size());
//		
//		
//		if(li4.size()>0)
//		{
//			String ledger;
//
//		for (int i = 0; i < li4.size(); i++)   
//		{ 	
//				
//			if(!li4.get(i).getDescription().equals("ledger creation")&&!li4.get(i).getMode().equals("Nil"))
//				
//			{
//			
//			
// if(li4.get(i).getDbt_ac().equals("30") && !li4.get(i).getCrdt_ac().equals("30")){
//			
//			ledger=li4.get(i).getCrdt_ac();
//     }
//		
//     if(li4.get(i).getDbt_ac().equals("Nil") || li4.get(i).getCrdt_ac().equals("Nil")){
//  	   
//    	ledger="30";
//     }
//     else{
//  	   
//  	   ledger=li4.get(i).getDbt_ac();
//     }	
//     
//     
//   
//		
//     
//  List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(ledger));
//     
// // System.out.println("li1  ls "+ li1.size());
//		 
//  if(li1.size()>0)
//	{
//	  li4.get(i).setBranch(li1.get(0).getLedger_name());
//	  
//	  Amount =Float.parseFloat(li4.get(i).getAmount()) ;
//	  
//	  balance_type=li1.get(0).getBalance_type();
//	  
//	     
//	  System.out.println("debit ac of "+i+" "+ li4.get(i).getDbt_ac());
//	  
//	  System.out.println("credit ac of "+i+" "+ li4.get(i).getCrdt_ac());
//	  
//	  
//
//	  
//	 
//	  
//	 // System.out.println("current bal ac of "+i+" "+ cur_balance1);
//	  
//	     ////////////new code///////////////
//	     
//	     if (li4.get(i).getDbt_ac().equals("30")) {
//	      	  
//	            if (balance_type.equals("debit")) {
//	            
//	              cur_balance1 =
//	              cur_balance1 + Amount;
//	              
//	            } else if (balance_type.equals("credit")) {
//	             
//	              cur_balance1 =
//	                cur_balance1 - Amount;
//	              
//	            }
//	            
//	            else
//		         {
//		        	 cur_balance1 =
//		     	            cur_balance1 + Amount;
//		         }
//		         
//	            
////	            debit_total1 =
////	              debit_total1 + Amount;
//	            
//	            flag="debit";
//	            
//	          }
//
//
//	    if (li4.get(i).getCrdt_ac().equals("30")) {
//	        
//	         if (balance_type.equals("debit")) {
//	          
//	           cur_balance1 =
//	            cur_balance1 - Amount;
//	          
//	         } else if (balance_type.equals("credit")) {
//	         
//	           cur_balance1 =
//	             cur_balance1 + Amount;
//	         
//	      }
//	         else
//	         {
//	        	 cur_balance1 =
//	     	            cur_balance1 - Amount;
//	         }
//	         
////	         credit_total1 =
////	          credit_total1 + Amount;
//	         
//	      flag="credit";
//	        }
//
//	    
//	    ////////////////////////////////////////////////////
//	  
//	  
//	  
//	  
//  	
//	}
//  
//
//  
//  if(li4.get(i).getDbt_ac().equals("30"))
//  {
//  
//    totalDebit=totalDebit+Long.parseLong(li4.get(i).getAmount()); 
//  }
// 
//  
//  if(li4.get(i).getCrdt_ac().equals("30"))
//  {
//  
//  	tot=tot+Long.parseLong(li4.get(i).getAmount()); 
//  }
//  
////  System.out.println("totalDebit  "+ totalDebit);
////  System.out.println("tot  "+ tot);
//
//			
// li4.get(i).setFilename(Float.toString(cur_balance1));		
// 
// System.out.println("cur_balance1  "+ cur_balance1);
// 
//			
//	}
//			
//	 else
//	  {
//				
//		li4.remove(i);
//	   }
//		
//		totalCredit=totalDebitcontra+tot;
//		
//		li4.get(0).setAc_no(Long.toString(totalDebit));
//		li4.get(0).setBank(Long.toString(totalCredit));
//		
//		//li4.get(i).setFilename(Float.toString(cur_balance1));
//		
//		
//		}
//		
//		
//	}
//		
//		
//		
//	
//		
//		
//	return li4;
//	
//}
public List<Account_transactions_v3> cashBookOpeningBalanceData()  {
	  Double totalDebit =(double) 0;
	  Double totalCredit = (double) 0;
	  Double totalDebitcontra =(double) 0;
	  Double tot =(double) 0;
    List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.cashBookOpenBalanceDataFetch();
	  System.out.println("li "+ li.size());
		if(li.size()>0)
		{
			String ledger;
		for (int i = 0; i < li.size(); i++)   
		{ 
			if(li.get(i).getDbt_ac().equals("30") && !li.get(i).getCrdt_ac().equals("30")){
				ledger=li.get(i).getCrdt_ac();
           }
           if(li.get(i).getDbt_ac().equals("Nil") || li.get(i).getCrdt_ac().equals("Nil")){
          	ledger="30";
           }
           else{
        	   ledger=li.get(i).getDbt_ac();
           }	
        List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(ledger));
        System.out.println("li1 "+ li1.size());
        if(li1.size()>0)
		  {
        	li.get(i).setBranch(li1.get(0).getLedger_name());
        	li.get(i).setTran_Date(li1.get(0).getLedger_date());
		  }
        if(li.get(i).getDbt_ac().equals("30"))
        {
          totalDebit=totalDebit+Double.parseDouble(li.get(i).getAmount()); 
        }
        if(li.get(i).getCrdt_ac().equals("30"))
        {
        	tot=tot+Double.parseDouble(li.get(i).getAmount()); 
        }
        System.out.println("totalDebit  "+ totalDebit);
        System.out.println("tot  "+ tot);
        li.get(i).setTran_gen("Yes");
		}
		totalCredit=totalDebitcontra+tot;
		li.get(0).setAc_no(Double.toString(totalDebit));
		li.get(0).setBank(Double.toString(totalCredit));
		}
	return li;
}
public List<Account_transactions_v3> cashbook_sorts(String field, String type)  {
	System.out.println(field+' '+type);
	List<Account_transactions_v3> li = null;
	if(field.equals("tran_Date")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.tran_DateCashBookA();
	}
	if(field.equals("tran_Date")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.tran_DateCashBookD();
	}
	if(field.equals("ledger_name")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.ledgerCashBookA();
	}
	if(field.equals("ledger_name")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.ledgerCashBookD();
	}
	if(field.equals("type")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.typeCashBookA();
	}
	if(field.equals("type")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.typeCashBookD();
	}
	if(field.equals("typeWithNo")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.typeWithNoCashBookA();
	}
	if(field.equals("typeWithNo")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.typeWithNoCashBookD();
	}
	if(field.equals("description")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.descriptionCashBookA();
	}
	if(field.equals("description")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.descriptionCashBookD();
	}
	if(field.equals("amount")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.amountCashBookA();
	}
	if(field.equals("amount")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.amountCashBookD();
	}
	Double totalDebit =(double) 0;
	Double totalCredit = (double) 0;
	Double totalDebitcontra =(double) 0;
	Double tot =(double) 0;
	  System.out.println("li "+ li.size());
		if(li.size()>0)
		{
			String ledger;
		for (int i = 0; i < li.size(); i++)   
			{ 
			if(li.get(i).getDbt_ac().equals("30") && !li.get(i).getCrdt_ac().equals("30")){
				ledger=li.get(i).getCrdt_ac();
             }
               if(li.get(i).getDbt_ac().equals("Nil") || li.get(i).getCrdt_ac().equals("Nil")){
              	ledger="30";
               }
               else{
            	   ledger=li.get(i).getDbt_ac();
               }	
            List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(ledger));
            System.out.println("li1 "+ li1.size());
            if(li1.size()>0)
    		{
            	li.get(i).setBranch(li1.get(0).getLedger_name());
    		}
            if(li.get(i).getDbt_ac().equals("30"))
            {
              totalDebit=totalDebit+Double.parseDouble(li.get(i).getAmount()); 
            }
            if(li.get(i).getCrdt_ac().equals("30"))
            {
            	tot=tot+Double.parseDouble(li.get(i).getAmount()); 
            }
            System.out.println("totalDebit  "+ totalDebit);
            System.out.println("tot  "+ tot);
		}
		totalCredit=totalDebitcontra+tot;
		li.get(0).setAc_no(Double.toString(totalDebit));
		li.get(0).setBank(Double.toString(totalCredit));
		}
	return li;
}
public List<Account_transactions_v3> cashBookOpeningBalanceBnDates(String start,String end)  {
	Double totalDebit =(double) 0;
	Double totalCredit = (double) 0;
	Double totalDebitcontra =(double) 0;
	Double tot =(double) 0;
	List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.cashBookOpenBalanceDataFetch();
	  System.out.println("li "+ li.size());
		if(li.size()>0)
		{
			String ledger;
		for (int i = 0; i < li.size(); i++)   
		{ 
			List<Account_ledger_v3> li3=(List<Account_ledger_v3>) ledgerServiceRepo.cashBookOpenBalanceDataBnDate(Integer.parseInt("30"),start,end);
			System.out.println("li3 size "+li3.size());
			  if(li3.size()>0)
	  		  {
			if(li.get(i).getDbt_ac().equals("30") && !li.get(i).getCrdt_ac().equals("30")){
				ledger=li.get(i).getCrdt_ac();
             }
             if(li.get(i).getDbt_ac().equals("Nil") || li.get(i).getCrdt_ac().equals("Nil")){
            	ledger="30";
             }
             else{
          	   ledger=li.get(i).getDbt_ac();
             }	
          List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(ledger));
          System.out.println("li1 "+ li1.size());
          if(li1.size()>0)
  		  {
          	li.get(i).setBranch(li1.get(0).getLedger_name());
          	li.get(i).setTran_Date(li1.get(0).getLedger_date());
  		  }
          if(li.get(i).getDbt_ac().equals("30"))
          {
            totalDebit=totalDebit+Double.parseDouble(li.get(i).getAmount()); 
          }
          if(li.get(i).getCrdt_ac().equals("30"))
          {
          	tot=tot+Double.parseDouble(li.get(i).getAmount()); 
          }
          System.out.println("totalDebit  "+ totalDebit);
          System.out.println("tot  "+ tot);
          li.get(i).setTran_gen("Yes");
	  	  }
		}
		totalCredit=totalDebitcontra+tot;
		li.get(0).setAc_no(Double.toString(totalDebit));
		li.get(0).setBank(Double.toString(totalCredit));
		}
	return li;
}
public List<Account_transactions_v3> cashBookBnDates(String start,String end)  {
	Double totalDebit =(double) 0;
	Double totalCredit = (double) 0;
	Double totalDebitcontra =(double) 0;
	Double tot =(double) 0;
	/////////second calculation/////////////////
	List<Account_transactions_v3> li4=(List<Account_transactions_v3>) transactionServiceRepo.cashBookBnDate(start,end);
	  System.out.println("li4 "+ li4.size());
		if(li4.size()>0)
		{
			String ledger;
		for (int i = 0; i < li4.size(); i++)   
		{ 	
			if(!li4.get(i).getDescription().equals("ledger creation")&&!li4.get(i).getMode().equals("Nil"))
			{
   if(li4.get(i).getDbt_ac().equals("30") && !li4.get(i).getCrdt_ac().equals("30")){
			ledger=li4.get(i).getCrdt_ac();
       }
       if(li4.get(i).getDbt_ac().equals("Nil") || li4.get(i).getCrdt_ac().equals("Nil")){
      	ledger="30";
       }
       else{
    	   ledger=li4.get(i).getDbt_ac();
       }	
    List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(ledger));
    System.out.println("li1 "+ li1.size());
    if(li1.size()>0)
	{
  	  li4.get(i).setBranch(li1.get(0).getLedger_name());
	}
    if(li4.get(i).getDbt_ac().equals("30"))
    {
      totalDebit=totalDebit+Double.parseDouble(li4.get(i).getAmount()); 
    }
    if(li4.get(i).getCrdt_ac().equals("30"))
    {
    	tot=tot+Double.parseDouble(li4.get(i).getAmount()); 
    }
    System.out.println("totalDebit  "+ totalDebit);
    System.out.println("tot  "+ tot);
	}
	 else
	  {
		li4.remove(i);
	   }
		totalCredit=totalDebitcontra+tot;
		li4.get(0).setAc_no(Double.toString(totalDebit));
		li4.get(0).setBank(Double.toString(totalCredit));
		}
	}
		//////////////second calculation////////////
		List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.cashBookOpenBalanceDataFetch();
		  System.out.println("li "+ li.size());
			if(li.size()>0)
			{
				String ledger;
			for (int i = 0; i < li.size(); i++)   
			{ 
				List<Account_ledger_v3> li3=(List<Account_ledger_v3>) ledgerServiceRepo.cashBookOpenBalanceDataBnDate(Integer.parseInt("30"),start,end);
				System.out.println("li3 size "+li3.size());
				  if(li3.size()>0)
		  		  {
				if(li.get(i).getDbt_ac().equals("30") && !li.get(i).getCrdt_ac().equals("30")){
					ledger=li.get(i).getCrdt_ac();
	             }
	             if(li.get(i).getDbt_ac().equals("Nil") || li.get(i).getCrdt_ac().equals("Nil")){
	            	ledger="30";
	             }
	             else{
	          	   ledger=li.get(i).getDbt_ac();
	             }	
	          List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(ledger));
	          System.out.println("li1 "+ li1.size());
	          if(li1.size()>0)
	  		  {
	          	li.get(i).setBranch(li1.get(0).getLedger_name());
	          	li.get(i).setTran_Date(li1.get(0).getLedger_date());
	  		  }
	          if(li.get(i).getDbt_ac().equals("30"))
	          {
	            totalDebit=totalDebit+Double.parseDouble(li.get(i).getAmount()); 
	          }
	          if(li.get(i).getCrdt_ac().equals("30"))
	          {
	          	tot=tot+Double.parseDouble(li.get(i).getAmount()); 
	          }
	          System.out.println("totalDebit  "+ totalDebit);
	          System.out.println("tot  "+ tot);
	          li.get(i).setTran_gen("Yes");
		  	  }
			}
			totalCredit=totalDebitcontra+tot;
			li4.get(0).setAc_no(Double.toString(totalDebit));
			li4.get(0).setBank(Double.toString(totalCredit));
			}
	return li4;
}
         //////////////bank book///////////////
public List<Account_transactions_v3> bankBookData()  {
	  Double debit_total =(double) 0;
	  Double credit_total = (double) 0;
	List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.bankBookDatas();
	  System.out.println("li "+ li.size());
		if(li.size()>0)
		{
		for (int i = 0; i < li.size(); i++)   
			{ 
          List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(i).getDbt_ac()));
          System.out.println("li1 "+ li1.size());
            if(li1.size()>0)
  		    {
          	li.get(i).setBranch(li1.get(0).getLedger_name());
        	if(li.get(i).getDescription().equals("ledger creation"))
          	{
          		li.get(i).setTran_Date(li1.get(0).getLedger_date());
          	}
  		    }
            li.get(i).setAmount("-");
          }
		}
	return li;
}
public List<Account_transactions_v3> bankbook_sorts(String field, String type)  {
	System.out.println(field+' '+type);
	List<Account_transactions_v3> li = null;
	if(field.equals("tran_Date")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.tran_DateBankBookA();
	}
	if(field.equals("tran_Date")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.tran_DateBankBookD();
	}
	if(field.equals("ledger_name")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.ledgerBankBookA();
	}
	if(field.equals("ledger_name")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.ledgerBankBookD();
	}
	if(field.equals("type")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.typeBankBookA();
	}
	if(field.equals("type")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.typeBankBookD();
	}
	if(field.equals("typeWithNo")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.typeWithNoBankBookA();
	}
	if(field.equals("typeWithNo")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.typeWithNoBankBookD();
	}
	if(field.equals("description")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.descriptionBankBookA();
	}
	if(field.equals("description")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.descriptionBankBookD();
	}
	if(field.equals("amount")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.amountBankBookA();
	}
	if(field.equals("amount")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.amountBankBookD();
	}
	Double debit_total =(double) 0;
	Double credit_total = (double) 0;
      System.out.println("li "+ li.size());
		if(li.size()>0)
		{
		for (int i = 0; i < li.size(); i++)   
			{ 
        List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(i).getDbt_ac()));
        System.out.println("li1 "+ li1.size());
          if(li1.size()>0)
		    {
        	li.get(i).setBranch(li1.get(0).getLedger_name());
		    }
          li.get(i).setAmount("-");
        }
		}
	return li;
}
public List<Account_transactions_v3> bankBookOpenBalanceDataB(String id, String description,String start, String end)  {
	List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.selectAccStmtTransactionBndates(id,description,start,end);
	return li;
}
public List<Account_transactions_v3> bankBookDataB(int ledgerId,String start,String end)  {
	///////////old php code//////////////////
//	  Long debit_total1 =(long) 0;
//	  Long debit_total2 =(long) 0;
//	  Long credit_totals = (long) 0;
//      Long tot =(long) 0;
//      Long debit_total =(long) 0;
//	  Long credit_total = (long) 0;
//	  Long bal = (long) 0;
//	  
//	
//	List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.bankBookDatasB(start,end,bankId);
//	
//	  System.out.println("li "+ li.size());
//		
//		
//		if(li.size()>0)
//		{
//			String ledger;
//
//		for (int i = 0; i < li.size(); i++)   
//			{ 
//			
//			
//			
//			if(li.get(i).getDbt_ac().equals(bankId) && !li.get(i).getCrdt_ac().equals(bankId)){
//				
//				ledger=li.get(i).getCrdt_ac();
//           }
//			
//             if(li.get(i).getDbt_ac().equals("Nil") || li.get(i).getCrdt_ac().equals("Nil")){
//          	   
//            	ledger=bankId;
//             }
//             else{
//          	   
//          	   ledger=li.get(i).getDbt_ac();
//             }	
//			
//             
//          List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(ledger));
//             
//          System.out.println("li1 "+ li1.size());
//			 
//          if(li1.size()>0)
//  		{
//          	li.get(i).setBranch(li1.get(0).getLedger_name());
//          	
//  		}
//          
//          
//          String amnt=li.get(i).getAmount();
//          
//          if(li.get(i).getDbt_ac().equals(bankId))
//          {
//          
//        	  debit_total2=debit_total2+Long.parseLong(amnt); 
//        	  
//          }
//          else
//          {
//        	  li.get(i).setAmount("-");
//        	  
//          }
//          
//          
//          
//          
//          if(li.get(i).getCrdt_ac().equals(bankId))
//          {
//          
//          	tot=tot+Long.parseLong(amnt); 
//          	li.get(i).setChq_date(amnt);
//          	
//          }
//          else
//          {
//        	  li.get(i).setChq_date("-");
//        	 
//          }
//          
//          System.out.println("debit_total2  "+ debit_total2);
//          System.out.println("tot  "+ tot);
//    
//		}
//		
//		  credit_total=credit_totals+tot; 
//          debit_total=debit_total1+debit_total2;
//          if(debit_total>=credit_total)
//          {					   
//          bal=debit_total-credit_total;
//          }
//          
//          if(debit_total<credit_total)
//          {					   
//          bal=credit_total-debit_total;
//          }	 
//		
//		
//		
//		
//		li.get(0).setBank(Long.toString(credit_total));
//		li.get(0).setAc_no(Long.toString(debit_total));
//		li.get(0).setChq_no(Long.toString(bal));
//		
//		}
//		
//	return li;
	////////////////////////////////////////////////////
	System.out.println(ledgerId);
		String ledger_id=Integer.toString(ledgerId);
		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.ledger_Search2(ledgerId);
		System.out.println(li.size());
		Float cur_amount,cur_balance1 = (float) 0 , debit_total1 = (float) 0 ,credit_total1 =(float) 0 ;
		String  balance_type = null , description = "ledger creation" ,particularname=null,created_date=null,tran_description=null;
		Float  Amount=(float)0,tran_open_balance=(float)0;
	    Integer particular=0;
	    Float   balance=(float)0;
		for (int i = 0; i < li.size(); i++)   
		{  
			System.out.println(li.get(i).getAc_group());
			balance_type=li.get(i).getBalance_type();
			 if (balance_type.equals("debit")) {
				   if(li.get(i).getOpen_balance().equals(""))
	 	 	      {
					debit_total1 +=(float) 0;
	 	 	      }
	 	 	      else
	 	 	      {
	 	          debit_total1 += Float.parseFloat(li.get(i).getOpen_balance());
	 	 	      }
	 	        }
	 	        if (balance_type.equals("credit")){
	 	          if(li.get(i).getOpen_balance().equals(""))
	 	 	      {
	 	        	credit_total1 +=(float) 0;
	 	 	      }
	 	 	      else
	 	 	      {
	 	          credit_total1 += Float.parseFloat(li.get(i).getOpen_balance()); 
	 	 	      }
	 	        }
	 	      System.out.println(debit_total1+"  "+credit_total1);
	 	    if(li.get(i).getOpen_balance().equals(""))
 	      {
 	    	tran_open_balance=(float) 0;
 	      }
 	      else
 	      {
 	        tran_open_balance=Float.parseFloat(li.get(i).getOpen_balance());
 	      }
		}
		 System.out.println(" balance_type  "+balance_type);
		 JSONObject jsonObject = new JSONObject();
	      //Inserting key-value pairs into the json object
//	      jsonObject.put("ID", "1");
//	      jsonObject.put("First_Name", "Krishna Kasyap");
//	      jsonObject.put("Last_Name", "Bhagavatula");
//	      jsonObject.put("Date_Of_Birth", "1989-09-26");
//	      jsonObject.put("Place_Of_Birth", "Vishakhapatnam");
//	      jsonObject.put("Country", "25000");
	      //Creating a json array
	      JSONArray array = new JSONArray();
//	      array.put("e-mail: krishna_kasyap@gmail.com");
	      //Adding array to the json object
	     cur_balance1=cur_balance1+tran_open_balance;
List<Account_transactions_v3> li1=(List<Account_transactions_v3>) transactionServiceRepo.selectAccStmtTransaction2Bndates(ledgerId,description,start,end);
//Iterator<Account_transactions_v3> it = li1.iterator(); 
//while (it.hasNext()) { 
//
//Account_transactions_v3 ob = it.next(); 
//
//System.out.println("fgdfgdf"+ob.getType());
//
//}
String flag=null;
System.out.println(li1.size());
if(li1.size()>0)
{
for (int i = 0; i < li1.size(); i++)   
{ 
	if(li1.get(i).getDbt_ac().equals(ledgerId) && !li1.get(i).getCrdt_ac().equals(ledgerId)){
		particular=Integer.parseInt(li1.get(i).getCrdt_ac());
   }
     if(li1.get(i).getDbt_ac().equals("Nil") || li1.get(i).getCrdt_ac().equals("Nil")){
    	 particular=ledgerId;
     }
		else{
			particular=Integer.parseInt(li1.get(i).getDbt_ac());
    }	
// if (li1.get(i).getDbt_ac().equals( ledger_id)) {
//     
//	  particular = Integer.parseInt(li1.get(i).getCrdt_ac());
//	  
// }
// 
//
// if (li1.get(i).getCrdt_ac().equals(ledger_id)) {
//	  
//	  particular = Integer.parseInt(li1.get(i).getDbt_ac());
// 
//
// }
particularname= ledgerServiceRepo.ac_ledger_SearchName(particular);
System.out.println("particularname  "+ particularname);
//System.out.println("balance_type  "+ balance_type);
//balance_type=li1.get(i).get
   Amount =Float.parseFloat(li1.get(i).getAmount()) ;
//   System.out.println("Amount  "+ Amount);
//   System.out.println("Dbt_ac  "+ li1.get(i).getDbt_ac());
//   
//   System.out.println("Crdt_ac  "+ li1.get(i).getCrdt_ac());
               if (li1.get(i).getDbt_ac().equals( ledger_id)) {
   	                if (balance_type.equals("debit")) {
   	                  cur_balance1 =
   	                  cur_balance1 + Amount;
   	                } else if (balance_type.equals("credit")) {
   	                  cur_balance1 =
   	                    cur_balance1 - Amount;
   	                }
   	                debit_total1 =
   	                  debit_total1 + Amount;
   	                flag="debit";
   	              }
               if (li1.get(i).getCrdt_ac().equals(ledger_id)) {
	                if (balance_type.equals("debit")) {
	                  cur_balance1 =
	                   cur_balance1 - Amount;
	                } else if (balance_type.equals("credit")) {
	                  cur_balance1 =
	                    cur_balance1 + Amount;
	             }
	                credit_total1 =
	                 credit_total1 + Amount;
	             flag="credit";
	               }
               created_date=li1.get(i).getCreatedDate();
               tran_description=li1.get(i).getDescription();
               System.out.println("cur_balance1  "+ cur_balance1);
               array.put("e-mail:"+cur_balance1);
               array.put("particularname "+particularname);
               array.put("flag "+flag);
//               cur_balance1=(float) 0;
//               Amount=(float) 0;
               li1.get(i).setFilename(particularname);
   			li1.get(i).setAmount(Float.toString(Amount));
   			li1.get(i).setCreatedDate(created_date);
   			li1.get(i).setDescription(tran_description);
   		li1.get(i).setBank(Float.toString(cur_balance1));
   		li1.get(i).setBranch(flag);
       }
li1.get(0).setChq_no(Float.toString( debit_total1));
li1.get(0).setChq_date(Float.toString( credit_total1));
if(debit_total1==0&&credit_total1!=0)
{
 balance=credit_total1; 
}
if(debit_total1!=0&&credit_total1==0)
{
 balance=debit_total1;  
}
if(debit_total1!=0&&credit_total1!=0)
{
balance=debit_total1-credit_total1;
}
li1.get(0).setCreatedTime(Float.toString( balance));
  System.out.println("debit_total1  "+ debit_total1+"  "+credit_total1);
  jsonObject.put("contact",array);
  System.out.println("array  "+ array);
}	
		return li1;
}
///////////////////Day Book///////////////
public List<Account_transactions_v3> dayBookData()  {
	List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.dayBookDatas();
	  System.out.println("li "+ li.size());
		if(li.size()>0)
		{
		for (int i = 0; i < li.size(); i++)   
			{ 
			List<Account_ledger_v3> li1 = new ArrayList<Account_ledger_v3>();	
			 List<Account_ledger_v3> li2 = new ArrayList<Account_ledger_v3>() ;
			if(!li.get(i).getDbt_ac().equals("Nil")&&!li.get(i).getDbt_ac().equals(""))
			{
//				System.out.println("tran id li1 inserted"+li.get(i).getTranID());
             li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(i).getDbt_ac()));
			}
			if(!li.get(i).getCrdt_ac().equals("Nil")&&!li.get(i).getCrdt_ac().equals(""))
			{
//				System.out.println("tran id li2 inserted"+li.get(i).getTranID());
             li2=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(i).getCrdt_ac()));   
			}
//			System.out.println("tran id "+li.get(i).getTranID());
//          System.out.println("li1 size"+li1.size());
//          System.out.println("li2 size"+li2.size());
//          System.out.println("Integer.parseInt(li.get(i).getDbt_ac())"+"   "+ li.get(i).getDbt_ac());
//			
            if(li1.size()>0)
  		    {
          	li.get(i).setBranch(li1.get(0).getLedger_name());
          	if(li.get(i).getDescription().equals("ledger creation"))
          	{
          		li.get(i).setTran_Date(li1.get(0).getLedger_date());
          	}
  		    }
//            System.out.println("li2 size"+ li2.size()+" li2"+li2 );
            if(li2.size()>0)
  		    {
          	li.get(i).setChq_date(li2.get(0).getLedger_name());
          	if(li.get(i).getDescription().equals("ledger creation"))
          	{
          		li.get(i).setTran_Date(li2.get(0).getLedger_date());
          	}
  		    }
          }
		}
	return li;
}
public List<Account_transactions_v3> debitAcData()  {
	List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.debitAcDatas();
	  System.out.println("li "+ li.size());
		if(li.size()>0)
		{
		for (int i = 0; i < li.size(); i++)   
			{ 
			if(!li.get(i).getDbt_ac().equals("Nil")&&!li.get(i).getDbt_ac().equals(""))
			{
          List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(i).getDbt_ac()));
          System.out.println("li1 "+ li1.size());
            if(li1.size()>0)
  		    {
//        	li.set(i, li1.get(0).getLedger_name());
        	li.get(i).setBranch(li1.get(0).getLedger_name());
  		    }
            else
            {
            	li.get(i).setBranch("");
            }
			}
          }
		}
	return li;
}
public List<Account_transactions_v3> creditAcData()  {
	List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.creditAcDatas();
	  System.out.println("li "+ li.size());
		if(li.size()>0)
		{
		for (int i = 0; i < li.size(); i++)   
			{ 
			System.out.print("credit ac "+li.get(i).getCrdt_ac());
			if(!li.get(i).getCrdt_ac().equals("Nil")&&!li.get(i).getCrdt_ac().equals(""))
			{
          List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(i).getCrdt_ac()));
          System.out.println("li1 "+ li1.size());
            if(li1.size()>0)
  		    {
//        	li.set(i, li1.get(0).getLedger_name()+"-"+li.get(i));
            	li.get(i).setBranch(li1.get(0).getLedger_name());
  		    }
            else
            {
//            	li.set(i,"");
            	li.get(i).setBranch("");
            }
             }
		   }
		}
	return li;
}
public List<Account_transactions_v3> daybook_sorts(String field, String type)  {
	System.out.println(field+' '+type);
	List<Account_transactions_v3> li = null;
	if(field.equals("tran_Date")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.tran_DateDayBookA();
	}
	else if(field.equals("tran_Date")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.tran_DateDayBookD();
	}
	else if(field.equals("description")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.descriptionDayBookA();
	}
	else if(field.equals("description")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.descriptionDayBookD();
	}
	else if(field.equals("amount")&&type.equals("ASC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.amountDayBookA();
	}
	else if(field.equals("amount")&&type.equals("DESC"))
	{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.amountDayBookD();
	}
	else
	{
		li=(List<Account_transactions_v3>) transactionServiceRepo.dayBookDatas();
	}
	 System.out.println("li "+ li.size());
	  List<Account_ledger_v3> li1 = new ArrayList<Account_ledger_v3>();	
		 List<Account_ledger_v3> li2 = new ArrayList<Account_ledger_v3>() ;
		if(li.size()>0)
		{
		for (int i = 0; i < li.size(); i++)   
			{ 
			if(!li.get(i).getDbt_ac().equals("Nil")&&!li.get(i).getDbt_ac().equals(""))
			{
            li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(i).getDbt_ac()));
			}
			if(!li.get(i).getCrdt_ac().equals("Nil")&&!li.get(i).getCrdt_ac().equals(""))
			{
            li2=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(i).getCrdt_ac()));   
			}
         System.out.println("li1 "+li1);
           if(li1.size()>0)
 		    {
         	li.get(i).setBranch(li1.get(0).getLedger_name());
         	 if(li.get(i).getDescription().equals("ledger creation"))
        	  {
        		li.get(i).setTran_Date(li1.get(0).getLedger_date());
        	  }
 		    }
//           System.out.println("li2 size"+ li2.size()+" li2"+li2 );
           if(li2.size()>0)
 		    {
         	li.get(i).setChq_date(li2.get(0).getLedger_name());
         	 if(li.get(i).getDescription().equals("ledger creation"))
        	  {
        		li.get(i).setTran_Date(li2.get(0).getLedger_date());
        	  }
 		    }
         }
		}	
	return li;
}
public List<Account_transactions_v3> dayBookDataBnDate(String start,String end,String debit,String credit)  {
	  System.out.println("debit"+ debit+"credit"+credit);
	List<Account_transactions_v3> li=null;
	if(!debit.equals("null") && !credit.equals("null") &&  !debit.equals(credit))
		{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.dayBookDataBnDate1(start,end,debit,credit);
		}
		if(!debit.equals("null") && !credit.equals("null") && debit.equals(credit))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.dayBookDataBnDate2(start,end,debit,credit);
		}
       if(!debit.equals("null") && credit.equals("null"))
		{
    	   li=(List<Account_transactions_v3>) transactionServiceRepo.dayBookDataBnDate3(start,end,debit);
		}
		if(debit.equals("null") && !credit.equals("null"))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.dayBookDataBnDate4(start,end,credit);
		}
		if(debit.equals("null") && credit.equals("null"))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.dayBookDataBnDate5(start,end);
		}
	  System.out.println("li "+ li.size());
	  List<Account_ledger_v3> li1 = new ArrayList<Account_ledger_v3>();	
		 List<Account_ledger_v3> li2 = new ArrayList<Account_ledger_v3>() ;
		if(li.size()>0)
		{
		for (int i = 0; i < li.size(); i++)   
			{ 
			if(!li.get(i).getDbt_ac().equals("Nil")&&!li.get(i).getDbt_ac().equals(""))
			{
				 li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(i).getDbt_ac()));
			}
			if(!li.get(i).getCrdt_ac().equals("Nil")&&!li.get(i).getCrdt_ac().equals(""))
			{
				 li2=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(i).getCrdt_ac()));   
			}
          System.out.println("li1 "+ li1.size());
            if(li1.size()>0)
  		    {
          	li.get(i).setBranch(li1.get(0).getLedger_name());
          	 if(li.get(i).getDescription().equals("ledger creation"))
          	  {
          		li.get(i).setTran_Date(li1.get(0).getLedger_date());
          	  }
  		    }
            System.out.println("li2 "+ li2.size());
            if(li2.size()>0)
  		    {
          	li.get(i).setChq_date(li2.get(0).getLedger_name());
          	 if(li.get(i).getDescription().equals("ledger creation"))
          	  {
          		li.get(i).setTran_Date(li2.get(0).getLedger_date());
          	  }
  		    }
          }
		}
	return li;
}
public List<Account_transactions_v3> dayBookDelete(String tranId)  {
	Double amount1=(double) 0,previousAmount=(double) 0,finalAmount=(double) 0;  
	List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.journal_Search(tranId);
	  System.out.println("li "+ li.size());
		if(li.size()>0)
		{
			if(li.get(0).getType().equals("Voucher"))
			 {
				amount1=Double.parseDouble(li.get(0).getAmount());
				if(li.get(0).getMode().equals("cash"))
				{
				    List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt("30"));
				    System.out.println("li1 "+ li1.size());
				    if(li1.size()>0)
					{
				    	finalAmount=Double.parseDouble(li1.get(0).getAmount())+amount1;
				    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt("30"));
//						if (!studentOptional.isPresent())
//							return ResponseEntity.notFound().build();
                        li1.get(0).setAmount(Double.toString(finalAmount));
						ledgerServiceRepo.saveAll(li1);
					}	
				    List<Account_ledger_v3> li2=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(0).getDbt_ac()));
				    System.out.println("li2 "+ li2.size());
				    if(li2.size()>0)
					{
				    	finalAmount=Double.parseDouble(li2.get(0).getAmount())-amount1;
				    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt(li.get(0).getDbt_ac()));
//						if (!studentOptional.isPresent())
//							return ResponseEntity.notFound().build();
                        li2.get(0).setAmount(Double.toString(finalAmount));
						ledgerServiceRepo.saveAll(li2);
					}	
				}
				else
				{
					 List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(0).getBank()));
					    System.out.println("li1 "+ li1.size());
					    if(li1.size()>0)
						{
					    	finalAmount=Double.parseDouble(li1.get(0).getAmount())+amount1;
					    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt(li.get(0).getBank()));
//							if (!studentOptional.isPresent())
//								return ResponseEntity.notFound().build();
	                        li1.get(0).setAmount(Double.toString(finalAmount));
							ledgerServiceRepo.saveAll(li1);
						}	
					    List<Account_ledger_v3> li2=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(0).getDbt_ac()));
					    System.out.println("li2 "+ li2.size());
					    if(li2.size()>0)
						{
					    	finalAmount=Double.parseDouble(li2.get(0).getAmount())-amount1;
					    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt(li.get(0).getDbt_ac()));
//							if (!studentOptional.isPresent())
//								return ResponseEntity.notFound().build();
	                        li2.get(0).setAmount(Double.toString(finalAmount));
							ledgerServiceRepo.saveAll(li2);
						}	
				}
				transactionServiceRepo.deleteById(Integer.parseInt(tranId));
			 }
			/////////Receipt type///////////
		if(li.get(0).getType().equals("Receipt"))
		  {
		  if(li.get(0).getStatus().equals("Recieve")||li.get(0).getStatus().equals("1"))
		    {
			  amount1=Double.parseDouble(li.get(0).getAmount());
				if(li.get(0).getMode().equals("cash"))
				{
				    List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt("30"));
				    System.out.println("li1 "+ li1.size());
				    if(li1.size()>0)
					{
				    	finalAmount=Double.parseDouble(li1.get(0).getAmount())-amount1;
				    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt("30"));
//						if (!studentOptional.isPresent())
//							return ResponseEntity.notFound().build();
                       li1.get(0).setAmount(Double.toString(finalAmount));
						ledgerServiceRepo.saveAll(li1);
					}	
				    List<Account_ledger_v3> li2=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(0).getCrdt_ac()));
				    System.out.println("li2 "+ li2.size());
				    if(li2.size()>0)
					{
				    	finalAmount=Double.parseDouble(li2.get(0).getAmount())-amount1;
				    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt(li.get(0).getCrdt_ac()));
//						if (!studentOptional.isPresent())
//							return ResponseEntity.notFound().build();
                       li2.get(0).setAmount(Double.toString(finalAmount));
						ledgerServiceRepo.saveAll(li2);
					}	
				}
				else
				{
					 List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(0).getBank()));
					    System.out.println("li1 "+ li1.size());
					    if(li1.size()>0)
						{
					    	finalAmount=Double.parseDouble(li1.get(0).getAmount())-amount1;
					    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt(li.get(0).getBank()));
//							if (!studentOptional.isPresent())
//								return ResponseEntity.notFound().build();
	                        li1.get(0).setAmount(Double.toString(finalAmount));
							ledgerServiceRepo.saveAll(li1);
						}	
					    List<Account_ledger_v3> li2=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(0).getCrdt_ac()));
					    System.out.println("li2 "+ li2.size());
					    if(li2.size()>0)
						{
					    	finalAmount=Double.parseDouble(li2.get(0).getAmount())-amount1;
					    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt(li.get(0).getCrdt_ac()));
//							if (!studentOptional.isPresent())
//								return ResponseEntity.notFound().build();
	                        li2.get(0).setAmount(Double.toString(finalAmount));
							ledgerServiceRepo.saveAll(li2);
						}	
				}
				transactionServiceRepo.deleteById(Integer.parseInt(tranId));
			 }
		     if(li.get(0).getStatus().equals("Not_now")||li.get(0).getStatus().equals("2"))
		      {
		    	 transactionServiceRepo.deleteById(Integer.parseInt(tranId));
		      }     
		 }
		/////////////Contra/////////////
		if(li.get(0).getType().equals("Contra"))
		 {
			amount1=Double.parseDouble(li.get(0).getAmount());
			    List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(0).getDbt_ac()));
			    System.out.println("li1 "+ li1.size());
			    if(li1.size()>0)
				{
			    	finalAmount=Double.parseDouble(li1.get(0).getAmount())-amount1;
			    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt(li.get(0).getDbt_ac()));
//					if (!studentOptional.isPresent())
//						return ResponseEntity.notFound().build();
                   li1.get(0).setAmount(Double.toString(finalAmount));
					ledgerServiceRepo.saveAll(li1);
				}	
			    List<Account_ledger_v3> li2=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(0).getCrdt_ac()));
			    System.out.println("li2 "+ li2.size());
			    if(li2.size()>0)
				{
			    	if(li2.get(0).getAc_type().equals("2")||li2.get(0).getAc_type().equals("3"))
			    	{
			    	finalAmount=Double.parseDouble(li2.get(0).getAmount())-amount1;
			    	}
			    	else
			    	{
			    		finalAmount=Double.parseDouble(li2.get(0).getAmount())+amount1;
			    	}
			    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt(li.get(0).getCrdt_ac()));
//					if (!studentOptional.isPresent())
//						return ResponseEntity.notFound().build();
                   li2.get(0).setAmount(Double.toString(finalAmount));
					ledgerServiceRepo.saveAll(li2);
				}	
			transactionServiceRepo.deleteById(Integer.parseInt(tranId));
		 }
		if(!li.get(0).getType().equals("Contra")&&!li.get(0).getType().equals("Receipt")&&!li.get(0).getType().equals("Voucher"))
		{
			 li.get(0).setUser_bank("can't");
		}
	  }
	return li;
}
public List<Account_transactions_v3> cashBookDelete(String tranId)  {
	Double amount1=(double) 0,previousAmount=(double) 0,finalAmount=(double) 0;  
	List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.journal_Search(tranId);
	  System.out.println("li "+ li.size());
		if(li.size()>0)
		{
			if(li.get(0).getType().equals("Voucher"))
			 {
				amount1=Double.parseDouble(li.get(0).getAmount());
				    List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt("30"));
				    System.out.println("li1 "+ li1.size());
				    if(li1.size()>0)
					{
				    	finalAmount=Double.parseDouble(li1.get(0).getAmount())+amount1;
				    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt("30"));
//						if (!studentOptional.isPresent())
//							return ResponseEntity.notFound().build();
                        li1.get(0).setAmount(Double.toString(finalAmount));
						ledgerServiceRepo.saveAll(li1);
					}	
				    List<Account_ledger_v3> li2=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(0).getDbt_ac()));
				    System.out.println("li2 "+ li2.size());
				    if(li2.size()>0)
					{
				    	finalAmount=Double.parseDouble(li2.get(0).getAmount())-amount1;
				    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt(li.get(0).getDbt_ac()));
//						if (!studentOptional.isPresent())
//							return ResponseEntity.notFound().build();
                        li2.get(0).setAmount(Double.toString(finalAmount));
						ledgerServiceRepo.saveAll(li2);
					}	
						transactionServiceRepo.deleteById(Integer.parseInt(tranId));
			 }
			/////////Receipt type///////////
		if(li.get(0).getType().equals("Receipt"))
		  {
		  if(li.get(0).getStatus().equals("Recieve")||li.get(0).getStatus().equals("1"))
		    {
			  amount1=Double.parseDouble(li.get(0).getAmount());
				    List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt("30"));
				    System.out.println("li1 "+ li1.size());
				    if(li1.size()>0)
					{
				    	finalAmount=Double.parseDouble(li1.get(0).getAmount())-amount1;
				    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt("30"));
//						if (!studentOptional.isPresent())
//							return ResponseEntity.notFound().build();
                       li1.get(0).setAmount(Double.toString(finalAmount));
						ledgerServiceRepo.saveAll(li1);
					}	
				    List<Account_ledger_v3> li2=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(0).getCrdt_ac()));
				    System.out.println("li2 "+ li2.size());
				    if(li2.size()>0)
					{
				    	finalAmount=Double.parseDouble(li2.get(0).getAmount())-amount1;
				    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt(li.get(0).getCrdt_ac()));
//						if (!studentOptional.isPresent())
//							return ResponseEntity.notFound().build();
                       li2.get(0).setAmount(Double.toString(finalAmount));
						ledgerServiceRepo.saveAll(li2);
					 }	
				    transactionServiceRepo.deleteById(Integer.parseInt(tranId));
		         }
		     if(li.get(0).getStatus().equals("Not_now")||li.get(0).getStatus().equals("2"))
		      {
		    	 transactionServiceRepo.deleteById(Integer.parseInt(tranId));
		      }     
		 }
		/////////////Contra/////////////
		if(li.get(0).getType().equals("Contra"))
		 {
			amount1=Double.parseDouble(li.get(0).getAmount());
			    List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(0).getDbt_ac()));
			    System.out.println("li1 "+ li1.size());
			    if(li1.size()>0)
				{
			    	finalAmount=Double.parseDouble(li1.get(0).getAmount())-amount1;
			    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt(li.get(0).getDbt_ac()));
//					if (!studentOptional.isPresent())
//						return ResponseEntity.notFound().build();
                   li1.get(0).setAmount(Double.toString(finalAmount));
					ledgerServiceRepo.saveAll(li1);
				}	
			    List<Account_ledger_v3> li2=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(0).getCrdt_ac()));
			    System.out.println("li2 "+ li2.size());
			    if(li2.size()>0)
				{
			    	if(li2.get(0).getAc_type().equals("2")||li2.get(0).getAc_type().equals("3"))
			    	{
			    	finalAmount=Double.parseDouble(li2.get(0).getAmount())-amount1;
			    	}
			    	else
			    	{
			    		finalAmount=Double.parseDouble(li2.get(0).getAmount())+amount1;
			    	}
			    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt(li.get(0).getCrdt_ac()));
//					if (!studentOptional.isPresent())
//						return ResponseEntity.notFound().build();
                   li2.get(0).setAmount(Double.toString(finalAmount));
					ledgerServiceRepo.saveAll(li2);
				}	
			transactionServiceRepo.deleteById(Integer.parseInt(tranId));
		 }
		if(!li.get(0).getType().equals("Contra")&&!li.get(0).getType().equals("Receipt")&&!li.get(0).getType().equals("Voucher"))
		{
			 li.get(0).setUser_bank("can't");
		}
	  }
	return li;
}
public List<Account_transactions_v3> bankBookDelete(String tranId)  {
	Double amount1=(double) 0,previousAmount=(double) 0,finalAmount=(double) 0;  
	List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.journal_Search(tranId);
	  System.out.println("li "+ li.size());
		if(li.size()>0)
		{
			if(li.get(0).getType().equals("Voucher"))
			 {
				amount1=Double.parseDouble(li.get(0).getAmount());
				    List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(0).getBank()));
				    System.out.println("li1 "+ li1.size());
				    if(li1.size()>0)
					{
				    	finalAmount=Double.parseDouble(li1.get(0).getAmount())+amount1;
				    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt(li.get(0).getBank()));
//						if (!studentOptional.isPresent())
//							return ResponseEntity.notFound().build();
                        li1.get(0).setAmount(Double.toString(finalAmount));
						ledgerServiceRepo.saveAll(li1);
					}	
				    List<Account_ledger_v3> li2=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(0).getDbt_ac()));
				    System.out.println("li2 "+ li2.size());
				    if(li2.size()>0)
					{
				    	finalAmount=Double.parseDouble(li2.get(0).getAmount())-amount1;
				    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt(li.get(0).getDbt_ac()));
//						if (!studentOptional.isPresent())
//							return ResponseEntity.notFound().build();
                        li2.get(0).setAmount(Double.toString(finalAmount));
						ledgerServiceRepo.saveAll(li2);
					}	
						transactionServiceRepo.deleteById(Integer.parseInt(tranId));
			 }
			/////////Receipt type///////////
		if(li.get(0).getType().equals("Receipt"))
		  {
		  if(li.get(0).getStatus().equals("Recieve")||li.get(0).getStatus().equals("1"))
		    {
			  amount1=Double.parseDouble(li.get(0).getAmount());
				    List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(0).getBank()));
				    System.out.println("li1 "+ li1.size());
				    if(li1.size()>0)
					{
				    	finalAmount=Double.parseDouble(li1.get(0).getAmount())-amount1;
				    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt(li.get(0).getBank()));
//						if (!studentOptional.isPresent())
//							return ResponseEntity.notFound().build();
                       li1.get(0).setAmount(Double.toString(finalAmount));
						ledgerServiceRepo.saveAll(li1);
					}	
				    List<Account_ledger_v3> li2=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(0).getCrdt_ac()));
				    System.out.println("li2 "+ li2.size());
				    if(li2.size()>0)
					{
				    	finalAmount=Double.parseDouble(li2.get(0).getAmount())-amount1;
				    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt(li.get(0).getCrdt_ac()));
//						if (!studentOptional.isPresent())
//							return ResponseEntity.notFound().build();
                       li2.get(0).setAmount(Double.toString(finalAmount));
						ledgerServiceRepo.saveAll(li2);
					 }	
				    transactionServiceRepo.deleteById(Integer.parseInt(tranId));
		         }
		     if(li.get(0).getStatus().equals("Not_now")||li.get(0).getStatus().equals("2"))
		      {
		    	 transactionServiceRepo.deleteById(Integer.parseInt(tranId));
		      }     
		 }
		/////////////Contra/////////////
		if(li.get(0).getType().equals("Contra"))
		 {
			amount1=Double.parseDouble(li.get(0).getAmount());
			    List<Account_ledger_v3> li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(0).getDbt_ac()));
			    System.out.println("li1 "+ li1.size());
			    if(li1.size()>0)
				{
			    	finalAmount=Double.parseDouble(li1.get(0).getAmount())-amount1;
			    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt(li.get(0).getDbt_ac()));
//					if (!studentOptional.isPresent())
//						return ResponseEntity.notFound().build();
                   li1.get(0).setAmount(Double.toString(finalAmount));
					ledgerServiceRepo.saveAll(li1);
				}	
			    List<Account_ledger_v3> li2=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(0).getCrdt_ac()));
			    System.out.println("li2 "+ li2.size());
			    if(li2.size()>0)
				{
			    	if(li2.get(0).getAc_type().equals("2")||li2.get(0).getAc_type().equals("3"))
			    	{
			    	finalAmount=Double.parseDouble(li2.get(0).getAmount())-amount1;
			    	}
			    	else
			    	{
			    		finalAmount=Double.parseDouble(li2.get(0).getAmount())+amount1;
			    	}
			    	Optional<Account_ledger_v3> studentOptional = ledgerServiceRepo.findById(Integer.parseInt(li.get(0).getCrdt_ac()));
//					if (!studentOptional.isPresent())
//						return ResponseEntity.notFound().build();
                   li2.get(0).setAmount(Double.toString(finalAmount));
					ledgerServiceRepo.saveAll(li2);
				}	
			transactionServiceRepo.deleteById(Integer.parseInt(tranId));
		 }
		if(!li.get(0).getType().equals("Contra")&&!li.get(0).getType().equals("Receipt")&&!li.get(0).getType().equals("Voucher"))
		{
			 li.get(0).setUser_bank("can't");
		}
	  }
	return li;
}
/////////////////Transaction History////////////////
public List<Account_transactions_v3> transactionHistory_searchs(String start,String end,String debit,String credit,String field,String val)  {
	  System.out.println("debit :"+ debit+"credit :"+credit+"field :"+field+"val :"+val);
	List<Account_transactions_v3> li=null;
	if(field.equals("")||field.equals("undefined"))
	{
	if(!debit.equals("null") && !credit.equals("null") &&  !debit.equals(credit))
		{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.dayBookDataBnDate1(start,end,debit,credit);
		}
		if(!debit.equals("null") && !credit.equals("null") && debit.equals(credit))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.dayBookDataBnDate2(start,end,debit,credit);
		}
     if(!debit.equals("null") && credit.equals("null"))
		{
  	   li=(List<Account_transactions_v3>) transactionServiceRepo.dayBookDataBnDate3(start,end,debit);
		}
		if(debit.equals("null") && !credit.equals("null"))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.dayBookDataBnDate4(start,end,credit);
		}
		if(debit.equals("null") && credit.equals("null"))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.dayBookDataBnDate5(start,end);
		}
	}
		///////////////new code///////////////////
		if(field.equals("narration"))
		{
		if(!debit.equals("null") && !credit.equals("null") &&  !debit.equals(credit))
		{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.transactionHistoryNarrationSearch1(start,end,debit,credit,val);
		}
		if(!debit.equals("null") && !credit.equals("null") && debit.equals(credit))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.transactionHistoryNarrationSearch2(start,end,debit,credit,val);
		}
     if(!debit.equals("null") && credit.equals("null"))
		{
  	   li=(List<Account_transactions_v3>) transactionServiceRepo.transactionHistoryNarrationSearch3(start,end,debit,val);
		}
		if(debit.equals("null") && !credit.equals("null"))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.transactionHistoryNarrationSearch4(start,end,credit,val);
		}
		if(debit.equals("null") && credit.equals("null"))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.transactionHistoryNarrationSearch5(start,end,val);
		}
		}
		if(field.equals("amount"))
		{
			String[] retval= val.split(",");
			  System.out.println("retval :"+ retval[0]+"  "+retval[1]);
		if(!debit.equals("null") && !credit.equals("null") &&  !debit.equals(credit))
		{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.transactionHistoryAmountSearch1(start,end,debit,credit,retval[0],retval[1]);
		}
		if(!debit.equals("null") && !credit.equals("null") && debit.equals(credit))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.transactionHistoryAmountSearch2(start,end,debit,credit,retval[0],retval[1]);
		}
     if(!debit.equals("null") && credit.equals("null"))
		{
  	   li=(List<Account_transactions_v3>) transactionServiceRepo.transactionHistoryAmountSearch3(start,end,debit,retval[0],retval[1]);
		}
		if(debit.equals("null") && !credit.equals("null"))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.transactionHistoryAmountSearch4(start,end,credit,retval[0],retval[1]);
		}
		if(debit.equals("null") && credit.equals("null"))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.transactionHistoryAmountSearch5(start,end,retval[0],retval[1]);
		}
		}
		if(field.equals("group"))
		{
		if(!debit.equals("null") && !credit.equals("null") &&  !debit.equals(credit))
		{
		 li=(List<Account_transactions_v3>) transactionServiceRepo.transactionHistoryGroupSearch1(start,end,debit,credit,val);
		}
		if(!debit.equals("null") && !credit.equals("null") && debit.equals(credit))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.transactionHistoryGroupSearch2(start,end,debit,credit,val);
		}
     if(!debit.equals("null") && credit.equals("null"))
		{
  	   li=(List<Account_transactions_v3>) transactionServiceRepo.transactionHistoryGroupSearch3(start,end,debit,val);
		}
		if(debit.equals("null") && !credit.equals("null"))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.transactionHistoryGroupSearch4(start,end,credit,val);
		}
		if(debit.equals("null") && credit.equals("null"))
		{
			 li=(List<Account_transactions_v3>) transactionServiceRepo.transactionHistoryGroupSearch5(start,end,val);
		}
		}
		////////////////////////////////////////
	  System.out.println("li "+ li.size());
	  List<Account_ledger_v3> li1 = new ArrayList<Account_ledger_v3>();	
		 List<Account_ledger_v3> li2 = new ArrayList<Account_ledger_v3>() ;
		if(li.size()>0)
		{
		for (int i = 0; i < li.size(); i++)   
			{ 
			if(!li.get(i).getDbt_ac().equals("Nil")&&!li.get(i).getDbt_ac().equals(""))
			{
				 li1=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(i).getDbt_ac()));
			}
			if(!li.get(i).getCrdt_ac().equals("Nil")&&!li.get(i).getCrdt_ac().equals(""))
			{
				 li2=(List<Account_ledger_v3>) ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(i).getCrdt_ac()));   
			}
        System.out.println("li1 "+ li1.size());
          if(li1.size()>0)
		    {
        	li.get(i).setBranch(li1.get(0).getLedger_name());
        	 if(li.get(i).getDescription().equals("ledger creation"))
        	  {
        		li.get(i).setTran_Date(li1.get(0).getLedger_date());
        	  }
		    }
          System.out.println("li2 "+ li2.size());
          if(li2.size()>0)
		    {
        	li.get(i).setChq_date(li2.get(0).getLedger_name());
        	 if(li.get(i).getDescription().equals("ledger creation"))
        	  {
        		li.get(i).setTran_Date(li2.get(0).getLedger_date());
        	  }
		    }
        }
		}
	return li;
}
/////////////////////////////////////////////
////////////////invoice////////////
public List<Account_transactions_v3> tran_gen_Search(String tran_gen_id)  {
	List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.tran_gen_Search(tran_gen_id);
	return li;
}
//////////////////////////////////////////
}
