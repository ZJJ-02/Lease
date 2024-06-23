package com.group12.lease.web.admin.service.impl;

import com.group12.lease.model.entity.ViewAppointment;
import com.group12.lease.web.admin.mapper.ViewAppointmentMapper;
import com.group12.lease.web.admin.service.ViewAppointmentService;
import com.group12.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.group12.lease.web.admin.vo.appointment.AppointmentVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewAppointmentServiceImpl extends ServiceImpl<ViewAppointmentMapper, ViewAppointment>
        implements ViewAppointmentService {

    @Autowired
    private ViewAppointmentMapper viewAppointmentMapper;

    @Override
    public IPage<AppointmentVo> pageAppointmentByQuery(IPage<AppointmentVo> page, AppointmentQueryVo queryVo) {
        return viewAppointmentMapper.pageAppointmentByQuery(page, queryVo);
    }
}




