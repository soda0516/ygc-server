package com.bin.serverapi.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bin.serverapi.order.entity.OrderIn;
import com.bin.serverapi.order.entity.OrderInDetail;
import com.bin.serverapi.order.mapper.OrderInMapper;
import com.bin.serverapi.order.service.IOrderInDetailService;
import com.bin.serverapi.order.service.IOrderInService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.serverapi.order.vo.OrderInVo;
import lombok.extern.slf4j.Slf4j;
import me.subin.response.service.ServiceResponse;
import me.subin.response.service.ServiceResponseBuilder;
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class OrderInServiceImpl extends ServiceImpl<OrderInMapper, OrderIn> implements IOrderInService {
    @Autowired
    IOrderInDetailService iOrderInDetailService;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResponse<Long> saveOrder(OrderIn orderIn, List<OrderInDetail> orderInDetailList) {
        if (Objects.nonNull(orderIn.getId())){
            OrderIn order = this.baseMapper.selectById(orderIn.getId());
            BeanUtil.copyProperties(orderIn,order, CopyOptions.create().setIgnoreNullValue(false).setIgnoreError(true).setIgnoreProperties("createTime","updateTime"));
            this.baseMapper.updateById(order);
            List<OrderInDetail> list = iOrderInDetailService.lambdaQuery()
                    .eq(OrderInDetail::getOrderId, order.getId())
                    .list();
            List<Long> collect = orderInDetailList.stream().map(OrderInDetail::getId).collect(Collectors.toList());
            List<Long> collectInDb = list.stream().map(OrderInDetail::getId).collect(Collectors.toList());
            collectInDb.removeAll(collect);
//            先删除DB里面存在，但是前端传过来的里面不存在的id
            for (Long id:collectInDb
                 ) {
                OrderInDetail byId = iOrderInDetailService.getById(id);
                if (Objects.nonNull(byId)){
                    iOrderInDetailService.removeById(byId.getId());
                }
            }
//            遍历前端，如果ID存在，就更新，不存在就新增
            for (OrderInDetail detail:orderInDetailList
                 ) {
                if (Objects.nonNull(detail.getId())){
                    OrderInDetail detailById = iOrderInDetailService.getById(detail.getId());
                    BeanUtil.copyProperties(detail,detailById, CopyOptions.create().setIgnoreNullValue(false).setIgnoreError(true).setIgnoreProperties("createTime","updateTime"));
                    detail.setOrderId(orderIn.getId());
                    detail.setOrderType(orderIn.getOrderType());
                    detail.setSubjectId(orderIn.getSubjectType());
                    detail.setOrderDate(orderIn.getOrderDate());
                    iOrderInDetailService.updateById(detailById);
                }else {
                    detail.setOrderId(orderIn.getId());
                    detail.setOrderType(orderIn.getOrderType());
                    detail.setSubjectId(orderIn.getSubjectType());
                    detail.setOrderDate(orderIn.getOrderDate());
                    iOrderInDetailService.save(detail);
                }
            }
        }else {
            this.baseMapper.insert(orderIn);
            for (OrderInDetail detail:orderInDetailList
            ) {
                // TODO: 2020/12/30 注意一下从jwt里面获取操作人员小队的信息
                detail.setOrderId(orderIn.getId());
                detail.setOrderType(orderIn.getOrderType());
                detail.setSubjectId(orderIn.getSubjectType());
                detail.setOrderDate(orderIn.getOrderDate());
                iOrderInDetailService.save(detail);
            }
        }
        log.info(JsonConverterBin.transferToJson(orderIn));
        return ServiceResponseBuilder.success(orderIn.getId());
    }

    @Override
    public IPage<OrderInVo> pageOrderInVoWithDetail(IPage<OrderInVo> page) {
        return this.baseMapper.selectWithDetail(page);
    }

    @Override
    public ServiceResponse<Long> removeAll(Long id) {
        OrderIn orderIn = this.baseMapper.selectById(id);
        if (Objects.nonNull(orderIn)){
            this.baseMapper.deleteById(orderIn.getId());
        }
        List<OrderInDetail> list = iOrderInDetailService.lambdaQuery()
                .eq(OrderInDetail::getOrderId, id)
                .list();
        for (OrderInDetail detail:list
             ) {
            if (Objects.nonNull(detail.getId())){
                iOrderInDetailService.removeById(detail.getId());
            }
        }
        return ServiceResponseBuilder.success(id);
    }
}
