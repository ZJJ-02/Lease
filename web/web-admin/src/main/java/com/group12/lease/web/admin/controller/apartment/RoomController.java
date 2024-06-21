package com.group12.lease.web.admin.controller.apartment;


import com.group12.lease.common.result.Result;
import com.group12.lease.model.entity.RoomInfo;
import com.group12.lease.model.enums.ReleaseStatus;
import com.group12.lease.web.admin.service.RoomInfoService;
import com.group12.lease.web.admin.vo.room.RoomDetailVo;
import com.group12.lease.web.admin.vo.room.RoomItemVo;
import com.group12.lease.web.admin.vo.room.RoomQueryVo;
import com.group12.lease.web.admin.vo.room.RoomSubmitVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "房间信息管理")
@RestController
@RequestMapping("/admin/room")
public class RoomController {

    @Autowired
    private RoomInfoService roomInfoService;

    @Operation(summary = "保存或更新房间信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody RoomSubmitVo roomSubmitVo) {
        roomInfoService.saveOrUpdateRoomInfo(roomSubmitVo);
        return Result.ok();
    }

    @Operation(summary = "根据条件分页查询房间列表")
    @GetMapping("pageItem")
    public Result<IPage<RoomItemVo>> pageItem(@RequestParam long current, @RequestParam long size, RoomQueryVo queryVo) {
        IPage<RoomItemVo> page = roomInfoService.pageItem(current, size, queryVo);
        return Result.ok(page);
    }

    @Operation(summary = "根据id获取房间详细信息")
    @GetMapping("getDetailById")
    public Result<RoomDetailVo> getDetailById(@RequestParam Long id) {
        RoomDetailVo vo = roomInfoService.getDetailById(id);
        return Result.ok(vo);
    }

    @Operation(summary = "根据id删除房间信息")
    @DeleteMapping("removeById")
    public Result removeById(@RequestParam Long id) {
        roomInfoService.delRoomInfoById(id);
        return Result.ok();
    }

    @Operation(summary = "根据id修改房间发布状态")
    @PostMapping("updateReleaseStatusById")
    public Result updateReleaseStatusById(Long id, ReleaseStatus status) {
        roomInfoService.lambdaUpdate()
                .eq(id != null, RoomInfo::getId, id)
                .set(RoomInfo::getIsRelease, status)
                .update();
        return Result.ok();
    }

    @GetMapping("listBasicByApartmentId")
    @Operation(summary = "根据公寓id查询房间列表")
    public Result<List<RoomInfo>> listBasicByApartmentId(Long id) {
        List<RoomInfo> roomInfos = roomInfoService.lambdaQuery()
                .eq(id != null, RoomInfo::getApartmentId, id)
                .orderByDesc(RoomInfo::getCreateTime).list();
        return Result.ok(roomInfos);
    }

}


















