package com.bin.serverapi.report.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import me.subin.jackson.serializer.ZeroToNullSerializer;

/**
 * @author bin
 * @ClassName StoreAccountVo
 * @Description TODO
 * @date 2021/1/14 9:47
 */
@Data
public class ReportStoreAccountVo {
    private Long productCategoryId;
    private Long productModelId;
    private Long productDetailId;
    private String productName;
    private Integer subject1Num;
    private Integer subject2Num;
    private Integer subject3Num;
    private Integer subject4Num;
    private Integer subject5Num;
}
