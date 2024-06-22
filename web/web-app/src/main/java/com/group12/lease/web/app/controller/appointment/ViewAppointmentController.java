package com.group12.lease.web.app.controller.appointment;


import com.group12.lease.common.context.AppUserContext;
import com.group12.lease.common.result.Result;
import com.group12.lease.model.entity.ViewAppointment;
import com.group12.lease.web.app.service.ViewAppointmentService;
import com.group12.lease.web.app.vo.appointment.AppointmentDetailVo;
import com.group12.lease.web.app.vo.appointment.AppointmentItemVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "看房预约信息")
@RestController
@RequestMapping("/app/appointment")
public class ViewAppointmentController {

    @Autowired
    private ViewAppointmentService viewAppointmentService;

    @Operation(summary = "保存或更新看房预约")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody ViewAppointment viewAppointment) {
        //少了这步，导致写数据库时用户id为空，随后用用户id查预约记录就查不出来
        viewAppointment.setUserId(AppUserContext.get().getUserId());
        viewAppointmentService.saveOrUpdate(viewAppointment);
        return Result.ok();
    }

    @Operation(summary = "查询个人预约看房列表")
    @GetMapping("listItem")
    public Result<List<AppointmentItemVo>> listItem() {
        List<AppointmentItemVo> list = viewAppointmentService.listItemByUserId(AppUserContext.get().getUserId());
        return Result.ok(list);
    }


    @GetMapping("getDetailById")
    @Operation(summary = "根据ID查询预约详情信息")
    public Result<AppointmentDetailVo> getDetailById(Long id) {
        AppointmentDetailVo vo = viewAppointmentService.getDetailById(id);
        return Result.ok(vo);
    }

}

