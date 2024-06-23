package com.group12.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group12.lease.model.entity.FeeKey;
import com.group12.lease.web.app.service.FeeKeyService;
import com.group12.lease.web.app.mapper.FeeKeyMapper;
import org.springframework.stereotype.Service;


@Service
public class FeeKeyServiceImpl extends ServiceImpl<FeeKeyMapper, FeeKey>
    implements FeeKeyService{

}




