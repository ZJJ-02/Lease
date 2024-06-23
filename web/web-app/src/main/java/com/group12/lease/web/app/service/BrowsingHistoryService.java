package com.group12.lease.web.app.service;

import com.group12.lease.model.entity.BrowsingHistory;
import com.group12.lease.web.app.vo.history.HistoryItemVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


public interface BrowsingHistoryService extends IService<BrowsingHistory> {

    IPage<HistoryItemVo> pageItem(long current, long size);

    void saveHistory(Long userId, Long roomId);
}
