package com.example.springbootpostmatch.controller;

import com.example.springbootpostmatch.component.vo.EnterpriseInoVo;
import com.example.springbootpostmatch.component.vo.StudentInoVo;
import com.example.springbootpostmatch.entity.Enterprise;
import com.example.springbootpostmatch.entity.Post;
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

    @GetMapping("index")
    public Map getIndex(){
        List<Enterprise> enterprises = enterpriseService.listEnterprises();
        List<Post> posts = enterpriseService.listPosts();
        List<Student> students = studentService.listStudents();
        return Map.of(
                "enterprises",enterprises,
                "posts",posts,
                "students",students
        );
    }

    @PostMapping("enterprise")
    public Map addEnterprise(@Valid @RequestBody Enterprise enterprise){
        Enterprise e = new Enterprise();
        if(enterprise.getName()!=null && enterprise.getPhoneNumber()!=null){
            if (enterpriseService.getEnterprise(enterprise.getPhoneNumber(),enterprise.getName())!=null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "企业已经存在！");
            }
            User u = new User();
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
                "enterprise",e
        );
    }

    @PostMapping("enterprises")
    public Map addEnterprises(@Valid @RequestBody List<Enterprise> enterprises){
        enterprises.forEach(enterprise -> {
            Enterprise e = new Enterprise();
            if(enterprise.getName()!=null && enterprise.getPhoneNumber()!=null){
                if (enterpriseService.getEnterprise(enterprise.getPhoneNumber(),enterprise.getName())!=null){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "企业已经存在！");
                }
                User u = new User();
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
        });
        List<Enterprise> es = enterpriseService.listEnterprises();
        return Map.of(
                "enterprises",es
        );
    }

    //批量注册企业并填入相应信息，实际情况不可用，仅用于为测试添加信息
    @PostMapping("enterprisesInformation")
    public Map addEnterprisesInformation(@Valid @RequestBody List<EnterpriseInoVo> enterpriseInoVos){
        enterpriseInoVos.forEach(enterpriseInoVo -> {
            Enterprise e = new Enterprise();
            Enterprise enterprise = enterpriseInoVo.getEnterprise();
            if(enterprise.getName()!=null && enterprise.getPhoneNumber()!=null){
                if (enterpriseService.getEnterprise(enterprise.getPhoneNumber(),enterprise.getName())!=null){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "企业已经存在！");
                }
                User u = new User();
                u.setRole(User.Role.ENTERPRISE);
                e.setName(enterprise.getName());
                e.setPhoneNumber(enterprise.getPhoneNumber());
                e.setUser(u);
                enterpriseService.addEnterprise(e);
                log.debug("controller :{}+{}", enterprise.getPhoneNumber(),enterprise.getName());
                int id  = enterpriseService.getEnterprise(enterprise.getPhoneNumber(),enterprise.getName()).getId();
                String num = "E" + number + id;
                User u1 = userService.getUserByID(id);
                u1.setNumber(num);
                u1.setPassword(encoder.encode(num));
                userService.updateUser(u1);
                enterpriseService.updateEnterprise(enterpriseInoVo, id);
            }
            else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Name, number cannot be empty.");
            }
        });
        List<Enterprise> es = enterpriseService.listEnterprises();
        return Map.of(
                "enterprises",es
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
        if(student.getName() != null && student.getPhoneNumber()!=null){
            if (studentService.getStudent(student.getPhoneNumber(), student.getName())!=null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "学生已经存在！");
            }
            User u = new User();
            u.setRole(User.Role.STUDENT);
            s.setName(student.getName());
            s.setUser(u);
            s.setExpectedSalary(0);
            s.setPaperCount(0);
            s.setWorkExperience(0);
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
                "student",student
        );
    }

    @PostMapping("students")
    public Map addStudents(@Valid @RequestBody List<Student> students){
        students.forEach(student -> {
            Student s = new Student();
            if(student.getName() != null && student.getPhoneNumber()!=null){
                if (studentService.getStudent(student.getPhoneNumber(), student.getName())!=null){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "学生已经存在！");
                }
                User u = new User();
                u.setRole(User.Role.STUDENT);
                s.setName(student.getName());
                s.setUser(u);
                s.setExpectedSalary(0);
                s.setPaperCount(0);
                s.setWorkExperience(0);
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
        });
        List<Student> ss = studentService.listStudents();
        return Map.of(
                "students",ss
        );
    }

    //批量注册学生并填入相应信息，实际情况不可用，仅用于为测试添加信息
    @PostMapping("studentInformation")
    public Map addStudentsInformation(@Valid @RequestBody List<StudentInoVo> studentInoVos){
        studentInoVos.forEach(studentInoVo -> {
            Student student = studentInoVo.getStudent();
            log.debug("{}+{}", student.getName(),student.getPhoneNumber());
            Student s = new Student();
            if(student.getName() != null && student.getPhoneNumber()!=null){
                if (studentService.getStudent(student.getPhoneNumber(), student.getName())!=null){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "学生已经存在！");
                }
                User u = new User();
                u.setRole(User.Role.STUDENT);
                s.setName(student.getName());
                s.setUser(u);
                s.setExpectedSalary(0);
                s.setPaperCount(0);
                s.setWorkExperience(0);
                s.setPhoneNumber(student.getPhoneNumber());
                studentService.addStudent(s);
                int id = studentService.getStudent(student.getPhoneNumber(),student.getName()).getId();
                String num = "S" + number + id;
                User u1 = userService.getUserByID(id);
                u1.setNumber(num);
                u1.setPassword(encoder.encode(num));
                userService.updateUser(u1);
                studentService.updateStudent(studentInoVo, id);
            }
            else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Name, number cannot be empty.");
            }
        });
        List<Student> ss = studentService.listStudents();
        return Map.of(
                "students",ss
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
