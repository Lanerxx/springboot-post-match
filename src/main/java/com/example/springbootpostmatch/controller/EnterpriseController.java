package com.example.springbootpostmatch.controller;

import com.example.springbootpostmatch.component.RequestComponent;
import com.example.springbootpostmatch.component.vo.EnterpriseInoVo;
import com.example.springbootpostmatch.component.vo.PostVo;
import com.example.springbootpostmatch.component.vo.StudentInoVo;
import com.example.springbootpostmatch.entity.Enterprise;
import com.example.springbootpostmatch.entity.Post;
import com.example.springbootpostmatch.entity.Student;
import com.example.springbootpostmatch.service.EnterpriseService;
import com.example.springbootpostmatch.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        int eid = requestComponent.getUid();
        Enterprise enterprise = enterpriseService.getEnterprise(eid);
        List<Post> posts = enterpriseService.listPost(eid);
        return Map.of(
                "enterprise", enterprise,
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
    public Map updateEnterpriseInformation(@RequestBody EnterpriseInoVo enterpriseInoVo){
        enterpriseService.updateEnterprise(enterpriseInoVo, requestComponent.getUid());
        Enterprise enterprise = enterpriseService.getEnterprise(requestComponent.getUid());
        return Map.of(
                "enterprise",enterprise
        );
    }

    //post
    @PostMapping("post")
    public Map addPost(@Valid @RequestBody PostVo postVo){
        Post p = new Post();
        Post post = postVo.getPost();
        Enterprise e = enterpriseService.getEnterprise(requestComponent.getUid());
        if (post.getName()!=null && postVo.getStartTime() !=null && postVo.getEndTime()!=null){
            p.setName(post.getName());
            p.setEnterprise(e);

            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startTime = LocalDateTime.parse(postVo.getStartTime(), df);
            LocalDateTime endTime = LocalDateTime.parse(postVo.getEndTime(), df);
            p.setStartTime(startTime);
            p.setEndTime(endTime);

            if (post.getDetail()!=null) p.setDetail(post.getDetail());
            if (post.getSalary()>0) p.setSalary(post.getSalary());
            else p.setSalary(0);
            if (post.getCount()>0) p.setCount(post.getCount());
            else p.setCount(0);
            enterpriseService.addPost(p);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Name, StartTime, EndTime cannot be empty.");
        }
        return Map.of(
                "post",p
        );
    }

    @DeleteMapping("post/{pid}")
    public Map deleteEnterprise(@PathVariable int pid){

        if (enterpriseService.getPost(pid)==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The Post you want to delete does not exist.");
        }
        enterpriseService.deletePost(pid);
        return Map.of("massage", "Successful delete!");
    }

    @GetMapping("posts")
    public Map listPosts(){
        List<Post> posts = enterpriseService.listPosts();
        return Map.of(
                "posts",posts
        );
    }

    @PatchMapping("post/information/{pid}")
    public Map updatePostInformation(@RequestBody PostVo postVo,@PathVariable int pid){
        enterpriseService.updatePost(postVo, pid);
        Post p = enterpriseService.getPost(pid);
        return Map.of(
                "post",p
        );
    }
}
