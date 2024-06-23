package com.group12.lease.web.admin.mapper;

import com.group12.lease.model.entity.SystemUser;
import com.group12.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.group12.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;


public interface SystemUserMapper extends BaseMapper<SystemUser> {

    IPage<SystemUserItemVo> pageSystemUserByQuery(IPage<SystemUserItemVo> page, SystemUserQueryVo queryVo);

    SystemUserItemVo getUserItem(Long id);
}




