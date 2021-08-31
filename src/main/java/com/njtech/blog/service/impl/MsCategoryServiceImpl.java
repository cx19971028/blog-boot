package com.njtech.blog.service.impl;

import com.njtech.blog.entity.MsCategory;
import com.njtech.blog.mapper.MsCategoryMapper;
import com.njtech.blog.service.MsCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njtech.blog.vo.CategoryVo;
import com.njtech.blog.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class MsCategoryServiceImpl extends ServiceImpl<MsCategoryMapper, MsCategory> implements MsCategoryService {

    @Autowired
    private MsCategoryMapper categoryMapper;

    @Override
    public ResultVO findAll() {
        List<MsCategory> msCategories = categoryMapper.selectList(null);
        return ResultVO.success(copyList(msCategories));
    }

    @Override
    public ResultVO findCategoryDetail() {
        List<MsCategory> msCategories = categoryMapper.selectList(null);
        return ResultVO.success(msCategories);
    }

    @Override
    public ResultVO findCategoryById(Long id) {
        MsCategory msCategory = categoryMapper.selectById(id);
        return ResultVO.success(msCategory);
    }

    private List<CategoryVo> copyList(List<MsCategory> msCategories) {
        List<CategoryVo> categoryVoList = new ArrayList<>();
        for (MsCategory msCategory : msCategories) {
            categoryVoList.add(copy(msCategory));
        }
        return categoryVoList;
    }

    private CategoryVo copy(MsCategory msCategory) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(msCategory,categoryVo);
        return categoryVo;
    }
}
