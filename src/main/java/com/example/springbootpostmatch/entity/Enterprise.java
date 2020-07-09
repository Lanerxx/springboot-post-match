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
    public enum GenderCut {
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
        ENGLISH_CET_6,ENGLISH_CET_4,ENGLISH_Foreign_Exchange_Experience,
        JAPANESE_N2,JAPANESE_N3,FRENCH,RUSSIAN,ARABIC,SPANISH,
        OTHER,NONE
    }
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
