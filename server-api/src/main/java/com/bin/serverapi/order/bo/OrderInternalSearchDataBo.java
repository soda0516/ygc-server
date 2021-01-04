package com.bin.serverapi.order.bo;

import lombok.Data;

import java.util.List;

/**
 * @author bin
 * @ClassName OrderInOutSearchData
 * @Description TODO
 * @date 2021/1/3 21:57
 */
@Data
public class OrderInternalSearchDataBo {
    private String startDate;
    private String endDate;
    private Integer addSubject;
    private Integer subtractSubject;
    private String searchWords;
    private Integer orderType;
    private String operType;
    private List<Long> productDetailIds;
}
