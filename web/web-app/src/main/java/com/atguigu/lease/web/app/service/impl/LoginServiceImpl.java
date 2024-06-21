package com.atguigu.lease.web.app.service.impl;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.atguigu.lease.common.constant.RedisConstant;
import com.atguigu.lease.common.context.AppUser;
import com.atguigu.lease.common.context.AppUserContext;
import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.result.ResultCodeEnum;
import com.atguigu.lease.common.utils.JwtUtil;
import com.atguigu.lease.model.entity.UserInfo;
import com.atguigu.lease.model.enums.BaseStatus;
import com.atguigu.lease.web.app.mapper.UserInfoMapper;
import com.atguigu.lease.web.app.service.LoginService;
import com.atguigu.lease.web.app.vo.user.LoginVo;
import com.atguigu.lease.web.app.vo.user.UserInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public void getCode(String phone) {
        String randomCode = RandomUtil.randomNumbers(6);
        System.out.println("验证码为：" + randomCode);
        stringRedisTemplate.opsForValue().set(
                RedisConstant.APP_LOGIN_CODE + phone,
                randomCode,
                RedisConstant.APP_LOGIN_CODE_RESEND_TIME_SEC,
                TimeUnit.SECONDS
        );
    }

    @Override
    public String login(LoginVo loginVo) {
        // 从redis中获取验证码，如果为空，则返回验证码过期，需要重新获取验证码
        String redisCode = stringRedisTemplate.opsForValue().get(RedisConstant.APP_LOGIN_CODE + loginVo.getPhone());
        if (StrUtil.isBlank(redisCode)) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EXPIRED);
        }
        // 对比验证码，如果不一致，返回验证码错误
        if (!redisCode.equals(loginVo.getCode())) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_ERROR);
        }
        // 根据手机号查库，如果没有查到，就新建账户
        UserInfo user = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("phone", loginVo.getPhone()));
        if (user == null) {
            user = new UserInfo();
            user.setPhone(loginVo.getPhone());
            user.setStatus(BaseStatus.ENABLE);
            user.setNickname(RandomUtil.randomString("用户", 6));
            userInfoMapper.insert(user);
        }
        // 判断用户是否被禁
        if (user.getStatus().equals(BaseStatus.DISABLE)) {
            throw new LeaseException(ResultCodeEnum.APP_ACCOUNT_DISABLED_ERROR);
        }
        // 生成token，保存到redis
        String token = JwtUtil.createToken(user.getId(), user.getPhone());
        // 返回token
        return token;
    }

    @Override
    public UserInfoVo info() {
        AppUser user = AppUserContext.get();
        UserInfo userInfo = userInfoMapper.selectById(user.getUserId());
        return new UserInfoVo(userInfo.getNickname(), userInfo.getAvatarUrl());
    }
}
