package com.example.springbootpostmatch.repository;

import com.example.springbootpostmatch.entity.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends BaseRepository<Post,Integer>{

    @Query("SELECT post FROM Post post")
    Optional<List<Post>> list();

    Optional<Post> findById (int  id);

    void deleteById(int id);
}
