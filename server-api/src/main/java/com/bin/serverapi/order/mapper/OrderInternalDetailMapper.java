package com.bin.serverapi.order.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.bin.serverapi.order.bo.OrderInternalSearchDataBo;
import com.bin.serverapi.order.entity.OrderInternalDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.serverapi.order.entity.OrderOutDetail;
import com.bin.serverapi.report.vo.ReportStoreAccountVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Soda
 * @since 2020-12-29
 */
public interface OrderInternalDetailMapper extends BaseMapper<OrderInternalDetail> {
    /**
     * 查询当天的调拨单
     * @param page
     * @return
     */
    IPage<Map<String,Object>> selectWithOrderInternalToday(IPage<?> page);

    /**
     * 根据条件查询单据详情
     * @param page
     * @param searchData
     * @return
     */
    IPage<Map<String,Object>> selectWithOrderInternal(IPage<?> page, @Param("searchData") OrderInternalSearchDataBo searchData);

    /**
     * 查询一个有key的，用来设置库存
     * @param queryWrapper
     * @return
     */
    @MapKey("productDetailId")
    Map<Long, ReportStoreAccountVo> selectAddForStoreAccount(@Param(Constants.WRAPPER) Wrapper<OrderInternalDetail> queryWrapper);

    /**
     * 查询一个有key的，用来设置库存
     * @param queryWrapper
     * @return
     */
    @MapKey("productDetailId")
    Map<Long, ReportStoreAccountVo> selectSubtractForStoreAccount(@Param(Constants.WRAPPER) Wrapper<OrderInternalDetail> queryWrapper);

    /**
     * 查询一个有key的，用来设置库存
     * @param queryWrapper
     * @return
     */
    @MapKey("productDetailId")
    Map<Long, ReportStoreAccountVo> selectAddSubtractForStoreAccount(@Param(Constants.WRAPPER) Wrapper<OrderInternalDetail> queryWrapper);
}
