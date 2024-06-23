package com.group12.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group12.lease.model.entity.FeeValue;
import com.group12.lease.web.admin.service.FeeValueService;
import com.group12.lease.web.admin.mapper.FeeValueMapper;
import org.springframework.stereotype.Service;


@Service
public class FeeValueServiceImpl extends ServiceImpl<FeeValueMapper, FeeValue>
    implements FeeValueService{

}




