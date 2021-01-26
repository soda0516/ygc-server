package com.bin.serverapi.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.serverapi.order.entity.OrderInDetail;
import com.bin.serverapi.order.entity.OrderInternalDetail;
import com.bin.serverapi.order.entity.OrderOutDetail;
import com.bin.serverapi.order.service.IOrderInDetailService;
import com.bin.serverapi.order.service.IOrderInternalDetailService;
import com.bin.serverapi.order.service.IOrderOutDetailService;
import com.bin.serverapi.product.entity.ProductDetail;
import com.bin.serverapi.product.service.IProductDetailService;
import com.bin.serverapi.report.bo.DailyReportVoSearchData;
import com.bin.serverapi.report.mapper.DailyReportVoMapper;
import com.bin.serverapi.report.service.IDailyReportVoService;
import com.bin.serverapi.report.service.IReportStoreAccountService;
import com.bin.serverapi.report.vo.DailyReportDetailVo;
import com.bin.serverapi.report.vo.DailyReportVo;
import com.bin.serverapi.report.vo.ReportStoreAccountVo;
import com.bin.serverapi.store.service.IStoreAccountService;
import lombok.extern.slf4j.Slf4j;
import me.subin.utils.JsonConverterBin;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author bin
 * @ClassName DailyReportVoServiceImpl
 * @Description TODO
 * @date 2021/1/15 15:34
 */
