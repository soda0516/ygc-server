package com.bin.serverapi.report.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.serverapi.report.vo.ReportStoreAccountVo;

import java.time.LocalDate;
import java.util.List;

/**
 * @author bin
 * @ClassName IStoreAccountService
 * @Description TODO
 * @date 2021/1/14 11:17
 */
public interface IReportStoreAccountService extends IService<ReportStoreAccountVo> {


    /**
     * 根据时间和小队的编号，显示库存
     * @param storeId
     * @param searchDate
     * @param categoryId
     * @return
     */
    List<ReportStoreAccountVo> showStoreAccountByStoreIdDate(Long storeId, LocalDate searchDate,Long categoryId);

}
