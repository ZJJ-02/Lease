package com.group12.lease.web.app.service;

import com.group12.lease.model.entity.LeaseAgreement;
import com.group12.lease.web.app.vo.agreement.AgreementDetailVo;
import com.group12.lease.web.app.vo.agreement.AgreementItemVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface LeaseAgreementService extends IService<LeaseAgreement> {

    List<AgreementItemVo> listItem();

    AgreementDetailVo getAgreementDetailById(Long id);
}
