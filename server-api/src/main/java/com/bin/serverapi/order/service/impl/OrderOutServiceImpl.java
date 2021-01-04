package com.bin.serverapi.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.bin.serverapi.order.entity.OrderIn;
import com.bin.serverapi.order.entity.OrderInDetail;
import com.bin.serverapi.order.entity.OrderOut;
import com.bin.serverapi.order.entity.OrderOutDetail;
import com.bin.serverapi.order.mapper.OrderOutMapper;
import com.bin.serverapi.order.service.IOrderOutDetailService;
import com.bin.serverapi.order.service.IOrderOutService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import me.subin.response.service.ServiceResponse;
import me.subin.response.service.ServiceResponseBuilder;
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Soda
 * @since 2020-12-29
 */
@Slf4j
@Service
public class OrderOutServiceImpl extends ServiceImpl<OrderOutMapper, OrderOut> implements IOrderOutService {
    @Autowired
    IOrderOutDetailService iOrderOutDetailService;
    @Override
    public ServiceResponse<Long> saveOrder(OrderOut orderOut, List<OrderOutDetail> orderOutDetailList) {
        if (Objects.nonNull(orderOut.getId())){
            OrderOut order = this.baseMapper.selectById(orderOut.getId());
            BeanUtil.copyProperties(orderOut,order, CopyOptions.create().setIgnoreNullValue(false).setIgnoreError(true).setIgnoreProperties("createTime","updateTime"));
            this.baseMapper.updateById(order);
            List<OrderOutDetail> list = iOrderOutDetailService.lambdaQuery()
                    .eq(OrderOutDetail::getOrderId, order.getId())
                    .list();
            List<Long> collect = orderOutDetailList.stream().map(OrderOutDetail::getId).collect(Collectors.toList());
            List<Long> collectInDb = list.stream().map(OrderOutDetail::getId).collect(Collectors.toList());
            collectInDb.removeAll(collect);
//            先删除DB里面存在，但是前端传过来的里面不存在的id
            for (Long id:collectInDb
            ) {
                OrderOutDetail byId = iOrderOutDetailService.getById(id);
                if (Objects.nonNull(byId)){
                    iOrderOutDetailService.removeById(byId.getId());
                }
            }
//            遍历前端，如果ID存在，就更新，不存在就新增
            for (OrderOutDetail detail:orderOutDetailList
            ) {
                if (Objects.nonNull(detail.getId())){
                    OrderOutDetail detailById = iOrderOutDetailService.getById(detail.getId());
                    BeanUtil.copyProperties(detail,detailById, CopyOptions.create().setIgnoreNullValue(false).setIgnoreError(true).setIgnoreProperties("createTime","updateTime"));
                    detail.setOrderId(orderOut.getId());
                    detail.setOrderType(orderOut.getOrderType());
                    detail.setSubjectId(orderOut.getSubjectType());
                    detail.setOrderDate(orderOut.getOrderDate());
                    iOrderOutDetailService.updateById(detailById);
                }else {
                    detail.setOrderId(orderOut.getId());
                    detail.setOrderType(orderOut.getOrderType());
                    detail.setSubjectId(orderOut.getSubjectType());
                    detail.setOrderDate(orderOut.getOrderDate());
                    iOrderOutDetailService.save(detail);
                }
            }
        }else {
            this.baseMapper.insert(orderOut);
            for (OrderOutDetail detail:orderOutDetailList
            ) {
                // TODO: 2020/12/30 注意一下从jwt里面获取操作人员小队的信息
                detail.setOrderId(orderOut.getId());
                detail.setOrderType(orderOut.getOrderType());
                detail.setSubjectId(orderOut.getSubjectType());
                detail.setOrderDate(orderOut.getOrderDate());
                iOrderOutDetailService.save(detail);
            }
        }
        log.info(JsonConverterBin.transferToJson(orderOut));
        return ServiceResponseBuilder.success(orderOut.getId());
    }

    @Override
    public ServiceResponse<Long> removeAll(Long id) {
        OrderOut orderOut = this.baseMapper.selectById(id);
        if (Objects.nonNull(orderOut)){
            this.baseMapper.deleteById(orderOut.getId());
        }
        List<OrderOutDetail> list = iOrderOutDetailService.lambdaQuery()
                .eq(OrderOutDetail::getOrderId, id)
                .list();
        for (OrderOutDetail detail:list
        ) {
            if (Objects.nonNull(detail.getId())){
                iOrderOutDetailService.removeById(detail.getId());
            }
        }
        return ServiceResponseBuilder.success(id);
    }
}
