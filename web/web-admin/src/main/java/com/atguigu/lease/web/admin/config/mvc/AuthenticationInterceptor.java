package com.atguigu.lease.web.admin.config.mvc;

import com.atguigu.lease.common.context.AdminUser;
import com.atguigu.lease.common.context.AdminUserContext;
import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.result.ResultCodeEnum;
import com.atguigu.lease.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // todo:预检请求直接放行
        if(request.getMethod().equalsIgnoreCase("OPTIONS")){
            return true;
        }
        String token = request.getHeader("access_token");
        if (token == null) {
            throw new LeaseException(ResultCodeEnum.ADMIN_LOGIN_AUTH);
        } else {
            Claims claims = JwtUtil.parseToken(token);
            AdminUser user = new AdminUser();
            user.setUserId(claims.get("userId", Long.class));
            user.setUsername(claims.get("username", String.class));
            AdminUserContext.set(user);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AdminUserContext.remove();
    }
}
