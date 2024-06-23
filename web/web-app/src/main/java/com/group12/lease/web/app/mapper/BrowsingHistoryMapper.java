package com.group12.lease.web.app.mapper;

import com.group12.lease.model.entity.BrowsingHistory;
import com.group12.lease.web.app.vo.history.HistoryItemVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

public interface BrowsingHistoryMapper extends BaseMapper<BrowsingHistory> {

    IPage<HistoryItemVo> pageItem(IPage<HistoryItemVo> page, @Param("userId") Long userId);
}




