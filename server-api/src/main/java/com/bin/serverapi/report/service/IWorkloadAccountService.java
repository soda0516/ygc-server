package com.bin.serverapi.report.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.serverapi.report.bo.WorkloadSearchData;
import com.bin.serverapi.report.vo.ReportStoreAccountVo;
import com.bin.serverapi.report.vo.WorkloadAccountVo;

import java.time.LocalDate;
import java.util.List;

/**
 * @author subin
 */
public interface IWorkloadAccountService extends IService<WorkloadAccountVo> {
    /**
     * 获取工作量信息
     * @return
     */
    List<WorkloadAccountVo> listWorkloadAccount(WorkloadSearchData searchData);
}
