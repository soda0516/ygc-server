package com.bin.serverapi.product.mapper;

import com.bin.serverapi.product.entity.ProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.serverapi.product.vo.detail.ProductCategoryVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Soda
 * @since 2020-12-26
 */
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {
    /**
     * 查询所有的商品信息
     * @return
     */
    List<ProductCategoryVo> listAllWithChildren();
}
