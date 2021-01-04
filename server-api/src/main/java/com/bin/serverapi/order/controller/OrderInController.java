package com.bin.serverapi.order.controller;

import com.bin.serverapi.order.entity.OrderInDetail;
import com.bin.serverapi.order.service.IOrderInService;
import com.bin.serverapi.order.entity.OrderIn;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.serverapi.order.vo.OrderInVo;
import lombok.extern.slf4j.Slf4j;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.response.service.ServiceResponse;
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order/orderIn")
public class OrderInController {

    @Autowired
    public IOrderInService orderInService;

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
        OrderIn orderIn = JsonConverterBin.transferToObject(orderInStr, OrderIn.class);
        log.info(JsonConverterBin.transferToJson(orderIn));
        List<OrderInDetail> orderInDetails = JsonConverterBin.transferToObjectList(orderInDetailListStr,
                OrderInDetail.class);
        ServiceResponse<Long> longServiceResponse = orderInService.saveOrder(orderIn, orderInDetails);
        return ResponseBuilder.success(longServiceResponse.getData());
    }

    @GetMapping(value = "/page/detail")
    public ResponseModel<IPage<OrderInVo>> pageWithDetail(
            @RequestParam("searchData") String searchData,
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("size") Integer size,
            @RequestParam("pages") Integer pages){
        log.info(searchData);
        IPage<OrderInVo> page = new Page<>();
        page.setCurrent(currentPage);
        page.setPages(pages);
        page.setSize(size);
        IPage<OrderInVo> voWithDetail = orderInService.pageOrderInVoWithDetail(page);
        log.info(JsonConverterBin.transferToJson(voWithDetail));
        return ResponseBuilder.success(voWithDetail);
    }

    /**
     * 根据id获取订单信息
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseModel<OrderIn> getOrderDetailById(@PathVariable("id") Long id) {
        log.info(String.valueOf(id));
        return ResponseBuilder.success(orderInService.getById(id));
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
            orderInService.removeAll(id);
            return ResponseBuilder.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.failure("删除对象失败");
        }
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping(value = "/list")
    public ResponseModel<List<OrderIn>> list(){
        return ResponseBuilder.success(orderInService.list());
    }


    /**
    * 分页查询数据
    * @return PageList 分页对象
    */
    @GetMapping(value = "/page")
    public ResponseModel<IPage<OrderIn>> page(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("size") Integer size,
            @RequestParam("pages") Integer pages) {
        IPage<OrderIn> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(size);
        page.setPages(pages);
        return ResponseBuilder.success(orderInService.page(page));
    }
}