@Slf4j
@Service
public class DailyReportVoServiceImpl extends ServiceImpl<DailyReportVoMapper, DailyReportVo> implements IDailyReportVoService {
    @Autowired
    IReportStoreAccountService iReportStoreAccountService;
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
    public List<DailyReportVo> generateDailyReportVo(Long categoryId, DailyReportVoSearchData searchData) {
        List<ReportStoreAccountVo> reportStoreAccountVos = iReportStoreAccountService.showStoreAccountByStoreIdDate(searchData.getStoreId()
                , LocalDate.now(), categoryId);
        Map<Long, ReportStoreAccountVo> mapInCount = iOrderInDetailService.listForStoreAccount(
                new LambdaQueryWrapper<>(OrderInDetail.class)
                        .eq(OrderInDetail::getStoreId, searchData.getStoreId())
                        .eq(OrderInDetail::getProductCategoryId, categoryId)
                        .eq(OrderInDetail::getOrderDate, searchData.getSearchDate()));
        Map<Long, ReportStoreAccountVo> mapOutCount = iOrderOutDetailService.listForStoreAccount(
                new LambdaQueryWrapper<>(OrderOutDetail.class)
                        .eq(OrderOutDetail::getStoreId, 0)
                        .eq(OrderOutDetail::getProductCategoryId, categoryId)
                        .eq(OrderOutDetail::getOrderDate, searchData.getSearchDate()));
        Map<Long, ReportStoreAccountVo> mapInternalAddCount = iOrderInternalDetailService.listAddForStoreAccount(
                new LambdaQueryWrapper<>(OrderInternalDetail.class)
                        .eq(OrderInternalDetail::getStoreId, searchData.getStoreId())
                        .eq(OrderInternalDetail::getProductCategoryId, categoryId)
                        .eq(OrderInternalDetail::getInternalDate, searchData.getSearchDate())
                        .gt(OrderInternalDetail::getAddSubject,0));
        Map<Long, ReportStoreAccountVo> mapInternalSubtractCount = iOrderInternalDetailService.listSubtractForStoreAccount(
                new LambdaQueryWrapper<>(OrderInternalDetail.class)
                        .eq(OrderInternalDetail::getStoreId, searchData.getStoreId())
                        .eq(OrderInternalDetail::getProductCategoryId, categoryId)
                        .eq(OrderInternalDetail::getInternalDate, searchData.getSearchDate())
                        .gt(OrderInternalDetail::getSubtractSubject,0));
        Map<Long,ReportStoreAccountVo> map = new LinkedHashMap<>();
        List<DailyReportVo> dailyReportVoList = this.generateEmptyDailyReportVo(categoryId);
        for (int i = 0; i < dailyReportVoList.size(); i++) {
            DailyReportVo dailyReportVo = dailyReportVoList.get(i);
            for (int j = 0; j < dailyReportVo.getData().size(); j++) {
//                回收
                if (i == 0){
                    this.setReportStoreAccountVoToMap(dailyReportVo.getData().get(j).getProductDetailId(),map,reportStoreAccountVos);
                    ReportStoreAccountVo vo = map.get(dailyReportVo.getData().get(j).getProductDetailId());
                    if (Objects.nonNull(vo.getSubject1Num())){
                        dailyReportVo.getData().get(j).setNumber(vo.getSubject1Num());
                    }
                }
                if (i == 1){
                    ReportStoreAccountVo vo1 = mapInCount.get(dailyReportVo.getData().get(j).getProductDetailId());
                    ReportStoreAccountVo vo2 = mapInternalAddCount.get(dailyReportVo.getData().get(j).getProductDetailId());
                    if (Objects.nonNull(vo1) && Objects.nonNull(vo1.getSubject1Num())){
                        Integer number = dailyReportVo.getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber(vo1.getSubject1Num() + (number==null?0:number));
                    }
                    if (Objects.nonNull(vo2) && Objects.nonNull(vo2.getSubject1Num())){
                        Integer number = dailyReportVo.getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber(vo2.getSubject1Num() + (number==null?0:number));
                    }
                }
                if (i == 2){
                    ReportStoreAccountVo vo1 = mapOutCount.get(dailyReportVo.getData().get(j).getProductDetailId());
                    ReportStoreAccountVo vo2 = mapInternalSubtractCount.get(dailyReportVo.getData().get(j).getProductDetailId());
                    if (Objects.nonNull(vo1) && Objects.nonNull(vo1.getSubject1Num())){
                        Integer number = dailyReportVo.getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber((number==null?0:number) + vo1.getSubject1Num());
                    }
                    if (Objects.nonNull(vo2) && Objects.nonNull(vo2.getSubject1Num())){
                        Integer number = dailyReportVo.getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber((number==null?0:number) + vo2.getSubject1Num());
                    }
                }
                if (i == 3){
                    if (Objects.nonNull(dailyReportVoList.get(0).getData().get(j))){
                        Integer number0 = dailyReportVoList.get(0).getData().get(j).getNumber();
                        Integer number1 = dailyReportVoList.get(1).getData().get(j).getNumber();
                        Integer number2 = dailyReportVoList.get(2).getData().get(j).getNumber();
                        DailyReportDetailVo detailVo = dailyReportVo.getData().get(j);
                        dailyReportVo.getData().get(j).setNumber((number0 == null? 0 : number0) + (number1 == null? 0 : number1) + (number2 == null? 0 : number2));
                    }
                }
//                领用
                if (i == 4){
                    this.setReportStoreAccountVoToMap(dailyReportVo.getData().get(j).getProductDetailId(),map,reportStoreAccountVos);
                    ReportStoreAccountVo vo = map.get(dailyReportVo.getData().get(j).getProductDetailId());
                    if (Objects.nonNull(vo.getSubject2Num())){
                        dailyReportVo.getData().get(j).setNumber(vo.getSubject2Num());
                    }
                }
                if (i == 5){
                    ReportStoreAccountVo vo1 = mapInCount.get(dailyReportVo.getData().get(j).getProductDetailId());
                    ReportStoreAccountVo vo2 = mapInternalAddCount.get(dailyReportVo.getData().get(j).getProductDetailId());
                    if (Objects.nonNull(vo1) && Objects.nonNull(vo1.getSubject2Num())){
                        Integer number = dailyReportVo.getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber(vo1.getSubject2Num() + (number==null?0:number));
                    }
                    if (Objects.nonNull(vo2) && Objects.nonNull(vo2.getSubject2Num())){
                        Integer number = dailyReportVo.getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber(vo2.getSubject2Num() + (number==null?0:number));
                    }
                }
                if (i == 6){
                    ReportStoreAccountVo vo1 = mapOutCount.get(dailyReportVo.getData().get(j).getProductDetailId());
                    ReportStoreAccountVo vo2 = mapInternalSubtractCount.get(dailyReportVo.getData().get(j).getProductDetailId());
                    if (Objects.nonNull(vo1) && Objects.nonNull(vo1.getSubject2Num())){
                        Integer number = dailyReportVo.getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber((number==null?0:number) + vo1.getSubject2Num());
                    }
                    if (Objects.nonNull(vo2) && Objects.nonNull(vo2.getSubject2Num())){
                        Integer number = dailyReportVo.getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber((number==null?0:number) + vo2.getSubject2Num());
                    }
                }
                if (i == 7){
                    if (Objects.nonNull(dailyReportVoList.get(4).getData().get(j))){
                        Integer number0 = dailyReportVoList.get(4).getData().get(j).getNumber();
                        Integer number1 = dailyReportVoList.get(5).getData().get(j).getNumber();
                        Integer number2 = dailyReportVoList.get(6).getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber((number0 == null? 0 : number0) + (number1 == null? 0 : number1) + (number2 == null? 0 : number2));
                    }
                }
//                周转
                if (i == 8){
                    this.setReportStoreAccountVoToMap(dailyReportVo.getData().get(j).getProductDetailId(),map,reportStoreAccountVos);
                    ReportStoreAccountVo vo = map.get(dailyReportVo.getData().get(j).getProductDetailId());
                    if (Objects.nonNull(vo.getSubject3Num())){
                        dailyReportVo.getData().get(j).setNumber(vo.getSubject3Num());
                    }
                }
                if (i == 9){
                    ReportStoreAccountVo vo1 = mapInCount.get(dailyReportVo.getData().get(j).getProductDetailId());
                    ReportStoreAccountVo vo2 = mapInternalAddCount.get(dailyReportVo.getData().get(j).getProductDetailId());
                    if (Objects.nonNull(vo1) && Objects.nonNull(vo1.getSubject3Num())){
                        Integer number = dailyReportVo.getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber(vo1.getSubject3Num() + (number==null?0:number));
                    }
                    if (Objects.nonNull(vo2) && Objects.nonNull(vo2.getSubject3Num())){
                        Integer number = dailyReportVo.getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber(vo2.getSubject3Num() + (number==null?0:number));
                    }
                }
                if (i == 10){
                    ReportStoreAccountVo vo1 = mapOutCount.get(dailyReportVo.getData().get(j).getProductDetailId());
                    ReportStoreAccountVo vo2 = mapInternalSubtractCount.get(dailyReportVo.getData().get(j).getProductDetailId());
                    if (Objects.nonNull(vo1) && Objects.nonNull(vo1.getSubject3Num())){
                        Integer number = dailyReportVo.getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber((number==null?0:number) + vo1.getSubject3Num());
                    }
                    if (Objects.nonNull(vo2) && Objects.nonNull(vo2.getSubject3Num())){
                        Integer number = dailyReportVo.getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber((number==null?0:number) + vo2.getSubject3Num());
                    }
                }
                if (i == 11){
                    if (Objects.nonNull(dailyReportVoList.get(8).getData().get(j))){
                        Integer number0 = dailyReportVoList.get(8).getData().get(j).getNumber();
                        Integer number1 = dailyReportVoList.get(9).getData().get(j).getNumber();
                        Integer number2 = dailyReportVoList.get(10).getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber((number0 == null? 0 : number0) + (number1 == null? 0 : number1) + (number2 == null? 0 : number2));
                    }
                }
//                待修
                if (i == 12){
                    this.setReportStoreAccountVoToMap(dailyReportVo.getData().get(j).getProductDetailId(),map,reportStoreAccountVos);
                    ReportStoreAccountVo vo = map.get(dailyReportVo.getData().get(j).getProductDetailId());
                    if (Objects.nonNull(vo.getSubject4Num())){
                        dailyReportVo.getData().get(j).setNumber(vo.getSubject4Num());
                    }
                }
                if (i == 13){
                    ReportStoreAccountVo vo1 = mapInCount.get(dailyReportVo.getData().get(j).getProductDetailId());
                    ReportStoreAccountVo vo2 = mapInternalAddCount.get(dailyReportVo.getData().get(j).getProductDetailId());
                    if (Objects.nonNull(vo1) && Objects.nonNull(vo1.getSubject4Num())){
                        Integer number = dailyReportVo.getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber(vo1.getSubject4Num() + (number==null?0:number));
                    }
                    if (Objects.nonNull(vo2) && Objects.nonNull(vo2.getSubject4Num())){
                        Integer number = dailyReportVo.getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber(vo2.getSubject4Num() + (number==null?0:number));
                    }
                }
                if (i == 14){
                    ReportStoreAccountVo vo1 = mapOutCount.get(dailyReportVo.getData().get(j).getProductDetailId());
                    ReportStoreAccountVo vo2 = mapInternalSubtractCount.get(dailyReportVo.getData().get(j).getProductDetailId());
                    if (Objects.nonNull(vo1) && Objects.nonNull(vo1.getSubject4Num())){
                        Integer number = dailyReportVo.getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber((number==null?0:number) + vo1.getSubject4Num());
                    }
                    if (Objects.nonNull(vo2) && Objects.nonNull(vo2.getSubject4Num())){
                        Integer number = dailyReportVo.getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber((number==null?0:number) + vo2.getSubject4Num());
                    }
                }
                if (i == 15){
                    if (Objects.nonNull(dailyReportVoList.get(12).getData().get(j))){
                        Integer number0 = dailyReportVoList.get(12).getData().get(j).getNumber();
                        Integer number1 = dailyReportVoList.get(13).getData().get(j).getNumber();
                        Integer number2 = dailyReportVoList.get(14).getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber((number0 == null? 0 : number0) + (number1 == null? 0 : number1) + (number2 == null? 0 : number2));
                    }
                }
//                待报废
                if (i == 16){
                    this.setReportStoreAccountVoToMap(dailyReportVo.getData().get(j).getProductDetailId(),map,reportStoreAccountVos);
                    ReportStoreAccountVo vo = map.get(dailyReportVo.getData().get(j).getProductDetailId());
                    if (Objects.nonNull(vo.getSubject5Num())){
                        dailyReportVo.getData().get(j).setNumber(vo.getSubject5Num());
                    }
                }
                if (i == 17){
                    ReportStoreAccountVo vo1 = mapInCount.get(dailyReportVo.getData().get(j).getProductDetailId());
                    ReportStoreAccountVo vo2 = mapInternalAddCount.get(dailyReportVo.getData().get(j).getProductDetailId());
                    if (Objects.nonNull(vo1) && Objects.nonNull(vo1.getSubject5Num())){
                        Integer number = dailyReportVo.getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber(vo1.getSubject5Num() + (number==null?0:number));
                    }
                    if (Objects.nonNull(vo2) && Objects.nonNull(vo2.getSubject5Num())){
                        Integer number = dailyReportVo.getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber(vo2.getSubject5Num() + (number==null?0:number));
                    }
                }
                if (i == 18){
                    ReportStoreAccountVo vo1 = mapOutCount.get(dailyReportVo.getData().get(j).getProductDetailId());
                    ReportStoreAccountVo vo2 = mapInternalSubtractCount.get(dailyReportVo.getData().get(j).getProductDetailId());
                    if (Objects.nonNull(vo1) && Objects.nonNull(vo1.getSubject5Num())){
                        Integer number = dailyReportVo.getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber((number==null?0:number) + vo1.getSubject5Num());
                    }
                    if (Objects.nonNull(vo2) && Objects.nonNull(vo2.getSubject5Num())){
                        Integer number = dailyReportVo.getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber((number==null?0:number) + vo2.getSubject5Num());
                    }
                }
                if (i == 19){
                    if (Objects.nonNull(dailyReportVoList.get(16).getData().get(j))){
                        Integer number0 = dailyReportVoList.get(16).getData().get(j).getNumber();
                        Integer number1 = dailyReportVoList.get(17).getData().get(j).getNumber();
                        Integer number2 = dailyReportVoList.get(18).getData().get(j).getNumber();
                        dailyReportVo.getData().get(j).setNumber((number0 == null? 0 : number0) + (number1 == null? 0 : number1) + (number2 == null? 0 : number2));
                    }
                }
            }
        }
        log.info(JsonConverterBin.transferToJson(dailyReportVoList));
        return dailyReportVoList;
    }

