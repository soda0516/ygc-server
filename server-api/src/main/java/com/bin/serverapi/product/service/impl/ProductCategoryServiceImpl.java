package com.bin.serverapi.product.service.impl;

import com.bin.serverapi.product.entity.ProductCategory;
import com.bin.serverapi.product.mapper.ProductCategoryMapper;
import com.bin.serverapi.product.service.IProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.serverapi.product.vo.detail.ProductCategoryVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Soda
 * @since 2020-12-26
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements IProductCategoryService {
    @Override
    public List<ProductCategoryVo> listAllWithChildren() {
        return this.baseMapper.listAllWithChildren();
    }
}
