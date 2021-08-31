package com.njtech.blog.controller;

import com.njtech.blog.entity.MsSysUser;
import com.njtech.blog.utils.UserThreadLocal;
import com.njtech.blog.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public ResultVO test(){
        MsSysUser user = UserThreadLocal.getLocal();
        return ResultVO.success(user);
    }
}
