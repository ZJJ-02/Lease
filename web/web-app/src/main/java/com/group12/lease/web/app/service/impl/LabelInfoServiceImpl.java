package com.group12.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group12.lease.model.entity.LabelInfo;
import com.group12.lease.web.app.service.LabelInfoService;
import com.group12.lease.web.app.mapper.LabelInfoMapper;
import org.springframework.stereotype.Service;


@Service
public class LabelInfoServiceImpl extends ServiceImpl<LabelInfoMapper, LabelInfo>
    implements LabelInfoService{

}




