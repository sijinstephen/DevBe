package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.model.Account_user_v3;
import com.example.demo.repository.UserRepo;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${app.password.mode:plain-text}")
    private String passwordMode;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<Account_user_v3> login(String userName, String password) {
        logger.info("Login attempt for user: {}", userName);
        List<Account_user_v3> users = userRepo.logins(userName);
        List<Account_user_v3> matchedUsers;

        if ("bcrypt".equals(passwordMode)) {
            matchedUsers = users.stream()
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .collect(Collectors.toList());
        } else {
            matchedUsers = users.stream()
                .filter(user -> password.equals(user.getPassword()))
                .collect(Collectors.toList());
        }

        if (matchedUsers.isEmpty()) {
            logger.warn("Login failed for user: {}", userName);
        } else {
            logger.info("Login successful for user: {}, mode: {}", userName, passwordMode);
        }
        return matchedUsers;
    }

    public Account_user_v3 addUsers(Account_user_v3 fp) {
        logger.info("Adding user: {}", fp.getUser_name());
        if ("bcrypt".equals(passwordMode)) {
            fp.setPassword(passwordEncoder.encode(fp.getPassword()));
        } else {
            logger.warn("Plain-text password for dev: {}", fp.getPassword());
        }
        Account_user_v3 savedUser = userRepo.save(fp);
        logger.info("User added: {}", fp.getUser_name());
        return savedUser;
    }

    public List<Account_user_v3> userSearchs(String userName) {
        return (List<Account_user_v3>) userRepo.userSearch(userName);
    }

    public List<Account_user_v3> userLists() {
        return (List<Account_user_v3>) userRepo.userList();
    }

    public String userDeletes(int id) {
        userRepo.deleteById(id);
        return "Deleted successfully";
    }

    public List<Account_user_v3> userSearchByIds(int id) {
        return (List<Account_user_v3>) userRepo.userSearchById(id);
    }
}