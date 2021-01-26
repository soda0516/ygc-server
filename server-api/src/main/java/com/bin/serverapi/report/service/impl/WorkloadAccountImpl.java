package com.bin.serverapi.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.serverapi.report.bo.WorkloadSearchData;
import com.bin.serverapi.report.mapper.WorkloadAccountMapper;
import com.bin.serverapi.report.service.IWorkloadAccountService;
import com.bin.serverapi.report.vo.WorkloadAccountVo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author bin
 * @ClassName WorkloadAccountImpl
 * @Description TODO
 * @date 2021/1/14 14:06
 */
@Service
public class WorkloadAccountImpl extends ServiceImpl<WorkloadAccountMapper, WorkloadAccountVo> implements IWorkloadAccountService {
    @Override
    public List<WorkloadAccountVo> listWorkloadAccount(WorkloadSearchData searchData) {
        List<WorkloadAccountVo> workloadAccountVoList = new ArrayList<>();
        WorkloadAccountVo inNum = this.baseMapper.selectWorkloadInNum(searchData);
        if (Objects.isNull(inNum)){
            inNum = new WorkloadAccountVo();
        }
        inNum.setWorkloadOneName("回收油管");
        inNum.setWorkloadTwoName("回收抽油杆");
        workloadAccountVoList.add(inNum);
        WorkloadAccountVo outNum = this.baseMapper.selectWorkloadOutNum(searchData);
        if (Objects.isNull(outNum)){
            outNum = new WorkloadAccountVo();
        }
        outNum.setWorkloadOneName("发出油管");
        outNum.setWorkloadTwoName("发出抽油杆");
        workloadAccountVoList.add(outNum);
        WorkloadAccountVo checkNum = this.baseMapper.selectWorkloadCheckNum(searchData);
        if (Objects.isNull(checkNum)){
            checkNum = new WorkloadAccountVo();
        }
        checkNum.setWorkloadOneName("分检油管");
        checkNum.setWorkloadTwoName("分检抽油杆");
        workloadAccountVoList.add(checkNum);
        WorkloadAccountVo checkNoUserNum = this.baseMapper.selectWorkloadCheckNoUserNum(searchData);
        if (Objects.isNull(checkNoUserNum)){
            checkNoUserNum = new WorkloadAccountVo();
        }
        checkNoUserNum.setWorkloadOneName("待报废油管");
        checkNoUserNum.setWorkloadTwoName("待报废抽油杆");
        workloadAccountVoList.add(checkNoUserNum);
        WorkloadAccountVo workloadWellCount = this.baseMapper.selectWorkloadWellCount(searchData);
        if (Objects.isNull(workloadWellCount)){
            workloadWellCount = new WorkloadAccountVo();
        }
        workloadWellCount.setWorkloadOneName("回收井次");
        workloadWellCount.setWorkloadTwoName("发出井次");
        workloadAccountVoList.add(workloadWellCount);
        return workloadAccountVoList;
    }
}
