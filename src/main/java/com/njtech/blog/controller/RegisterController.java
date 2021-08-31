package com.njtech.blog.controller;


import com.njtech.blog.service.MsSysLogService;
import com.njtech.blog.vo.ResultVO;
import com.njtech.blog.vo.param.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private MsSysLogService msSysLogService;

    @PostMapping
    public ResultVO register(@RequestBody LoginVO loginVO){
        return msSysLogService.register(loginVO);
    }
}
