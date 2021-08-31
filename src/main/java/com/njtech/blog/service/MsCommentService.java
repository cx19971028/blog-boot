package com.njtech.blog.service;

import com.njtech.blog.entity.MsComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njtech.blog.vo.ResultVO;
import com.njtech.blog.vo.param.CommentParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxin
 * @since 2021-08-12
 */
public interface MsCommentService extends IService<MsComment> {

    ResultVO commentList(Long id);

    ResultVO insertComment(CommentParam commentParam);
}
