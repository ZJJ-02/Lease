package com.group12.lease.web.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.group12.lease.common.exception.LeaseException;
import com.group12.lease.common.result.ResultCodeEnum;
import com.group12.lease.model.entity.ApartmentInfo;
import com.group12.lease.model.entity.FacilityInfo;
import com.group12.lease.model.entity.LabelInfo;
import com.group12.lease.model.entity.RoomInfo;
import com.group12.lease.model.enums.ItemType;
import com.group12.lease.web.app.mapper.*;
import com.group12.lease.web.app.service.ApartmentInfoService;
import com.group12.lease.web.app.vo.apartment.ApartmentDetailVo;
import com.group12.lease.web.app.vo.apartment.ApartmentItemVo;
import com.group12.lease.web.app.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {

    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;

    @Autowired
    private LabelInfoMapper labelInfoMapper;

    @Autowired
    private GraphInfoMapper graphInfoMapper;

    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Autowired
    private FacilityInfoMapper facilityInfoMapper;

    @Autowired
    private ThreadPoolExecutor threadPool;


    @Override
    public ApartmentItemVo selectApartmentItemVoById(Long id) {
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectApartmentById(id);

        List<LabelInfo> labelInfoList = labelInfoMapper.selectListByApartmentId(id);

        List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT, id);

        BigDecimal minRent = roomInfoMapper.selectMinRentByApartmentId(id);

        ApartmentItemVo apartmentItemVo = new ApartmentItemVo();

        BeanUtils.copyProperties(apartmentInfo, apartmentItemVo);
        apartmentItemVo.setGraphVoList(graphVoList);
        apartmentItemVo.setLabelInfoList(labelInfoList);
        apartmentItemVo.setMinRent(minRent);

        return apartmentItemVo;
    }

    @Override
    public ApartmentDetailVo getDetailById(Long id) {
        ApartmentDetailVo vo = new ApartmentDetailVo();
        // 获取公寓信息
        CompletableFuture<Void> apartmentInfoFuture = CompletableFuture.supplyAsync(() -> this.getById(id), threadPool)
                .thenAcceptAsync((info) -> {
                    boolean isDelete = info.getIsDeleted() == 0 ? false : true;
                    BeanUtils.copyProperties(info, vo);
                    vo.setIsDelete(isDelete);
                });
        // 获取图片列表
        CompletableFuture<Void> graphVosFuture = CompletableFuture.runAsync(() -> {
            List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT, id);
            vo.setGraphVoList(graphVoList);
        }, threadPool);
        // 获取标签列表
        CompletableFuture<Void> labelInfoFuture = CompletableFuture.runAsync(() -> {
            List<LabelInfo> labelInfoList = labelInfoMapper.selectListByApartmentId(id);
            vo.setLabelInfoList(labelInfoList);
        }, threadPool);
        // 获取配套列表
        CompletableFuture<Void> facilityInfosFuture = CompletableFuture.runAsync(() -> {
            List<FacilityInfo> list = facilityInfoMapper.selectListByApartmentId(id);
            vo.setFacilityInfoList(list);
        }, threadPool);
        // 获取租金最小值
        CompletableFuture<Void> minRentFuture = CompletableFuture.runAsync(() -> {
            QueryWrapper<RoomInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("min(rent) as minRent").eq("apartment_id", id);
            List<Map<String, Object>> maps = roomInfoMapper.selectMaps(queryWrapper);
            if (CollUtil.isNotEmpty(maps)) {
                BigDecimal minRent = (BigDecimal) maps.get(0).get("minRent");
                vo.setMinRent(minRent);
            }
        }, threadPool);
        try {
            CompletableFuture.allOf(apartmentInfoFuture,
                    graphVosFuture,
                    labelInfoFuture,
                    facilityInfosFuture,
                    minRentFuture).get();
        } catch (Exception e) {
            throw new LeaseException(ResultCodeEnum.SERVICE_ERROR);
        }
        return vo;
    }
}




