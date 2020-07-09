package com.example.springbootpostmatch.repository;

import com.example.springbootpostmatch.entity.Enterprise;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnterpriseRepository extends BaseRepository<Enterprise,Integer> {
    @Query("SELECT ent FROM Enterprise ent")
    Optional<List<Enterprise>> list();

    Optional<Enterprise> findById (int  id);
    Optional<Enterprise> findByName(String name);
    @Query("SELECT e FROM Enterprise  e WHERE e.user.number=:number")
    Optional<Enterprise> getEnterpriseByUserNumber (@Param("number")int number);

    void deleteById(int id);
}
