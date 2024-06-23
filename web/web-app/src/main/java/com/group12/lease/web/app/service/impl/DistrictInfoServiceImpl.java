package com.group12.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group12.lease.model.entity.DistrictInfo;
import com.group12.lease.web.app.service.DistrictInfoService;
import com.group12.lease.web.app.mapper.DistrictInfoMapper;
import org.springframework.stereotype.Service;


@Service
public class DistrictInfoServiceImpl extends ServiceImpl<DistrictInfoMapper, DistrictInfo>
    implements DistrictInfoService{

}




