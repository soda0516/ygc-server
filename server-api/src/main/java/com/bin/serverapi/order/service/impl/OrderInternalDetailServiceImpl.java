package com.bin.serverapi.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bin.serverapi.order.bo.OrderInternalSearchDataBo;
import com.bin.serverapi.order.entity.OrderInternalDetail;
import com.bin.serverapi.order.mapper.OrderInternalDetailMapper;
import com.bin.serverapi.order.service.IOrderInternalDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.serverapi.report.vo.ReportStoreAccountVo;
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
    public IPage<Map<String, Object>> pageWithOrderInternalToday(IPage<Map<String, Object>> page) {
        return this.baseMapper.selectWithOrderInternalToday(page);
    }

    @Override
    public IPage<Map<String, Object>> pageWithOrderInternal(IPage<Map<String, Object>> page, OrderInternalSearchDataBo searchData) {
        return this.baseMapper.selectWithOrderInternal(page,searchData);
    }

    @Override
    public Map<Long, ReportStoreAccountVo> listSubtractForStoreAccount(Wrapper<OrderInternalDetail> queryWrapper) {
        return this.baseMapper.selectSubtractForStoreAccount(queryWrapper);
    }

    @Override
    public Map<Long, ReportStoreAccountVo> listAddForStoreAccount(Wrapper<OrderInternalDetail> queryWrapper) {
        return this.baseMapper.selectAddForStoreAccount(queryWrapper);
    }

    @Override
    public Map<Long, ReportStoreAccountVo> listAddSubtractForStoreAccount(Wrapper<OrderInternalDetail> queryWrapper) {
        return this.baseMapper.selectAddSubtractForStoreAccount(queryWrapper);
    }
}
