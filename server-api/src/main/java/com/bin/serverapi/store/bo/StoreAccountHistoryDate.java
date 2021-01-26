package com.bin.serverapi.store.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author bin
 * @ClassName StoreAccountHistoryDate
 * @Description TODO
 * @date 2021/1/6 21:37
 */
@Data
public class StoreAccountHistoryDate {
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate historyDate;
}
