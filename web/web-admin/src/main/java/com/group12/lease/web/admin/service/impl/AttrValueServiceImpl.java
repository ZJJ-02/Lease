package com.group12.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group12.lease.model.entity.AttrValue;
import com.group12.lease.web.admin.service.AttrValueService;
import com.group12.lease.web.admin.mapper.AttrValueMapper;
import org.springframework.stereotype.Service;


@Service
public class AttrValueServiceImpl extends ServiceImpl<AttrValueMapper, AttrValue>
    implements AttrValueService{

}




