package com.example.demo.service;

import java.util.Iterator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Account_group_v3;
import com.example.demo.model.Account_ledger_v3;
import com.example.demo.model.Account_transactions_v3;
import com.example.demo.repository.GroupServiceRepo;
import com.example.demo.repository.LedgerServiceRepo;
import com.example.demo.repository.TransactionServiceRepo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



@Service
public class LedgerService {
	
	@Autowired
	private LedgerServiceRepo ledgerServiceRepo;
	
	@Autowired
	private GroupServiceRepo groupServiceRepo;
	
	@Autowired
	private TransactionServiceRepo transactionServiceRepo;
	

	
	public Account_ledger_v3 add_Ledgers(Account_ledger_v3 fp) {
		

		return ledgerServiceRepo.save(fp);
	}
	
	
	public List<Account_ledger_v3> last_idSearchs()  {
		
		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.last_id_Search();
		
		
		return li;
	}
	
	
	public List<Account_ledger_v3> ledger_name_searchs(String ledgerName)  {
		System.out.println(ledgerName);
		
		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.ledger_name_search(ledgerName);
		
		
		return li;
	}
	
	
	public List<Account_ledger_v3> list_ledgers()  {
		
		
		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.selectData();
		
		Iterator<Account_ledger_v3> it = li.iterator(); 
		  
	    while (it.hasNext()) { 
	    	
	    	Account_ledger_v3 ob = it.next(); 
	    	
	    	System.out.println(ob.getAc_group());
	    	
	    	String grpName = (String) groupServiceRepo.selectGroup(ob.getAc_group());
	    	
	    	System.out.println(grpName);
	    	
	    	ob.setAc_group(grpName);
	    	
	    	
//	    	ob.setAc_title(acTitle);
//	    	
//	    	String acType = (String) acTypeRepo.selectType(ob.getAc_type());
//	    	
//	    	ob.setAc_type(acType);
	    }
		
		
		
		return li;
	}
	
	
public List<Account_ledger_v3> ledger_sorts(String field, String type)  {
		
		System.out.println(field+' '+type);
		
		List<Account_ledger_v3> li = null;
		
		
		if(field.equals("ledger_name")&&type.equals("ASC"))
		{
			 li=(List<Account_ledger_v3>) ledgerServiceRepo.ledger_nameA();
		}
		
		if(field.equals("ledger_name")&&type.equals("DESC"))
		{
			 li=(List<Account_ledger_v3>) ledgerServiceRepo.ledger_nameD();
		}
		
		if(field.equals("ac_group")&&type.equals("ASC"))
		{
			 li=(List<Account_ledger_v3>) ledgerServiceRepo.ac_groupA();
		}
		
		if(field.equals("ac_group")&&type.equals("DESC"))
		{
			 li=(List<Account_ledger_v3>) ledgerServiceRepo.ac_groupD();
		}
		
		if(field.equals("open_balance")&&type.equals("ASC"))
		{
			 li=(List<Account_ledger_v3>) ledgerServiceRepo.open_balanceA();
			 System.out.println("open balance asc");
		}
		
		if(field.equals("open_balance")&&type.equals("DESC"))
		{
			 li=(List<Account_ledger_v3>) ledgerServiceRepo.open_balanceD();
			 System.out.println("open balance desc");
		}
		
		if(field.equals("mobile")&&type.equals("ASC"))
		{
			 li=(List<Account_ledger_v3>) ledgerServiceRepo.mobileA();
		}
		
		if(field.equals("mobile")&&type.equals("DESC"))
		{
			 li=(List<Account_ledger_v3>) ledgerServiceRepo.mobileD();
		}
		
		if(field.equals("email")&&type.equals("ASC"))
		{
			 li=(List<Account_ledger_v3>) ledgerServiceRepo.emailA();
		}
		
		if(field.equals("email")&&type.equals("DESC"))
		{
			 li=(List<Account_ledger_v3>) ledgerServiceRepo.emailD();
		}
		
		if(field.equals("bank")&&type.equals("ASC"))
		{
			 li=(List<Account_ledger_v3>) ledgerServiceRepo.bankA();
		}
		
		if(field.equals("bank")&&type.equals("DESC"))
		{
			 li=(List<Account_ledger_v3>) ledgerServiceRepo.bankD();
		}
		
		
		Iterator<Account_ledger_v3> it = li.iterator(); 
		  
	    while (it.hasNext()) { 
	    	
	    	Account_ledger_v3 ob = it.next(); 
	    	
	    	System.out.println(ob.getAc_group());
	    	
	    	String grpName = (String) groupServiceRepo.selectGroup(ob.getAc_group());
	    	
	    	System.out.println(grpName);
	    	
	    	ob.setAc_group(grpName);
	    }
		
		
		
		return li;
    }
	


    public List<Account_ledger_v3> ledger_bn_dates(String start, String end)  {
	
	System.out.println("start"+start+"end"+end);
	List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.selectDataBnDate(start,end);
	
	Iterator<Account_ledger_v3> it = li.iterator(); 
	  
    while (it.hasNext()) { 
    	
    	Account_ledger_v3 ob = it.next(); 
    	
    	System.out.println(ob.getAc_group());
    	
    	String grpName = (String) groupServiceRepo.selectGroup(ob.getAc_group());
    	
    	System.out.println(grpName);
    	
    	ob.setAc_group(grpName);
    	
          }
    
    return li;
    
   }


