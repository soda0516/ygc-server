package com.bin.serverapi.order.service;

import com.bin.serverapi.order.entity.OrderOut;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.serverapi.order.entity.OrderOutDetail;
import me.subin.response.service.ServiceResponse;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Soda
 * @since 2020-12-29
 */
public interface IOrderOutService extends IService<OrderOut> {
    ServiceResponse<Long> saveOrder(OrderOut orderOut, List<OrderOutDetail> orderOutDetailList);
    ServiceResponse<Long> removeAll(Long id);
}
