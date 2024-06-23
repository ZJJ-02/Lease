package com.group12.lease.web.app.mapper;

import com.group12.lease.model.entity.FeeValue;
import com.group12.lease.web.app.vo.fee.FeeValueVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface FeeValueMapper extends BaseMapper<FeeValue> {

    List<FeeValueVo> selectListByApartmentId(Long apartmentId);
}




