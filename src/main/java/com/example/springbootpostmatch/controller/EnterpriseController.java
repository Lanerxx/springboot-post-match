package com.example.springbootpostmatch.controller;

import com.example.springbootpostmatch.component.RequestComponent;
import com.example.springbootpostmatch.component.vo.EnterpriseInoVo;
import com.example.springbootpostmatch.component.vo.StudentInoVo;
import com.example.springbootpostmatch.entity.Enterprise;
import com.example.springbootpostmatch.entity.Student;
import com.example.springbootpostmatch.service.EnterpriseService;
import com.example.springbootpostmatch.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enterprise/")
@Slf4j
public class EnterpriseController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private RequestComponent requestComponent;

    @GetMapping("index")
    public Map getIndex(){
        Enterprise enterprise = enterpriseService.getEnterprise(requestComponent.getUid());
        List<Student> students = studentService.listStudents();
        return Map.of(
                "enterprise", enterprise,
                "students",students

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
    public Map updateEnterpriseInformation(@RequestBody EnterpriseInoVo enterpriseInoVo){
        enterpriseService.updateEnterprise(enterpriseInoVo, requestComponent.getUid());
        Enterprise enterprise = enterpriseService.getEnterprise(requestComponent.getUid());
        return Map.of(
                "enterprise",enterprise
        );
    }
}
