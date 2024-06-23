package com.group12.lease.web.admin.mapper;

import com.group12.lease.model.entity.LabelInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface LabelInfoMapper extends BaseMapper<LabelInfo> {

    List<LabelInfo> getLabelInfos(Long id);

    List<LabelInfo> selectListByApartmentId(Long id);
}




