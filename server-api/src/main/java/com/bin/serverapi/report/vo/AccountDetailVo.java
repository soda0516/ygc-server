package com.bin.serverapi.report.vo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author bin
 * @ClassName AccountDetail
 * @Description TODO
 * @date 2021/1/12 14:18
 */
@Data
public class AccountDetailVo {
    private LocalDate orderDate;
    private String summary;
    private Integer inNum;
    private Integer outNum;
    private Integer storeNum;
    private Integer type;
}
