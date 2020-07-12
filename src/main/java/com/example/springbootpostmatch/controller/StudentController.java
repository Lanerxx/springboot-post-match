package com.example.springbootpostmatch.controller;

import com.example.springbootpostmatch.component.RequestComponent;
import com.example.springbootpostmatch.component.vo.StudentInoVo;
import com.example.springbootpostmatch.entity.Enterprise;
import com.example.springbootpostmatch.entity.Post;
import com.example.springbootpostmatch.entity.Student;
import com.example.springbootpostmatch.service.EnterpriseService;
import com.example.springbootpostmatch.service.StudentService;
import com.example.springbootpostmatch.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student/")
@Slf4j
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private RequestComponent requestComponent;

    @GetMapping("index")
    public Map getIndex(){
        Student student = studentService.getStudent(requestComponent.getUid());
        List<Post> posts = enterpriseService.listPosts();
        return Map.of(
                "student",student,
                "posts",posts
        );
    }

    @GetMapping("match")
    public Map getMatch(){
        Enterprise enterprise = enterpriseService.getEnterprise(requestComponent.getUid());
        List<Student> students = studentService.listStudents();
        return Map.of(
                "enterprise", enterprise,
                "students",students

        );
    }

    @PatchMapping("information")
    public Map updateStudentInformation(@RequestBody StudentInoVo studentInoVo){
         studentService.updateStudent(studentInoVo, requestComponent.getUid());
         Student student = studentService.getStudent(requestComponent.getUid());
        return Map.of(
                "student",student
        );
    }


}
