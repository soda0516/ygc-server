package com.bin.serverapi.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bin.serverapi.order.bo.OrderInternalSearchDataBo;
import com.bin.serverapi.order.entity.OrderInternalDetail;
import com.bin.serverapi.order.mapper.OrderInternalDetailMapper;
import com.bin.serverapi.order.service.IOrderInternalDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Soda
 * @since 2020-12-29
 */
@Service
public class OrderInternalDetailServiceImpl extends ServiceImpl<OrderInternalDetailMapper, OrderInternalDetail> implements IOrderInternalDetailService {
    @Override
    public IPage<Map<String, Object>> pageWithOrderInternal(IPage<Map<String, Object>> page, OrderInternalSearchDataBo searchData) {
        return this.baseMapper.selectWithOrderInternal(page,searchData);
    }
}
