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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/register/")
@Slf4j
public class RegisterController {
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
    public Map registerEnterprise(@Valid @RequestBody Enterprise enterprise){
        Enterprise e = new Enterprise();
        User u = new User();
        if(enterprise.getName()!=null && enterprise.getPhoneNumber()!=null){
            if (enterpriseService.getEnterprise(enterprise.getPhoneNumber(),enterprise.getName())!=null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "该企业名和电话号码已注册！");
            }
            u.setRole(User.Role.ENTERPRISE);
            e.setName(enterprise.getName());
            e.setPhoneNumber(enterprise.getPhoneNumber());
            e.setUser(u);
            enterpriseService.addEnterprise(e);
            int id  = enterpriseService.getEnterprise(enterprise.getPhoneNumber(),enterprise.getName()).getId();
            String num = "E" + number + id;
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
                "user",u
        );
    }

    @PostMapping("student")
    public Map registerStudent(@Valid @RequestBody Student student){
        Student s = new Student();
        User u = new User();
        if(student.getName() != null && student.getPhoneNumber()!=null){
            if (studentService.getStudent(student.getPhoneNumber(), student.getName())!=null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "该学生名和电话号码已注册！");
            }
            u.setRole(User.Role.STUDENT);
            s.setName(student.getName());
            s.setUser(u);
            s.setExpectedSalary(0);
            s.setPaperCount(0);
            s.setWorkExperience(0);
            s.setGrade(0);
            s.setGraduationDate(0);
            s.setPhoneNumber(student.getPhoneNumber());
            studentService.addStudent(s);
            int id = studentService.getStudent(student.getPhoneNumber(),student.getName()).getId();
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
                "user",u
        );
    }

}
