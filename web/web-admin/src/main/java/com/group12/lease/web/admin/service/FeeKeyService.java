package com.group12.lease.web.admin.service;

import com.group12.lease.model.entity.FeeKey;
import com.group12.lease.web.admin.vo.fee.FeeKeyVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface FeeKeyService extends IService<FeeKey> {

    List<FeeKeyVo> feeInfoList();

    void removeFeeKeyById(Long feeKeyId);
}
