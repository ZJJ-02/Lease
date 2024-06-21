package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.common.context.AppUserContext;
import com.atguigu.lease.model.entity.BrowsingHistory;
import com.atguigu.lease.web.app.mapper.BrowsingHistoryMapper;
import com.atguigu.lease.web.app.service.BrowsingHistoryService;
import com.atguigu.lease.web.app.vo.history.HistoryItemVo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author liubo
 * @description 针对表【browsing_history(浏览历史)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
@Slf4j
public class BrowsingHistoryServiceImpl extends ServiceImpl<BrowsingHistoryMapper, BrowsingHistory>
        implements BrowsingHistoryService {

    @Autowired
    private BrowsingHistoryMapper browsingHistoryMapper;

    @Override
    public IPage<HistoryItemVo> pageItem(long current, long size) {
        Long userId = AppUserContext.get().getUserId();
        IPage<HistoryItemVo> page = new Page<>(current, size);
        IPage<HistoryItemVo> pageVo = browsingHistoryMapper.pageItem(page, userId);
        return pageVo;
    }

    @Override
    public void saveHistory(Long userId, Long roomId) {
        BrowsingHistory browsingHistory = new BrowsingHistory();
        browsingHistory.setUserId(userId);
        browsingHistory.setRoomId(roomId);
        browsingHistory.setBrowseTime(new Date());
        LambdaUpdateWrapper<BrowsingHistory> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(BrowsingHistory::getUserId, userId);
        updateWrapper.eq(BrowsingHistory::getRoomId, roomId);
        this.saveOrUpdate(browsingHistory, updateWrapper);
    }
}




