package com.group12.lease.web.admin.service;

import com.group12.lease.model.entity.SystemUser;
import com.group12.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.group12.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


public interface SystemUserService extends IService<SystemUser> {

    IPage<SystemUserItemVo> pageInfo(IPage<SystemUserItemVo> page, SystemUserQueryVo queryVo);

    SystemUserItemVo getUserItem(Long id);
}