    @Override
    public List<DailyReportVo> generateEmptyDailyReportVo(Long categoryId) {
        List<ProductDetail> list = iProductDetailService.lambdaQuery()
                .eq(ProductDetail::getCategoryId, categoryId)
                .list();
//        List<DailyReportDetailVo> dailyReportDetailVoList = new ArrayList<>();
//        for (ProductDetail detail:list
//             ) {
//            DailyReportDetailVo vo = new DailyReportDetailVo();
//            vo.setProductDetailId(detail.getId());
//            vo.setProductName(detail.getModelName() + detail.getSpecificationName() + detail.getDescriptionName());
//            dailyReportDetailVoList.add(vo);
//        }
        List<DailyReportVo>  dailyReportVoList = new ArrayList<>();

        DailyReportVo dailyReportVo1 = new DailyReportVo();
        dailyReportVo1.setSubjectName("回收");
        dailyReportVo1.setTypeName("上日库存");
        dailyReportVoList.add(dailyReportVo1);

        DailyReportVo dailyReportVo2 = new DailyReportVo();
        dailyReportVo2.setSubjectName("回收");
        dailyReportVo2.setTypeName("今日增加");
        dailyReportVoList.add(dailyReportVo2);

        DailyReportVo dailyReportVo3 = new DailyReportVo();
        dailyReportVo3.setSubjectName("回收");
        dailyReportVo3.setTypeName("今日减少");
        dailyReportVoList.add(dailyReportVo3);

        DailyReportVo dailyReportVo4 = new DailyReportVo();
        dailyReportVo4.setSubjectName("回收");
        dailyReportVo4.setTypeName("今日库存");
        dailyReportVoList.add(dailyReportVo4);

        DailyReportVo dailyReportVo5 = new DailyReportVo();
        dailyReportVo5.setSubjectName("领用");
        dailyReportVo5.setTypeName("上日库存");
        dailyReportVoList.add(dailyReportVo5);

        DailyReportVo dailyReportVo6 = new DailyReportVo();
        dailyReportVo6.setSubjectName("领用");
        dailyReportVo6.setTypeName("今日增加");
        dailyReportVoList.add(dailyReportVo6);

        DailyReportVo dailyReportVo7 = new DailyReportVo();
        dailyReportVo7.setSubjectName("领用");
        dailyReportVo7.setTypeName("今日减少");
        dailyReportVoList.add(dailyReportVo7);

        DailyReportVo dailyReportVo8 = new DailyReportVo();
        dailyReportVo8.setSubjectName("领用");
        dailyReportVo8.setTypeName("今日库存");
        dailyReportVoList.add(dailyReportVo8);

        DailyReportVo dailyReportVo9 = new DailyReportVo();
        dailyReportVo9.setSubjectName("周转");
        dailyReportVo9.setTypeName("上日库存");
        dailyReportVoList.add(dailyReportVo9);

        DailyReportVo dailyReportVo10 = new DailyReportVo();
        dailyReportVo10.setSubjectName("周转");
        dailyReportVo10.setTypeName("今日增加");
        dailyReportVoList.add(dailyReportVo10);

        DailyReportVo dailyReportVo11 = new DailyReportVo();
        dailyReportVo11.setSubjectName("周转");
        dailyReportVo11.setTypeName("今日减少");
        dailyReportVoList.add(dailyReportVo11);

        DailyReportVo dailyReportVo12 = new DailyReportVo();
        dailyReportVo12.setSubjectName("周转");
        dailyReportVo12.setTypeName("今日库存");
        dailyReportVoList.add(dailyReportVo12);

        DailyReportVo dailyReportVo13 = new DailyReportVo();
        dailyReportVo13.setSubjectName("待修");
        dailyReportVo13.setTypeName("上日库存");
        dailyReportVoList.add(dailyReportVo13);

        DailyReportVo dailyReportVo14 = new DailyReportVo();
        dailyReportVo14.setSubjectName("待修");
        dailyReportVo14.setTypeName("今日增加");
        dailyReportVoList.add(dailyReportVo14);

        DailyReportVo dailyReportVo15 = new DailyReportVo();
        dailyReportVo15.setSubjectName("待修");
        dailyReportVo15.setTypeName("今日减少");
        dailyReportVoList.add(dailyReportVo15);

        DailyReportVo dailyReportVo16 = new DailyReportVo();
        dailyReportVo16.setSubjectName("待修");
        dailyReportVo16.setTypeName("今日库存");
        dailyReportVoList.add(dailyReportVo16);

        DailyReportVo dailyReportVo17 = new DailyReportVo();
        dailyReportVo17.setSubjectName("待报废");
        dailyReportVo17.setTypeName("上日库存");
        dailyReportVoList.add(dailyReportVo17);

        DailyReportVo dailyReportVo18 = new DailyReportVo();
        dailyReportVo18.setSubjectName("待报废");
        dailyReportVo18.setTypeName("今日增加");
        dailyReportVoList.add(dailyReportVo18);

        DailyReportVo dailyReportVo19 = new DailyReportVo();
        dailyReportVo19.setSubjectName("待报废");
        dailyReportVo19.setTypeName("今日减少");
        dailyReportVoList.add(dailyReportVo19);

        DailyReportVo dailyReportVo20 = new DailyReportVo();
        dailyReportVo20.setSubjectName("待报废");
        dailyReportVo20.setTypeName("今日库存");
        dailyReportVoList.add(dailyReportVo20);

        for (DailyReportVo vo:dailyReportVoList
             ) {
            List<DailyReportDetailVo> dailyReportDetailVos = new ArrayList<>();
            for (ProductDetail detail:list
            ) {
                DailyReportDetailVo detailVo = new DailyReportDetailVo();
                detailVo.setProductDetailId(detail.getId());
                detailVo.setProductName(detail.getModelName() + detail.getSpecificationName() + detail.getDescriptionName());
                dailyReportDetailVos.add(detailVo);
            }
            vo.setData(dailyReportDetailVos);
        }
        return dailyReportVoList;
    }

    private void setReportStoreAccountVoToMap(Long productDetailId,Map<Long,ReportStoreAccountVo> map,List<ReportStoreAccountVo> reportStoreAccountVos){
        if (!map.containsKey(productDetailId)){
            for (ReportStoreAccountVo vo:reportStoreAccountVos
                 ) {
                if (vo.getProductDetailId().equals(productDetailId)){
                    map.put(productDetailId,vo);
                }
            }
        }
    }
}
