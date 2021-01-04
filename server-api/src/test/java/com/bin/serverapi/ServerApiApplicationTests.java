package com.bin.serverapi;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.bin.serverapi.order.entity.OrderIn;
import com.bin.serverapi.order.service.IOrderInService;
import com.bin.serverapi.product.service.IProductCategoryService;
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

    @Test
    void contextLoads() {
        OrderIn orderIn = new OrderIn();
        orderIn.setId(1L);
        orderIn.setCenterId(1L);
        orderIn.setWellName("测试静好");
        OrderIn order = new OrderIn();
        order.setOrderDate(LocalDate.now());
        BeanUtil.copyProperties(orderIn,order,CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        log.info(JsonConverterBin.transferToJson(order));
    }
}
