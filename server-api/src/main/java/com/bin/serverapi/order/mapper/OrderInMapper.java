package com.bin.serverapi.order.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.serverapi.order.entity.OrderIn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.serverapi.order.vo.OrderInVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Soda
 * @since 2020-12-29
 */
public interface OrderInMapper extends BaseMapper<OrderIn> {
    IPage<OrderInVo> selectWithDetail(IPage<?> page);
}
