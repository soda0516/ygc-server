package com.bin.serverapi.order.controller;

import com.bin.serverapi.order.bo.OrderInternalSearchDataBo;
import com.bin.serverapi.order.service.IOrderInternalDetailService;
import com.bin.serverapi.order.entity.OrderInternalDetail;
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

@Slf4j
@RestController
@RequestMapping("/order/orderInternalDetail")
public class OrderInternalDetailController {

    @Autowired
    public IOrderInternalDetailService orderInternalDetailService;

    /**
     * 保存和修改公用的
     * @param orderInternalDetail  传递的实体
     * @return ResponseModel转换结果
     */
    @PostMapping
    public ResponseModel<String> save(@RequestBody OrderInternalDetail orderInternalDetail){
        try {
            if(orderInternalDetail.getId()!=null){
                orderInternalDetailService.updateById(orderInternalDetail);
            }else{
                orderInternalDetailService.save(orderInternalDetail);
            }
            return ResponseBuilder.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.failure("保存对象失败");
        }
    }

    @GetMapping(value = "/page/detail")
    public ResponseModel<IPage<Map<String,Object>>> pageWithDetail(
            @RequestParam("searchData") String searchData,
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("pages") Integer pages){
        OrderInternalSearchDataBo orderInternalSearchDataBo = JsonConverterBin.transferToObject(searchData,
                OrderInternalSearchDataBo.class);
        IPage<Map<String,Object>> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pages);
        return ResponseBuilder.success(orderInternalDetailService.pageWithOrderInternal(page,orderInternalSearchDataBo));
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @DeleteMapping(value="/{id}")
    public ResponseModel<String> delete(@PathVariable("id") Long id){
        try {
            orderInternalDetailService.removeById(id);
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
    public ResponseModel<OrderInternalDetail> get(@PathVariable("id") Long id) {
        return ResponseBuilder.success(orderInternalDetailService.getById(id));
    }

    /**
     * 查看所有的员工信息
     * @return
     */
    @GetMapping(value = "/list/order/{id}")
    public ResponseModel<List<OrderInternalDetail>> listByOrderId(@PathVariable("id") Long id){
        return ResponseBuilder.success(orderInternalDetailService.lambdaQuery().eq(OrderInternalDetail::getOrderId,id).list());
    }

    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping(value = "/list")
    public ResponseModel<List<OrderInternalDetail>> list(){
        return ResponseBuilder.success(orderInternalDetailService.list());
    }


    /**
    * 分页查询数据
    * @return PageList 分页对象
    */
    @GetMapping(value = "/page")
    public ResponseModel<IPage<OrderInternalDetail>> page(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("size") Integer size,
            @RequestParam("pages") Integer pages) {
        IPage<OrderInternalDetail> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(size);
        page.setPages(pages);
        return ResponseBuilder.success(orderInternalDetailService.page(page));
    }
}