package com.njtech.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njtech.blog.dto.Archives;
import com.njtech.blog.entity.*;
import com.njtech.blog.mapper.MsArticleMapper;
import com.njtech.blog.mapper.MsTagMapper;
import com.njtech.blog.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njtech.blog.utils.UserThreadLocal;
import com.njtech.blog.vo.*;
import com.njtech.blog.vo.param.ArticleParam;
import com.njtech.blog.vo.param.PageParamVO;
import lombok.Data;
import lombok.val;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenxin
 * @since 2021-08-12
 */
@Service
public class MsArticleServiceImpl extends ServiceImpl<MsArticleMapper, MsArticle> implements MsArticleService {

    @Autowired
    private MsArticleMapper articleMapper;

    @Autowired
    private MsTagService msTagService;

    @Autowired
    private MsSysUserService userService;

    @Autowired
    private MsArticleBodyService articleBodyService;

    @Autowired
    private MsCategoryService categoryService;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private MsArticleTagService articleTagService;
    /**
     * 分页查询文章列表
     * @param pageParamVO
     * @return
     */
    @Override
    public ResultVO listArticle(PageParamVO pageParamVO) {
        Page<MsArticle> page = new Page<>(pageParamVO.getPage(), pageParamVO.getPageSize());
        IPage<MsArticle> articleIPage = articleMapper.listArticle(page,pageParamVO.getCategoryId(),pageParamVO.getTagId(),pageParamVO.getMonth(),pageParamVO.getYear());
        return ResultVO.success(copyList(articleIPage.getRecords()));
    }

//    @Override
//    public ResultVO listArticle(PageParamVO pageParamVO) {
//        // 1. 分页查询数据库表
//        Page<MsArticle> page = new Page<>(pageParamVO.getPage(), pageParamVO.getPageSize());
//        LambdaQueryWrapper<MsArticle> queryWrapper = new LambdaQueryWrapper<>();
//        if(pageParamVO.getCategoryId() != null){
//            queryWrapper.eq(MsArticle::getCategoryId, pageParamVO.getCategoryId());
//        }
//        if(pageParamVO.getTagId() != null){
//            List<MsArticleTag> articleTagList = articleTagService.getList(pageParamVO.getTagId());
//            List<Long> articleIdList = new ArrayList<>();
//            for (MsArticleTag articleTag : articleTagList) {
//                articleIdList.add(articleTag.getArticleId());
//            }
//            if(articleIdList.size()>0){
//                queryWrapper.in(MsArticle::getId, articleIdList);
//            }
//        }
//        queryWrapper.orderByDesc(MsArticle::getWeight,MsArticle::getCreateDate);
//        Page<MsArticle> msArticlePage = articleMapper.selectPage(page, queryWrapper);
//        List<MsArticle> records = msArticlePage.getRecords();
//        List<ArticleVO> articleVOS = copyList(records);
//
//        return ResultVO.success(articleVOS);
//    }

    /**
     * 首页最新文章
     * @return
     */
    @Override
    public ResultVO selectNewArticles(Integer limit) {
        QueryWrapper<MsArticle> wrapper = new QueryWrapper();
        wrapper.select("id", "title");
        wrapper.orderByDesc("create_date");
        wrapper.last("limit " + limit);
        List<MsArticle> msArticles = articleMapper.selectList(wrapper);
        return ResultVO.success(msArticles);
    }

    /**
     * 文档归类
     * @return
     */
    @Override
    public ResultVO listArchives() {
        List<Archives> archives = articleMapper.listArchives();
        return ResultVO.success(archives);
    }

    @Override
    public ResultVO getArticleBodyById(Long id) {
        // 1.根据id查article
        // 2.根基articleId 查category和articleBody
        MsArticle article = articleMapper.selectById(id);
        ArticleVO articleVO = copy(article,true,true,true, true);
        // 查看完文章了，新增阅读数，有没有问题呢？
        // 查看完文章之后，本应该直接返回数据，这时候做了一个更新操作，更新时需要加写锁，写锁是独占锁，会阻塞其他的读操作，降低性能
        // 更新增加了此次接口的耗时，如果一旦更新出问题，不能影响其他操作
        // 线程池可以把更新操作，扔到线程池进行，和主线程无关
        threadService.updateViewCount(articleMapper,article);
        return ResultVO.success(articleVO);
    }

