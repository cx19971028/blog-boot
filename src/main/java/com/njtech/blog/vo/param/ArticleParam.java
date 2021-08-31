package com.njtech.blog.vo.param;

import com.njtech.blog.vo.ArticleBodyVo;
import com.njtech.blog.vo.CategoryVo;
import com.njtech.blog.vo.TagVO;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {
    private String title;
    private String id;
    private ArticleBodyVo body;
    private CategoryVo category;
    private String summary;
    private List<TagVO> tags;
}
