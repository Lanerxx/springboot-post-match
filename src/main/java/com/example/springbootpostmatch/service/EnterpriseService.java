package com.example.springbootpostmatch.service;
import com.example.springbootpostmatch.entity.Enterprise;
import com.example.springbootpostmatch.entity.Post;
import com.example.springbootpostmatch.repository.EnterpriseRepository;
import com.example.springbootpostmatch.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class EnterpriseService {
    @Autowired
    private EnterpriseRepository enterpriseRepository;
    @Autowired
    private PostRepository postRepository;

    //---------"Enterprise's CURD "-----------
    public Enterprise addEnterprise(Enterprise enterprise){
        enterpriseRepository.save(enterprise);
        return enterprise;
    }

    public void deleteEnterprise(int id){
        enterpriseRepository.deleteById(id);
    }

    public Enterprise updateEnterprise(Enterprise enterprise){
        enterpriseRepository.save(enterprise);
        return enterprise;
    }

    public Enterprise getEnterpriseByUserNumber(int number){
        return enterpriseRepository.getEnterpriseByUserNumber(number).orElse(null);
    }
    public List<Enterprise> listEnterprises() {
        return enterpriseRepository.findAll();
    }

    public Enterprise getEnterprise(String name){
        return enterpriseRepository.findByName(name).orElse(null);
    }

    public Enterprise getEnterprise(int id) {
        return enterpriseRepository.findById(id).orElse(null);
    }

    //---------"Post's CURD "-----------
    public Post addPost(Post post){
        postRepository.save(post);
        return post;
    }

    public void deletePost(int id){
        postRepository.deleteById(id);
    }

    public Post updatePost(Post post){
        postRepository.save(post);
        return post;
    }

    public List<Post> listPosts() {
        return postRepository.findAll();
    }

    public Post getPost(int id) {
        return postRepository.findById(id).orElse(null);
    }


}
