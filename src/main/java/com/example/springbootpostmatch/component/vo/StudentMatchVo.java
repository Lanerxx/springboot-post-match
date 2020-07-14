package com.example.springbootpostmatch.component.vo;

import com.example.springbootpostmatch.entity.Student;
import lombok.Data;

@Data
public class StudentMatchVo {
    Student student;
    double score;
}
