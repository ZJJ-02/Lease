package com.group12.lease.web.app.mapper;

import com.group12.lease.model.entity.ViewAppointment;
import com.group12.lease.web.app.vo.appointment.AppointmentItemVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface ViewAppointmentMapper extends BaseMapper<ViewAppointment> {


    List<AppointmentItemVo> listItem(Long userId);

    List<AppointmentItemVo> listItemByUserId(Long userId);
}




