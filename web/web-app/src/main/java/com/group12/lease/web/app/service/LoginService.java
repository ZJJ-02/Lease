package com.group12.lease.web.app.service;

import com.group12.lease.web.app.vo.user.LoginVo;
import com.group12.lease.web.app.vo.user.UserInfoVo;

public interface LoginService {


    void getCode(String phone);

    String login(LoginVo loginVo);

    UserInfoVo info();
}
