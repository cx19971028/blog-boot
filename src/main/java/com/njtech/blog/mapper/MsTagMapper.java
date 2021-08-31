package com.njtech.blog.mapper;

import com.njtech.blog.entity.MsTag;
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
public interface MsTagMapper extends BaseMapper<MsTag> {
    /**
     * 根基文章ID查询标签列表
     * @param id
     * @return
     */
    List<MsTag> findTagsByArticleId(String id);

    /**
     * 查询最热前limit标签
     * @return
     */
    List<Long> selectHotTagIds(Integer limit);
}
