package com.njtech.blog.controller;


import com.njtech.blog.service.MsCategoryService;
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
@RequestMapping("/categorys")
public class MsCategoryController {

    @Autowired
    private MsCategoryService categoryService;

    @GetMapping
    public ResultVO categorys(){
        return categoryService.findAll();
    }

    @GetMapping("/detail")
    public ResultVO findCategoryDetail(){
        return categoryService.findCategoryDetail();
    }

    @GetMapping("/detail/{id}")
    public ResultVO findCategoryById(@PathVariable("id") Long id){
        return categoryService.findCategoryById(id);
    }
}

