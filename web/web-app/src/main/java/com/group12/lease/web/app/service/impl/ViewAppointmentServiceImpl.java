package com.group12.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group12.lease.common.context.AppUserContext;
import com.group12.lease.model.entity.ViewAppointment;
import com.group12.lease.model.enums.ItemType;
import com.group12.lease.web.app.mapper.GraphInfoMapper;
import com.group12.lease.web.app.mapper.ViewAppointmentMapper;
import com.group12.lease.web.app.service.ApartmentInfoService;
import com.group12.lease.web.app.service.ViewAppointmentService;
import com.group12.lease.web.app.vo.apartment.ApartmentItemVo;
import com.group12.lease.web.app.vo.appointment.AppointmentDetailVo;
import com.group12.lease.web.app.vo.appointment.AppointmentItemVo;
import com.group12.lease.web.app.vo.graph.GraphVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ViewAppointmentServiceImpl extends ServiceImpl<ViewAppointmentMapper, ViewAppointment>
        implements ViewAppointmentService {

    @Autowired
    private ViewAppointmentMapper viewAppointmentMapper;

    @Autowired
    private ApartmentInfoService apartmentInfoService;
    @Autowired
    private GraphInfoMapper graphInfoMapper;

    @Override
    public List<AppointmentItemVo> listItem() {
        Long userId = AppUserContext.get().getUserId();
        return viewAppointmentMapper.listItem(userId);
    }

    @Override
    public AppointmentDetailVo getDetailById(Long id) {
        AppointmentDetailVo vo = new AppointmentDetailVo();
        ViewAppointment viewAppointment = this.getById(id);
        BeanUtils.copyProperties(viewAppointment, vo);
        ApartmentItemVo apartmentItemVo = apartmentInfoService.selectApartmentItemVoById(viewAppointment.getApartmentId());
        vo.setApartmentItemVo(apartmentItemVo);
        return vo;
    }

    @Override
    public List<AppointmentItemVo> listItemByUserId(Long userId) {
        List<AppointmentItemVo> appointmentItemVoList = viewAppointmentMapper.listItemByUserId(userId);
        for (AppointmentItemVo appointmentItemVo : appointmentItemVoList) {
            Long apartmentId = appointmentItemVo.getApartmentId();
            List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT, apartmentId);
            appointmentItemVo.setGraphVoList(graphVoList);
        }
        return appointmentItemVoList;
    }
}




