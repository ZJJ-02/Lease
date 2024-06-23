package com.group12.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group12.lease.model.entity.SystemPost;
import com.group12.lease.web.admin.service.SystemPostService;
import com.group12.lease.web.admin.mapper.SystemPostMapper;
import org.springframework.stereotype.Service;


@Service
public class SystemPostServiceImpl extends ServiceImpl<SystemPostMapper, SystemPost>
    implements SystemPostService{

}




