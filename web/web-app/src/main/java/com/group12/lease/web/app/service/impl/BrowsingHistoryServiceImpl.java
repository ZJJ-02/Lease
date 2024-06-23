package com.group12.lease.web.app.service.impl;

import com.group12.lease.common.context.AppUserContext;
import com.group12.lease.model.entity.BrowsingHistory;
import com.group12.lease.model.enums.ItemType;
import com.group12.lease.web.app.mapper.BrowsingHistoryMapper;
import com.group12.lease.web.app.mapper.GraphInfoMapper;
import com.group12.lease.web.app.service.BrowsingHistoryService;
import com.group12.lease.web.app.vo.graph.GraphVo;
import com.group12.lease.web.app.vo.history.HistoryItemVo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class BrowsingHistoryServiceImpl extends ServiceImpl<BrowsingHistoryMapper, BrowsingHistory>
        implements BrowsingHistoryService {

    @Autowired
    private BrowsingHistoryMapper browsingHistoryMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;

    @Override
    public IPage<HistoryItemVo> pageItem(long current, long size) {
        Long userId = AppUserContext.get().getUserId();
        IPage<HistoryItemVo> page = new Page<>(current, size);
        IPage<HistoryItemVo> pageVoList = browsingHistoryMapper.pageItem(page, userId);
        for (HistoryItemVo record : pageVoList.getRecords()) {
            List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.ROOM, record.getRoomId());
            record.setRoomGraphVoList(graphVoList);
        }
        return pageVoList;
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




