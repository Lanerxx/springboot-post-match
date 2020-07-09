package com.example.springbootpostmatch.service;
import com.example.springbootpostmatch.entity.Student;
import com.example.springbootpostmatch.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    //---------"Student's CURD "-----------
    public Student addStudent(Student student){
        studentRepository.save(student);
        return student;
    }

    public void deleteStudent(int id){
        studentRepository.deleteById(id);
    }

    public Student updateStudent(Student student){
        studentRepository.save(student);
        return student;
    }

    public Student getStudentByUserNumber(int number){
        return studentRepository.getStudentsByUserNumber(number).orElse(null);
    }
    public List<Student> listStudents() {
        return studentRepository.findAll();
    }
    //Because the User and Student share the primary key，you can input an Id from the User as well as an Id from the Student
    public Student getStudent(int id) {
        return studentRepository.findById(id).orElse(null);
    }
}