	public String ledger_deletes(int id) {
		
		ledgerServiceRepo.deleteById(id);
//		transactionServiceRepo.transactionDelete(id);
		// TODO Auto-generated method stub
		return "Deleted successfully";
	}

	public List<Account_ledger_v3> ledger_Searchs(int ledgerId)  {
		System.out.println(ledgerId);
		
		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.ledger_Search(ledgerId);
		
		
		return li;
	}
	
	
    public List<Account_ledger_v3> bank_names()  {
		
		
		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.selectBank();
		
		return li;
     }
	
	
    public List<Account_ledger_v3> ledger_Searchs2(int ledgerId)  {
		System.out.println(ledgerId);
		
		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.ledger_Search2(ledgerId);
		
		
		return li;
	}
    
    
    
    public List<Account_transactions_v3> account_statementDatas(int ledgerId)  {
 		System.out.println(ledgerId);
 		
 		String ledger_id=Integer.toString(ledgerId);
 		
 		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.ledger_Search2(ledgerId);
 		
 		System.out.println(li.size());
 		Double cur_amount,cur_balance1 = (double) 0 , debit_total1 = (double) 0 ,credit_total1 =(double) 0 ;
 		String  balance_type = null , description = "ledger creation" ,particularname=null,created_date=null,tran_description=null;
 		Double  Amount=(double)0,tran_open_balance=(double)0;
 	    Integer particular=0;
 	    Double   balance=(double)0;
 		
 		for (int i = 0; i < li.size(); i++)   
 		{  
 			
 			System.out.println(li.get(i).getAc_group());
 			
 			balance_type=li.get(i).getBalance_type();
 			
 			 if (balance_type.equals("debit")) {
 				 
 			    if(li.get(i).getOpen_balance().equals(""))
 	 	 	      {
 			    	 debit_total1 +=(double) 0;
 	 	 	      }
 	 	 	      else
 	 	 	      {
 				 
 	 	          debit_total1 += Double.parseDouble(li.get(i).getOpen_balance());   
 	 	          
 	 	 	      }
 	 	          
 	 	          
 	 	        }
 	 	        if (balance_type.equals("credit")){
 	 	        	
 	 	          if(li.get(i).getOpen_balance().equals(""))
 	 	 	      {
 	 	        	credit_total1 +=(double) 0;
 	 	 	      }
 	 	 	      else
 	 	 	      {
 	 	        	
 	 	          credit_total1 += Double.parseDouble(li.get(i).getOpen_balance()); 
 	 	          
 	 	 	      }
 	 	        }

 	 	      System.out.println(debit_total1+"  "+credit_total1);
 	 	      if(li.get(i).getOpen_balance().equals(""))
 	 	      {
 	 	    	tran_open_balance=(double) 0;
 	 	      }
 	 	      else
 	 	      {
 	 	        tran_open_balance=Double.parseDouble(li.get(i).getOpen_balance());
 	 	      }
 			
 		}
 		
 		 System.out.println(" balance_type  "+balance_type);
 		
 	
 		 
 		 JSONObject jsonObject = new JSONObject();
 	      //Inserting key-value pairs into the json object
// 	      jsonObject.put("ID", "1");
// 	      jsonObject.put("First_Name", "Krishna Kasyap");
// 	      jsonObject.put("Last_Name", "Bhagavatula");
// 	      jsonObject.put("Date_Of_Birth", "1989-09-26");
// 	      jsonObject.put("Place_Of_Birth", "Vishakhapatnam");
// 	      jsonObject.put("Country", "25000");
 	      //Creating a json array
 	      JSONArray array = new JSONArray();
// 	      array.add("e-mail: krishna_kasyap@gmail.com");
 	    
 	      //Adding array to the json object
 	  

 		 
 	     cur_balance1=cur_balance1+tran_open_balance;
 		 
 		 
  	    	
List<Account_transactions_v3> li1=(List<Account_transactions_v3>) transactionServiceRepo.selectAccStmtTransaction2(ledgerId,description);
 		

//Iterator<Account_transactions_v3> it = li1.iterator(); 

//while (it.hasNext()) { 
//	
//	Account_transactions_v3 ob = it.next(); 
//	
//	System.out.println("fgdfgdf"+ob.getType());
//	
//}

 String flag=null;
 		
System.out.println(li1.size());

if(li1.size()>0)
{
	
	List<Account_transactions_v3> filteredList =(List<Account_transactions_v3>) transactionServiceRepo.transactionFilter(ledgerId,description);;
	
	System.out.println("filteredList count "+  filteredList.size());
	
	if(filteredList.size()>1)
	{
	    int lastId=filteredList.size()-1;
		
	    System.out.println("filteredList last id "+  filteredList.get(lastId).getTranID());
//		int tempId=filteredList.get(lastId).getTranID();
		
		filteredList.remove(lastId);
		
		li1.addAll(filteredList);
	}
	
	
//	for (int j = 0; j < li1.size(); j++)   
//	{ 
//		int f=0;
//	
//		if(li1.get(j).getDescription().equals("ledger creation"))
//		{
//			f=f+1;
//		}
//		
//		
//	}
	

for (int i = 0; i < li1.size(); i++)   
	{ 
	
	
	 if (li1.get(i).getDbt_ac().equals( ledger_id)) {
         
   	  particular = Integer.parseInt(li1.get(i).getCrdt_ac());
   	  
     }
     

     if (li1.get(i).getCrdt_ac().equals(ledger_id)) {
   	  
   	  particular = Integer.parseInt(li1.get(i).getDbt_ac());
     
   
     }
     
    particularname= ledgerServiceRepo.ac_ledger_SearchName(particular);

    
    System.out.println("particularname  "+ particularname);
//    System.out.println("balance_type  "+ balance_type);
	

//	balance_type=li1.get(i).get
	   Amount =Double.parseDouble(li1.get(i).getAmount()) ;
	   
//	   System.out.println("Amount  "+ Amount);
	   
//	   System.out.println("Dbt_ac  "+ li1.get(i).getDbt_ac());
//	   
//	   System.out.println("Crdt_ac  "+ li1.get(i).getCrdt_ac());
	   
	   

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
                   
                   array.add("e-mail:"+cur_balance1);
                   array.add("particularname "+particularname);
                   array.add("flag "+flag);
//                   cur_balance1=(float) 0;
//                   Amount=(float) 0;
                   
                   li1.get(i).setFilename(particularname);
       			li1.get(i).setAmount(Double.toString(Amount));
       			li1.get(i).setCreatedDate(created_date);
       			li1.get(i).setDescription(tran_description);
       		li1.get(i).setBank(Double.toString(cur_balance1));
       		li1.get(i).setBranch(flag);
       		
       		
           }


li1.get(0).setChq_no(Double.toString( debit_total1));
li1.get(0).setChq_date(Double.toString( credit_total1));



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
      

li1.get(0).setCreatedTime(Double.toString( balance));

      System.out.println("debit_total1  "+ debit_total1+"  "+credit_total1);
 	       
      jsonObject.put("contact",array);
    
      System.out.println("array  "+ array);

}	
 		return li1;
 	


    
    }
    
    
    public List<Account_ledger_v3> profit_losss(String title)  {
    	
    	double directIncomeAmount=(double) 0;
    	
List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.profit_loss(title);
 		
 		System.out.println(li.size());
 		
 		
 		if(li.size()>0)
 		{

 		for (int i = 0; i < li.size(); i++)   
 			{ 
 			
 			if(!li.get(i).getAmount().equals("0")&&!li.get(i).getAmount().equals(""))
 			{
 			
 				directIncomeAmount=(double) (directIncomeAmount+Double.parseDouble(li.get(i).getAmount()));
 			}
 		
 			
 		
 		}
    	
    	li.get(0).setBranch(Double.toString(directIncomeAmount));
 		System.out.println("directIncomeAmount  "+directIncomeAmount);
    	
    }
 		return li;
    	
    }
    
    
    public List<Account_ledger_v3> profit_loss_bn_dates(String title, String start, String end)  {
    	
    	double directIncomeAmount=(double) 0;
    	int ledgerId;
    	double ledgerTotalAmount=(double) 0;
    	
    	List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.profit_loss_idSearchByTitle(title);
    	 		
    	 		System.out.println("acTitle "+title+" size "+ li.size());
    	 		
    	 		
    	 		if(li.size()>0)
    	 		{

    	 		for (int i = 0; i < li.size(); i++)   
    	 			{ 
    	 			
    	 			ledgerTotalAmount=(double) 0;
    	 			
    	 			ledgerId=li.get(i).getId();
    	 			
    	 			List<Account_transactions_v3> li1=(List<Account_transactions_v3>) transactionServiceRepo.profit_loss_bn_date(ledgerId,start,end);
    	 			
    	 			System.out.println("transaction "+ledgerId+" size "+ li1.size());
    	 			
    		 		if(li1.size()>0)
        	 		{

        	 		for (int j = 0; j < li1.size(); j++)   
        	 			{ 
        	 			
    	 						
    	 		    directIncomeAmount=(double) (directIncomeAmount+Float.parseFloat(li1.get(j).getAmount()));
    	 		     
    	 		    ledgerTotalAmount=(double) (ledgerTotalAmount+Float.parseFloat(li1.get(j).getAmount()));
    	 		    
        	 			}
        	 		}
    	 		
    	 			li.get(i).setAmount(Double.toString(ledgerTotalAmount));
    	 		
    	 		}
    	    	
    	    	li.get(0).setBranch(Double.toString(directIncomeAmount));
    	 		System.out.println("directIncomeAmount  "+directIncomeAmount);
    	    	
    	    }
    	
    	   	
    	return li;
    	
    	
    }
    
    
    
    
    public List<Account_transactions_v3> account_statementDataBnDates(int ledgerId,String start, String end)  {
 		System.out.println(ledgerId);
 		
 		String ledger_id=Integer.toString(ledgerId);
 		
 		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.ledger_Search2(ledgerId);
 		
 		System.out.println(li.size());
 		Double cur_amount,cur_balance1 = (double) 0 , debit_total1 = (double) 0 ,credit_total1 =(double) 0 ;
 		String  balance_type = null , description = "ledger creation" ,particularname=null,created_date=null,tran_description=null;
 		Double  Amount=(double)0,tran_open_balance=(double)0;
 	    Integer particular=0;
 	    Double   balance=(double)0;
 		
 		for (int i = 0; i < li.size(); i++)   
 		{  
 			
 			System.out.println(li.get(i).getAc_group());
 			
 			balance_type=li.get(i).getBalance_type();
 			
 			 if (balance_type.equals("debit")) {
 				 
 				   if(li.get(i).getOpen_balance().equals(""))
  	 	 	      {
 					debit_total1 +=(double) 0;
  	 	 	      }
  	 	 	      else
  	 	 	      {
 				 
 	 	          debit_total1 += Double.parseDouble(li.get(i).getOpen_balance());
  	 	 	      }
 	 	        }
 	 	        if (balance_type.equals("credit")){
 	 	        	
 	 	          if(li.get(i).getOpen_balance().equals(""))
 	 	 	      {
 	 	        	credit_total1 +=(double) 0;
 	 	 	      }
 	 	 	      else
 	 	 	      {
 	 	        	
 	 	          credit_total1 += Double.parseDouble(li.get(i).getOpen_balance()); 
 	 	          
 	 	 	      }
 	 	        }

 	 	      System.out.println(debit_total1+"  "+credit_total1);
 	 	      
 	 	    if(li.get(i).getOpen_balance().equals(""))
	 	      {
	 	    	tran_open_balance=(double) 0;
	 	      }
	 	      else
	 	      {
	 	        tran_open_balance=Double.parseDouble(li.get(i).getOpen_balance());
	 	      }
 	 	      
 			
 		}
 		
 		 System.out.println(" balance_type  "+balance_type);
 		
 	
 		 
 		 JSONObject jsonObject = new JSONObject();
 	      //Inserting key-value pairs into the json object
// 	      jsonObject.put("ID", "1");
// 	      jsonObject.put("First_Name", "Krishna Kasyap");
// 	      jsonObject.put("Last_Name", "Bhagavatula");
// 	      jsonObject.put("Date_Of_Birth", "1989-09-26");
// 	      jsonObject.put("Place_Of_Birth", "Vishakhapatnam");
// 	      jsonObject.put("Country", "25000");
 	      //Creating a json array
 	      JSONArray array = new JSONArray();
// 	      array.add("e-mail: krishna_kasyap@gmail.com");
 	    
 	      //Adding array to the json object
 	  

 		 
 	     cur_balance1=cur_balance1+tran_open_balance;
 		 
 		 
  	    	
List<Account_transactions_v3> li1=(List<Account_transactions_v3>) transactionServiceRepo.selectAccStmtTransaction2Bndates(ledgerId,description,start,end);
 		

//Iterator<Account_transactions_v3> it = li1.iterator(); 

//while (it.hasNext()) { 
//	
//	Account_transactions_v3 ob = it.next(); 
//	
//	System.out.println("fgdfgdf"+ob.getType());
//	
//}

 String flag=null;
 		
System.out.println(li1.size());

if(li1.size()>0)
{

for (int i = 0; i < li1.size(); i++)   
	{ 
	
	
	 if (li1.get(i).getDbt_ac().equals( ledger_id)) {
         
   	  particular = Integer.parseInt(li1.get(i).getCrdt_ac());
   	  
     }
     

     if (li1.get(i).getCrdt_ac().equals(ledger_id)) {
   	  
   	  particular = Integer.parseInt(li1.get(i).getDbt_ac());
     
   
     }
     
    particularname= ledgerServiceRepo.ac_ledger_SearchName(particular);

    
    System.out.println("particularname  "+ particularname);
//    System.out.println("balance_type  "+ balance_type);
	

//	balance_type=li1.get(i).get
	   Amount =Double.parseDouble(li1.get(i).getAmount()) ;
	   
//	   System.out.println("Amount  "+ Amount);
	   
//	   System.out.println("Dbt_ac  "+ li1.get(i).getDbt_ac());
//	   
//	   System.out.println("Crdt_ac  "+ li1.get(i).getCrdt_ac());
	   
	   

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
                   
                   array.add("e-mail:"+cur_balance1);
                   array.add("particularname "+particularname);
                   array.add("flag "+flag);
//                   cur_balance1=(float) 0;
//                   Amount=(float) 0;
                   
                   li1.get(i).setFilename(particularname);
       			li1.get(i).setAmount(Double.toString(Amount));
       			li1.get(i).setCreatedDate(created_date);
       			li1.get(i).setDescription(tran_description);
       		li1.get(i).setBank(Double.toString(cur_balance1));
       		li1.get(i).setBranch(flag);
       		
       		
           }


li1.get(0).setChq_no(Double.toString( debit_total1));
li1.get(0).setChq_date(Double.toString( credit_total1));



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
      

li1.get(0).setCreatedTime(Double.toString( balance));

      System.out.println("debit_total1  "+ debit_total1+"  "+credit_total1);
 	       
      jsonObject.put("contact",array);
    
      System.out.println("array  "+ array);

}	
 		return li1;
 	


    
    }

    
    public String updateledgerbalance()
    
