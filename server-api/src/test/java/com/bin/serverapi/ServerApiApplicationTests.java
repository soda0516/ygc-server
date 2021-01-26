package com.bin.serverapi;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bin.serverapi.order.entity.OrderIn;
import com.bin.serverapi.order.entity.OrderInDetail;
import com.bin.serverapi.order.service.IOrderInDetailService;
import com.bin.serverapi.order.service.IOrderInService;
import com.bin.serverapi.order.service.IOrderInternalDetailService;
import com.bin.serverapi.order.service.IOrderOutDetailService;
import com.bin.serverapi.product.service.IProductCategoryService;
import com.bin.serverapi.report.service.IDailyReportVoService;
import com.bin.serverapi.report.service.IReportStoreAccountService;
import com.bin.serverapi.report.service.IWorkloadAccountService;
import com.bin.serverapi.store.bo.StoreSearchForInOutOrder;
import com.bin.serverapi.store.service.IStoreAccountService;
import lombok.extern.slf4j.Slf4j;
import me.subin.utils.JsonConverterBin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@Slf4j
@SpringBootTest
class ServerApiApplicationTests {

    @Autowired
    IProductCategoryService iProductCategoryService;
    @Autowired
    IOrderInService iOrderInService;
    @Autowired
    IStoreAccountService iStoreAccountService;
    @Autowired
    IOrderInDetailService iOrderInDetailService;
    @Autowired
    IOrderOutDetailService iOrderOutDetailService;
    @Autowired
    IOrderInternalDetailService iOrderInternalDetailService;
    @Autowired
    IReportStoreAccountService iReportStoreAccountService;
    @Autowired
    IWorkloadAccountService iWorkloadAccountService;
    @Autowired
    IDailyReportVoService iDailyReportVoService;

    @Test
    void contextLoads() {
    }
}
