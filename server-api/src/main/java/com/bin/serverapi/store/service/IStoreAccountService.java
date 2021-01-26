package com.bin.serverapi.store.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.bin.serverapi.report.bo.AccountDetailSearchBo;
import com.bin.serverapi.report.vo.ReportStoreAccountVo;
import com.bin.serverapi.store.bo.StoreAccountHistoryDate;
import com.bin.serverapi.store.bo.StoreAccumulateCountBo;
import com.bin.serverapi.store.bo.StoreCountBo;
import com.bin.serverapi.store.entity.StoreAccount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.serverapi.store.bo.StoreAccountSetVo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Soda
 * @since 2021-01-05
 */
public interface IStoreAccountService extends IService<StoreAccount> {
    /**
     * 获取前端需要的库存盘点信息
     * @param queryWrapper
     * @return
     */
    List<StoreAccountSetVo> listStoreAccountSetVoByDateStoreId(Wrapper<StoreAccount> queryWrapper);

    List<StoreAccountSetVo> createTemplet(LocalDate accountDate,Integer storeId);

    void saveTemplet(List<StoreAccountSetVo> storeAccountSetVoList);

    List<StoreAccountHistoryDate> listStoreAccountHistoryDate(Integer storeId);

    Integer getCountByCondition(AccountDetailSearchBo searchData);

    StoreCountBo getStoreCountByCondition(AccountDetailSearchBo searchData);

    StoreAccumulateCountBo getAccumulateCountByCondition(AccountDetailSearchBo searchData);

    Map<Long, ReportStoreAccountVo> listForStoreAccountVo();
}