    {
    	double as1=0,lab2=0,inc2=0,exp1=0;
    	
    	 	
    	List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.ledgerLoad("1");
     	
    	if(li.size()>0)
 		{
 			
 		for (int i = 0; i < li.size(); i++)   
 		{ 
 			
 		as1=  ledgerBalance(li.get(i).getId());
 			
 		li.get(i).setAmount(Double.toString(as1));
        
		ledgerServiceRepo.saveAll(li);
 		
 		}
    	
 		
 		}
    	
    	
    	
	List<Account_ledger_v3> li2=(List<Account_ledger_v3>) ledgerServiceRepo.ledgerLoad("2");
     	
    	if(li2.size()>0)
 		{
 			
 		for (int i = 0; i < li2.size(); i++)   
 		{ 
 			
 			lab2=  ledgerBalanceforliabality(li2.get(i).getId());
 			
 		li2.get(i).setAmount(Double.toString(lab2));
        
		ledgerServiceRepo.saveAll(li2);
 		
 		}
    	
 		
 		}
    	
    	
    	
	List<Account_ledger_v3> li3=(List<Account_ledger_v3>) ledgerServiceRepo.ledgerLoad("3");
     	
    	if(li3.size()>0)
 		{
 			
 		for (int i = 0; i < li3.size(); i++)   
 		{ 
 			
 			inc2=  ledgerBalanceforliabality(li3.get(i).getId());
 			
 		li3.get(i).setAmount(Double.toString(inc2));
        
		ledgerServiceRepo.saveAll(li3);
 		
 		}
    	
 		
 		}
    	
    	
    	
    	
	List<Account_ledger_v3> li4=(List<Account_ledger_v3>) ledgerServiceRepo.ledgerLoad("4");
     	
    	if(li4.size()>0)
 		{
 			
 		for (int i = 0; i < li4.size(); i++)   
 		{ 
 			
 			exp1=  ledgerBalance(li4.get(i).getId());
 			
 		li4.get(i).setAmount(Double.toString(exp1));
        
		ledgerServiceRepo.saveAll(li4);
 		
 		}
    	
 		
 		}
    	
    	
    	
    	
    	
    	
    	return "ledgerbalance updated";
    }
    
    
    
