package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Account_user_v3;
import com.example.demo.model.Profile;
import com.example.demo.repository.AcTitleRepo;
import com.example.demo.repository.UserRepo;



@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	public String login(String userName,String password) {
		// TODO Auto-generated method stub
		
		String result;
		
		List<Account_user_v3> li=(List<Account_user_v3>) userRepo.logins(userName,password);
		
		if(li.size()>0)
		{
			result="success";
			
		}
		else
		{
			result="not success";
		}
		
		return result;
	}
	
	
	public List<Account_user_v3> userSearchs(String userName) {
	
		List<Account_user_v3> li=(List<Account_user_v3>) userRepo.userSearch(userName);
		
		return li;
	}
		
	
	public List<Account_user_v3> userLists() {
		
		List<Account_user_v3> li=(List<Account_user_v3>) userRepo.userList();
		
		return li;
	}
	
	 public Account_user_v3 addUsers(Account_user_v3 fp) {
			
		 userRepo.save(fp);
		   	    
			return null;
		}
	
	
	 
		public String userDeletes(int id) {
			
			userRepo.deleteById(id);
			// TODO Auto-generated method stub
			return "Deleted successfully";
		}
		
		
		public List<Account_user_v3> userSearchByIds(int id) {
			
			List<Account_user_v3> li=(List<Account_user_v3>) userRepo.userSearchById(id);
			
			return li;
		}
			
	
}
