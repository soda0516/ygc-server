package com.bin.serverapi.report.bo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author bin
 * @ClassName DailyReportVoSearchDate
 * @Description TODO
 * @date 2021/1/18 14:32
 */
@Data
public class DailyReportVoSearchData {
    private LocalDate searchDate;
    private Long storeId;
}
