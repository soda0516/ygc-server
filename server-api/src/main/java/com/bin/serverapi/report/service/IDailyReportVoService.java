package com.bin.serverapi.report.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.serverapi.report.bo.DailyReportVoSearchData;
import com.bin.serverapi.report.vo.DailyReportVo;

import java.time.LocalDate;
import java.util.List;

/**
 * @author subin
 */
public interface IDailyReportVoService extends IService<DailyReportVo> {
    /**
     * 返回一个日报表
     * @param categoryId
     * @param searchDate
     * @return
     */
    List<DailyReportVo> generateDailyReportVo(Long categoryId, DailyReportVoSearchData searchDate);

    /**
     * 返回一个空的日报表
     * @param categoryId
     * @return
     */
    List<DailyReportVo> generateEmptyDailyReportVo(Long categoryId);
}
