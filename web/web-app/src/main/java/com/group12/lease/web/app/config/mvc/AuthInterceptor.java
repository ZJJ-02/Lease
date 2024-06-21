package com.group12.lease.web.app.config.mvc;

import cn.hutool.core.util.StrUtil;
import com.group12.lease.common.context.AppUser;
import com.group12.lease.common.context.AppUserContext;
import com.group12.lease.common.exception.LeaseException;
import com.group12.lease.common.result.ResultCodeEnum;
import com.group12.lease.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // todo:预检请求直接放行
        if(request.getMethod().equalsIgnoreCase("OPTIONS")){
            return true;
        }
        String token = request.getHeader("access_token");
        if (StrUtil.isBlank(token)) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_AUTH);
        } else {
            Claims claims = JwtUtil.parseToken(token);
            AppUser appUser = new AppUser();
            appUser.setUserId(claims.get("userId", Long.class));
            appUser.setUsername(claims.get("username", String.class));
            AppUserContext.set(appUser);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AppUserContext.remove();
    }
}
