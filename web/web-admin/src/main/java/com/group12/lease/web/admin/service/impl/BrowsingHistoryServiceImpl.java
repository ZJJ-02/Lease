package com.group12.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group12.lease.model.entity.BrowsingHistory;
import com.group12.lease.web.admin.service.BrowsingHistoryService;
import com.group12.lease.web.admin.mapper.BrowsingHistoryMapper;
import org.springframework.stereotype.Service;


@Service
public class BrowsingHistoryServiceImpl extends ServiceImpl<BrowsingHistoryMapper, BrowsingHistory>
    implements BrowsingHistoryService{

}




