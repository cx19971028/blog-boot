package com.njtech.blog.service;

import com.njtech.blog.entity.MsSysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njtech.blog.vo.ResultVO;
import com.njtech.blog.vo.UserVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxin
 * @since 2021-08-12
 */
public interface MsSysUserService extends IService<MsSysUser> {

    ResultVO findUserByToken(String token);

    UserVo getAuthorById(String authorId);
}
