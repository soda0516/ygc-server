package com.bin.serverapi.order.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bin.serverapi.order.bo.OrderInternalSearchDataBo;
import com.bin.serverapi.order.entity.OrderInternalDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.serverapi.report.vo.ReportStoreAccountVo;

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
    /**
     * 查询当天的调拨单
     * @param page
     * @return
     */
    IPage<Map<String,Object>> pageWithOrderInternalToday(IPage<Map<String,Object>> page);

    /**
     * 根据条件查询调拨单详情
     * @param page
     * @param searchData
     * @return
     */
    IPage<Map<String,Object>> pageWithOrderInternal(IPage<Map<String,Object>> page, OrderInternalSearchDataBo searchData);

    /**
     * 查询一个有key的，用来设置库存
     * @param queryWrapper
     * @return
     */
    Map<Long, ReportStoreAccountVo> listAddForStoreAccount(Wrapper<OrderInternalDetail> queryWrapper);

    /**
     * 查询一个有key的，用来设置库存
     * @param queryWrapper
     * @return
     */
    Map<Long, ReportStoreAccountVo> listSubtractForStoreAccount(Wrapper<OrderInternalDetail> queryWrapper);

    /**
     * 查询一个有key的，用来设置库存
     * @param queryWrapper
     * @return
     */
    Map<Long, ReportStoreAccountVo> listAddSubtractForStoreAccount(Wrapper<OrderInternalDetail> queryWrapper);
}
