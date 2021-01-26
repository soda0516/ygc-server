package com.bin.serverapi.order.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bin.serverapi.order.bo.OrderInOutSearchDataBo;
import com.bin.serverapi.order.entity.OrderInDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.serverapi.report.vo.ReportStoreAccountVo;
import com.bin.serverapi.store.bo.StoreSearchForInOutOrder;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Soda
 * @since 2020-12-29
 */
public interface IOrderInDetailService extends IService<OrderInDetail>{
    /**
     * 查询当天的回收单子
     * @param page
     * @return
     */
    IPage<Map<String,Object>> pageWithOrderInToday(IPage<Map<String,Object>> page);

    /**
     * 根据条件查询
     * @param page
     * @param searchDataBo
     * @return
     */
    IPage<Map<String,Object>> pageWithOrderIn(IPage<Map<String,Object>> page, OrderInOutSearchDataBo searchDataBo);

    /**
     * 查询一个有key的，用来设置库存
     * @return
     */
    Map<Long, ReportStoreAccountVo> listForStoreAccount(Wrapper<OrderInDetail> queryWrapper);
}
