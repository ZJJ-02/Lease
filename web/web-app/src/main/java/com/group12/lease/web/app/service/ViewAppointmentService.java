package com.group12.lease.web.app.service;

import com.group12.lease.model.entity.ViewAppointment;
import com.group12.lease.web.app.vo.appointment.AppointmentDetailVo;
import com.group12.lease.web.app.vo.appointment.AppointmentItemVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ViewAppointmentService extends IService<ViewAppointment> {

    List<AppointmentItemVo> listItem();

    AppointmentDetailVo getDetailById(Long id);

    List<AppointmentItemVo> listItemByUserId(Long userId);
}
