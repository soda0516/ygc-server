package com.bin.serverapi.order.controller;

import com.bin.serverapi.order.entity.OrderIn;
import com.bin.serverapi.order.entity.OrderInDetail;
import com.bin.serverapi.order.entity.OrderOutDetail;
import com.bin.serverapi.order.service.IOrderOutService;
import com.bin.serverapi.order.entity.OrderOut;
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
@RequestMapping("/order/orderOut")
public class OrderOutController {

    @Autowired
    public IOrderOutService orderOutService;

    /**
     * 保存和修改公用的
     * @param orderOutStr  传递的实体
     * @param orderOutDetailListStr  传递的实体
     * @return ResponseModel转换结果
     */
    @PostMapping
    public ResponseModel<Long> save(
            @RequestParam("order") String orderOutStr,
            @RequestParam("orderDetailList") String orderOutDetailListStr){
        log.info(orderOutStr);
        OrderOut orderIn = JsonConverterBin.transferToObject(orderOutStr, OrderOut.class);
        log.info(JsonConverterBin.transferToJson(orderIn));
        List<OrderOutDetail> orderInDetails = JsonConverterBin.transferToObjectList(orderOutDetailListStr,
                OrderOutDetail.class);
//        LocalDate end = LocalDate.now();
//        LocalDate start = end.minusYears(8);
//        orderIn.setOrderDate(start);
//        while (start.isBefore(end)){
//            orderIn.setOrderDate(start);
//            log.info(orderIn.getOrderDate().toString());
//            for (int i = 0; i < 10 ; i++) {
//                if (Objects.nonNull(orderIn.getId())){
//                    orderIn.setId(null);
//                }
//                ServiceResponse<Long> longServiceResponse = orderOutService.saveOrder(orderIn, orderInDetails);
//            }
//            start= start.plusDays(1);
//            orderIn.setOrderDate(start);
//        }
        ServiceResponse<Long> longServiceResponse = orderOutService.saveOrder(orderIn, orderInDetails);
        return ResponseBuilder.success(longServiceResponse.getData());
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @DeleteMapping(value="/{id}")
    public ResponseModel<String> delete(@PathVariable("id") Long id){
        log.info(String.valueOf(id));
        try {
            orderOutService.removeAll(id);
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
    public ResponseModel<OrderOut> get(@PathVariable("id") Long id) {
        return ResponseBuilder.success(orderOutService.getById(id));
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping(value = "/list")
    public ResponseModel<List<OrderOut>> list(){
        return ResponseBuilder.success(orderOutService.list());
    }


    /**
    * 分页查询数据
    * @return PageList 分页对象
    */
    @GetMapping(value = "/page")
    public ResponseModel<IPage<OrderOut>> page(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("size") Integer size,
            @RequestParam("pages") Integer pages) {
        IPage<OrderOut> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(size);
        page.setPages(pages);
        return ResponseBuilder.success(orderOutService.page(page));
    }
}