package com.njtech.blog.controller;

import com.njtech.blog.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
public class LogoutController {

    @GetMapping
    public ResultVO logout(@RequestHeader("Authorization") String token){
        return ResultVO.success(null);
    }
}
