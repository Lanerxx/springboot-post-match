package com.example.springbootpostmatch.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Student {
    public enum Gender {
        MALE, FEMALE,NONE
    }
    public enum SchoolRank{
        _985,_211,PROVINCIAL_KEY,GENERAL_UNDERGRADUATE,
        JUNIOR_COLLEGE,HIGHER_VOCATIONAL_COLLEGE,OTHER
    }
    public enum Education{
        DOCTOR,MASTER,BACHELOR,HIGHER_VOCATIONAL_COLLEGE,OTHER
    }
    public enum ForeignLanguageProficiency{
        ENGLISH_Foreign_Exchange_Experience,ENGLISH_CET_6,ENGLISH_CET_4,NONE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @MapsId
    private User user;
    @NotNull
    private String name;
    private Gender gender;
    private float grade;
    private String graduationSchoolName;
    private SchoolRank graduationSchoolRank;
    private String major;
    private Education education;
    private int graduationDate;
    private String nativePlace;
    private ForeignLanguageProficiency foreignlanguageproficiency;
    private int expectedSalary;
    private String expectedPosition;
    private String expectedIndustry;
    private String employmentIntentionPlace;
    private String phoneNumber;
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
