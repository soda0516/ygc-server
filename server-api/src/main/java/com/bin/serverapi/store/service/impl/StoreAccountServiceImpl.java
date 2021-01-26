package com.bin.serverapi.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bin.serverapi.product.entity.ProductDetail;
import com.bin.serverapi.product.entity.ProductSubject;
import com.bin.serverapi.product.service.IProductDetailService;
import com.bin.serverapi.product.service.IProductSubjectService;
import com.bin.serverapi.report.bo.AccountDetailSearchBo;
import com.bin.serverapi.report.vo.ReportStoreAccountVo;
import com.bin.serverapi.store.bo.StoreAccountHistoryDate;
import com.bin.serverapi.store.bo.StoreAccumulateCountBo;
import com.bin.serverapi.store.bo.StoreCountBo;
import com.bin.serverapi.store.entity.StoreAccount;
import com.bin.serverapi.store.mapper.StoreAccountMapper;
import com.bin.serverapi.store.service.IStoreAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.serverapi.store.bo.StoreAccountSetVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Soda
 * @since 2021-01-05
 */
@Service
public class StoreAccountServiceImpl extends ServiceImpl<StoreAccountMapper, StoreAccount> implements IStoreAccountService {
    @Autowired
    IProductDetailService iProductDetailService;
    @Autowired
    IProductSubjectService iProductSubjectService;

    @Override
    public List<StoreAccountSetVo> listStoreAccountSetVoByDateStoreId(Wrapper<StoreAccount> queryWrapper) {
        return this.baseMapper.listStoreAccountSetVoByDateStoreId(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<StoreAccountSetVo> createTemplet(LocalDate accountDate, Integer storeId) {
        List<ProductDetail> detailList = iProductDetailService.list();
        List<ProductSubject> subjectList = iProductSubjectService.list();
        for (ProductDetail detail:detailList
             ) {
            for (ProductSubject subject:subjectList
                 ) {
                LambdaQueryWrapper<StoreAccount> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(StoreAccount::getAccountDate,accountDate);
                queryWrapper.eq(StoreAccount::getStoreId,storeId);
                queryWrapper.eq(StoreAccount::getProductDetailId,detail.getId());
                queryWrapper.eq(StoreAccount::getSubjectId,subject.getId());
                List<StoreAccount> storeAccounts = this.baseMapper.selectList(queryWrapper);
                if (storeAccounts.isEmpty()){
                    StoreAccount storeAccount = new StoreAccount();
                    storeAccount.setProductDetailId(detail.getId());
                    storeAccount.setSubjectId(subject.getId());
                    storeAccount.setStoreId(storeId);
                    storeAccount.setAccountDate(accountDate);
                    // TODO: 2021/1/6 这块更新一下操作人员的姓名啥的
//                    storeAccount.setTakeUser();
                    this.baseMapper.insert(storeAccount);
                }
            }
        }
        LambdaQueryWrapper<StoreAccount> storeAccountLambdaQueryWrapper = new LambdaQueryWrapper<>();
        storeAccountLambdaQueryWrapper.eq(StoreAccount::getAccountDate,accountDate);
        storeAccountLambdaQueryWrapper.eq(StoreAccount::getStoreId,storeId);
        return this.baseMapper.listStoreAccountSetVoByDateStoreId(storeAccountLambdaQueryWrapper);
    }

    @Override
    public void saveTemplet(List<StoreAccountSetVo> storeAccountSetVoList) {
        for (StoreAccountSetVo setVo:storeAccountSetVoList
             ) {
            LambdaQueryWrapper<StoreAccount> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(StoreAccount::getStoreId,setVo.getStoreId());
            lambdaQueryWrapper.eq(StoreAccount::getAccountDate,setVo.getAccountDate());
            lambdaQueryWrapper.eq(StoreAccount::getProductDetailId,setVo.getProductId());
            List<StoreAccount> storeAccounts = this.baseMapper.selectList(lambdaQueryWrapper);
            for (StoreAccount account:storeAccounts
                 ) {
                if (account.getSubjectId() == 1){
                    account.setStoreNum(setVo.getSubjectNumOne());
                    this.baseMapper.updateById(account);
                } else if (account.getSubjectId() == 2){
                    account.setStoreNum(setVo.getSubjectNumTwo());
                    this.baseMapper.updateById(account);
                } else if (account.getSubjectId() == 3){
                    account.setStoreNum(setVo.getSubjectNumThree());
                    this.baseMapper.updateById(account);
                } else if (account.getSubjectId() == 4){
                    account.setStoreNum(setVo.getSubjectNumFour());
                    this.baseMapper.updateById(account);
                } else if (account.getSubjectId() == 5){
                    account.setStoreNum(setVo.getSubjectNumFive());
                    this.baseMapper.updateById(account);
                } else {
                    account.setStoreNum(0);
                    this.baseMapper.updateById(account);
                }
            }
        }
    }

    @Override
    public List<StoreAccountHistoryDate> listStoreAccountHistoryDate(Integer storeId) {
        return this.baseMapper.selectStoreAccountHistoryDate(storeId);
    }

    @Override
    public Integer getCountByCondition(@Param("searchData") AccountDetailSearchBo searchData) {
        return this.baseMapper.selectCountByCondition(searchData);
    }

    @Override
    public StoreCountBo getStoreCountByCondition(AccountDetailSearchBo searchData) {
        return this.baseMapper.selectStoreCountByCondition(searchData);
    }

    @Override
    public StoreAccumulateCountBo getAccumulateCountByCondition(AccountDetailSearchBo searchData) {
        return this.baseMapper.selectAccumulateCountByCondition(searchData);
    }

    @Override
    public Map<Long, ReportStoreAccountVo> listForStoreAccountVo() {
        return this.baseMapper.selectForStoreAccountVo();
    }
}
