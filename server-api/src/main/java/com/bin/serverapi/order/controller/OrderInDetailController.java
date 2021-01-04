package com.bin.serverapi.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bin.serverapi.order.bo.OrderInOutSearchDataBo;
import com.bin.serverapi.order.service.IOrderInDetailService;
import com.bin.serverapi.order.entity.OrderInDetail;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.serverapi.order.vo.OrderInVo;
import lombok.extern.slf4j.Slf4j;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/order/orderInDetail")
public class OrderInDetailController {

    @Autowired
    public IOrderInDetailService orderInDetailService;

    @GetMapping(value = "/page/detail")
    public ResponseModel<IPage<Map<String,Object>>> pageWithDetail(
            @RequestParam("searchData") String searchData,
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("pages") Integer pages){
        log.info(searchData);
        OrderInOutSearchDataBo orderInOutSearchDataBo = JsonConverterBin.transferToObject(searchData,
                OrderInOutSearchDataBo.class);
        IPage<Map<String,Object>> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pages);
        return ResponseBuilder.success(orderInDetailService.pageWithOrderIn(page,orderInOutSearchDataBo));
    }

    /**
     * 根据id获取订单明细列表
     * @param id
     * @return
     */
    @GetMapping(value = "/list/{id}")
    public ResponseModel<List<OrderInDetail>> pageWithDetail(@PathVariable("id") Long id){
        LambdaQueryWrapper<OrderInDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderInDetail::getOrderId,id);
        List<OrderInDetail> list = orderInDetailService.list(lambdaQueryWrapper);
        return ResponseBuilder.success(list);
    }

    /**
     * 保存和修改公用的
     * @param orderInDetail  传递的实体
     * @return ResponseModel转换结果
     */
    @PostMapping
    public ResponseModel<String> save(@RequestBody OrderInDetail orderInDetail){
        try {
            if(orderInDetail.getId()!=null){
                orderInDetailService.updateById(orderInDetail);
            }else{
                orderInDetailService.save(orderInDetail);
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
            orderInDetailService.removeById(id);
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
    public ResponseModel<OrderInDetail> get(@PathVariable("id") Long id) {
        return ResponseBuilder.success(orderInDetailService.getById(id));
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping(value = "/list")
    public ResponseModel<List<OrderInDetail>> list(){
        return ResponseBuilder.success(orderInDetailService.list());
    }


    /**
    * 分页查询数据
    * @return PageList 分页对象
    */
    @GetMapping(value = "/page")
    public ResponseModel<IPage<OrderInDetail>> page(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("size") Integer size,
            @RequestParam("pages") Integer pages) {
        IPage<OrderInDetail> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(size);
        page.setPages(pages);
        return ResponseBuilder.success(orderInDetailService.page(page));
    }
}