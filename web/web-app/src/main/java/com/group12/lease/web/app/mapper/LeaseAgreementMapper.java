package com.group12.lease.web.app.mapper;

import com.group12.lease.model.entity.LeaseAgreement;
import com.group12.lease.web.app.vo.agreement.AgreementItemVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface LeaseAgreementMapper extends BaseMapper<LeaseAgreement> {


    List<AgreementItemVo> listItem(String phone);
}




