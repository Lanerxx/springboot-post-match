package com.example.springbootpostmatch.component;

import com.example.springbootpostmatch.entity.Admin;
import com.example.springbootpostmatch.entity.User;
import com.example.springbootpostmatch.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitializingComponent implements InitializingBean {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserService userService;

    @Override
    public void afterPropertiesSet() throws Exception {
        final String number = "A10000";
        final String Name = "LANER";
        User user = userService.getUserByNumber(number);
        if (user == null) {
            User u = new User();
            u.setNumber(number);
            u.setRole(User.Role.ADMIN);
            u.setPassword(encoder.encode(number));
            Admin admin = new Admin();
            admin.setName(Name);
            admin.setUser(u);
            userService.addAdmin(admin);
        }
    }
}
