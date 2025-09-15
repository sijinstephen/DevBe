package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.demo.model.Account_user_v3;

public interface UserRepo extends CrudRepository<Account_user_v3, Integer> {
    // Updated: Filter by userName only (password comparison in service)
    String ss1 = "SELECT * FROM account_user_v3 WHERE user_name = ?1";
    @Query(nativeQuery = true, value = ss1)
    List<Account_user_v3> logins(String userName);

    String ss2 = "SELECT * FROM account_user_v3 WHERE user_name = ?1";
    @Query(nativeQuery = true, value = ss2)
    List<Account_user_v3> userSearch(String userName);

    String ss3 = "SELECT * FROM account_user_v3";
    @Query(nativeQuery = true, value = ss3)
    List<Account_user_v3> userList();

    String ss4 = "SELECT * FROM account_user_v3 WHERE id = ?1";
    @Query(nativeQuery = true, value = ss4)
    List<Account_user_v3> userSearchById(int id);
}