package com.bin.serverapi.product.service;

import com.bin.serverapi.product.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.serverapi.product.vo.detail.ProductCategoryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Soda
 * @since 2020-12-26
 */
public interface IProductCategoryService extends IService<ProductCategory> {
    List<ProductCategoryVo> listAllWithChildren();
}
