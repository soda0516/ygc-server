package com.bin.serverapi.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.bin.serverapi.order.entity.OrderIn;
import com.bin.serverapi.order.entity.OrderInDetail;
import com.bin.serverapi.order.entity.OrderInternal;
import com.bin.serverapi.order.entity.OrderInternalDetail;
import com.bin.serverapi.order.mapper.OrderInternalMapper;
import com.bin.serverapi.order.service.IOrderInternalDetailService;
import com.bin.serverapi.order.service.IOrderInternalService;
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
public class OrderInternalServiceImpl extends ServiceImpl<OrderInternalMapper, OrderInternal> implements IOrderInternalService {
    @Autowired
    IOrderInternalDetailService iOrderInternalDetailService;

    @Override
    public ServiceResponse<Long> saveOrder(OrderInternal orderInternal, List<OrderInternalDetail> internalDetailList) {
        if (Objects.nonNull(orderInternal.getId())){
            OrderInternal order = this.baseMapper.selectById(orderInternal.getId());
            BeanUtil.copyProperties(orderInternal,order, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            this.baseMapper.updateById(order);
            List<OrderInternalDetail> list = iOrderInternalDetailService.lambdaQuery()
                    .eq(OrderInternalDetail::getOrderId, order.getId())
                    .list();
            List<Long> collect = internalDetailList.stream().map(OrderInternalDetail::getId).collect(Collectors.toList());
            List<Long> collectInDb = list.stream().map(OrderInternalDetail::getId).collect(Collectors.toList());
            collectInDb.removeAll(collect);
//            先删除DB里面存在，但是前端传过来的里面不存在的id
            for (Long id:collectInDb
            ) {
                OrderInternalDetail byId = iOrderInternalDetailService.getById(id);
                if (Objects.nonNull(byId)){
                    iOrderInternalDetailService.removeById(byId.getId());
                }
            }
//            遍历前端，如果ID存在，就更新，不存在就新增
            for (OrderInternalDetail detail:internalDetailList
            ) {

                if (Objects.nonNull(detail.getId())){
                    OrderInternalDetail detailById = iOrderInternalDetailService.getById(detail.getId());
                    BeanUtil.copyProperties(detail,detailById, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
                    detail.setOrderId(orderInternal.getId());
                    detail.setOrderType(orderInternal.getOrderType());
                    detail.setInternalDate(orderInternal.getInternalDate());
                    detail.setAddSubject(orderInternal.getAddSubject());
                    detail.setSubtractSubject(orderInternal.getSubtractSubject());
                    detail.setOperType(orderInternal.getOperType());
                    iOrderInternalDetailService.updateById(detailById);
                }else {
                    // TODO: 2021/1/3 得根据用户名称啥的输入storeId
                    detail.setOrderId(orderInternal.getId());
                    detail.setOrderType(orderInternal.getOrderType());
                    detail.setInternalDate(orderInternal.getInternalDate());
                    detail.setAddSubject(orderInternal.getAddSubject());
                    detail.setSubtractSubject(orderInternal.getSubtractSubject());
                    detail.setOperType(orderInternal.getOperType());
                    iOrderInternalDetailService.save(detail);
                }
            }
        }else {
            this.baseMapper.insert(orderInternal);
            for (OrderInternalDetail detail:internalDetailList
            ) {
                // TODO: 2020/12/30 注意一下从jwt里面获取操作人员小队的信息
                detail.setOrderId(orderInternal.getId());
                detail.setOrderType(orderInternal.getOrderType());
                detail.setInternalDate(orderInternal.getInternalDate());
                detail.setAddSubject(orderInternal.getAddSubject());
                detail.setSubtractSubject(orderInternal.getSubtractSubject());
                detail.setOperType(orderInternal.getOperType());
                iOrderInternalDetailService.save(detail);
            }
        }
        log.info(JsonConverterBin.transferToJson(orderInternal));
        return ServiceResponseBuilder.success(orderInternal.getId());
    }

    @Override
    public ServiceResponse<Long> removeAll(Long id) {
        OrderInternal order = this.baseMapper.selectById(id);
        if (Objects.nonNull(order)){
            this.baseMapper.deleteById(order.getId());
        }
        List<OrderInternalDetail> list = iOrderInternalDetailService.lambdaQuery()
                .eq(OrderInternalDetail::getOrderId, id)
                .list();
        for (OrderInternalDetail detail:list
        ) {
            if (Objects.nonNull(detail.getId())){
                iOrderInternalDetailService.removeById(detail.getId());
            }
        }
        return ServiceResponseBuilder.success(id);
    }
}
