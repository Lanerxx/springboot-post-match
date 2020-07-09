package com.example.springbootpostmatch.controller;

import com.example.springbootpostmatch.component.EncryptComponent;
import com.example.springbootpostmatch.component.MyToken;
import com.example.springbootpostmatch.entity.User;
import com.example.springbootpostmatch.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
public class LoginController {
    @Value("${my.admin}")
    private String roleAdmin;
    @Value("${my.enterprise}")
    private String roleEnterprise;
    @Value("${my.student}")
    private String roleStudent;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private EncryptComponent encrypt;

    @PostMapping("login")
    public Map login(@RequestBody User login, HttpServletResponse response) {
        User user = Optional.ofNullable(userService.getUserByNumber(login.getNumber()))
                .filter(u -> encoder.matches(login.getPassword(), u.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong username and password"));

//        User user = Optional.ofNullable(userService.getUserByName(login.getName()))
//                .filter(u -> encoder.matches(login.getPassword(), u.getPassword()))
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong username and password"));

        MyToken token = new MyToken(user.getId(), user.getRole());
        String auth = encrypt.encryptToken(token);
        response.setHeader(MyToken.AUTHORIZATION, auth);
        String roleCode = "";
        if(user.getRole() == User.Role.STUDENT){
            roleCode = roleStudent;
        }
        else if(user.getRole() == User.Role.ENTERPRISE){
            roleCode = roleEnterprise;
        }
        else if(user.getRole() == User.Role.ADMIN){
            roleCode = roleAdmin;
        }
        return Map.of(
                "role",roleCode
        );
    }
}
