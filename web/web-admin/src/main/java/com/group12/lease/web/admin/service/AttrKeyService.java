package com.group12.lease.web.admin.service;

import com.group12.lease.model.entity.AttrKey;
import com.group12.lease.web.admin.vo.attr.AttrKeyVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface AttrKeyService extends IService<AttrKey> {

    List<AttrKeyVo> listAttrInfo();
}
