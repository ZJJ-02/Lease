package com.group12.lease.web.app.service.impl;

import com.group12.lease.common.context.AppUserContext;
import com.group12.lease.model.entity.*;
import com.group12.lease.model.enums.ItemType;
import com.group12.lease.web.app.mapper.*;
import com.group12.lease.web.app.service.LeaseAgreementService;
import com.group12.lease.web.app.vo.agreement.AgreementDetailVo;
import com.group12.lease.web.app.vo.agreement.AgreementItemVo;
import com.group12.lease.web.app.vo.graph.GraphVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {

    @Autowired
    private LeaseAgreementMapper leaseAgreementMapper;

    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;

    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Autowired
    private GraphInfoMapper graphInfoMapper;

    @Autowired
    private LeaseTermMapper leaseTermMapper;

    @Autowired
    private PaymentTypeMapper paymentTypeMapper;

    @Override
    public List<AgreementItemVo> listItem() {
        String phone = AppUserContext.get().getUsername();
        return leaseAgreementMapper.listItem(phone);
    }

    @Override
    public AgreementDetailVo getAgreementDetailById(Long id) {
        //1.查询租约信息
        LeaseAgreement leaseAgreement = leaseAgreementMapper.selectById(id);
        if (leaseAgreement == null) {
            return null;
        }
        //2.查询公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectApartmentById(leaseAgreement.getApartmentId());
        //3.查询房间信息
        RoomInfo roomInfo = roomInfoMapper.selectRoomById(leaseAgreement.getRoomId());
        //4.查询公寓图片
        List<GraphVo> apartmentGraphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT, leaseAgreement.getApartmentId());
        //5.查询房间图片
        List<GraphVo> roomGraphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.ROOM, leaseAgreement.getRoomId());
        //6.查询租期信息
        LeaseTerm leaseTerm = leaseTermMapper.selectById(leaseAgreement.getLeaseTermId());
        //7.查询支付方式
        PaymentType paymentType = paymentTypeMapper.selectById(leaseAgreement.getPaymentTypeId());
        AgreementDetailVo agreementDetailVo = new AgreementDetailVo();
        BeanUtils.copyProperties(leaseAgreement, agreementDetailVo);
        agreementDetailVo.setApartmentName(apartmentInfo.getName());
        agreementDetailVo.setRoomNumber(roomInfo.getRoomNumber());
        agreementDetailVo.setApartmentGraphVoList(apartmentGraphVoList);
        agreementDetailVo.setRoomGraphVoList(roomGraphVoList);
        agreementDetailVo.setPaymentTypeName(paymentType.getName());
        agreementDetailVo.setLeaseTermMonthCount(leaseTerm.getMonthCount());
        agreementDetailVo.setLeaseTermUnit(leaseTerm.getUnit());
        return agreementDetailVo;
    }
}