   public Double ledgerBalance(int id)
     {
	
	   double dbtTotAmount =0;
	   double crdtTotAmount =0;
	   double balance=0;
	   
	   
	   
		List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.dbtAcLoad(id);
			
		if(li.size()>0)
 		{
 			
 		for (int i = 0; i < li.size(); i++)   
 		{ 
 			dbtTotAmount=dbtTotAmount+ Double.parseDouble(li.get(i).getAmount());
 			
 		}
 		
 		}
		
		List<Account_transactions_v3> li1=(List<Account_transactions_v3>) transactionServiceRepo.crdtAcLoad(id);
		
		if(li1.size()>0)
 		{
 			
 		for (int i = 0; i < li1.size(); i++)   
 		{ 
 			crdtTotAmount=crdtTotAmount+ Double.parseDouble(li1.get(i).getAmount());
 			
 		}
 		
 		}
		
		
		balance= dbtTotAmount-crdtTotAmount;
		
	
	   return  balance;
    }
   
    
   public Double ledgerBalanceforliabality(int id)
   {
	
	   double dbtTotAmount = 0;
	   double crdtTotAmount =0;
	   double balance=0;
	   
	   
	   
		List<Account_transactions_v3> li=(List<Account_transactions_v3>) transactionServiceRepo.dbtAcLoad(id);
			
		if(li.size()>0)
		{
			
		for (int i = 0; i < li.size(); i++)   
		{ 
			dbtTotAmount=dbtTotAmount+ Double.parseDouble(li.get(i).getAmount());
			
		}
		
		}
		
		List<Account_transactions_v3> li1=(List<Account_transactions_v3>) transactionServiceRepo.crdtAcLoad(id);
		
		if(li1.size()>0)
		{
			
		for (int i = 0; i < li1.size(); i++)   
		{ 
			crdtTotAmount=crdtTotAmount+ Double.parseDouble(li1.get(i).getAmount());
			
		}
		
		}
		
		
		balance= crdtTotAmount-dbtTotAmount;
		
	
	   return  balance;
  }
 
    
    
    
    
    
//    
//    public List<Account_ledger_v3> balanceSheetDataBnDates(String title, String end)  {
// 		System.out.println(title);
// 		
// 		String start="2017-04-01";
// 		
// 		Long fixedLiabalityAmount=(long) 0;
// 		Long credit_total=(long) 0;
// 		Long debit_total =(long) 0;
// 		Float amount_balance =(float) 0;
// 		Long balance =(long) 0;
//     
// 		
// 		
// 		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.profit_loss(title);
// 	
// 		
// 		if(li.size()>0)
// 		{
// 			
// 		for (int i = 0; i < li.size(); i++)   
// 		{ 
// 			 
// 	 		 credit_total=(long) 0;
// 	 		 debit_total =(long) 0;
// 	 		 amount_balance =(float) 0;
// 	 		 balance =(long) 0;
// 			
// 			
// 			
// 			
// 			
// 			if(!li.get(i).getAmount().equals("0") && !li.get(i).getAmount().equals(""))
// 			{
// 			
// 				int id=li.get(i).getId();
// 				String Id=Integer.toString( li.get(i).getId());
// 				
// 				System.out.println("id "+ id);
// 				
// 				Float amount=Float.parseFloat( li.get(i).getAmount());
// 				
// 				List<Account_transactions_v3> li1=(List<Account_transactions_v3>) transactionServiceRepo.selectBalanceSheetDataBnDates(id,start,end);
// 				
// 				System.out.println("li1 "+ li1.size());
// 				
// 				
// 				for (int j = 0; j < li1.size(); j++)   
// 		 		{  
// 		 			
// 				
// 					if (li1.get(j).getDbt_ac().equals(Id)) {
// 		 	 	          debit_total =  (long) (debit_total +Float.parseFloat(li1.get(j).getAmount()));   
// 		 	 	        }
// 		 	 	        if (li1.get(j).getCrdt_ac().equals(Id)){
// 		 	 	          credit_total = (long) (credit_total+Float.parseFloat(li1.get(j).getAmount()));  
// 		 	 	        }
// 					
// 		 	 	     System.out.println("dbtAc"+ li1.get(j).getDbt_ac());
// 		 	 	  System.out.println("CrdtAc"+ li1.get(j).getCrdt_ac());
// 		 		System.out.println("credit_total "+ credit_total);
// 		 	 	        
// 			    }
// 				
// 				
// 				if(debit_total>credit_total)
//                {
//                balance=(long) (debit_total-credit_total);
//                }
//                
//                else
//                {
//                balance=(long) (credit_total-debit_total);
//                }
// 				
// 				System.out.println("debit_total "+ debit_total);
// 				System.out.println("credit_total "+ credit_total);
// 				System.out.println("balance "+ balance);
// 				
// 				
// 			
// 				fixedLiabalityAmount=(long) (fixedLiabalityAmount +balance);
//
// 	 	      li.get(i).setAmount(Long.toString(balance));
// 			
// 	 	    System.out.println("fixedLiabalityAmount "+ fixedLiabalityAmount);
// 			}
// 		}
// 		
// 		li.get(0).setBranch(Long.toString(fixedLiabalityAmount));
// 		}
// 		
// 	return li;	
//    }
    
   
   
   
   public List<Account_ledger_v3> balanceSheetDataBnDates(String title,String Start, String end)  {
		System.out.println(title);
		System.out.println("Start Date "+Start);
		
		String start=Start;
		
		double fixedLiabalityAmount=(double) 0;
		double credit_total=(double) 0;
		double debit_total =(double) 0;
		Float amount_balance =(float) 0;
		double balance =(double) 0;
    
		
		
		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.profit_loss(title);
	
		
		if(li.size()>0)
		{
			
		for (int i = 0; i < li.size(); i++)   
		{ 
			 
	 		 credit_total=(double) 0;
	 		 debit_total =(double) 0;
	 		 amount_balance =(float) 0;
	 		 balance =(double) 0;
			
			
			
			
			
			if(!li.get(i).getAmount().equals("0") && !li.get(i).getAmount().equals(""))
			{
			
				int id=li.get(i).getId();
				String Id=Integer.toString( li.get(i).getId());
				
				System.out.println("id "+ id);
				
				Float amount=Float.parseFloat( li.get(i).getAmount());
				
				List<Account_transactions_v3> li1=(List<Account_transactions_v3>) transactionServiceRepo.selectBalanceSheetDataBnDates(id,start,end);
				
				System.out.println("li1 "+ li1.size());
				
				
				for (int j = 0; j < li1.size(); j++)   
		 		{  
		 			
				
					if (li1.get(j).getDbt_ac().equals(Id)) {
		 	 	          debit_total =  (double) (debit_total +Float.parseFloat(li1.get(j).getAmount()));   
		 	 	        }
		 	 	        if (li1.get(j).getCrdt_ac().equals(Id)){
		 	 	          credit_total = (double) (credit_total+Float.parseFloat(li1.get(j).getAmount()));  
		 	 	        }
					
		 	 	     System.out.println("dbtAc"+ li1.get(j).getDbt_ac());
		 	 	  System.out.println("CrdtAc"+ li1.get(j).getCrdt_ac());
		 		System.out.println("credit_total "+ credit_total);
		 	 	        
			    }
				
				
				if(debit_total>credit_total)
               {
               balance=(double) (debit_total-credit_total);
               }
               
               else
               {
               balance=(double) (credit_total-debit_total);
               }
				
				System.out.println("debit_total "+ debit_total);
				System.out.println("credit_total "+ credit_total);
				System.out.println("balance "+ balance);
				
				
			
				fixedLiabalityAmount=(double) (fixedLiabalityAmount +balance);

	 	      li.get(i).setAmount(Double.toString(balance));
			
	 	    System.out.println("fixedLiabalityAmount "+ fixedLiabalityAmount);
			}
		}
		
		li.get(0).setBranch(Double.toString(fixedLiabalityAmount));
		}
		
	return li;	
   }

   
   
   
   
   
    
//    public List<Account_ledger_v3> balanceSheetProfitLossDataBnDates(String title, String end)  {
// 		System.out.println(title);
// 		System.out.println(end);
// 		
// 		long directIncomeAmount=(long) 0;
//    	
// 		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.balanceSheetProfitLossDataBnDate(title,end);
// 		 		
// 		 		System.out.println(li.size());
// 		 		
// 		 		
// 		 		if(li.size()>0)
// 		 		{
//
// 		 		for (int i = 0; i < li.size(); i++)   
// 		 			{ 
// 		 			
// 		 			if(!li.get(i).getAmount().equals("0")&&!li.get(i).getAmount().equals(""))
// 		 			{
// 		 			
// 		 				directIncomeAmount=(long) (directIncomeAmount+Float.parseFloat(li.get(i).getAmount()));
// 		 			}
// 		 		
// 		 			
// 		 		
// 		 		}
// 		    	
// 		    	li.get(0).setBranch(Long.toString(directIncomeAmount));
// 		 		System.out.println("directIncomeAmount  "+directIncomeAmount);
// 		    	
// 		    }
// 		 		
// 		
// 		return li;
// 		
//    }
    
    
    public List<Account_ledger_v3> balanceSheetProfitLossDataBnDates(String title, String start  , String end)  {
 		System.out.println(title);
 		System.out.println(end);
 		
 		double directIncomeAmount=(double) 0;
    	
 		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.balanceSheetProfitLossDataBnDate(title,start ,end);
 		 		
 		 		System.out.println(li.size());
 		 		
 		 		
 		 		if(li.size()>0)
 		 		{

 		 		for (int i = 0; i < li.size(); i++)   
 		 			{ 
 		 			
 		 			if(!li.get(i).getAmount().equals("0")&&!li.get(i).getAmount().equals(""))
 		 			{
 		 			
 		 				directIncomeAmount=(double) (directIncomeAmount+Float.parseFloat(li.get(i).getAmount()));
 		 			}
 		 		
 		 			
 		 		
 		 		}
 		    	
 		    	li.get(0).setBranch(Double.toString(directIncomeAmount));
 		 		System.out.println("directIncomeAmount  "+directIncomeAmount);
 		    	
 		    }
 		 		
 		
 		return li;
 		
    }

    
    
    
    
