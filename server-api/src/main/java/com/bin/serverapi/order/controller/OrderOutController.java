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
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseModel<String> save(
            @RequestParam("order") String orderOutStr,
            @RequestParam("orderDetailList") String orderOutDetailListStr){
        log.info(orderOutStr);
        OrderOut orderIn = JsonConverterBin.transferToObject(orderOutStr, OrderOut.class);
        log.info(JsonConverterBin.transferToJson(orderIn));
        List<OrderOutDetail> orderInDetails = JsonConverterBin.transferToObjectList(orderOutDetailListStr,
                OrderOutDetail.class);
        orderOutService.saveOrder(orderIn,orderInDetails);
        return ResponseBuilder.success();
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