package com.bin.serverapi.report.controller;

import com.bin.serverapi.report.bo.DailyReportVoSearchData;
import com.bin.serverapi.report.service.IDailyReportVoService;
import com.bin.serverapi.report.vo.DailyReportVo;
import lombok.extern.slf4j.Slf4j;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Objects;

/**
 * @author bin
 * @ClassName DailyReportVoController
 * @Description TODO
 * @date 2021/1/16 13:10
 */
@Slf4j
@RestController
@RequestMapping(value = "/report/dailyReport")
public class DailyReportVoController {
    @Autowired
    IDailyReportVoService iDailyReportVoService;
    @GetMapping
    public ResponseModel<List<DailyReportVo>> getInfo(@RequestParam("categoryId") Long categoryId,
                                                      @RequestParam("searchData") String searchData) {
        DailyReportVoSearchData transferToObject = JsonConverterBin.transferToObject(searchData, DailyReportVoSearchData.class);
        log.info(JsonConverterBin.transferToJson(transferToObject));
        if (Objects.nonNull(transferToObject)){
            return ResponseBuilder.success(iDailyReportVoService.generateDailyReportVo(categoryId,transferToObject));
        } else {
            return ResponseBuilder.success(iDailyReportVoService.generateEmptyDailyReportVo(categoryId));
        }
    }
}
