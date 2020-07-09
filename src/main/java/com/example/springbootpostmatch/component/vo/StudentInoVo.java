package com.example.springbootpostmatch.component.vo;

import com.example.springbootpostmatch.entity.Student;
import lombok.Data;

@Data
public class StudentInoVo {
    private Student student;
    private String gender;
    private String SchoolRank;
    private String Education;
    private String ForeignLanguageProficiency;
}
