package com.bin.serverapi.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bin.serverapi.order.bo.OrderInternalSearchDataBo;
import com.bin.serverapi.order.entity.OrderInternalDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Soda
 * @since 2020-12-29
 */
public interface IOrderInternalDetailService extends IService<OrderInternalDetail> {
    IPage<Map<String,Object>> pageWithOrderInternal(IPage<Map<String,Object>> page, OrderInternalSearchDataBo searchData);
}
