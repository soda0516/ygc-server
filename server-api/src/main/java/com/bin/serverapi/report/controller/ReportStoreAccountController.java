package com.bin.serverapi.report.controller;

import com.bin.serverapi.report.bo.ReportStoreVoSearchData;
import com.bin.serverapi.report.service.IReportStoreAccountService;
import com.bin.serverapi.report.vo.ReportStoreAccountVo;
import lombok.extern.slf4j.Slf4j;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author bin
 * @ClassName ReportStoreAccountController
 * @Description TODO
 * @date 2021/1/14 12:23
 */
@Slf4j
@RestController
@RequestMapping(value = "/report/storeAccount")
public class ReportStoreAccountController {
    @Autowired
    IReportStoreAccountService iReportStoreAccountService;

    @GetMapping
    public ResponseModel<List<ReportStoreAccountVo>> getReportStore(@RequestParam("searchData") String searchData){
        ReportStoreVoSearchData reportStoreVoSearchData = JsonConverterBin.transferToObject(searchData,
                ReportStoreVoSearchData.class);
        if (Objects.isNull(reportStoreVoSearchData.getSearchDate())){
            reportStoreVoSearchData.setSearchDate(LocalDate.now());
        }
        return ResponseBuilder.success(iReportStoreAccountService.showStoreAccountByStoreIdDate(reportStoreVoSearchData.getStoreId(), reportStoreVoSearchData.getSearchDate(),null));
    }
}
