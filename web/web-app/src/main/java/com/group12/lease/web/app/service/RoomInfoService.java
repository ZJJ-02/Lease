package com.group12.lease.web.app.service;

import com.group12.lease.model.entity.RoomInfo;
import com.group12.lease.web.app.vo.room.RoomDetailVo;
import com.group12.lease.web.app.vo.room.RoomItemVo;
import com.group12.lease.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface RoomInfoService extends IService<RoomInfo> {

    IPage<RoomItemVo> pageItem(long current, long size, RoomQueryVo queryVo);

    RoomDetailVo getRoomDetailById(Long id);

    IPage<RoomItemVo> pageItemByApartmentId(long current, long size, Long id);
}
