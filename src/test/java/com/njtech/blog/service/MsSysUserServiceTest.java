package com.njtech.blog.service;

import com.njtech.blog.mapper.MsTagMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;


@SpringBootTest
class MsSysUserServiceTest {

    @Autowired
    private MsTagMapper msTagMapper;

//    @Test
//    void test01(){
//        msTagMapper.findTagsByArticleId(1L);
//    }

}