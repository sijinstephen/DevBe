package com.example.demo.controller.user;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Account_user_v3;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.UserService;

@CrossOrigin(origins = {"http://localhost:5173", "https://your-prod-domain.com"}, maxAge = 3600)
@RestController
@RequestMapping("")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired private UserService userService;
    @Autowired private UserRepo userRepo;

    public static class LoginRequest {
        private String userName;
        private String password;
        public String getUserName() { return userName; }
        public void setUserName(String userName) { this.userName = userName; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    @PostMapping("/login")
    public List<Account_user_v3> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Login attempt for userName={}", loginRequest.getUserName());
        List<Account_user_v3> users = userService.login(loginRequest.getUserName(), loginRequest.getPassword());
        logger.info("Login response size={}", users.size());
        return users;
    }

    @GetMapping("/userSearch")
    public List<Account_user_v3> userSearch(@RequestParam("userName") String userName) {
        return userService.userSearchs(userName);
    }

    @GetMapping("/userList")
    public List<Account_user_v3> userList() {
        return userService.userLists();
    }

    @PostMapping("/addUser")
    public Account_user_v3 addUser(@RequestBody Account_user_v3 body) {
        return userService.addUsers(body);
    }

    @PutMapping("/editUser/{id}")
    public ResponseEntity<Object> editUser(@RequestBody Account_user_v3 body, @PathVariable int id) {
        logger.debug("Editing user id={}, payload={}", id, body);
        Optional<Account_user_v3> existing = userRepo.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        body.setId(id);
        userRepo.save(body);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/userDelete/{id}")
    public String userDelete(@PathVariable int id) {
        return userService.userDeletes(id);
    }

    @GetMapping("/userSearchById")
    public List<Account_user_v3> userSearchById(@RequestParam("id") int id) {
        return userService.userSearchByIds(id);
    }
}