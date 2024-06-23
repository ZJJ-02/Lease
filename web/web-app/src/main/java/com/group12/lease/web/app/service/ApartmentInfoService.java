package com.group12.lease.web.app.service;

import com.group12.lease.model.entity.ApartmentInfo;
import com.group12.lease.web.app.vo.apartment.ApartmentDetailVo;
import com.group12.lease.web.app.vo.apartment.ApartmentItemVo;
import com.baomidou.mybatisplus.extension.service.IService;


public interface ApartmentInfoService extends IService<ApartmentInfo> {

    ApartmentItemVo selectApartmentItemVoById(Long id);

    ApartmentDetailVo getDetailById(Long id);
}
