package com.example.springbootpostmatch.controller;

import com.example.springbootpostmatch.component.EncryptComponent;
import com.example.springbootpostmatch.component.RequestComponent;
import com.example.springbootpostmatch.component.vo.PasswordVo;
import com.example.springbootpostmatch.component.vo.StudentInoVo;
import com.example.springbootpostmatch.entity.Enterprise;
import com.example.springbootpostmatch.entity.Student;
import com.example.springbootpostmatch.entity.User;
import com.example.springbootpostmatch.service.EnterpriseService;
import com.example.springbootpostmatch.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/user/")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RequestComponent requestComponent;
    @Autowired
    private PasswordEncoder encoder;


    @PatchMapping("password")
    public Map updateStudentInformation(@RequestBody PasswordVo passwordVo){
        log.debug("{}",passwordVo);
        String oldPwd = passwordVo.getOldPassword();
        String newPwd = passwordVo.getNewPassword();
        String newPwdR = passwordVo.getNewPasswordR();
        log.debug("{} / {} / {}",oldPwd,newPwd,newPwdR);
        User user = userService.getUserByID(requestComponent.getUid());
        if (user==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "该用户不存在。");
        }else if (oldPwd==null || newPwd==null ||newPwdR ==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您输入的数据不完整。");
        } else if(!newPwd.equals(newPwdR)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您两次输入的新密码不一致。");
        }else if(!encoder.matches(oldPwd, user.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您输入的旧密码错误。");
        }else {
            userService.updateUserPassword(requestComponent.getUid(), newPwd);
        }
        return Map.of(
                "user",user
        );
    }
}
