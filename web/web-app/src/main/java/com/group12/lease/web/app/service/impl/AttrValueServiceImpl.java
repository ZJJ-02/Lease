package com.group12.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group12.lease.model.entity.AttrValue;
import com.group12.lease.web.app.service.AttrValueService;
import com.group12.lease.web.app.mapper.AttrValueMapper;
import org.springframework.stereotype.Service;


@Service
public class AttrValueServiceImpl extends ServiceImpl<AttrValueMapper, AttrValue>
    implements AttrValueService{

}




