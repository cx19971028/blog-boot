package com.njtech.blog.controller;


import com.njtech.blog.service.MsSysUserService;
import com.njtech.blog.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
@RequestMapping("users")
public class MsSysUserController {

    @Autowired
    private MsSysUserService userService;

    @GetMapping("currentUser")
    public ResultVO currentUser(@RequestHeader("Authorization") String token){
        ResultVO resultVO = userService.findUserByToken(token);
        return resultVO;
    }
}

