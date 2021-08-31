package com.njtech.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.njtech.blog.entity.MsArticle;
import com.njtech.blog.mapper.MsArticleMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {

    @Async("taskExecutor")
    public void updateViewCount(MsArticleMapper articleMapper, MsArticle article){
        // 异步任务  更新阅读数
        Integer viewCounts = article.getViewCounts();
        article.setViewCounts(viewCounts+1);
        UpdateWrapper<MsArticle> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",article.getId());
        // 乐观锁
        wrapper.eq("view_counts",viewCounts);
        articleMapper.update(article,wrapper);
    }
}
