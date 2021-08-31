package com.njtech.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njtech.blog.entity.MsSysLog;
import com.njtech.blog.entity.MsSysUser;
import com.njtech.blog.enums.ErrorCode;
import com.njtech.blog.mapper.MsSysLogMapper;
import com.njtech.blog.mapper.MsSysUserMapper;
import com.njtech.blog.service.MsSysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njtech.blog.utils.JWTUtils;
import com.njtech.blog.vo.ResultVO;
import com.njtech.blog.vo.param.LoginVO;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenxin
 * @since 2021-08-12
 */
@Service
@Transactional
public class MsSysLogServiceImpl extends ServiceImpl<MsSysLogMapper, MsSysLog> implements MsSysLogService {

    @Autowired
    private MsSysUserMapper mapper;

    private static final String salt = "mszlu!@#";

    @Override
    public ResultVO login(LoginVO loginVO) {
        /**
         * 1.检查参数是否合法
         * 2.根据用户名和密码去user表中查询是否存在
         * 3.如果不存在登录失败
         * 4.如果存在 使用jwt 生成token 返回
         * */
        String account = loginVO.getAccount();
        String password = loginVO.getPassword();
        if(StringUtils.isBlank(account)||StringUtils.isBlank(password)){
            return ResultVO.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMessage());
        }
        QueryWrapper<MsSysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account",account);
        // 加盐加密
        password = DigestUtils.md5Hex(password + salt);
        queryWrapper.eq("password",password);
        queryWrapper.last("limit 1");

        MsSysUser msSysUser = mapper.selectOne(queryWrapper);
        if(msSysUser == null){
            return ResultVO.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMessage());
        }
        String token = JWTUtils.createToken(msSysUser.getId());

        return ResultVO.success(token);
    }

    @Override
    public MsSysUser checkToken(String token) {

        if(StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null){
            return null;
        }
        QueryWrapper<MsSysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("id",map.get("userId"));
        MsSysUser msSysUser = mapper.selectOne(wrapper);

        return msSysUser;
    }

    @Override
    public ResultVO register(LoginVO loginVO) {
        /**
         * 1. 判断参数是否合法
         * 2. 判断账户是否存在，若存在，返回账户已被注册
         * 3. 不存在，注册用户
         * 4. 生成token
         * 5. 返回，加上事务，注册过程出现问题，则回滚事务
         */
        String account = loginVO.getAccount();
        String password = loginVO.getPassword();
        String nickname = loginVO.getNickname();
        if(StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)){
            return ResultVO.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMessage());
        }
        QueryWrapper<MsSysUser> wrapper = new QueryWrapper();
        wrapper.eq("account", account);
        wrapper.last("limit 1");
        MsSysUser msSysUser = mapper.selectOne(wrapper);
        if(msSysUser != null){
            return ResultVO.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMessage());
        }
        msSysUser = new MsSysUser();
        msSysUser.setNickname(nickname);
        msSysUser.setAccount(account);
        msSysUser.setPassword(DigestUtils.md5Hex(password+salt));
        msSysUser.setCreateDate(System.currentTimeMillis());
        msSysUser.setLastLogin(System.currentTimeMillis());
        msSysUser.setAvatar("/static/img/logo.b3a48c0.png");
        msSysUser.setAdmin(1); //1 为true
        msSysUser.setDeleted(0); // 0 为false
        msSysUser.setSalt("");
        msSysUser.setStatus("");
        msSysUser.setEmail("");
        mapper.insert(msSysUser);
        // ID自动生成，默认雪花算法
        String token = JWTUtils.createToken(msSysUser.getId());
        return ResultVO.success(token);
    }
}
