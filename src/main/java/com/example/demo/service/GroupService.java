package com.example.demo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Account_group_v3;
import com.example.demo.repository.AcTitleRepo;
import com.example.demo.repository.AcTypeRepo;
import com.example.demo.repository.GroupServiceRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jsonCreate.GroupJsonView;




@Service
public class GroupService {
	
	@Autowired
	private GroupServiceRepo groupServiceRepo;
	
	@Autowired
	private AcTitleRepo acTitleRepo;
	
	
	@Autowired
	private AcTypeRepo acTypeRepo;
	
	
	
	public Account_group_v3 add_Groups(Account_group_v3 fp) {
		

		return groupServiceRepo.save(fp);
	}
	
	
	
	public List<Account_group_v3> view_Groups()  {
		
		
				List<Account_group_v3> li=(List<Account_group_v3>) groupServiceRepo.selectData();
				
				Iterator<Account_group_v3> it = li.iterator(); 
				  
			    while (it.hasNext()) { 
			    	
			    	Account_group_v3 ob = it.next(); 
			    	
			    	System.out.println(ob.getAc_title());
			    	
			    	String acTitle = (String) acTitleRepo.selectTitle(ob.getAc_title());
			    	
			    	System.out.println(acTitle);
			    	
			    	ob.setAc_title(acTitle);
			    	
			    	String acType = (String) acTypeRepo.selectType(ob.getAc_type());
			    	
			    	ob.setAc_type(acType);
//
//			    	if(ob.getFp_type().equals("Income"))
//			    	{
//			    		totIncome=totIncome+Float.parseFloat(ob.getFp_amount());
//			    		
//			    		
//			    	
//			    		if(ob.getFp_data_head().equals("Cash received from MD"))
//			    		{
//			    			totIncMD=totIncMD+Float.parseFloat(ob.getFp_amount());
//			    			
//			    		}
//			    		else
//			    		{
//			    			if(ob.getFp_status().equals("close"))
//			    			{
//			    			
//			    			totIncCustomer=totIncCustomer+Float.parseFloat(ob.getFp_amount());
//			    			}
//			    		}
//			    		
//			    	}
//			    	
//			    	
//			    	
//			    	if(ob.getFp_type().equals("Expence"))
//			    	{
//			    		totExpence=totExpence+Float.parseFloat(ob.getFp_amount());
//			    		
//			    		
//			    		
//			    		if(ob.getFp_data_head().equals("Cash paid to MD"))
//			    		{
//			    			totExpMD=totExpMD+Float.parseFloat(ob.getFp_amount());
//			    			
//			    		}
//			    		else
//			    		{
//			    			if(ob.getFp_status().equals("close"))
//			    			{
//			    			
//			    			
//			    			totExpCustomer=totExpCustomer+Float.parseFloat(ob.getFp_amount());
//			    		
//			    			}
//			    		}
//			    	}
			    	
			    	
			    	
			    	 
//					List<FinancialPlannHead> list=(List<FinancialPlannHead>) fpRepo.findAll();
//					    
//					Iterator<FinancialPlannHead> it1 = list.iterator(); 
//					  
//					int h2=0,h3=0;
//				    
//				    while (it1.hasNext()) { 
//				    	FinancialPlannHead ob1 = it1.next(); 
//				    	
//				    	
//				    	if(ob.getFp_data_head().equals(ob1.getFp_head()))
//				    	{
//				    		
//				    	 headTot=Integer.parseInt(ob.getFp_amount());
//				    	 h2=ob1.getHead_total();
//				    	 h3=h2+headTot;
//				    		ob1.setHead_total(h3);
//				    		
//				    		//System.out.println(ob.getFp_data_head()+" "+ob1.getFp_head());
//				    		System.out.println(ob.getFp_data_head()+" "+ob1.getHead_total()); 
//				    	}
//				   
//				   
//				    
//				    }
//				    
//			    	
//				   
//				    headTot=0;
			    	
			    	
			    
			    
			    
//			    	flag=1;
//			    	
//			    	
//			    
//			    	System.out.println(totIncome);
//			    	System.out.println(totIncMD);
//			    	System.out.println(totIncCustomer);
//			    	
//			    	balIncDue=totIncome-(totIncMD+totIncCustomer);
//			    	
//			    	System.out.println(balIncDue);
//			    	
//			    	System.out.println(totExpence);
//			    	System.out.println(totExpMD);
//			    	System.out.println(totExpCustomer);
//			    	
//			    	balExpDue=totExpence-(totExpMD+totExpCustomer);
//			    	
//			    	System.out.println(balExpDue);
			    	
			
			    	// TODO Auto-generated method stub
			//		 ObjectMapper objectMapper = new ObjectMapper();
					    //Set pretty printing of json
				//	    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

					    //Define map which will be converted to JSON
//					    List<GroupJsonView> personList1 = Stream.of(
//					            new GroupJsonView(ob.getGroup_id(),ob.getAc_title(),ob.getGroup_name(),ob.getAc_type())
//					           
//					       
//					           
//					            ).collect(Collectors.toList());
//			    	
//			    	
//					     arrayToJson = objectMapper.writeValueAsString(personList1);
//			    	
					    
			    }
					    	    
					
				return li;
			 
			}
	
	
	
	
	public List<Account_group_v3> grp_sorts(String field, String type)  {
		
		System.out.println(field+' '+type);
		
		List<Account_group_v3> li = null;
		
		
		if(field.equals("ac_title")&&type.equals("ASC"))
		{
			 li=(List<Account_group_v3>) groupServiceRepo.ac_titleA();
		}
		
		if(field.equals("ac_title")&&type.equals("DESC"))
		{
			 li=(List<Account_group_v3>) groupServiceRepo.ac_titleD();
		}
		
		if(field.equals("group_name")&&type.equals("ASC"))
		{
			 li=(List<Account_group_v3>) groupServiceRepo.group_nameA();
		}
		
		if(field.equals("group_name")&&type.equals("DESC"))
		{
			 li=(List<Account_group_v3>) groupServiceRepo.group_nameD();
		}
		
		if(field.equals("under")&&type.equals("ASC"))
		{
			 li=(List<Account_group_v3>) groupServiceRepo.ac_typeA();
		}
		
		if(field.equals("under")&&type.equals("DESC"))
		{
			 li=(List<Account_group_v3>) groupServiceRepo.ac_typeD();
		}
		
		
		Iterator<Account_group_v3> it = li.iterator(); 
		  
	    while (it.hasNext()) { 
	    	
	    	Account_group_v3 ob = it.next(); 
	    	
	    	System.out.println(ob.getAc_title());
	    	
	    	String acTitle = (String) acTitleRepo.selectTitle(ob.getAc_title());
	    	
	    	System.out.println(acTitle);
	    	
	    	ob.setAc_title(acTitle);
	    	
	    	String acType = (String) acTypeRepo.selectType(ob.getAc_type());
	    	
	    	ob.setAc_type(acType);
	    }
		
		
		return li;
	}
	
	
	
	
	public List<Account_group_v3> grp_idSearchs(String grpId)  {
	
		List<Account_group_v3> li=(List<Account_group_v3>) groupServiceRepo.grp_id_Search(grpId);
		
		
		return li;
	}
	
	
	public String grp_deletes(int id) {
		
		groupServiceRepo.deleteById(id);
		// TODO Auto-generated method stub
		return "Deleted successfully";
	}

	public List<Account_group_v3> grp_nameSearchs(String grpName)  {
		System.out.println(grpName);
		
		List<Account_group_v3> li=(List<Account_group_v3>) groupServiceRepo.grp_name_searchs(grpName);
		
		
		return li;
	}
	
	

}
