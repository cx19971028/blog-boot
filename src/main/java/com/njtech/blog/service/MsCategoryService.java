package com.njtech.blog.service;

import com.njtech.blog.entity.MsCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njtech.blog.vo.ResultVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxin
 * @since 2021-08-12
 */
public interface MsCategoryService extends IService<MsCategory> {

    // 查询所有分类
    ResultVO findAll();

    ResultVO findCategoryDetail();

    ResultVO findCategoryById(Long id);
}
