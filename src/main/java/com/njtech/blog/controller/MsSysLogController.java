package com.njtech.blog.controller;


import com.njtech.blog.service.MsSysLogService;
import com.njtech.blog.vo.ResultVO;
import com.njtech.blog.vo.param.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenxin
 * @since 2021-08-12
 */
@RestController
@RequestMapping("/login")
public class MsSysLogController {

    @Autowired
    private MsSysLogService sysLogService;
    @PostMapping
    public ResultVO login(@RequestBody LoginVO loginVO, HttpSession session){
        return sysLogService.login(loginVO);
    }
}

