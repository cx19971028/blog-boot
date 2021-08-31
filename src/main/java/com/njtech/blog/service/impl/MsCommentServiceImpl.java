package com.njtech.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njtech.blog.entity.MsComment;
import com.njtech.blog.entity.MsSysUser;
import com.njtech.blog.mapper.MsCommentMapper;
import com.njtech.blog.service.MsCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njtech.blog.service.MsSysUserService;
import com.njtech.blog.utils.UserThreadLocal;
import com.njtech.blog.vo.CommentVo;
import com.njtech.blog.vo.ResultVO;
import com.njtech.blog.vo.UserVo;
import com.njtech.blog.vo.param.CommentParam;
import org.joda.time.DateTime;
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
public class MsCommentServiceImpl extends ServiceImpl<MsCommentMapper, MsComment> implements MsCommentService {

    @Autowired
    private MsCommentMapper commentMapper;

    @Autowired
    private MsSysUserService userService;

    @Override
    public ResultVO commentList(Long id) {
        // 1.根据文章id查询文章列表
        // 2.根据authorId查询作者信息
        // 3.如果评论有子评论，根据parentId查询子评论
        QueryWrapper<MsComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", id);
        queryWrapper.eq("level", 1);
        List<MsComment> msComments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copyList(msComments);
        return ResultVO.success(commentVoList);
    }

    /**
     * 创建评论
     * @param commentParam
     * @return
     */
    @Override
    public ResultVO insertComment(CommentParam commentParam) {
        MsSysUser user = UserThreadLocal.getLocal();
        MsComment comment =  new MsComment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setCreateDate(System.currentTimeMillis());
        comment.setContent(commentParam.getContent());
        comment.setAuthorId(user.getId());
        String parentId = commentParam.getParent();
        if(parentId == null || parentId.length() ==  0){
            comment.setLevel(1);
        }else {
            comment.setLevel(2);
        }
        comment.setParentId(parentId == null ? "0" : parentId);
        String toUser = commentParam.getToUserId();
        comment.setToUid(toUser == null? "0" :toUser);
        commentMapper.insert(comment);
        return ResultVO.success(null);
    }

    private List<CommentVo> copyList(List<MsComment> msComments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (MsComment msComment : msComments) {
            commentVoList.add(copy(msComment));
        }
        return commentVoList;
    }

    private CommentVo copy(MsComment msComment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(msComment,commentVo);
        commentVo.setCreateDate(new DateTime(msComment.getCreateDate()).toString("yyyy-MM-hh HH:mm"));

        String authorId = msComment.getAuthorId();
        UserVo userVo = userService.getAuthorById(authorId);
        commentVo.setAuthor(userVo);

        //评论的评论
        List<CommentVo> commentVoList = findCommentByParentId(msComment.getId());
        commentVo.setChildrens(commentVoList);

        if(msComment.getLevel()>1){
            String toUid = msComment.getToUid();
            UserVo toUser = userService.getAuthorById(toUid);
            commentVo.setToUser(toUser);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentByParentId(String parentId) {
        QueryWrapper<MsComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",parentId);
        queryWrapper.eq("level", 2);
        List<MsComment> msComments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copyList(msComments);
        return commentVoList;

    }
}
