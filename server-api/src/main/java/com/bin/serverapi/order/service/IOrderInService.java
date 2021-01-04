package com.bin.serverapi.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.serverapi.order.entity.OrderIn;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.serverapi.order.entity.OrderInDetail;
import com.bin.serverapi.order.vo.OrderInVo;
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
public interface IOrderInService extends IService<OrderIn> {
    ServiceResponse<Long> saveOrder(OrderIn orderIn, List<OrderInDetail> orderInDetailList);
    IPage<OrderInVo> pageOrderInVoWithDetail(IPage<OrderInVo> page);
    ServiceResponse<Long> removeAll(Long id);
}
