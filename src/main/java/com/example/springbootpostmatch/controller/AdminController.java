package com.example.springbootpostmatch.controller;

import com.example.springbootpostmatch.entity.Enterprise;
import com.example.springbootpostmatch.entity.Student;
import com.example.springbootpostmatch.entity.User;
import com.example.springbootpostmatch.service.EnterpriseService;
import com.example.springbootpostmatch.service.StudentService;
import com.example.springbootpostmatch.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
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
    private StudentService studentService;
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

    @DeleteMapping("enterprise/{eid}")
    public Map deleteEnterprise(@PathVariable int eid){
        if(enterpriseService.getEnterprise(eid)==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The Enterprise you want to delete does not exist.");
        }
        enterpriseService.deleteEnterprise(eid);
        return Map.of("massage", "Successful delete!");
    }

    @GetMapping("enterprises")
    public Map listEnterprises(){
        List<Enterprise> enterprises = enterpriseService.listEnterprises();
        return Map.of(
                "enterprises",enterprises
        );
    }


    @PostMapping("student")
    public Map addStudent(@Valid @RequestBody Student student){
        Student s = new Student();
        if(student.getName() != null){
            User u = new User();
            u.setRole(User.Role.STUDENT);
            s.setName(student.getName());
            s.setUser(u);
            s.setExpectedSalary(0);
            s.setPaperCount(0);
            s.setWorkExperience(0);
            studentService.addStudent(s);
            int id = studentService.getStudent(student.getName()).getId();
            String num = "S" + number + id;
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
                "student",student
        );
    }

    @DeleteMapping("student/{sid}")
    public Map deleteStudent(@PathVariable int sid){
        if(studentService.getStudent(sid)==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The Student you want to delete does not exist.");
        }
        studentService.deleteStudent(sid);
        return Map.of("massage", "Successful delete!");
    }

    @GetMapping("students")
    public Map listStudents(){
        List<Student> students = studentService.listStudents();
        return Map.of(
                "students",students
        );
    }
}
