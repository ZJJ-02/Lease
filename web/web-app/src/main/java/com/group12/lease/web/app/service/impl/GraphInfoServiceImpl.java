package com.group12.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group12.lease.model.entity.GraphInfo;
import com.group12.lease.web.app.service.GraphInfoService;
import com.group12.lease.web.app.mapper.GraphInfoMapper;
import org.springframework.stereotype.Service;


@Service
public class GraphInfoServiceImpl extends ServiceImpl<GraphInfoMapper, GraphInfo>
    implements GraphInfoService{

}




