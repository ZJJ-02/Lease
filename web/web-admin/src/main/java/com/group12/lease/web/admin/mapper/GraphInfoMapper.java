package com.group12.lease.web.admin.mapper;

import com.group12.lease.model.entity.GraphInfo;
import com.group12.lease.model.enums.ItemType;
import com.group12.lease.web.admin.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface GraphInfoMapper extends BaseMapper<GraphInfo> {

    List<GraphVo> selectListByItemTypeAndId(ItemType itemType, Long itemId);
}




