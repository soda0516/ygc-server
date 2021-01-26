package com.bin.serverapi.report.bo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author bin
 * @ClassName ReportStoreSearchData
 * @Description TODO
 * @date 2021/1/21 21:56
 */
@Data
public class ReportStoreVoSearchData {
    private LocalDate searchDate;
    private Long storeId;
}
