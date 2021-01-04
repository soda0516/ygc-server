package com.bin.serverapi.order.bo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author bin
 * @ClassName OrderInOutSearchData
 * @Description TODO
 * @date 2021/1/3 21:57
 */
@Data
public class OrderInOutSearchDataBo {
    private String startDate;
    private String endDate;
    private Long selectedRegion;
    private Long selectedSubject;
    private String wellName;
    private Integer orderType;
    private String operItem;
    private String remark;
    private List<Long> productDetailIds;
}
