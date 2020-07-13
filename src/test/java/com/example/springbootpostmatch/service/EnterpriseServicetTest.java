package com.example.springbootpostmatch.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class EnterpriseServicetTest {
    @Autowired
    private EnterpriseService enterpriseService;

    @Test
    public void test_postMatch(){
        enterpriseService.postMatch(5, enterpriseService.getEnterprise(40));
    }
}
