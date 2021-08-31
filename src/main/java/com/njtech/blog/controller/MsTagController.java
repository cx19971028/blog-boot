package com.njtech.blog.controller;


import com.njtech.blog.service.MsTagService;
import com.njtech.blog.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenxin
 * @since 2021-08-12
 */
@RestController
@RequestMapping("/tags")
public class MsTagController {

    @Autowired
    private MsTagService msTagService;

    @GetMapping("/hot")
    public ResultVO hot(){
        int limit = 6;
        ResultVO resultVO = msTagService.hots(limit);
        return  resultVO;
    }

    @GetMapping
    public ResultVO getTags(){
        return msTagService.getTags();
    }

    @GetMapping("/detail")
    public ResultVO getTagsDetail(){
        return msTagService.getTagsDetail();
    }

    @GetMapping("/detail/{id}")
    public ResultVO getTagById(@PathVariable("id") Long id){
        return msTagService.getTagById(id);
    }
}

