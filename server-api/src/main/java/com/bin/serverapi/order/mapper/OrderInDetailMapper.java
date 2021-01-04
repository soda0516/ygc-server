package com.bin.serverapi.order.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bin.serverapi.order.bo.OrderInOutSearchDataBo;
import com.bin.serverapi.order.entity.OrderInDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.serverapi.order.vo.OrderInDetailVo;
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
    IPage<Map<String,Object>> selectWithOrderIn(IPage<?> page,@Param("searchData") OrderInOutSearchDataBo searchDataBo);
}
