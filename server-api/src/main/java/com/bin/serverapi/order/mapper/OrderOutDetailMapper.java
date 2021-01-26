package com.bin.serverapi.order.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.bin.serverapi.order.bo.OrderInOutSearchDataBo;
import com.bin.serverapi.order.entity.OrderInDetail;
import com.bin.serverapi.order.entity.OrderOutDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface OrderOutDetailMapper extends BaseMapper<OrderOutDetail> {
    /**
     * 查询今天的回收单据
     * @param page
     * @return
     */
    IPage<Map<String,Object>> selectWithOrderInToday(IPage<?> page);
    /**
     * 查询一个有key的，用来设置库
     * @param searchData
     * @return
     */
    IPage<Map<String,Object>> selectWithOrderOut(IPage<?> page, @Param("searchData") OrderInOutSearchDataBo searchData);

    /**
     * 查询一个有key的，用来设置库存
     * @param queryWrapper
     * @return
     */
    @MapKey("productDetailId")
    Map<Long, ReportStoreAccountVo> selectForStoreAccount(@Param(Constants.WRAPPER) Wrapper<OrderOutDetail> queryWrapper);
}
