package com.bin.serverapi.report.mapper;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bin.serverapi.report.bo.AccountDetailSearchBo;
import com.bin.serverapi.report.vo.AccountDetailVo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * @author bin
 * @ClassName AccountDetailMapper
 * @Description TODO
 * @date 2021/1/12 14:17
 */
public interface AccountDetailMapper extends BaseMapper<AccountDetailVo> {

    /**
     * 根据条件，查询账目明细
     * @param page
     * @param searchData
     * @param storeNum
     * @return
     */
    IPage<AccountDetailVo> selectByCondition(IPage<?> page, @Param("searchData") AccountDetailSearchBo searchData, @Param("storeNum") Integer storeNum);

    /**
     * 查询最大日期
     * @param searchData
     * @return
     */
    LocalDate selectMaxDateInOrder(@Param("searchData") AccountDetailSearchBo searchData);
}
