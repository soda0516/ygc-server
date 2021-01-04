package com.bin.serverapi.product.vo.detail;

import lombok.Data;

import java.util.List;

/**
 * @author bin
 * @ClassName ProductCategory
 * @Description TODO
 * @date 2020/12/29 10:14
 */
@Data
public class ProductCategoryVo {
    private Long value;
    private String label;
    List<ProductDetailVo> children;
}
