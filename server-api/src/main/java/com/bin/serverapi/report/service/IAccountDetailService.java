package com.bin.serverapi.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.serverapi.report.bo.AccountDetailSearchBo;
import com.bin.serverapi.report.vo.AccountDetailVo;

import java.util.List;

/**
 * @author subin
 */
public interface IAccountDetailService extends IService<AccountDetailVo> {
    IPage<AccountDetailVo> listByCondition(IPage<AccountDetailVo> page, AccountDetailSearchBo searchData, Integer storeNum);
}
