package com.njtech.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njtech.blog.common.aop.LogAnnotation;
import com.njtech.blog.common.cache.Cache;
import com.njtech.blog.entity.MsArticle;
import com.njtech.blog.entity.MsArticleBody;
import com.njtech.blog.service.MsArticleBodyService;
import com.njtech.blog.service.MsArticleService;
import com.njtech.blog.vo.ResultVO;
import com.njtech.blog.vo.param.ArticleParam;
import com.njtech.blog.vo.param.PageParamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenxin
 * @since 2021-08-12
 */
// json数据交互
@RestController
@RequestMapping("/articles")
public class MsArticleController {

    @Autowired
    private MsArticleService articleService;

    /**
     * 首页文章列表
     * @param pageParamVO
     * @return
     */
    @PostMapping
    // 加上此注解代表要对此接口记录日志
    @LogAnnotation(module="文章", operator="获取文章列表")
    public ResultVO listArticle(@RequestBody PageParamVO pageParamVO){
        return articleService.listArticle(pageParamVO);
    }

    /**
     * 首页最热文章
     * @return
     */
    @PostMapping("/hot")
    @Cache(expire = 5 * 60 * 1000,name = "hot_article")
    public ResultVO hot(){
        int limit = 3;
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("view_counts");
        wrapper.last("limit " + limit);
        List<MsArticle> list = articleService.list(wrapper);
        return ResultVO.success(list);
    }

    /**
     * 首页最新文章
     * @return
     */
    @Cache(expire = 5 * 60 * 1000,name = "new_article")
    @PostMapping("/new")
    public ResultVO newArticles(){
        int limit = 3;
        return articleService.selectNewArticles(limit);
    }

    /**
     * 文档归类
     * @return
     */
    @PostMapping("/listArchives")
    public ResultVO listArchives(){
        return  articleService.listArchives();
    }

    /**
     * 查看文章详情
     * @param id
     * @return
     */
    @PostMapping("/view/{id}")
    public ResultVO articleDetail(@PathVariable("id") Long id){

        return articleService.getArticleBodyById(id);
    }

    @PostMapping("/publish")
    public ResultVO publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }
}

