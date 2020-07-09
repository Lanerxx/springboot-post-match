package com.example.springbootpostmatch.controller;

import com.example.springbootpostmatch.entity.Enterprise;
import com.example.springbootpostmatch.entity.User;
import com.example.springbootpostmatch.service.EnterpriseService;
import com.example.springbootpostmatch.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/")
@Slf4j
public class AdminController {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private UserService userService;
    @Value("${my.number}")
    private int number;


    @PostMapping("enterprise")
    public Map addEnterprise(@Valid @RequestBody Enterprise enterprise){
        Enterprise e = new Enterprise();
        if(enterprise.getName()!=null){
            User u = new User();
            u.setRole(User.Role.ENTERPRISE);
            e.setName(enterprise.getName());
            e.setUser(u);
            enterpriseService.addEnterprise(e);
            if (enterpriseService.getEnterprise(enterprise.getName()) == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "不能寸完马上取数据.");
            }
            int id  = enterpriseService.getEnterprise(enterprise.getName()).getId();
            String num = "T" + number + id;
            User u1 = userService.getUserByID(id);
            u1.setNumber(num);
            u1.setPassword(encoder.encode(num));
            userService.updateUser(u1);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Name, number cannot be empty.");
        }
        return Map.of(
                "enterprise",e
        );
    }

//    @DeleteMapping("tutor/{tid}")
//    public Map deleteTotur(@PathVariable int tid){
//        if(userService.getTutorById(tid)==null){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                    "The tutor you want to delete does not exist.");
//        }
//        userService.deletTutor(tid);
//        return Map.of("massage", "Successful delete!");
//    }
//
//    @PostMapping("student")
//    public Map addStudent(@Valid @RequestBody User user){
//        Student student = new Student();
//        if(user.getNumber() != null && user.getName() != null){
//            User u = new User();
//            u.setNumber(user.getNumber());
//            u.setName(user.getName());
//            u.setPassword(encoder.encode(String.valueOf(user.getNumber())));
//            u.setRole(User.Role.TUTOR);
//            student.setUser(u);
//            userService.addStudent(student);
//        }
//        else{
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                    "Name, number cannot be empty.");
//        }
//        return Map.of(
//                "student",student
//        );
//    }
}
