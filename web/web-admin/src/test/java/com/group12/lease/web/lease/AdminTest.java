package com.group12.lease.web.lease;

import com.group12.lease.model.entity.LeaseTerm;
import com.group12.lease.web.admin.AdminWebApplication;
import com.group12.lease.web.admin.service.AttrKeyService;
import com.group12.lease.web.admin.service.FeeKeyService;
import com.group12.lease.web.admin.service.LeaseTermService;
import com.group12.lease.web.admin.service.RoomInfoService;
import com.group12.lease.web.admin.vo.attr.AttrKeyVo;
import com.group12.lease.web.admin.vo.fee.FeeKeyVo;
import com.group12.lease.web.admin.vo.room.RoomDetailVo;
import com.group12.lease.web.admin.vo.room.RoomItemVo;
import com.group12.lease.web.admin.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = AdminWebApplication.class)
public class AdminTest {

    @Autowired
    LeaseTermService termService;

    @Autowired
    RoomInfoService roomInfoService;

    @Autowired
    FeeKeyService feeKeyService;

    @Autowired
    AttrKeyService attrKeyService;


    @Test
    void termConnTest(){
        LeaseTerm term = new LeaseTerm();
        term.setId(10l);
        term.setMonthCount(10);
        term.setUnit("月");
        termService.saveOrUpdate(term);
    }

    @Test
    void termLogicTest(){
        List<LeaseTerm> list = termService.list();
        System.out.println(list);
    }

    @Test
    void roomInfoPageTest(){
        RoomQueryVo vo = new RoomQueryVo();
        vo.setProvinceId(11l);
        vo.setApartmentId(9l);
        IPage<RoomItemVo> page = roomInfoService.pageItem(1, 10, vo);
        System.out.println(page.getRecords());
    }

    @Test
    void getRoomDetailTest() {
        RoomDetailVo detailById = roomInfoService.getDetailById(2l);
        System.out.println(detailById);
    }

    @Test
    void feeKeyServiceTest() {
        List<FeeKeyVo> feeKeyVos = feeKeyService.feeInfoList();
        System.out.println(feeKeyVos);
    }

    @Test
    void attrKeyServiceTest() {
        List<AttrKeyVo> attrKeyVos = attrKeyService.listAttrInfo();
        System.out.println(attrKeyVos);
    }
}
