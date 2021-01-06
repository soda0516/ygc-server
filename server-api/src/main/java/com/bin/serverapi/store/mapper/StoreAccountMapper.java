package com.bin.serverapi.store.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.bin.serverapi.store.entity.StoreAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.serverapi.store.bo.StoreAccountSetVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
}
