package com.njtech.blog.vo;

import com.njtech.blog.entity.MsArticleBody;
import com.njtech.blog.entity.MsCategory;
import lombok.Data;

import java.util.List;

@Data
public class ArticleVO {

    private String id;
    private String title;
    private String summary;
    private int commentCounts;
    private int viewCounts;
    private int weight;
    private String createDate;
    private String author;
    private List<TagVO> tags;
    private CategoryVo category;
    private ArticleBodyVo body;
}
