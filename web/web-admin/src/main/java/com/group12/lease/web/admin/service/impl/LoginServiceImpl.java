package com.group12.lease.web.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.group12.lease.common.constant.RedisConstant;
import com.group12.lease.common.context.AdminUser;
import com.group12.lease.common.context.AdminUserContext;
import com.group12.lease.common.exception.LeaseException;
import com.group12.lease.common.result.ResultCodeEnum;
import com.group12.lease.common.utils.JwtUtil;
import com.group12.lease.model.entity.SystemUser;
import com.group12.lease.model.enums.BaseStatus;
import com.group12.lease.web.admin.service.LoginService;
import com.group12.lease.web.admin.service.SystemUserService;
import com.group12.lease.web.admin.vo.login.CaptchaVo;
import com.group12.lease.web.admin.vo.login.LoginVo;
import com.group12.lease.web.admin.vo.system.user.SystemUserInfoVo;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SystemUserService systemUserService;

    @Override
    public CaptchaVo getCaptcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        specCaptcha.setCharType(Captcha.TYPE_DEFAULT);
        String code = specCaptcha.text().toLowerCase();
        String key = RedisConstant.ADMIN_LOGIN_PREFIX + UUID.randomUUID();
        String image = specCaptcha.toBase64();
        redisTemplate.opsForValue().set(key, code, RedisConstant.ADMIN_LOGIN_CAPTCHA_TTL_SEC, TimeUnit.SECONDS);
        //TODO 发送手机验证码
        return new CaptchaVo(image, key);
    }

    @Override
    public String login(LoginVo loginVo) {
        String code = redisTemplate.opsForValue().get(loginVo.getCaptchaKey());
        if (StrUtil.isBlank(code)) {
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_EXPIRED);
        }
        if (!code.equals(loginVo.getCaptchaCode())) {
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        }
        List<SystemUser> list = systemUserService.lambdaQuery().eq(SystemUser::getUsername, loginVo.getUsername()).list();
        if (CollUtil.isEmpty(list)) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR);
        }
        SystemUser user = list.get(0);
        if (user.getStatus().equals(BaseStatus.DISABLE)) {
            throw new LeaseException(ResultCodeEnum.APP_ACCOUNT_DISABLED_ERROR);
        }
        String inputPwd = DigestUtils.md5DigestAsHex(loginVo.getPassword().getBytes(StandardCharsets.UTF_8));
        if (!user.getPassword().equals(inputPwd)) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
        }
        String token = JwtUtil.createToken(user.getId(), user.getUsername());
        return token;
    }

    @Override
    public SystemUserInfoVo getUserInfo() {
        AdminUser user = AdminUserContext.get();
        SystemUser systemUser = systemUserService.getById(user.getUserId());
        SystemUserInfoVo vo = new SystemUserInfoVo();
        vo.setName(systemUser.getName());
        vo.setAvatarUrl(systemUser.getAvatarUrl());
        return vo;
    }
}
