package com.group12.lease.web.admin.service;

import com.group12.lease.model.entity.ApartmentInfo;
import com.group12.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.group12.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.group12.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.group12.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


public interface ApartmentInfoService extends IService<ApartmentInfo> {

    void saveOrUpdateApartmentInfo(ApartmentSubmitVo apartmentSubmitVo);

    IPage<ApartmentItemVo> pageApartmentItemByQuery(IPage<ApartmentItemVo> page, ApartmentQueryVo queryVo);

    ApartmentDetailVo getApartmentDetailById(Long id);

    void removeApartInfo(Long id);
}
