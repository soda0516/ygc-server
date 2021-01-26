package com.bin.serverapi.report.controller;

import com.bin.serverapi.report.bo.WorkloadSearchData;
import com.bin.serverapi.report.service.IWorkloadAccountService;
import com.bin.serverapi.report.vo.WorkloadAccountVo;
import com.bin.serverapi.store.entity.StoreInfo;
import com.bin.serverapi.store.service.IStoreInfoService;
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
import java.util.stream.Collectors;

/**
 * @author bin
 * @ClassName WorkloadAccountController
 * @Description TODO
 * @date 2021/1/14 14:06
 */
@RestController
@RequestMapping("/report/workloadAccount")
public class WorkloadAccountController {

    @Autowired
    IWorkloadAccountService iWorkloadAccountService;
    @Autowired
    IStoreInfoService iStoreInfoService;

    @GetMapping
    public ResponseModel<List<WorkloadAccountVo>> getInfo(@RequestParam("searchWorkloadData") String searchWorkloadData) {
        WorkloadSearchData workloadSearchData = JsonConverterBin.transferToObject(searchWorkloadData,
                WorkloadSearchData.class);
        if (Objects.isNull(workloadSearchData.getEndDate())){
            workloadSearchData.setEndDate(LocalDate.now());
        }
        if (Objects.isNull(workloadSearchData.getStartDate())){
            workloadSearchData.setEndDate(LocalDate.now().minusWeeks(1));
        }
//        if (workloadSearchData.getStoreId() <= 0){
//            List<StoreInfo> list = iStoreInfoService.list();
//            List<Long> collect = list.stream().map(StoreInfo::getId).collect(Collectors.toList());
//            workloadSearchData.setStoreIdList(collect);
//        }
        List<WorkloadAccountVo> workloadAccountVoList =
                iWorkloadAccountService.listWorkloadAccount(workloadSearchData);
        return ResponseBuilder.success(workloadAccountVoList);
    }
}
