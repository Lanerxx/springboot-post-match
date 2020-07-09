package com.example.springbootpostmatch.repository;

import com.example.springbootpostmatch.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User,Integer>{
    Optional<User> findByNumber(String number);

    @Modifying
    @Query("UPDATE User u SET u.password=:password WHERE u.id=:id")
    int updatePassword(@Param("password") String password, @Param("id") int id);

}
