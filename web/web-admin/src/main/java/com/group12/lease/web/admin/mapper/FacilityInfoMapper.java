package com.group12.lease.web.admin.mapper;

import com.group12.lease.model.entity.FacilityInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface FacilityInfoMapper extends BaseMapper<FacilityInfo> {

    List<FacilityInfo> getFacilityInfos(Long id);

    List<FacilityInfo> selectListByApartmentId(Long id);
}




