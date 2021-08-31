package com.njtech.blog.controller;


import com.njtech.blog.service.MsCommentService;
import com.njtech.blog.vo.ResultVO;
import com.njtech.blog.vo.param.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenxin
 * @since 2021-08-12
 */
@RestController
@RequestMapping("/comments")
public class MsCommentController {

    @Autowired
    private MsCommentService commentService;

    @GetMapping("/article/{id}")
    public ResultVO commentList(@PathVariable("id") Long id){
        return commentService.commentList(id);
    }

    @PostMapping("/create/change")
    public ResultVO createComment(@RequestBody CommentParam commentParam){
        return commentService.insertComment(commentParam);
    }
}

