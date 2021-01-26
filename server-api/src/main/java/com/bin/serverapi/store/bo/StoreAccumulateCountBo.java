package com.bin.serverapi.store.bo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author bin
 * @ClassName StoreCountBo
 * @Description TODO
 * @date 2021/1/13 18:01
 */
@Data
public class StoreAccumulateCountBo {
    private Integer addCount;
    private Integer substractCount;
}
