package com.group12.lease.web.admin.mapper;

import com.group12.lease.model.entity.RoomInfo;
import com.group12.lease.web.admin.vo.room.RoomItemVo;
import com.group12.lease.web.admin.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;


public interface RoomInfoMapper extends BaseMapper<RoomInfo> {
    IPage<RoomItemVo> pageRoomItemByQuery(IPage<RoomItemVo> page, RoomQueryVo queryVo);

}




