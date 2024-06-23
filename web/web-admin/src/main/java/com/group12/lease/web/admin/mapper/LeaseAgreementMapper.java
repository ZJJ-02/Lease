package com.group12.lease.web.admin.mapper;

import com.group12.lease.model.entity.LeaseAgreement;
import com.group12.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.group12.lease.web.admin.vo.agreement.AgreementVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;


public interface LeaseAgreementMapper extends BaseMapper<LeaseAgreement> {

    IPage<AgreementVo> pageAgreementByQuery(IPage<AgreementVo> page, AgreementQueryVo queryVo);
}




