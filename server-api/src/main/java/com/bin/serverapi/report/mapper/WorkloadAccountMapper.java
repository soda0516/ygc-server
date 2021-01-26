package com.bin.serverapi.report.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.serverapi.report.bo.WorkloadSearchData;
import com.bin.serverapi.report.vo.ReportStoreAccountVo;
import com.bin.serverapi.report.vo.WorkloadAccountVo;
import org.apache.ibatis.annotations.Param;

/**
 * 用来统计工作量的接口
 * @author subin
 */
public interface WorkloadAccountMapper extends BaseMapper<WorkloadAccountVo> {
    /**
     * 回收工作量
     * @return
     */
    WorkloadAccountVo selectWorkloadInNum(@Param("searchData") WorkloadSearchData searchData);
    /**
     * 发出作量
     * @return
     */
    WorkloadAccountVo selectWorkloadOutNum(@Param("searchData") WorkloadSearchData searchData);
    /**
     * 分拣不含待报废工作量
     * @return
     */
    WorkloadAccountVo selectWorkloadCheckNum(@Param("searchData") WorkloadSearchData searchData);
    /**
     * 分拣待报废工作量
     * @return
     */
    WorkloadAccountVo selectWorkloadCheckNoUserNum(@Param("searchData") WorkloadSearchData searchData);
    /**
     * 工作井次
     * @return
     */
    WorkloadAccountVo selectWorkloadWellCount(@Param("searchData") WorkloadSearchData searchData);
}