    public List<Account_ledger_v3> trial_balance(String acType)  {
    	
    	Double total_debit =(double) 0;
    	Double total_credit = (double) 0;
    	Double total_balance =(double) 0;
    	Double Balance =(double) 0;
    	Double as1=(double) 0;
    	
List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.trial_balances(acType);
 		
 		System.out.println(li.size());
 		
 		
 		if(li.size()>0)
 		{

 		for (int i = 0; i < li.size(); i++)   
 			{ 
 			
 			int id= li.get(i).getId();
 			
 			
 			Double dbtAmnt=transactionServiceRepo.selectTrialBalanceDbt(id);
 			
 			Double dbtAmntWithoutOpenBalance = transactionServiceRepo.selectTrialBalanceDbtWithoutOpenBalance(id);
 				
 			System.out.println("dbtAmnt  "+dbtAmnt);
 			System.out.println("dbtAmntWithoutOpenBalance  "+dbtAmntWithoutOpenBalance);
 			
 			if(dbtAmnt!=null)
 			{
 			total_debit=total_debit+dbtAmnt;
 			}
 			
 			Double crdtAmnt=transactionServiceRepo.selectTrialBalanceCrdt(id);
 			
 			Double crdtAmntWithoutOpenBalance = transactionServiceRepo.selectTrialBalanceCrdtWithoutOpenBalance(id);
 			
 			
 			
 			System.out.println("crdtAmnt  "+crdtAmnt);
 			System.out.println("crdtAmntWithoutOpenBalance  "+crdtAmntWithoutOpenBalance);
 			
 			if(crdtAmnt!=null)
 			{
 			total_credit=total_credit+crdtAmnt;
 			}
 			
 			
 			if(dbtAmnt==null)
 			{
 				dbtAmnt=(double) 0;
 				dbtAmntWithoutOpenBalance=(double) 0;
 			}
 			
 			if(crdtAmnt==null)
 			{
 				crdtAmnt=(double) 0;
 				crdtAmntWithoutOpenBalance=(double) 0;
 			}
 			
 			if(dbtAmntWithoutOpenBalance==null)
 			{
 				
 				dbtAmntWithoutOpenBalance=(double) 0;
 			}
 			
 			if(crdtAmntWithoutOpenBalance==null)
 			{
 				
 				crdtAmntWithoutOpenBalance=(double) 0;
 			}
 			
 			
 			Balance=Math.abs(dbtAmnt-crdtAmnt);
			as1= dbtAmnt-crdtAmnt;
			total_balance = total_balance+Balance;
			
 			
			li.get(i).setAddress(Double.toString(dbtAmnt));
			li.get(i).setEmail(Double.toString(crdtAmnt));
			li.get(i).setBank(Double.toString(Balance));
			li.get(i).setMobile(Double.toString(as1));
			li.get(i).setIfsc_code(Double.toString(dbtAmntWithoutOpenBalance));
			li.get(i).setState(Double.toString(crdtAmntWithoutOpenBalance));

			 		
 		}
    	
//    	li.get(0).setBranch(Long.toString(directIncomeAmount));
// 		System.out.println("directIncomeAmount  "+directIncomeAmount);
 		li.get(0).setContact(Double.toString(total_balance));
 		
 		System.out.println("total_balance "+ total_balance);
    	
    }
 		return li;
    	
    }
    
    
    public String trial_balance_total()  {
    	
    	Double totalDbt= transactionServiceRepo.selectTotalDbtAmnt();
			
			System.out.println("totalDbt "+ totalDbt);
		
			Double totalCrd= transactionServiceRepo.selectTotalCrdAmnt();
			
			System.out.println("totalCrd "+ totalCrd);
    	
			
			String c= totalDbt+","+totalCrd;
		
			return c;
    	
    }
    
    
    
