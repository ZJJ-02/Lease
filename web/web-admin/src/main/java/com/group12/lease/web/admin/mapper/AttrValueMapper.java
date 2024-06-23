package com.group12.lease.web.admin.mapper;

import com.group12.lease.model.entity.AttrValue;
import com.group12.lease.web.admin.vo.attr.AttrValueVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface AttrValueMapper extends BaseMapper<AttrValue> {

    List<AttrValueVo> getAttrValueVosByRoomId(Long id);
}




