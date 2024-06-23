package com.group12.lease.web.admin.mapper;

import com.group12.lease.model.entity.ViewAppointment;
import com.group12.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.group12.lease.web.admin.vo.appointment.AppointmentVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;


public interface ViewAppointmentMapper extends BaseMapper<ViewAppointment> {

    IPage<AppointmentVo> pageAppointmentByQuery(IPage<AppointmentVo> page, AppointmentQueryVo queryVo);
}




