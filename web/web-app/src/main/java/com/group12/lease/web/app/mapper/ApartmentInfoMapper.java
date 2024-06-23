package com.group12.lease.web.app.mapper;

import com.group12.lease.model.entity.ApartmentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


public interface ApartmentInfoMapper extends BaseMapper<ApartmentInfo> {


    ApartmentInfo selectApartmentById(Long id);
}




