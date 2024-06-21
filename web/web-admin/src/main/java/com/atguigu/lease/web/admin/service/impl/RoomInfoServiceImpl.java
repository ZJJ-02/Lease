package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.web.admin.mapper.*;
import com.atguigu.lease.web.admin.service.*;
import com.atguigu.lease.web.admin.vo.graph.GraphVo;
import com.atguigu.lease.web.admin.vo.room.RoomDetailVo;
import com.atguigu.lease.web.admin.vo.room.RoomItemVo;
import com.atguigu.lease.web.admin.vo.room.RoomQueryVo;
import com.atguigu.lease.web.admin.vo.room.RoomSubmitVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {

    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Autowired
    private AttrValueMapper attrValueMapper;

    @Autowired
    private FacilityInfoMapper facilityInfoMapper;

    @Autowired
    private LabelInfoMapper labelInfoMapper;

    @Autowired
    private PaymentTypeMapper paymentTypeMapper;

    @Autowired
    private LeaseTermMapper leaseTermMapper;

    @Autowired
    private ApartmentInfoService apartmentInfoService;

    @Autowired
    private GraphInfoService graphInfoService;

    @Autowired
    private RoomAttrValueService roomAttrValueService;

    @Autowired
    private RoomFacilityService roomFacilityService;

    @Autowired
    private RoomLabelService roomLabelService;

    @Autowired
    private RoomPaymentTypeService roomPaymentTypeService;

    @Autowired
    private RoomLeaseTermService roomLeaseTermService;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Transactional
    @Override
    public void saveOrUpdateRoomInfo(RoomSubmitVo roomSubmitVo) {
        // 判断入参是否有id，若有，先删除房间关联信息
        CompletableFuture<Void> deleteRoomInfoFuture = CompletableFuture.runAsync(() -> {
            Long voId = roomSubmitVo.getId();
            if (voId != null) asyncRemoveRoomInfo(voId);
        }, threadPoolExecutor);
        // 更新房间信息
        CompletableFuture<Long> infoFuture = deleteRoomInfoFuture.supplyAsync(() -> {
            RoomInfo roomInfo = new RoomInfo();
            BeanUtils.copyProperties(roomSubmitVo, roomInfo);
            saveOrUpdate(roomInfo);
            return roomInfo.getId();
        }, threadPoolExecutor);
        // 更新图片信息
        infoFuture.thenAcceptAsync((roomId) -> {
            List<GraphInfo> graphInfos = roomSubmitVo.getGraphVoList().stream().map(item -> {
                GraphInfo graph = new GraphInfo();
                BeanUtils.copyProperties(item, graph);
                graph.setItemType(ItemType.ROOM);
                graph.setItemId(roomId);
                return graph;
            }).collect(Collectors.toList());
            graphInfoService.saveBatch(graphInfos);
        }, threadPoolExecutor);
        // 更新属性信息
        infoFuture.thenAcceptAsync((roomId) -> {
            List<RoomAttrValue> roomAttrValues = roomSubmitVo.getAttrValueIds().stream().map(item -> {
                RoomAttrValue roomAttrValue = RoomAttrValue.builder()
                        .roomId(roomId)
                        .attrValueId(item)
                        .build();
                return roomAttrValue;
            }).collect(Collectors.toList());
            roomAttrValueService.saveBatch(roomAttrValues);
        }, threadPoolExecutor);
        // 更新配套信息
        infoFuture.thenAcceptAsync((roomId) -> {
            List<RoomFacility> roomFacilities = roomSubmitVo.getFacilityInfoIds().stream().map(item -> {
                RoomFacility roomFacility = RoomFacility.builder()
                        .roomId(roomId)
                        .facilityId(item)
                        .build();
                return roomFacility;
            }).collect(Collectors.toList());
            roomFacilityService.saveBatch(roomFacilities);
        }, threadPoolExecutor);
        // 更新标签信息
        infoFuture.thenAcceptAsync((roomId) -> {
            List<RoomLabel> roomLabels = roomSubmitVo.getLabelInfoIds().stream().map(item -> {
                RoomLabel roomLabel = RoomLabel.builder()
                        .roomId(roomId)
                        .labelId(item)
                        .build();
                return roomLabel;
            }).collect(Collectors.toList());
            roomLabelService.saveBatch(roomLabels);
        }, threadPoolExecutor);
        // 更新支付方式
        infoFuture.thenAcceptAsync((roomId) -> {
            List<RoomPaymentType> roomPaymentTypes = roomSubmitVo.getPaymentTypeIds().stream().map(item -> {
                RoomPaymentType roomPaymentType = RoomPaymentType.builder()
                        .roomId(roomId)
                        .paymentTypeId(item)
                        .build();
                return roomPaymentType;
            }).collect(Collectors.toList());
            roomPaymentTypeService.saveBatch(roomPaymentTypes);
        }, threadPoolExecutor);
        // 更新可选租期
        infoFuture.thenAcceptAsync((roomId) -> {
            List<RoomLeaseTerm> roomLeaseTerms = roomSubmitVo.getLeaseTermIds().stream().map(item -> {
                RoomLeaseTerm roomLeaseTerm = RoomLeaseTerm.builder()
                        .roomId(roomId)
                        .leaseTermId(item)
                        .build();
                return roomLeaseTerm;
            }).collect(Collectors.toList());
            roomLeaseTermService.saveBatch(roomLeaseTerms);
        }, threadPoolExecutor);

    }

    @Override
    public IPage<RoomItemVo> pageItem(long current, long size, RoomQueryVo queryVo) {
        Page<RoomItemVo> roomItemVoPage = new Page<>(current, size);
        return roomInfoMapper.pageRoomItemByQuery(roomItemVoPage, queryVo);
    }

    @Override
    public RoomDetailVo getDetailById(Long id) {
        RoomDetailVo resultVo = new RoomDetailVo();
        // 获取房间详情和公寓信息
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
            RoomInfo roomInfo = this.getById(id);
            BeanUtils.copyProperties(roomInfo, resultVo);
            return roomInfo;
        }, threadPoolExecutor).thenAcceptAsync((roomInfo) -> {
            resultVo.setApartmentInfo(apartmentInfoService.getById(roomInfo.getApartmentId()));
        }, threadPoolExecutor);
        // 获取图片列表
        CompletableFuture<Void> graphVosFuture = CompletableFuture.runAsync(() -> {
            List<GraphInfo> graphInfos = graphInfoService.lambdaQuery()
                    .eq(GraphInfo::getItemId, id)
                    .eq(GraphInfo::getItemType, ItemType.ROOM)
                    .list();
            List<GraphVo> graphVos = graphInfos.stream().map(item -> {
                GraphVo graphVo = new GraphVo();
                BeanUtils.copyProperties(item, graphVo);
                return graphVo;
            }).collect(Collectors.toList());
            resultVo.setGraphVoList(graphVos);
        }, threadPoolExecutor);
        // 获取属性信息列表
        CompletableFuture<Void> attrValueVosFuture = CompletableFuture.runAsync(() -> {
            resultVo.setAttrValueVoList(attrValueMapper.getAttrValueVosByRoomId(id));
        }, threadPoolExecutor);
        // 获取配套信息列表
        CompletableFuture<Void> facilityInfoFuture = CompletableFuture.runAsync(() -> {
            resultVo.setFacilityInfoList(facilityInfoMapper.getFacilityInfos(id));
        }, threadPoolExecutor);
        // 获取标签信息列表
        CompletableFuture<Void> labelInfoFuture = CompletableFuture.runAsync(() -> {
            resultVo.setLabelInfoList(labelInfoMapper.getLabelInfos(id));
        }, threadPoolExecutor);
        // 获取支付方式列表
        CompletableFuture<Void> paymentTypeFuture = CompletableFuture.runAsync(() -> {
            resultVo.setPaymentTypeList(paymentTypeMapper.getPaymentTypes(id));
        }, threadPoolExecutor);
        // 获取可选租期列表
        CompletableFuture<Void> leaseTermFuture = CompletableFuture.runAsync(() -> {
            resultVo.setLeaseTermList(leaseTermMapper.getLeaseTerms(id));
        }, threadPoolExecutor);
        // 等待所有任务全部完成
        try {
            CompletableFuture.allOf(completableFuture,
                    graphVosFuture,
                    attrValueVosFuture,
                    facilityInfoFuture,
                    labelInfoFuture,
                    paymentTypeFuture,
                    leaseTermFuture).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultVo;
    }

    @Transactional
    @Override
    public void delRoomInfoById(Long id) {
        // 删除房间详情
        CompletableFuture.runAsync(() -> this.removeById(id), threadPoolExecutor);
        asyncRemoveRoomInfo(id);
    }

    /**
     * 异步删除指定房间的关联信息
     *
     * @param id
     */
    private void asyncRemoveRoomInfo(Long id) {
        // 删除房间图片列表
        CompletableFuture.runAsync(() -> {
            graphInfoService.remove(new LambdaQueryWrapper<GraphInfo>()
                    .eq(GraphInfo::getItemType, ItemType.ROOM)
                    .eq(GraphInfo::getItemId, id));
        }, threadPoolExecutor);
        // 删除房间属性信息列表
        CompletableFuture.runAsync(() -> {
            roomAttrValueService.remove(new LambdaQueryWrapper<RoomAttrValue>()
                    .eq(RoomAttrValue::getRoomId, id));
        }, threadPoolExecutor);
        // 删除房间配套信息列表
        CompletableFuture.runAsync(() -> {
            roomFacilityService.remove(new LambdaQueryWrapper<RoomFacility>()
                    .eq(RoomFacility::getRoomId, id));
        }, threadPoolExecutor);
        // 删除房间标签信息列表
        CompletableFuture.runAsync(() -> {
            roomLabelService.remove(new LambdaQueryWrapper<RoomLabel>()
                    .eq(RoomLabel::getRoomId, id));
        }, threadPoolExecutor);
        // 删除房间支付方式列表
        CompletableFuture.runAsync(() -> {
            roomPaymentTypeService.remove(new LambdaQueryWrapper<RoomPaymentType>()
                    .eq(RoomPaymentType::getRoomId, id));
        }, threadPoolExecutor);
        // 删除房间可选租期列表
        CompletableFuture.runAsync(() -> {
            roomLeaseTermService.remove(new LambdaQueryWrapper<RoomLeaseTerm>()
                    .eq(RoomLeaseTerm::getRoomId, id));
        }, threadPoolExecutor);
    }
}




