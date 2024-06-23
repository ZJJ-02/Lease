package com.group12.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group12.lease.model.entity.PaymentType;
import com.group12.lease.web.admin.service.PaymentTypeService;
import com.group12.lease.web.admin.mapper.PaymentTypeMapper;
import org.springframework.stereotype.Service;


@Service
public class PaymentTypeServiceImpl extends ServiceImpl<PaymentTypeMapper, PaymentType>
    implements PaymentTypeService{

}




