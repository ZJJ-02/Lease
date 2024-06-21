package com.group12.lease.web.app;

import com.group12.lease.web.app.service.ApartmentInfoService;
import com.group12.lease.web.app.service.BrowsingHistoryService;
import com.group12.lease.web.app.service.LeaseAgreementService;
import com.group12.lease.web.app.service.ViewAppointmentService;
import com.group12.lease.web.app.vo.agreement.AgreementItemVo;
import com.group12.lease.web.app.vo.apartment.ApartmentDetailVo;
import com.group12.lease.web.app.vo.appointment.AppointmentItemVo;
import com.group12.lease.web.app.vo.history.HistoryItemVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AppWebApplicationTest {

    @Autowired
    private ApartmentInfoService apartmentInfoService;

    @Autowired
    BrowsingHistoryService browsingHistoryService;

    @Autowired
    ViewAppointmentService viewAppointmentService;

    @Autowired
    LeaseAgreementService leaseAgreementService;

    @Test
    void apartDetailTest() {
        ApartmentDetailVo vo = apartmentInfoService.getDetailById(9l);
        System.out.println(vo);
    }

    @Test
    void historyPageItemTest() {
        IPage<HistoryItemVo> page = browsingHistoryService.pageItem(1, 10);
        System.out.println(page.getRecords());
    }

    @Test
    void viewListItemTest() {
        List<AppointmentItemVo> voList = viewAppointmentService.listItem();
        System.out.println(voList);
    }

    @Test
    void leaseListItemTest() {
        List<AgreementItemVo> list = leaseAgreementService.listItem();
        System.out.println(list);
    }
}
