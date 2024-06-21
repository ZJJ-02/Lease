package com.group12.lease.web.app.mapper;

import com.group12.lease.model.entity.GraphInfo;
import com.group12.lease.model.enums.ItemType;
import com.group12.lease.web.app.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author liubo
* @description 针对表【graph_info(图片信息表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.group12.lease.model.entity.GraphInfo
*/
public interface GraphInfoMapper extends BaseMapper<GraphInfo> {

    List<GraphVo> selectListByItemTypeAndId(@Param("itemType") ItemType itemType, @Param("id") Long id);
}




