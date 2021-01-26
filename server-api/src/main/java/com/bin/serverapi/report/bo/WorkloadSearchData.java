package com.bin.serverapi.report.bo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author bin
 * @ClassName WorkloadSearchData
 * @Description TODO
 * @date 2021/1/21 22:16
 */
@Data
public class WorkloadSearchData {
    private Long storeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> storeIdList;
}
