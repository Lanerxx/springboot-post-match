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
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"posts"})
public class Enterprise {
    public enum EnterpriseNature{
        STATE_OWNED,FOREIGN,PRIVATE_ENTERPRISE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @MapsId
    private User user;
    @NotNull
    private String name;
    private String detail;
    private Student.SchoolRank schoolRankCut;
    private Student.Education educationCut;
    private Student.ForeignLanguageProficiency foreignLanguageProficiency;
    private String majorCut;
    private Student.Gender genderCut;
    private int lowestSalery;
    private int highestSalery;
    private EnterpriseNature enterpriseNature;
    private String industry;
    private String location;
    private String phoneNumber;
    private String otherRequirements;

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
