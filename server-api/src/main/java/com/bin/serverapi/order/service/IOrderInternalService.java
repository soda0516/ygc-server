package com.bin.serverapi.order.service;

import com.bin.serverapi.order.entity.OrderInternal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.serverapi.order.entity.OrderInternalDetail;
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
public interface IOrderInternalService extends IService<OrderInternal> {
    ServiceResponse<Long> saveOrder(OrderInternal orderInternal, List<OrderInternalDetail> internalDetailList);
    ServiceResponse<Long> removeAll(Long id);
}
