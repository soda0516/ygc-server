package com.bin.serverapi.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bin.serverapi.order.bo.OrderInOutSearchDataBo;
import com.bin.serverapi.order.entity.OrderOutDetail;
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
public interface IOrderOutDetailService extends IService<OrderOutDetail> {
    IPage<Map<String,Object>> pageWithOrderOut(IPage<Map<String,Object>> page, OrderInOutSearchDataBo searchDataBo);
}
