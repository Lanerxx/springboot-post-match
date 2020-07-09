package com.example.springbootpostmatch.repository;

import com.example.springbootpostmatch.entity.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends BaseRepository<Post,Integer>{
}
