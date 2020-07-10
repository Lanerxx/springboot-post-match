package com.example.springbootpostmatch.component.vo;

import com.example.springbootpostmatch.entity.Post;
import lombok.Data;

@Data
public class PostVo {
    private Post post;
    private String startTime;
    private String endTime;
}
