package com.example.springbootpostmatch.component.vo;

import com.example.springbootpostmatch.entity.Enterprise;
import lombok.Data;

@Data
public class EnterpriseInoVo {
    private Enterprise enterprise;
    private String genderCut;
    private String schoolRankCut;
    private String educationCut;
    private String foreignLanguageProficiency;
    private String enterpriseNature;
}
