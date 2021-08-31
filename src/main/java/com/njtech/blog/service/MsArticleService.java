package com.njtech.blog.service;

import com.njtech.blog.entity.MsArticle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njtech.blog.vo.ResultVO;
import com.njtech.blog.vo.param.ArticleParam;
import com.njtech.blog.vo.param.PageParamVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxin
 * @since 2021-08-12
 */
public interface MsArticleService extends IService<MsArticle> {

    ResultVO listArticle(PageParamVO pageParamVO);

    ResultVO selectNewArticles(Integer limit);


    ResultVO listArchives();

    ResultVO getArticleBodyById(Long id);

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    ResultVO publish(ArticleParam articleParam);
}
