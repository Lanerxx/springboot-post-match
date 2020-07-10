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

    @Query("SELECT e FROM Enterprise  e WHERE e.user.number=:number")
    Optional<Enterprise> getEnterpriseByUserNumber (@Param("number")int number);

    @Query("SELECT e FROM Enterprise  e WHERE e.phoneNumber=:phoneNumber AND e.name=:name")
    Optional<Enterprise> getEnterpriseByPhoneNumberAndName (@Param("phoneNumber")String phoneNumber,@Param("name")String name);


    void deleteById(int id);
}
