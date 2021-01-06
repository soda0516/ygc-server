package com.bin.serverapi.store.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author bin
 * @ClassName StoreAccountSetVo
 * @Description TODO
 * @date 2021/1/5 12:19
 */
@Data
public class StoreAccountSetVo {
    /**
     * 盘点日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate accountDate;
    /**
     * 管材规格型号名称
     */
    private String productName;
    /**
     * 管材规格型号ID
     */
    private Long productId;

    /**
     * 回收
     */
    private Integer subjectNumOne;
    /**
     * 领用
     */
    private Integer subjectNumTwo;
    /**
     * 周转
     */
    private Integer subjectNumThree;
    /**
     * 待修
     */
    private Integer subjectNumFour;
    /**
     * 待报废
     */
    private Integer subjectNumFive;
    /**
     * 仓库ID
     */
    private Integer storeId;
}
