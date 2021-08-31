package com.njtech.blog.service;

import com.njtech.blog.entity.MsArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxin
 * @since 2021-08-12
 */
public interface MsArticleTagService extends IService<MsArticleTag> {

    List<MsArticleTag> getList(String tagId);
}
