package com.bin.serverapi.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.serverapi.order.entity.OrderInDetail;
import com.bin.serverapi.order.entity.OrderInternalDetail;
import com.bin.serverapi.order.entity.OrderOutDetail;
import com.bin.serverapi.order.service.IOrderInDetailService;
import com.bin.serverapi.order.service.IOrderInternalDetailService;
import com.bin.serverapi.order.service.IOrderOutDetailService;
import com.bin.serverapi.product.service.IProductDetailService;
import com.bin.serverapi.report.mapper.ReportStoreAccountMapper;
import com.bin.serverapi.report.service.IReportStoreAccountService;
import com.bin.serverapi.report.vo.ReportStoreAccountVo;
import com.bin.serverapi.store.service.IStoreAccountService;
import lombok.extern.slf4j.Slf4j;
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author bin
 * @ClassName StoreAccountServiceImpl
 * @Description TODO
 * @date 2021/1/14 11:17
 */
@Slf4j
@Service
public class ReportStoreAccountServiceImpl extends ServiceImpl<ReportStoreAccountMapper, ReportStoreAccountVo> implements IReportStoreAccountService {
    @Autowired
    IProductDetailService iProductDetailService;
    @Autowired
    IOrderInDetailService iOrderInDetailService;
    @Autowired
    IOrderOutDetailService iOrderOutDetailService;
    @Autowired
    IOrderInternalDetailService iOrderInternalDetailService;
    @Autowired
    IStoreAccountService iStoreAccountService;
    @Override
    public List<ReportStoreAccountVo> showStoreAccountByStoreIdDate(Long storeId, LocalDate searchDate, Long categoryId) {
        List<ReportStoreAccountVo> storeAccountVos = iProductDetailService.listAllForStoreAccountVo();
        LambdaQueryWrapper<OrderInDetail> inDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(storeId) && storeId > 0) {
            inDetailLambdaQueryWrapper.eq(OrderInDetail::getStoreId,storeId);
        }
        if (Objects.nonNull(categoryId)){
            inDetailLambdaQueryWrapper.eq(OrderInDetail::getProductCategoryId,categoryId);
        }
        inDetailLambdaQueryWrapper.gt(OrderInDetail::getOrderDate,LocalDate.now().minusYears(8));
        inDetailLambdaQueryWrapper.le(OrderInDetail::getOrderDate,LocalDate.now());
        Map<Long, ReportStoreAccountVo> in = iOrderInDetailService.listForStoreAccount(inDetailLambdaQueryWrapper);

        LambdaQueryWrapper<OrderOutDetail> outDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(storeId) && storeId > 0) {
            outDetailLambdaQueryWrapper.eq(OrderOutDetail::getStoreId,storeId);
        }
        if (Objects.nonNull(categoryId)){
            outDetailLambdaQueryWrapper.eq(OrderOutDetail::getProductCategoryId,categoryId);
        }
        outDetailLambdaQueryWrapper.gt(OrderOutDetail::getOrderDate,LocalDate.now().minusYears(8));
        outDetailLambdaQueryWrapper.le(OrderOutDetail::getOrderDate,LocalDate.now());
        Map<Long, ReportStoreAccountVo> out = iOrderOutDetailService.listForStoreAccount(outDetailLambdaQueryWrapper);

        LambdaQueryWrapper<OrderInternalDetail> internalWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(storeId) && storeId > 0) {
            internalWrapper.eq(OrderInternalDetail::getStoreId,storeId);
        }
        if (Objects.nonNull(categoryId)){
            internalWrapper.eq(OrderInternalDetail::getProductCategoryId,categoryId);
        }
        internalWrapper.gt(OrderInternalDetail::getInternalDate,LocalDate.now().minusYears(8));
        internalWrapper.le(OrderInternalDetail::getInternalDate,LocalDate.now());
        Map<Long, ReportStoreAccountVo> internalNum = iOrderInternalDetailService.listAddSubtractForStoreAccount(internalWrapper);

        Map<Long, ReportStoreAccountVo> longStoreAccountVoMap = iStoreAccountService.listForStoreAccountVo();

        for (ReportStoreAccountVo storeAccountVo:storeAccountVos
             ) {
            Long productDetailId = storeAccountVo.getProductDetailId();
//            先设置原始库存
            ReportStoreAccountVo store = longStoreAccountVoMap.get(productDetailId);
            if (Objects.nonNull(store)){
                setStoreAccountVo(storeAccountVo,store,1);
            }
//            设置入库单
            ReportStoreAccountVo inNum = in.get(productDetailId);
            if (Objects.nonNull(inNum)){
                setStoreAccountVo(storeAccountVo,inNum,1);
            }
//            设置出库单
            ReportStoreAccountVo outNum = out.get(productDetailId);
            if (Objects.nonNull(outNum)){
                setStoreAccountVo(storeAccountVo,outNum,0);
            }
//            调拨单增加
            ReportStoreAccountVo internalAddNum = internalNum.get(productDetailId);
            if (Objects.nonNull(internalAddNum)){
                setStoreAccountVo(storeAccountVo,internalAddNum,1);
            }

        }
//        第二次轮询设置回收的数量，方便前端进行合并处理
        log.info(JsonConverterBin.transferToJson(storeAccountVos));
        return storeAccountVos;
    }
    private void setStoreAccountVo(ReportStoreAccountVo v1, ReportStoreAccountVo v2, Integer addOrSubtract){
        if (addOrSubtract <= 0){
            v1.setSubject1Num(v1.getSubject1Num() - v2.getSubject1Num());
            v1.setSubject2Num(v1.getSubject2Num() - v2.getSubject2Num());
            v1.setSubject3Num(v1.getSubject3Num() - v2.getSubject3Num());
            v1.setSubject4Num(v1.getSubject4Num() - v2.getSubject4Num());
            v1.setSubject5Num(v1.getSubject5Num() - v2.getSubject5Num());
        }else {
            v1.setSubject1Num(v1.getSubject1Num() + v2.getSubject1Num());
            v1.setSubject2Num(v1.getSubject2Num() + v2.getSubject2Num());
            v1.setSubject3Num(v1.getSubject3Num() + v2.getSubject3Num());
            v1.setSubject4Num(v1.getSubject4Num() + v2.getSubject4Num());
            v1.setSubject5Num(v1.getSubject5Num() + v2.getSubject5Num());
        }
    }
}
