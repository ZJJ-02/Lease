package com.group12.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group12.lease.model.entity.ApartmentLabel;
import com.group12.lease.web.app.service.ApartmentLabelService;
import com.group12.lease.web.app.mapper.ApartmentLabelMapper;
import org.springframework.stereotype.Service;


@Service
public class ApartmentLabelServiceImpl extends ServiceImpl<ApartmentLabelMapper, ApartmentLabel>
    implements ApartmentLabelService{

}




