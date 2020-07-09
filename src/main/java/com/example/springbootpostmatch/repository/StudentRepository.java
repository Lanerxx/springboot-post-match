package com.example.springbootpostmatch.repository;

import com.example.springbootpostmatch.entity.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends BaseRepository<Student, Integer> {
    @Query("SELECT stu FROM Student stu")
    Optional<List<Student>> list();

    Optional<Student> findById (int  id);
    Optional<Student> findByName(String name);

    @Query("SELECT s FROM Student  s WHERE s.user.number=:number")
    Optional<Student> getStudentsByUserNumber (@Param("number")int number);


    void deleteById(int id);
}
