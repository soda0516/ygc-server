package com.bin.serverapi.order.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bin.serverapi.order.bo.OrderInOutSearchDataBo;
import com.bin.serverapi.order.entity.OrderInDetail;
import com.bin.serverapi.order.entity.OrderOutDetail;
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
public interface IOrderOutDetailService extends IService<OrderOutDetail> {
    /**
     * 获取当天输入的单据信息
     * @param page
     * @return
     */
    IPage<Map<String,Object>> pageWithOrderOutToday(IPage<Map<String,Object>> page);

    /**
     * 根据条件获取单据信息
     * @param page
     * @param searchDataBo
     * @return
     */
    IPage<Map<String,Object>> pageWithOrderOut(IPage<Map<String,Object>> page, OrderInOutSearchDataBo searchDataBo);

    /**
     * 查询一个有key的，用来设置库存
     * @return
     */
    Map<Long, ReportStoreAccountVo> listForStoreAccount(Wrapper<OrderOutDetail> queryWrapper);
}
