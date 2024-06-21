package com.group12.lease.web.admin.service.impl;

import com.group12.lease.model.entity.FeeKey;
import com.group12.lease.model.entity.FeeValue;
import com.group12.lease.web.admin.mapper.FeeKeyMapper;
import com.group12.lease.web.admin.mapper.FeeValueMapper;
import com.group12.lease.web.admin.service.FeeKeyService;
import com.group12.lease.web.admin.vo.fee.FeeKeyVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author liubo
* @description 针对表【fee_key(杂项费用名称表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class FeeKeyServiceImpl extends ServiceImpl<FeeKeyMapper, FeeKey>
    implements FeeKeyService{

    @Autowired
    private FeeKeyMapper feeKeyMapper;

    @Autowired
    private FeeValueMapper feeValueMapper;

    @Override
    public List<FeeKeyVo> feeInfoList() {
        List<FeeKeyVo> feeKeyVos = feeKeyMapper.feeInfoList();
        return feeKeyVos;
    }

    @Transactional
    @Override
    public void removeFeeKeyById(Long feeKeyId) {
        feeKeyMapper.deleteById(feeKeyId);
        feeValueMapper.delete(new LambdaQueryWrapper<FeeValue>()
                .eq(FeeValue::getFeeKeyId, feeKeyId));
    }
}




