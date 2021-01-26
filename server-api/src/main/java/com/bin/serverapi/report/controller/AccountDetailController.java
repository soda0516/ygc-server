package com.bin.serverapi.report.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.serverapi.report.bo.AccountDetailSearchBo;
import com.bin.serverapi.report.service.IAccountDetailService;
import com.bin.serverapi.report.vo.AccountDetailVo;
import com.bin.serverapi.store.bo.StoreAccumulateCountBo;
import com.bin.serverapi.store.bo.StoreCountBo;
import com.bin.serverapi.store.service.IStoreAccountService;
import lombok.extern.slf4j.Slf4j;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author bin
 * @ClassName ReportDetailController
 * @Description TODO
 * @date 2021/1/12 14:14
 */
@Slf4j
@RestController
@RequestMapping(value = "/report/reportDetail")
public class AccountDetailController {

    private BeanCopier beanCopier = BeanCopier.create(AccountDetailSearchBo.class,AccountDetailSearchBo.class,false);

    @Autowired
    IAccountDetailService iAccountDetailService;
    @Autowired
    IStoreAccountService iStoreAccountService;
    @PostMapping()
    public ResponseModel<IPage<AccountDetailVo>> getDetail(
            @RequestParam("searchData") String searchData,
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("pages") Integer pages) {
        log.info(searchData);
        AccountDetailSearchBo accountDetailSearchBo = JsonConverterBin.transferToObject(searchData,
                AccountDetailSearchBo.class);
        if (Objects.isNull(accountDetailSearchBo.getEndDate())){
            accountDetailSearchBo.setEndDate(LocalDate.now());
        }
        if (Objects.isNull(accountDetailSearchBo.getStartDate())){
            accountDetailSearchBo.setStartDate(LocalDate.now().minusYears(1L));
        }
        if (Objects.isNull(accountDetailSearchBo.getSubjectId())){
            accountDetailSearchBo.setSubjectId(0);
        }
        if (accountDetailSearchBo.getProductIds().isEmpty()){
            accountDetailSearchBo.getProductIds().add(0L);
        }
//        在三种单据里面找找，最大的时间
        StoreCountBo storeCountByCondition = iStoreAccountService.getStoreCountByCondition(accountDetailSearchBo);
        if (Objects.isNull(storeCountByCondition)){
            storeCountByCondition = new StoreCountBo();
            storeCountByCondition.setStoreDate(accountDetailSearchBo.getStartDate().minusDays(1));
            storeCountByCondition.setStoreNum(0);
        }
        accountDetailSearchBo.setStoreDate(storeCountByCondition.getStoreDate());
        Integer countByCondition = iStoreAccountService.getCountByCondition(accountDetailSearchBo);
        log.info(JsonConverterBin.transferToJson(accountDetailSearchBo));
        IPage<AccountDetailVo> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(15);
        page.setPages(pages);
        IPage<AccountDetailVo> voPage = iAccountDetailService.listByCondition(page, accountDetailSearchBo, storeCountByCondition.getStoreNum() + countByCondition);
        List<AccountDetailVo> records = voPage.getRecords();
        for (int i = 0; i < records.size(); i++) {
            AccountDetailVo accountDetailVo = records.get(i);
            if (accountDetailVo.getType() <= 0) {
                accountDetailVo.setOrderDate(null);
            }else {
                if (accountDetailVo.getType() < 100){
                    if ((i + 1) < records.size()){
                        if (accountDetailVo.getOrderDate().equals(records.get(i+1).getOrderDate()) && records.get(i+1).getType() < 100){
                            accountDetailVo.setStoreNum(null);
                        }
                    }
                }else {
                    AccountDetailSearchBo searchBo = new AccountDetailSearchBo();
                    beanCopier.copy(accountDetailSearchBo,searchBo,null);
                    searchBo.setProductIds(accountDetailSearchBo.getProductIds());
                    if (accountDetailVo.getType() == 100){
                        if (searchBo.getStartDate().isBefore(accountDetailVo.getOrderDate().minusMonths(1))){
                            searchBo.setStartDate(accountDetailVo.getOrderDate().minusMonths(1));
                        }
                        searchBo.setEndDate(accountDetailVo.getOrderDate());
                        StoreAccumulateCountBo accumulateCountByCondition =
                                iStoreAccountService.getAccumulateCountByCondition(searchBo);
                        accountDetailVo.setInNum(accumulateCountByCondition.getAddCount());
                        accountDetailVo.setOutNum(accumulateCountByCondition.getSubstractCount());
                        accountDetailVo.setStoreNum(null);
                    }
                    if (accountDetailVo.getType() == 200){
                        searchBo.setStartDate(accountDetailSearchBo.getStartDate());
                        searchBo.setEndDate(accountDetailVo.getOrderDate());
                        StoreAccumulateCountBo accumulateCountByCondition =
                                iStoreAccountService.getAccumulateCountByCondition(searchBo);
                        accountDetailVo.setInNum(accumulateCountByCondition.getAddCount());
                        accountDetailVo.setOutNum(accumulateCountByCondition.getSubstractCount());
                        accountDetailVo.setStoreNum(null);
                    }
                }
            }
        }
        return ResponseBuilder.success(voPage);
    }
}
