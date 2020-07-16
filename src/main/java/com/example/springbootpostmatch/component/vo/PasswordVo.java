package com.example.springbootpostmatch.component.vo;

import lombok.Data;

@Data
public class PasswordVo {
    private String oldPassword;
    private String newPassword;
    private String newPasswordR;
}
