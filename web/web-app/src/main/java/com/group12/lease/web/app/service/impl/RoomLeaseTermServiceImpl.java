package com.group12.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group12.lease.model.entity.RoomLeaseTerm;
import com.group12.lease.web.app.service.RoomLeaseTermService;
import com.group12.lease.web.app.mapper.RoomLeaseTermMapper;
import org.springframework.stereotype.Service;

@Service
public class RoomLeaseTermServiceImpl extends ServiceImpl<RoomLeaseTermMapper, RoomLeaseTerm>
    implements RoomLeaseTermService{

}