    public List<Account_ledger_v3> trial_balanceBnDate(String acType,String start,String end)  {
    	
        Double total_debit =(double) 0;
        Double total_credit = (double) 0;
        Double total_balance =(double) 0;
        Double Balance =(double) 0;
        Double as1=(double) 0;
        	
    List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.trial_balances(acType);
     		
     		System.out.println(li.size());
     		
     		
     		if(li.size()>0)
     		{

     		for (int i = 0; i < li.size(); i++)   
     			{ 
     			
     			int id= li.get(i).getId();
     			
     			
     			Double dbtAmnt=transactionServiceRepo.selectTrialBalanceDbtBnDates(id,start,end);
     				
     			System.out.println("dbtAmnt  "+dbtAmnt);
     			
     			if(dbtAmnt!=null)
     			{
     			total_debit=total_debit+dbtAmnt;
     			}
     			
     			Double crdtAmnt=transactionServiceRepo.selectTrialBalanceCrdtBnDates(id,start,end);
     			
     			System.out.println("crdtAmnt  "+crdtAmnt);
     			
     			if(crdtAmnt!=null )
     			{
     			total_credit=total_credit+crdtAmnt;
     			}
     			
     			
     			if(dbtAmnt==null)
     			{
     				dbtAmnt=(double) 0;
     			}
     			
     			if(crdtAmnt==null)
     			{
     				crdtAmnt=(double) 0;
     			}
     			
     			Balance=Math.abs(dbtAmnt-crdtAmnt);
    			as1= dbtAmnt-crdtAmnt;
    			total_balance = total_balance+Balance;
    			
     			
    			li.get(i).setAddress(Double.toString(dbtAmnt));
    			li.get(i).setEmail(Double.toString(crdtAmnt));
    			li.get(i).setBank(Double.toString(Balance));
    			li.get(i).setMobile(Double.toString(as1));
    			 		
     		}
        	
//        	li.get(0).setBranch(Long.toString(directIncomeAmount));
//     		System.out.println("directIncomeAmount  "+directIncomeAmount);
     		li.get(0).setContact(Double.toString(total_balance));
     		
     		System.out.println("total_balance "+ total_balance);
        	
        }
     		return li;
        	
        }
        

    
   public String trial_balance_totalBnDate(String start,String end)  {
    	
    	Double totalDbt= transactionServiceRepo.selectTotalDbtAmntBnDates(start,end);
			
			System.out.println("totalDbt "+ totalDbt);
		
			Double totalCrd= transactionServiceRepo.selectTotalCrdAmntBnDates(start,end);
			
			System.out.println("totalCrd "+ totalCrd);
    	
			if(totalDbt==null)
			{
				totalDbt=(double) 0;
			}
			
			if(totalCrd==null)
			{
				totalCrd=(double) 0;
			}
			
			String c= totalDbt+","+totalCrd;
		
			return c;
    	
    }
    
