package com.bin.serverapi.store.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.bin.serverapi.report.bo.AccountDetailSearchBo;
import com.bin.serverapi.report.vo.ReportStoreAccountVo;
import com.bin.serverapi.store.bo.StoreAccountHistoryDate;
import com.bin.serverapi.store.bo.StoreAccumulateCountBo;
import com.bin.serverapi.store.bo.StoreCountBo;
import com.bin.serverapi.store.entity.StoreAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.serverapi.store.bo.StoreAccountSetVo;
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
 * @since 2021-01-05
 */
public interface StoreAccountMapper extends BaseMapper<StoreAccount> {
    /**
     * 获取前端需要的库存盘点信息
     * @param queryWrapper
     * @return
     */
    List<StoreAccountSetVo> listStoreAccountSetVoByDateStoreId(@Param(Constants.WRAPPER) Wrapper<StoreAccount> queryWrapper);

    /**
     * 查询盘点历史事件的列表
     * @param storeId
     * @return
     */
    List<StoreAccountHistoryDate> selectStoreAccountHistoryDate(@Param("storeId") Integer storeId);

    /**
     * 管局条就按查询库存
     * @return
     */
    Integer selectCountByCondition(@Param("searchData") AccountDetailSearchBo searchData);

    /**
     * 返回库存的数字
     * @return
     */
    StoreCountBo selectStoreCountByCondition(@Param("searchData") AccountDetailSearchBo searchData);

    /**
     * 用来计算月计累计啊
     * @param searchData
     * @return
     */
    StoreAccumulateCountBo selectAccumulateCountByCondition(@Param("searchData") AccountDetailSearchBo searchData);

    /**
     * 返回一个用来库存表查询的结果
     * @return
     */
    @MapKey("productDetailId")
    Map<Long, ReportStoreAccountVo> selectForStoreAccountVo();
}
