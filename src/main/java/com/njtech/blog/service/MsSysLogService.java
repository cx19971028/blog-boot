package com.njtech.blog.service;

import com.njtech.blog.entity.MsSysLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njtech.blog.entity.MsSysUser;
import com.njtech.blog.vo.ResultVO;
import com.njtech.blog.vo.param.LoginVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxin
 * @since 2021-08-12
 */
public interface MsSysLogService extends IService<MsSysLog> {

    ResultVO login(LoginVO loginVO);

    MsSysUser checkToken(String token);

    ResultVO register(LoginVO loginVO);
}
