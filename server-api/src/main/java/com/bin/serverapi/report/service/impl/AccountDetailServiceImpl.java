package com.bin.serverapi.report.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.serverapi.report.bo.AccountDetailSearchBo;
import com.bin.serverapi.report.mapper.AccountDetailMapper;
import com.bin.serverapi.report.service.IAccountDetailService;
import com.bin.serverapi.report.vo.AccountDetailVo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author bin
 * @ClassName AccountDetailServiceImpl
 * @Description TODO
 * @date 2021/1/12 14:19
 */
@Service
public class AccountDetailServiceImpl extends ServiceImpl<AccountDetailMapper, AccountDetailVo> implements IAccountDetailService {
    @Override
    public IPage<AccountDetailVo> listByCondition(IPage<AccountDetailVo> page, AccountDetailSearchBo searchData, Integer storeNum) {
        LocalDate localDate = this.baseMapper.selectMaxDateInOrder(searchData);
        if (Objects.isNull(localDate)){
            localDate = searchData.getStartDate();
        }
        if (localDate.isBefore(searchData.getEndDate())){
            searchData.setEndDate(localDate);
        }
        return this.baseMapper.selectByCondition(page,searchData,storeNum);
    }
}
