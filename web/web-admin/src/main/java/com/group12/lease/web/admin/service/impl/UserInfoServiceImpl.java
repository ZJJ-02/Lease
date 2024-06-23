package com.group12.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group12.lease.model.entity.UserInfo;
import com.group12.lease.web.admin.service.UserInfoService;
import com.group12.lease.web.admin.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

}




