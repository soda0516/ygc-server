package com.bin.serverapi.product.service.impl;

import com.bin.serverapi.product.entity.ProductSpecification;
import com.bin.serverapi.product.mapper.ProductSpecificationMapper;
import com.bin.serverapi.product.service.IProductSpecificationService;
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
public class ProductSpecificationServiceImpl extends ServiceImpl<ProductSpecificationMapper, ProductSpecification> implements IProductSpecificationService {

}
