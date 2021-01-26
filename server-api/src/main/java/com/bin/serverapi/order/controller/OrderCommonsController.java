package com.bin.serverapi.order.controller;


import com.bin.serverapi.order.bo.OrderOperTypeBo;
import com.bin.serverapi.order.bo.OrderTypeBo;
import com.bin.serverapi.order.constant.OrderCommonsConstant;
import lombok.extern.slf4j.Slf4j;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author subin
 */
@Slf4j
@RestController
@RequestMapping("/order/commons")
public class OrderCommonsController {
    @GetMapping("/orderType/list")
    public ResponseModel<List<OrderTypeBo>> listAllOrderTypeBo(){
        return ResponseBuilder.success(OrderCommonsConstant.OrderTypeList());
    }
    @GetMapping("/operType/list")
    public ResponseModel<List<OrderOperTypeBo>> listAllOrderOperTypeBo(){
        return ResponseBuilder.success(OrderCommonsConstant.OrderOperTypeList());
    }
}