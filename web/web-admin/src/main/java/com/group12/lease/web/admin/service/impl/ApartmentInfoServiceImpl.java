package com.group12.lease.web.admin.service.impl;

import com.group12.lease.common.exception.LeaseException;
import com.group12.lease.common.result.ResultCodeEnum;
import com.group12.lease.model.entity.*;
import com.group12.lease.model.enums.ItemType;
import com.group12.lease.web.admin.mapper.*;
import com.group12.lease.web.admin.service.*;
import com.group12.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.group12.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.group12.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.group12.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;


@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {

    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;

    @Autowired
    private ApartmentFacilityService apartmentFacilityService;

    @Autowired
    private ApartmentLabelService apartmentLabelService;

    @Autowired
    private ApartmentFeeValueService apartmentFeeValueService;

    @Autowired
    private GraphInfoService graphInfoService;

    @Autowired
    private GraphInfoMapper graphInfoMapper;

    @Autowired
    private LabelInfoMapper labelInfoMapper;

    @Autowired
    private FacilityInfoMapper facilityInfoMapper;

    @Autowired
    private FeeValueMapper feeValueMapper;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Transactional
    @Override
    public void saveOrUpdateApartmentInfo(ApartmentSubmitVo apartmentSubmitVo) {
        if (!apartmentSubmitVo.getPhone().matches("1[3-9]\\d{9}")) {
            throw new LeaseException(ResultCodeEnum.PARAM_ERROR);
        }
        // 判断入参是否有id，若有，先删除公寓关联信息
        CompletableFuture<Void> deleteApartInfoFuture = CompletableFuture.runAsync(() -> {
            Long apartmentId = apartmentSubmitVo.getId();
            if (apartmentId != null) asyncRemoveApartInfo(apartmentId);
        }, threadPoolExecutor);
        // 保存公寓基本信息
        CompletableFuture<Long> apartmentFuture = deleteApartInfoFuture.supplyAsync(() -> {
            ApartmentInfo apartmentInfo = new ApartmentInfo();
            BeanUtils.copyProperties(apartmentSubmitVo, apartmentInfo);
            ApartmentInfo regionInfo = apartmentInfoMapper.getRegionInfo(apartmentInfo.getProvinceId(),
                    apartmentInfo.getCityId(),
                    apartmentInfo.getDistrictId());
            apartmentInfo.setProvinceName(regionInfo.getProvinceName());
            apartmentInfo.setCityName(regionInfo.getCityName());
            apartmentInfo.setDistrictName(regionInfo.getDistrictName());
            this.saveOrUpdate(apartmentInfo);
            return apartmentInfo.getId();
        }, threadPoolExecutor);
        // 保存公寓配套信息
        apartmentFuture.thenAcceptAsync((apartId) -> {
            List<Long> facilityInfoIds = apartmentSubmitVo.getFacilityInfoIds();
            List<ApartmentFacility> afs = facilityInfoIds.stream().map(item -> {
                return ApartmentFacility.builder().apartmentId(apartId).facilityId(item).build();
            }).collect(Collectors.toList());
            apartmentFacilityService.saveBatch(afs);
        }, threadPoolExecutor);
        // 保存公寓标签信息
        apartmentFuture.thenAcceptAsync((apartId) -> {
            List<Long> labelIds = apartmentSubmitVo.getLabelIds();
            List<ApartmentLabel> als = labelIds.stream().map(item -> {
                return ApartmentLabel.builder().apartmentId(apartId).labelId(item).build();
            }).collect(Collectors.toList());
            apartmentLabelService.saveBatch(als);
        }, threadPoolExecutor);
        // 保存公寓杂费信息
        apartmentFuture.thenAcceptAsync((apartId) -> {
            List<Long> feeValueIds = apartmentSubmitVo.getFeeValueIds();
            List<ApartmentFeeValue> afvs = feeValueIds.stream().map(item -> {
                return ApartmentFeeValue.builder().apartmentId(apartId).feeValueId(item).build();
            }).collect(Collectors.toList());
            apartmentFeeValueService.saveBatch(afvs);
        }, threadPoolExecutor);
        // 保存公寓图片信息
        apartmentFuture.thenAcceptAsync((apartId) -> {
            List<GraphInfo> graphInfos = apartmentSubmitVo.getGraphVoList().stream().map(item -> {
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setItemType(ItemType.APARTMENT);
                graphInfo.setItemId(apartId);
                graphInfo.setName(item.getName());
                graphInfo.setUrl(item.getUrl());
                return graphInfo;
            }).collect(Collectors.toList());
            graphInfoService.saveBatch(graphInfos);
        }, threadPoolExecutor);
    }

    @Override
    public IPage<ApartmentItemVo> pageApartmentItemByQuery(IPage<ApartmentItemVo> page, ApartmentQueryVo queryVo) {
        return apartmentInfoMapper.pageApartmentItemByQuery(page, queryVo);
    }

    @Override
    public ApartmentDetailVo getApartmentDetailById(Long id) {
        ApartmentDetailVo apartmentVo = new ApartmentDetailVo();
        //1.查询ApartmentInfo
        CompletableFuture<Void> ApartInfoFuture = CompletableFuture.supplyAsync(() -> {
            return this.getById(id);
        }, threadPoolExecutor).thenAccept(apartmentInfo -> BeanUtils.copyProperties(apartmentInfo, apartmentVo));
        //2.查询GraphInfo
        CompletableFuture<Void> graphVoFuture = CompletableFuture.supplyAsync(() -> {
            return graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT, id);
        }, threadPoolExecutor).thenAccept(graphVos -> apartmentVo.setGraphVoList(graphVos));
        //3.查询LabelInfo
        CompletableFuture<Void> labelInfoFuture = CompletableFuture.supplyAsync(() -> {
            return labelInfoMapper.selectListByApartmentId(id);
        }, threadPoolExecutor).thenAccept(labelInfos -> apartmentVo.setLabelInfoList(labelInfos));
        //4.查询FacilityInfo
        CompletableFuture<Void> facilityInfoFuture = CompletableFuture.supplyAsync(() -> {
            return facilityInfoMapper.selectListByApartmentId(id);
        }, threadPoolExecutor).thenAccept(facilityInfoList -> apartmentVo.setFacilityInfoList(facilityInfoList));
        //5.查询FeeValue
        CompletableFuture<Void> feeValFuture = CompletableFuture.supplyAsync(() -> {
            return feeValueMapper.selectListByApartmentId(id);
        }, threadPoolExecutor).thenAccept(feeValueVoList -> apartmentVo.setFeeValueVoList(feeValueVoList));
        try {
            CompletableFuture.allOf(ApartInfoFuture,
                    graphVoFuture,
                    labelInfoFuture,
                    facilityInfoFuture,
                    feeValFuture).get();
            return apartmentVo;
        } catch (Exception e) {
            throw new LeaseException(ResultCodeEnum.SERVICE_ERROR);
        }
    }

    @Override
    public void removeApartInfo(Long id) {
        CompletableFuture.runAsync(() -> this.removeById(id), threadPoolExecutor);
        asyncRemoveApartInfo(id);
    }

    /**
     * 删除公寓的关联信息
     *
     * @param id
     */
    private void asyncRemoveApartInfo(Long id) {
        //1.删除图片列表
        CompletableFuture.runAsync(() -> {
            graphInfoService.remove(new LambdaQueryWrapper<GraphInfo>()
                    .eq(GraphInfo::getItemType, ItemType.APARTMENT)
                    .eq(GraphInfo::getItemId, id));
        }, threadPoolExecutor);
        //2.删除配套列表
        CompletableFuture.runAsync(() -> {
            apartmentFacilityService.remove(new LambdaQueryWrapper<ApartmentFacility>()
                    .eq(ApartmentFacility::getApartmentId, id));
        }, threadPoolExecutor);
        //3.删除标签列表
        CompletableFuture.runAsync(() -> {
            apartmentLabelService.remove(new LambdaQueryWrapper<ApartmentLabel>()
                    .eq(ApartmentLabel::getApartmentId, id));
        }, threadPoolExecutor);
        //4.删除杂费列表
        CompletableFuture.runAsync(() -> {
            apartmentFeeValueService.remove(new LambdaQueryWrapper<ApartmentFeeValue>()
                    .eq(ApartmentFeeValue::getApartmentId, id));
        }, threadPoolExecutor);
    }
}




