package com.example.demo.repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.demo.model.Account_group_v3;
import com.example.demo.model.Account_title_v3;
public interface AcTitleRepo extends CrudRepository<Account_title_v3,Integer>{
String ss1="select ac_title from account_title_v3 where ac_id = ?1";
	@Query(nativeQuery =true, value=ss1)
	String selectTitle(String ac_id);
String ss2="select ac_title from account_title_v3 where ac_id = ?1";
	@Query(nativeQuery =true, value=ss2)
	String selectAcStatementTitle(String ac_id);	
String ss3="select * from account_title_v3 where ac_id = ?1";
	@Query(nativeQuery =true, value=ss3)
	List<Account_title_v3> acTitle_searchs(String ac_id);	
}
