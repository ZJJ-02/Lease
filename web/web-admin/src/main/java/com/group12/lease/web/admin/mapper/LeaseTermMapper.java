package com.group12.lease.web.admin.mapper;

import com.group12.lease.model.entity.LeaseTerm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface LeaseTermMapper extends BaseMapper<LeaseTerm> {

    List<LeaseTerm> getLeaseTerms(Long id);
}




