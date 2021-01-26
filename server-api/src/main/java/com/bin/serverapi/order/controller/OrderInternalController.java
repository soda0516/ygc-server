package com.bin.serverapi.order.controller;

import com.bin.serverapi.order.entity.OrderIn;
import com.bin.serverapi.order.entity.OrderInDetail;
import com.bin.serverapi.order.entity.OrderInternalDetail;
import com.bin.serverapi.order.service.IOrderInternalService;
import com.bin.serverapi.order.entity.OrderInternal;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.response.service.ServiceResponse;
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author subin
 */
@Slf4j
@RestController
@RequestMapping("/order/orderInternal")
public class OrderInternalController {

    @Autowired
    public IOrderInternalService orderInternalService;

    /**
     * 保存和修改公用的
     * @param orderInStr  传递的实体
     * @param orderInDetailListStr  传递的实体
     * @return ResponseModel转换结果
     */
    @PostMapping
    public ResponseModel<Long> save(
            @RequestParam("order") String orderInStr,
            @RequestParam("orderDetailList") String orderInDetailListStr){
        log.info(orderInStr);
        OrderInternal orderInternal = JsonConverterBin.transferToObject(orderInStr, OrderInternal.class);
        log.info(JsonConverterBin.transferToJson(orderInternal));
        List<OrderInternalDetail> orderInternalDetailList = JsonConverterBin.transferToObjectList(orderInDetailListStr,
                OrderInternalDetail.class);
        log.info(orderInDetailListStr);
//        LocalDate end = LocalDate.now();
//        LocalDate start = end.minusYears(8);
//        orderInternal.setInternalDate(start);
//        while (start.isBefore(end)){
//            orderInternal.setInternalDate(start);
//            log.info(orderInternal.getInternalDate().toString());
//            for (int i = 0; i < 5; i++) {
//                if (Objects.nonNull(orderInternal.getId())){
//                    orderInternal.setId(null);
//                }
//                ServiceResponse<Long> longServiceResponse = orderInternalService.saveOrder(orderInternal, orderInternalDetailList);
//            }
//            start= start.plusDays(1);
//            orderInternal.setInternalDate(start);
//        }
        ServiceResponse<Long> longServiceResponse = orderInternalService.saveOrder(orderInternal, orderInternalDetailList);
        return ResponseBuilder.success(longServiceResponse.getData());
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @DeleteMapping(value="/{id}")
    public ResponseModel<String> delete(@PathVariable("id") Long id){
        try {
            orderInternalService.removeAll(id);
            return ResponseBuilder.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.failure("删除对象失败");
        }
    }

    /**
    * 查看单个信息
    * @return
    */
    @GetMapping(value = "/{id}")
    public ResponseModel<OrderInternal> get(@PathVariable("id") Long id) {
        return ResponseBuilder.success(orderInternalService.getById(id));
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping(value = "/list")
    public ResponseModel<List<OrderInternal>> list(){
        return ResponseBuilder.success(orderInternalService.list());
    }


    /**
    * 分页查询数据
    * @return PageList 分页对象
    */
    @GetMapping(value = "/page")
    public ResponseModel<IPage<OrderInternal>> page(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("size") Integer size,
            @RequestParam("pages") Integer pages) {
        IPage<OrderInternal> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(size);
        page.setPages(pages);
        return ResponseBuilder.success(orderInternalService.page(page));
    }
}