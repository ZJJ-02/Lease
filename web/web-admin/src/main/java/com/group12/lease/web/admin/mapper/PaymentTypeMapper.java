package com.group12.lease.web.admin.mapper;

import com.group12.lease.model.entity.PaymentType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface PaymentTypeMapper extends BaseMapper<PaymentType> {

    List<PaymentType> getPaymentTypes(Long id);
}




