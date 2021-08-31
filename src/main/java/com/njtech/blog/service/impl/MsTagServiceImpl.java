package com.njtech.blog.service.impl;

import com.njtech.blog.entity.MsTag;
import com.njtech.blog.mapper.MsTagMapper;
import com.njtech.blog.service.MsTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njtech.blog.vo.ResultVO;
import com.njtech.blog.vo.TagVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
public class MsTagServiceImpl extends ServiceImpl<MsTagMapper, MsTag> implements MsTagService {

    @Autowired
    private MsTagMapper tagMapper;

    @Override
    public List<TagVO> findTagsByArticleId(String id) {
        //mp无法进行多表查询
        List<MsTag> msTags = tagMapper.findTagsByArticleId(id);
        return copyLis(msTags);
    }


    private List<TagVO> copyLis(List<MsTag> msTags) {
        List<TagVO> tagVOS = new ArrayList<>();
        for (MsTag msTag : msTags) {
            tagVOS.add(copy(msTag));
        }
        return tagVOS;
    }

    private TagVO copy(MsTag msTag) {
        TagVO tagVO = new TagVO();
        BeanUtils.copyProperties(msTag,tagVO);
        return tagVO;
    }

    @Override
    public ResultVO hots(int limit) {
        /**
         * 1. 标签所拥有的文章数量最多，最热标签
         * 2. 根据tag_id查询 分组计数， 从大到小排序 取前limit
         */
        List<Long> longs = tagMapper.selectHotTagIds(limit);
        // 判断id集合是否为空
        if(CollectionUtils.isEmpty(longs)){
            // 返回一个空的list集合 防止前端报错
            return ResultVO.success(Collections.emptyList());
        }

        List<MsTag> msTags = tagMapper.selectBatchIds(longs);
        return ResultVO.success(msTags);
    }

    @Override
    public ResultVO getTags() {
        List<MsTag> msTags = tagMapper.selectList(null);
        return ResultVO.success(copyLis(msTags));
    }

    @Override
    public ResultVO getTagsDetail() {
        List<MsTag> msTags = tagMapper.selectList(null);
        return ResultVO.success(msTags);
    }

    @Override
    public ResultVO getTagById(Long id) {
        MsTag msTag = tagMapper.selectById(id);
        return ResultVO.success(msTag);
    }

}
