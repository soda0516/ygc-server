package com.bin.serverapi.store.bo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author bin
 * @ClassName StoreSearchForInOutOrder
 * @Description TODO
 * @date 2021/1/15 13:44
 */
@Data
public class StoreSearchForInOutOrder {
    private Integer storeId;
    private Long productCategoryId;
    private LocalDate orderDate;
}
