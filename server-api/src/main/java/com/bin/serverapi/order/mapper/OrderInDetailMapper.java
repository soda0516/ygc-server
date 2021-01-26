package com.bin.serverapi.order.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.bin.serverapi.order.bo.OrderInOutSearchDataBo;
import com.bin.serverapi.order.entity.OrderInDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.serverapi.order.vo.OrderInDetailVo;
import com.bin.serverapi.report.vo.ReportStoreAccountVo;
import com.bin.serverapi.store.bo.StoreSearchForInOutOrder;
import com.bin.serverapi.store.entity.StoreAccount;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Soda
 * @since 2020-12-29
 */
public interface OrderInDetailMapper extends BaseMapper<OrderInDetail> {
    List<OrderInDetailVo> selectBasicList(@Param("pid") Long id);

    /**
     * 查询今天的回收单据
     * @param page
     * @return
     */
    IPage<Map<String,Object>> selectWithOrderInToday(IPage<?> page);

    /**
     * 根据条件查询回收单据
     * @param page
     * @param searchDataBo
     * @return
     */
    IPage<Map<String,Object>> selectWithOrderIn(IPage<?> page,@Param("searchData") OrderInOutSearchDataBo searchDataBo);

    /**
     * 根据条件查询入库的数量根据商品ID分组
     * @param queryWrapper
     * @return
     */
    @MapKey("productDetailId")
    Map<Long, ReportStoreAccountVo> selectForStoreAccount(@Param(Constants.WRAPPER) Wrapper<OrderInDetail> queryWrapper);
}
