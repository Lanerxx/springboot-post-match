package com.example.springbootpostmatch.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"posts"})
public class Enterprise {
    public enum GenderCut {
        MALE, FEMALE,NONE
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
    public enum EnterpriseNature{
        NONE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String detail;
    private SchoolRank schoolRankCut;
    private Education educationCut;
    private ForeignLanguageProficiency foreignLanguageProficiency;
    private String majorCut;
    private GenderCut genderCut;
    private int lowestSalery;
    private int highestSalery;
    private EnterpriseNature enterpriseNature;
    private String industry;
    private String location;
    private LocalDate graduationDate;
    private int phoneNumber;


    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.REMOVE)
    private List<Post> posts;

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
