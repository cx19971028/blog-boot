package com.njtech.blog.service.impl;

import com.njtech.blog.entity.MsSysUser;
import com.njtech.blog.enums.ErrorCode;
import com.njtech.blog.mapper.MsSysUserMapper;
import com.njtech.blog.service.MsSysLogService;
import com.njtech.blog.service.MsSysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njtech.blog.vo.LoginUserVO;
import com.njtech.blog.vo.ResultVO;
import com.njtech.blog.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenxin
 * @since 2021-08-12
 */
@Service
public class MsSysUserServiceImpl extends ServiceImpl<MsSysUserMapper, MsSysUser> implements MsSysUserService {

    @Autowired
    private MsSysLogService logService;

    @Autowired
    private MsSysUserMapper userMapper;

    @Override
    public ResultVO findUserByToken(String token) {
        // 1，验证token是否合法
        // 2.若不合法，返回错误信息

        MsSysUser msSysUser = logService.checkToken(token);
        if(msSysUser == null){
            return ResultVO.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMessage());
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(msSysUser, loginUserVO);
        return ResultVO.success(loginUserVO);
    }

    @Override
    public UserVo getAuthorById(String authorId) {
        UserVo userVo = new UserVo();
        MsSysUser msSysUser = userMapper.selectById(authorId);
        BeanUtils.copyProperties(msSysUser, userVo);
        return userVo;
    }
}
