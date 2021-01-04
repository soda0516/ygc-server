package com.bin.serverapi.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.serverapi.order.service.IOrderInService;
import com.bin.serverapi.order.vo.OrderInVo;
import lombok.extern.slf4j.Slf4j;
import me.subin.utils.JsonConverterBin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderInServiceImplTest {

    @Autowired
    IOrderInService iOrderInService;
    @Test
    void pageOrderInVoWithDetail() {
        IPage<OrderInVo> page = new Page<>();
        page.setCurrent(2);
        page.setPages(5);
        page.setSize(5);
        IPage<OrderInVo> orderInVoIPage = iOrderInService.pageOrderInVoWithDetail(page);
        log.info(JsonConverterBin.transferToJson(orderInVoIPage));
    }
}