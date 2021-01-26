package com.bin.serverapi.report.vo;

import lombok.Data;

import java.util.List;

/**
 * @author bin
 * @ClassName DailyReportVo
 * @Description TODO
 * @date 2021/1/14 20:43
 */
@Data
public class DailyReportVo {
    private String subjectName;
    private String typeName;
    private List<DailyReportDetailVo> data;
}
