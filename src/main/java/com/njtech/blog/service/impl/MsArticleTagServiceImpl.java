package com.njtech.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.njtech.blog.entity.MsArticle;
import com.njtech.blog.entity.MsArticleTag;
import com.njtech.blog.mapper.MsArticleTagMapper;
import com.njtech.blog.service.MsArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenxin
 * @since 2021-08-12
 */
@Service
public class MsArticleTagServiceImpl extends ServiceImpl<MsArticleTagMapper, MsArticleTag> implements MsArticleTagService {

    @Autowired
    private MsArticleTagMapper articleTagMapper;

    @Override
    public List<MsArticleTag> getList(String tagId) {

        LambdaQueryWrapper<MsArticleTag> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(MsArticleTag::getTagId, tagId);
        List<MsArticleTag> articleTags = articleTagMapper.selectList(queryWrapper);
        return articleTags;
    }
}
