package com.group12.lease.web.admin.mapper;

import com.group12.lease.model.entity.ApartmentInfo;
import com.group12.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.group12.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;


public interface ApartmentInfoMapper extends BaseMapper<ApartmentInfo> {

    IPage<ApartmentItemVo> pageApartmentItemByQuery(IPage<ApartmentItemVo> page, ApartmentQueryVo queryVo);

    ApartmentInfo getRegionInfo(@Param("provinceId") Long provinceId, @Param("cityId") Long cityId, @Param("districtId") Long districtId);
}