   public List<Account_ledger_v3> bankDatas()  {
   	
   
   	
List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.bankData();
		
	
	return li;	
		
   }
   
   
   
   public List<Account_ledger_v3> selectCustomers()  {
		
		
		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.selectCustomer();
		
		return li;
    }
	

   public List<Account_ledger_v3> selectServices()  {
		
		
		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.selectService();
		
		return li;
    }
   
   
   
     ////////////////////////////////ac_dashboard//////////////////////////////
   
	public List<Account_ledger_v3> ac_dashboardCashData()  {
		
		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.ac_dashboardCashDatas();
		
		
		return li;
	}
   
   
    public List<Account_ledger_v3> ac_dashboardBankData()  {
		
		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.ac_dashboardBankDatas();
		
		
		return li;
	}
   
	
    //////////////////index page////////////////////
    
    
    public List<Account_ledger_v3> index_customer_vendorApi(String grp)  {
		
		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.index_customer_vendorApis(grp);
		
		
		return li;
	}
	
    
  //////////////////Migration Date////////////////////
    
    
    public String migrationDateAdd(String mgrDate)  {
	
    	
		List<Account_ledger_v3> li=(List<Account_ledger_v3>) ledgerServiceRepo.last_id_Search();
		
		if(li.size()>0)
		{
			
			for(int i=0;i<li.size();i++)
			{
				Account_ledger_v3 li1= ledgerServiceRepo.ledger_Search_MigrationDate(li.get(i).getId());
				
				li1.setLedger_date(mgrDate);
				ledgerServiceRepo.save(li1);
				
			}
			
		}
		
		return "Added";
	}
	
	
}
