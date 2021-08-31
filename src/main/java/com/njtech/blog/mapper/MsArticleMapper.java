package com.njtech.blog.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njtech.blog.dto.Archives;
import com.njtech.blog.entity.MsArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenxin
 * @since 2021-08-12
 */
public interface MsArticleMapper extends BaseMapper<MsArticle> {

    List<Archives> listArchives();

    IPage<MsArticle> listArticle(Page<MsArticle> page, String categoryId, String tagId, String month, String year);
}
