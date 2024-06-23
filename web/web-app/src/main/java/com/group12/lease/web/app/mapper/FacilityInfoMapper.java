package com.group12.lease.web.app.mapper;

import com.group12.lease.model.entity.FacilityInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface FacilityInfoMapper extends BaseMapper<FacilityInfo> {

    List<FacilityInfo> selectListByRoomId(Long id);

    List<FacilityInfo> selectListByApartmentId(Long apartmentId);
}




