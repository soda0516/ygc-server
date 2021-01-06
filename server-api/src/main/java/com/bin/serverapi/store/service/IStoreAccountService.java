package com.bin.serverapi.store.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.bin.serverapi.store.entity.StoreAccount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.serverapi.store.bo.StoreAccountSetVo;

import java.time.LocalDate;
import java.util.List;

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
     * @param accountDate
     * @param storeId
     * @return
     */
    List<StoreAccountSetVo> listStoreAccountSetVoByDateStoreId(Wrapper<StoreAccount> queryWrapper);

    List<StoreAccountSetVo> createTemplet(LocalDate accountDate,Integer storeId);

    void saveTemplet(List<StoreAccountSetVo> storeAccountSetVoList);
}
