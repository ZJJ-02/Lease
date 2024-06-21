package com.group12.lease.web.admin.service;

import com.group12.lease.web.admin.vo.login.CaptchaVo;
import com.group12.lease.web.admin.vo.login.LoginVo;
import com.group12.lease.web.admin.vo.system.user.SystemUserInfoVo;

public interface LoginService {

    CaptchaVo getCaptcha();

    String login(LoginVo loginVo);

    SystemUserInfoVo getUserInfo();
}
