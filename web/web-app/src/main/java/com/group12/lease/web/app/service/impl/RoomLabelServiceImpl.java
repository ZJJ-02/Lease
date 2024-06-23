package com.group12.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group12.lease.model.entity.RoomLabel;
import com.group12.lease.web.app.service.RoomLabelService;
import com.group12.lease.web.app.mapper.RoomLabelMapper;
import org.springframework.stereotype.Service;

@Service
public class RoomLabelServiceImpl extends ServiceImpl<RoomLabelMapper, RoomLabel>
    implements RoomLabelService{

}




