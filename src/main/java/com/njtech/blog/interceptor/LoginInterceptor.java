package com.njtech.blog.interceptor;

import com.alibaba.fastjson.JSON;
import com.njtech.blog.entity.MsSysUser;
import com.njtech.blog.enums.ErrorCode;
import com.njtech.blog.service.MsSysLogService;
import com.njtech.blog.utils.UserThreadLocal;
import com.njtech.blog.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private MsSysLogService sysLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(HttpMethod.OPTIONS.toString().equals(request.getMethod())){
            return true;
        }
        String token = request.getHeader("Authorization");
        if(StringUtils.isBlank(token)){
            ResultVO resultVO = ResultVO.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMessage());
            // 将响应数据转成json
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(resultVO));
            return false;
        }
        MsSysUser msSysUser = sysLogService.checkToken(token);
        if(msSysUser==null){
            ResultVO resultVO = ResultVO.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMessage());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(resultVO));
            return false;
        }

        UserThreadLocal.setLocal(msSysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.removeLocal();
    }
}
