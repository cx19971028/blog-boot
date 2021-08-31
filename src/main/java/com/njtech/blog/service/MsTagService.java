package com.njtech.blog.service;

import com.njtech.blog.entity.MsTag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njtech.blog.vo.ResultVO;
import com.njtech.blog.vo.TagVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxin
 * @since 2021-08-12
 */
public interface MsTagService extends IService<MsTag> {

    List<TagVO> findTagsByArticleId(String id);

    ResultVO hots(int limit);

    /**
     * 查询所有标签
     * @return
     */
    ResultVO getTags();

    ResultVO getTagsDetail();

    ResultVO getTagById(Long id);
}
