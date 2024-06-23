package com.group12.lease.web.app.service.impl;

import com.group12.lease.model.entity.PaymentType;
import com.group12.lease.web.app.mapper.PaymentTypeMapper;
import com.group12.lease.web.app.service.PaymentTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PaymentTypeServiceImpl extends ServiceImpl<PaymentTypeMapper, PaymentType>
    implements PaymentTypeService{

    @Autowired
    private PaymentTypeMapper paymentTypeMapper;

    @Override
    public List<PaymentType> listByRoomId(Long roomId) {
        return paymentTypeMapper.listByRoomId(roomId);
    }
}




