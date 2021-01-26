package com.bin.serverapi.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bin.serverapi.order.bo.OrderInOutSearchDataBo;
import com.bin.serverapi.order.entity.OrderInDetail;
import com.bin.serverapi.order.mapper.OrderInDetailMapper;
import com.bin.serverapi.order.service.IOrderInDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.serverapi.report.vo.ReportStoreAccountVo;
import com.bin.serverapi.store.bo.StoreSearchForInOutOrder;
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
public class OrderInDetailServiceImpl extends ServiceImpl<OrderInDetailMapper, OrderInDetail> implements IOrderInDetailService {
    @Override
    public IPage<Map<String, Object>> pageWithOrderInToday(IPage<Map<String, Object>> page) {
        return this.baseMapper.selectWithOrderInToday(page);
    }

    @Override
    public IPage<Map<String,Object>> pageWithOrderIn(IPage<Map<String, Object>> page, OrderInOutSearchDataBo searchDataBo) {
        return this.baseMapper.selectWithOrderIn(page,searchDataBo);
    }

    @Override
    public Map<Long, ReportStoreAccountVo> listForStoreAccount(Wrapper<OrderInDetail> queryWrapper) {
        return this.baseMapper.selectForStoreAccount(queryWrapper);
    }
}
