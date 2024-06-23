package com.group12.lease.web.app.mapper;

import com.group12.lease.model.entity.RoomInfo;
import com.group12.lease.web.app.vo.room.RoomItemVo;
import com.group12.lease.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.math.BigDecimal;

public interface RoomInfoMapper extends BaseMapper<RoomInfo> {

    IPage<RoomItemVo> pageRoomItemByQuery(Page<RoomItemVo> page, RoomQueryVo queryVo);

    RoomInfo selectRoomById(Long id);

    BigDecimal selectMinRentByApartmentId(Long id);
}