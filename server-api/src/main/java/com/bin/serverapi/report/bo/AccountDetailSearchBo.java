package com.bin.serverapi.report.bo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author bin
 * @ClassName AccountDetailSearchBo
 * @Description TODO
 * @date 2021/1/13 8:54
 */
@Data
public class AccountDetailSearchBo {
    private LocalDate storeDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer subjectId;
    private Long storeId;
    private List<Long> productIds;
}
