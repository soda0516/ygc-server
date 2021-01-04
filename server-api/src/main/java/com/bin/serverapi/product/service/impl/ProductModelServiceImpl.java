package com.bin.serverapi.product.service.impl;

import com.bin.serverapi.product.entity.ProductModel;
import com.bin.serverapi.product.mapper.ProductModelMapper;
import com.bin.serverapi.product.service.IProductModelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Soda
 * @since 2020-12-26
 */
@Service
public class ProductModelServiceImpl extends ServiceImpl<ProductModelMapper, ProductModel> implements IProductModelService {

}