    @Override
    public ResultVO publish(ArticleParam articleParam) {
        // 1.插入文章，获取文章的id
        // 2.插入文章与标签的关系
        // 3.插入文章内容
        MsArticle article = new MsArticle();
        article.setViewCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setAuthorId(UserThreadLocal.getLocal().getId());
        article.setCategoryId(article.getCategoryId());
        article.setCommentCounts(0);
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setWeight(0);
        article.setCategoryId(Integer.parseInt(articleParam.getCategory().getId().toString()));
        articleMapper.insert(article);

        List<TagVO> tags = articleParam.getTags();
        for (TagVO tag : tags) {
            MsArticleTag articleTag = new MsArticleTag();
            articleTag.setArticleId(article.getId());
            articleTag.setTagId(tag.getId());
            articleTagService.save(articleTag);
        }

        MsArticleBody articleBody = new MsArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHTML());
        articleBodyService.save(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        Map<String, Object> map = new HashMap<>();
        // 由于id为long型 为了保存精度  采用字符串封装
        map.put("id",article.getId().toString());
        return ResultVO.success(map);
    }


    private List<ArticleVO> copyList(List<MsArticle> records) {
        List<ArticleVO> articleVOS = new ArrayList<>();
        for (MsArticle record : records) {
            articleVOS.add(copy(record,true, true));
        }
        return articleVOS;
    }

    private ArticleVO copy(MsArticle article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);
        // 讲Long型日期数据转成String
        Long createDate = article.getCreateDate();
        String s = new DateTime(createDate).toString("yyyy-MM-dd HH:mm");
        articleVO.setCreateDate(s);
        // 并不是所有接口都需要标签 和作者信息
        if(isTag){
            String id = article.getId();
            //根据文章id，查询Tag
            articleVO.setTags(msTagService.findTagsByArticleId(id));
        }
        if(isAuthor){
            String authorId = article.getId();
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("id",authorId);
            MsSysUser msSysUser = userService.getOne(wrapper);
            if(msSysUser==null){
                msSysUser = new MsSysUser();
                msSysUser.setNickname("大聪明");
            }
            articleVO.setAuthor(msSysUser.getNickname());
        }

        if(isBody){
            ArticleBodyVo articleBodyVo = new ArticleBodyVo();
            String bodyId = article.getBodyId();
            MsArticleBody articleBody = articleBodyService.getById(bodyId);
            BeanUtils.copyProperties(articleBody, articleBodyVo);
            articleVO.setBody(articleBodyVo);
        }

        if(isCategory){
            CategoryVo categoryVo = new CategoryVo();
            Integer categoryId = article.getCategoryId();
            MsCategory msCategory = categoryService.getById(categoryId);
            BeanUtils.copyProperties(msCategory, categoryVo);
           articleVO.setCategory(categoryVo);
        }

        return articleVO;
    }

    private ArticleVO copy(MsArticle article, boolean isTag, boolean isAuthor) {
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);
        // 讲Long型日期数据转成String
        Long createDate = article.getCreateDate();
        String s = new DateTime(createDate).toString("yyyy-MM-dd HH:mm");
        articleVO.setCreateDate(s);
        // 并不是所有接口都需要标签 和作者信息
        if(isTag){
            String id = article.getId();
            //根据文章id，查询Tag
            articleVO.setTags(msTagService.findTagsByArticleId(id));
        }
        if(isAuthor){
            String authorId = article.getId();
            QueryWrapper<MsSysUser> wrapper = new QueryWrapper();
            wrapper.eq("id",authorId);
            MsSysUser msSysUser = userService.getOne(wrapper);
            if(msSysUser==null){
                msSysUser = new MsSysUser();
                msSysUser.setNickname("大聪明");
            }
            articleVO.setAuthor(msSysUser.getNickname());
        }
        return articleVO;
    }
}
