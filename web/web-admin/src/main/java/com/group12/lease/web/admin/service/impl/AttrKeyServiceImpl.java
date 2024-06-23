package com.group12.lease.web.admin.service.impl;

import com.group12.lease.model.entity.AttrKey;
import com.group12.lease.web.admin.mapper.AttrKeyMapper;
import com.group12.lease.web.admin.service.AttrKeyService;
import com.group12.lease.web.admin.vo.attr.AttrKeyVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AttrKeyServiceImpl extends ServiceImpl<AttrKeyMapper, AttrKey>
    implements AttrKeyService{

    @Autowired
    private AttrKeyMapper attrKeyMapper;

    @Override
    public List<AttrKeyVo> listAttrInfo() {
        return attrKeyMapper.listAttrInfo();
    }
}




