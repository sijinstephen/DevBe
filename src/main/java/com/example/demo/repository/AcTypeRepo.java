package com.example.demo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.example.demo.model.Account_type_v3;

public interface AcTypeRepo extends CrudRepository<Account_type_v3,Integer> {
	
String ss1="select ac_type from account_type_v3 where ac_type_id = ?1";
	
	@Query(nativeQuery =true, value=ss1)
	String selectType(String string);
	
	

}
