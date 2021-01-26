package com.bin.serverapi.product.service;

import com.bin.serverapi.product.entity.ProductDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.serverapi.report.vo.ReportStoreAccountVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Soda
 * @since 2020-12-26
 */
public interface IProductDetailService extends IService<ProductDetail> {
    /**
     * 用来显示所有信息用来显示库存查询
     * @return
     */
    List<ReportStoreAccountVo> listAllForStoreAccountVo();
}
