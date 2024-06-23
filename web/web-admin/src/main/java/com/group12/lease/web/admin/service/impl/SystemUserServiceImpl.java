package com.group12.lease.web.admin.service.impl;

import com.group12.lease.model.entity.SystemUser;
import com.group12.lease.web.admin.mapper.SystemUserMapper;
import com.group12.lease.web.admin.service.SystemUserService;
import com.group12.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.group12.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser>
        implements SystemUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public IPage<SystemUserItemVo> pageInfo(IPage<SystemUserItemVo> page, SystemUserQueryVo queryVo) {
        return systemUserMapper.pageSystemUserByQuery(page, queryVo);
    }

    @Override
    public SystemUserItemVo getUserItem(Long id) {
        return systemUserMapper.getUserItem(id);
    }
}




