package com.bin.serverapi.product.service.impl;

import com.bin.serverapi.product.entity.ProductDescription;
import com.bin.serverapi.product.mapper.ProductDescriptionMapper;
import com.bin.serverapi.product.service.IProductDescriptionService;
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
public class ProductDescriptionServiceImpl extends ServiceImpl<ProductDescriptionMapper, ProductDescription> implements IProductDescriptionService {

}
