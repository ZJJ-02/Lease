package com.group12.lease.web.app.mapper;

import com.group12.lease.model.entity.PaymentType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface PaymentTypeMapper extends BaseMapper<PaymentType> {


    List<PaymentType> selectListByRoomId(Long id);

    List<PaymentType> listByRoomId(Long roomId);
}




