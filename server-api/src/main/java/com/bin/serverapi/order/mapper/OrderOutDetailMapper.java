package com.bin.serverapi.order.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bin.serverapi.order.bo.OrderInOutSearchDataBo;
import com.bin.serverapi.order.entity.OrderOutDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
    IPage<Map<String,Object>> selectWithOrderIn(IPage<?> page, @Param("searchData") OrderInOutSearchDataBo searchData);
}
