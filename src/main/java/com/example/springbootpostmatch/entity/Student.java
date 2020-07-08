package com.example.springbootpostmatch.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Student {
    public enum Gender {
        MALE, FEMALE
    }
    public enum SchoolRank{
        NONE,

    }
    public enum Education{
        NONE,
        PRIMARY_SCHOOL_ADUCATION,JUNIOR_HIGH_SCHOOL_ADUCATION,
        TECHNICAL_SECONDARY_SCHOOL_ADUCATION,
        HIGH_SCHOOL_ADUCATION,JUNIOR_COLLEGE_EDUCATION,
        BACHELOR_DEGREE,MASTER_DEGREE,DOCTOR_DEGREE,
        POSTDOCTORAL_DEGREE,ACADEMICIAN_EDUCATION
    }
    public enum ForeignLanguageProficiency{
        NONE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double weightedGrade;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @MapsId
    private User user;
    private String name;
    private Gender gender;
    private LocalDate birthday;
    private String graduationSchoolName;
    private SchoolRank graduationSchoolRank;
    private String major;
    private Education education;
    private LocalDate graduationDate;
    private String nativePlace;
    private ForeignLanguageProficiency foreignlanguageproficiency;
    private int expectedSalary;
    private String expectedPosition;
    private String expectedIndustry;
    private String employmentIntentionPlace;
    private int phoneNumber;
    private String majorCourse;
    private String skill;
    private int paperCount;
    private int workExperience;


    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;
    @Column(columnDefinition = "timestamp default current_timestamp " +
            "on update current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime updateTime;
}
