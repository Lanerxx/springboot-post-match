package com.example.springbootpostmatch.service;

import com.example.springbootpostmatch.entity.Admin;
import com.example.springbootpostmatch.entity.User;
import com.example.springbootpostmatch.repository.AdminRepository;
import com.example.springbootpostmatch.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserService userService;


    //---------"User's CURD "-----------
    public User addUser(User user){
        userRepository.save(user);
        return user;
    }
    public User updateUserPassword(int id, String password){
        User u = userService.getUserByID(id);
        u.setPassword(encoder.encode(password));
        userRepository.save(u);
        return u;
    }
    public User getUserByID(int id){
        return userRepository.findById(id).orElse(null);
    }
    public User getUserByNumber(String number) {
        return  userRepository.findByNumber(number).orElse(null);
    }
    public User updateUser(User user){
        userRepository.save(user);
        return user;
    }
    //---------"Admin's CURD "-----------
    public Admin addAdmin(Admin admin){
        adminRepository.save(admin);
        return admin;
    }
    public Admin getAdmin(int id){
        Admin admin = adminRepository.findById(id).orElse(null);
        return admin;
    }



}
