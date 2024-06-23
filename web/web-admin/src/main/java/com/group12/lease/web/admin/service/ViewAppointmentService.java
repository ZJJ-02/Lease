package com.group12.lease.web.admin.service;

import com.group12.lease.model.entity.ViewAppointment;
import com.group12.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.group12.lease.web.admin.vo.appointment.AppointmentVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


public interface ViewAppointmentService extends IService<ViewAppointment> {

    IPage<AppointmentVo> pageAppointmentByQuery(IPage<AppointmentVo> page, AppointmentQueryVo queryVo);
}
