package com.bin.serverapi.product.service.impl;

import com.bin.serverapi.product.entity.ProductDetail;
import com.bin.serverapi.product.mapper.ProductDetailMapper;
import com.bin.serverapi.product.service.IProductDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.serverapi.report.vo.ReportStoreAccountVo;
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
public class ProductDetailServiceImpl extends ServiceImpl<ProductDetailMapper, ProductDetail> implements IProductDetailService {
    @Override
    public List<ReportStoreAccountVo> listAllForStoreAccountVo() {
        return this.baseMapper.selectAllForStoreAccountVo();
    }
}
