package com.bin.serverapi.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bin.serverapi.order.bo.OrderInOutSearchDataBo;
import com.bin.serverapi.order.entity.OrderInDetail;
import com.bin.serverapi.order.entity.OrderOutDetail;
import com.bin.serverapi.order.mapper.OrderOutDetailMapper;
import com.bin.serverapi.order.service.IOrderOutDetailService;
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
public class OrderOutDetailServiceImpl extends ServiceImpl<OrderOutDetailMapper, OrderOutDetail> implements IOrderOutDetailService {
    @Override
    public IPage<Map<String, Object>> pageWithOrderOutToday(IPage<Map<String, Object>> page) {
        return this.baseMapper.selectWithOrderInToday(page);
    }

    @Override
    public IPage<Map<String, Object>> pageWithOrderOut(IPage<Map<String, Object>> page, OrderInOutSearchDataBo searchDataBo) {
        return this.baseMapper.selectWithOrderOut(page,searchDataBo);
    }

    @Override
    public Map<Long, ReportStoreAccountVo> listForStoreAccount(Wrapper<OrderOutDetail> queryWrapper) {
        return this.baseMapper.selectForStoreAccount(queryWrapper);
    }
}
