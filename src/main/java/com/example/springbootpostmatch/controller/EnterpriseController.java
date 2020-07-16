package com.example.springbootpostmatch.controller;

import com.example.springbootpostmatch.component.RequestComponent;
import com.example.springbootpostmatch.component.vo.EnterpriseInoVo;
import com.example.springbootpostmatch.component.vo.PostVo;
import com.example.springbootpostmatch.component.vo.StudentInoVo;
import com.example.springbootpostmatch.component.vo.StudentMatchVo;
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
        EnterpriseInoVo enterpriseInoVo = enterpriseService.getEnterpriseInoVo(eid);
        List<Post> posts = enterpriseService.listPost(eid);
        return Map.of(
                "enterprise", enterpriseInoVo,
                "posts",posts

        );
    }

    @GetMapping("match/post/{pid}")
    public Map getMatch(@PathVariable int pid){
        Enterprise enterprise = enterpriseService.getEnterprise(requestComponent.getUid());
        List<StudentMatchVo> studentMatchVos = enterpriseService.postMatch(pid,enterprise);
        return Map.of(
                "enterprise", enterprise,
                "students",studentMatchVos

        );
    }

    @PatchMapping("information")
    public Map updateEnterpriseInformation(@RequestBody EnterpriseInoVo enterpriseInoVo){
        log.debug("{}", enterpriseInoVo.getEducationCut());
        enterpriseService.updateEnterprise(enterpriseInoVo, requestComponent.getUid());
        EnterpriseInoVo enterpriseInoVo1 = enterpriseService.getEnterpriseInoVo(requestComponent.getUid());

        return Map.of(
                "enterprise",enterpriseInoVo1
        );
    }

    //post
    @PostMapping("post")
    public Map addPost(@Valid @RequestBody PostVo postVo){
        log.debug("{}", "asdasdas");
        log.debug("{}", postVo.getPost().getCount());
        log.debug("{}", postVo.getPost().getName());
        log.debug("{}", postVo.getStartTime());
        log.debug("{}", postVo.getEndTime());
        Post p = new Post();
        Post post = postVo.getPost();
        int eid = requestComponent.getUid();
        Enterprise e = enterpriseService.getEnterprise(eid);
        if (post.getName()!=null && postVo.getStartTime() !=null && postVo.getEndTime()!=null){
            if (enterpriseService.getPost(eid, post.getName())!=null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "你要添加的岗位已存在。");
            }
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
        List<Post> ps = enterpriseService.listPost(eid);
        return Map.of(
                "posts",ps
        );
    }

    @PostMapping("postsInformation")
    public Map addPostsInformation(@Valid @RequestBody List<PostVo> postVos){
        postVos.forEach(postVo -> {
            Post p = new Post();
            Post post = postVo.getPost();
            int eid = requestComponent.getUid();
            Enterprise e = enterpriseService.getEnterprise(eid);
            if (post.getName()!=null && postVo.getStartTime() !=null && postVo.getEndTime()!=null){
                if (enterpriseService.getPost(eid, post.getName())!=null){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "你要添加的岗位已存在。");
                }
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
        });
        List<Post> ps = enterpriseService.listPost(requestComponent.getUid());
        return Map.of(
                "posts",ps
        );
    }

    @DeleteMapping("post/{pid}")
    public Map deletePost(@PathVariable int pid){

        if (enterpriseService.getPost(pid)==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The Post you want to delete does not exist.");
        }
        enterpriseService.deletePost(pid);
        List<Post> ps = enterpriseService.listPost(requestComponent.getUid());
        return Map.of(
                "posts",ps
        );
    }

    @GetMapping("posts")
    public Map listPosts(){
        List<Post> posts = enterpriseService.listPost(requestComponent.getUid());
        return Map.of(
                "posts",posts
        );
    }

    @PatchMapping("post/information/{pid}")
    public Map updatePostInformation(@RequestBody PostVo postVo,@PathVariable int pid){
        enterpriseService.updatePost(postVo, pid);
        List<Post> ps = enterpriseService.listPost(requestComponent.getUid());
        return Map.of(
                "posts",ps
        );
    }

    @GetMapping("downloadResume/{sid}")
    public Map downloadResume(@PathVariable int sid){
        boolean flag = studentService.downloadResume(sid);
        String fileName = "./file/"+ studentService.getStudent(sid).getName()+ ".xls";
        return Map.of(
                "flag",flag,
                "fileName",fileName
        );
    }
}
