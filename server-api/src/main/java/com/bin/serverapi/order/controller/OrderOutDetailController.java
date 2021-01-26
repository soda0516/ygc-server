package com.bin.serverapi.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bin.serverapi.order.bo.OrderInOutSearchDataBo;
import com.bin.serverapi.order.entity.OrderInDetail;
import com.bin.serverapi.order.service.IOrderOutDetailService;
import com.bin.serverapi.order.entity.OrderOutDetail;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author subin
 */
@Slf4j
@RestController
@RequestMapping("/order/orderOutDetail")
public class OrderOutDetailController {

    @Autowired
    public IOrderOutDetailService orderOutDetailService;

    @GetMapping(value = "/page/detail")
    public ResponseModel<IPage<Map<String,Object>>> pageWithDetail(
            @RequestParam("today") Boolean today,
            @RequestParam("searchData") String searchData,
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("pages") Integer pages){
        log.info(searchData);
        OrderInOutSearchDataBo orderInOutSearchDataBo = JsonConverterBin.transferToObject(searchData,
                OrderInOutSearchDataBo.class);
        IPage<Map<String,Object>> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pages);
        if (today) {
            return ResponseBuilder.success(orderOutDetailService.pageWithOrderOutToday(page));
        }else {
            return ResponseBuilder.success(orderOutDetailService.pageWithOrderOut(page,orderInOutSearchDataBo));
        }
    }

    /**
     * 保存和修改公用的
     * @param orderOutDetail  传递的实体
     * @return ResponseModel转换结果
     */
    @PostMapping
    public ResponseModel<String> save(@RequestBody OrderOutDetail orderOutDetail){
        try {
            if(orderOutDetail.getId()!=null){
                orderOutDetailService.updateById(orderOutDetail);
            }else{
                orderOutDetailService.save(orderOutDetail);
            }
            return ResponseBuilder.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.failure("保存对象失败");
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @DeleteMapping(value="/{id}")
    public ResponseModel<String> delete(@PathVariable("id") Long id){
        try {
            orderOutDetailService.removeById(id);
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
    public ResponseModel<OrderOutDetail> get(@PathVariable("id") Long id) {
        return ResponseBuilder.success(orderOutDetailService.getById(id));
    }

    /**
     * 根据id获取订单明细列表
     * @param id
     * @return
     */
    @GetMapping(value = "/list/{id}")
    public ResponseModel<List<OrderOutDetail>> pageWithDetail(@PathVariable("id") Long id){
        LambdaQueryWrapper<OrderOutDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderOutDetail::getOrderId,id);
        List<OrderOutDetail> list = orderOutDetailService.list(lambdaQueryWrapper);
        return ResponseBuilder.success(list);
    }

    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping(value = "/list")
    public ResponseModel<List<OrderOutDetail>> list(){
        return ResponseBuilder.success(orderOutDetailService.list());
    }


    /**
    * 分页查询数据
    * @return PageList 分页对象
    */
    @GetMapping(value = "/page")
    public ResponseModel<IPage<OrderOutDetail>> page(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("size") Integer size,
            @RequestParam("pages") Integer pages) {
        IPage<OrderOutDetail> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(size);
        page.setPages(pages);
        return ResponseBuilder.success(orderOutDetailService.page(page));
    }
}