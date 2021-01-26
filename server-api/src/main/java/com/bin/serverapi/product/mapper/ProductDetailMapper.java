package com.bin.serverapi.product.mapper;

import com.bin.serverapi.product.entity.ProductDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.serverapi.report.vo.ReportStoreAccountVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Soda
 * @since 2020-12-26
 */
public interface ProductDetailMapper extends BaseMapper<ProductDetail> {
    /**
     * 用来显示所有信息用来显示库存查询
     * @return
     */
    List<ReportStoreAccountVo> selectAllForStoreAccountVo();
}
