package com.bin.serverapi.order.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bin.serverapi.order.bo.OrderInternalSearchDataBo;
import com.bin.serverapi.order.entity.OrderInternalDetail;
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
public interface OrderInternalDetailMapper extends BaseMapper<OrderInternalDetail> {
    IPage<Map<String,Object>> selectWithOrderInternal(IPage<?> page, @Param("searchData") OrderInternalSearchDataBo searchData);
}
