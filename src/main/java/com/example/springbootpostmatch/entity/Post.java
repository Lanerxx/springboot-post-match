package com.example.springbootpostmatch.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"jobhunters"})
public class Post {
    public enum Education{
        NONE,
        PRIMARY_SCHOOL_ADUCATION,JUNIOR_HIGH_SCHOOL_ADUCATION,
        TECHNICAL_SECONDARY_SCHOOL_ADUCATION,
        HIGH_SCHOOL_ADUCATION,JUNIOR_COLLEGE_EDUCATION,
        BACHELOR_DEGREE,MASTER_DEGREE,DOCTOR_DEGREE,
        POSTDOCTORAL_DEGREE,ACADEMICIAN_EDUCATION
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String detail;
    private int count;
    private int salary;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne
    private Enterprise enterprise;


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
