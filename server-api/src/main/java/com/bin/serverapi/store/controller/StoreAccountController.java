package com.bin.serverapi.store.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bin.serverapi.store.service.IStoreAccountService;
import com.bin.serverapi.store.entity.StoreAccount;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.serverapi.store.bo.StoreAccountSetVo;
import lombok.extern.slf4j.Slf4j;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.utils.JsonConverterBin;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/store/storeAccount")
public class StoreAccountController {

    @Autowired
    public IStoreAccountService storeAccountService;

    /**
     * 保存和修改公用的
     * @param storeAccount  传递的实体
     * @return ResponseModel转换结果
     */
    @PostMapping
    public ResponseModel<String> save(@RequestBody StoreAccount storeAccount){
        try {
            if(storeAccount.getId()!=null){
                storeAccountService.updateById(storeAccount);
            }else{
                storeAccountService.save(storeAccount);
            }
            return ResponseBuilder.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.failure("保存对象失败");
        }
    }

    /**
     * 保存和修改公用的
     * @return ResponseModel转换结果
     */
    @PostMapping("/templet")
    public ResponseModel<List<StoreAccountSetVo>> createTemplet(@RequestParam("accountDate") String accountDate, @RequestParam("storeId") Integer storeId){
        LocalDate parse = LocalDate.parse(accountDate);
        return ResponseBuilder.success(storeAccountService.createTemplet(parse,storeId));
    }
    @PutMapping("/templet")
    public ResponseModel<List<StoreAccountSetVo>> saveTemplet(@RequestParam("storeAccountSetVo") String storeAccountSetVo){
        List<StoreAccountSetVo> storeAccountSetVos = JsonConverterBin.transferToObjectList(storeAccountSetVo,
                StoreAccountSetVo.class);
        storeAccountService.saveTemplet(storeAccountSetVos);
        log.info("storeAccountSetVo");
        return ResponseBuilder.success();
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @DeleteMapping(value="/{id}")
    public ResponseModel<String> delete(@PathVariable("id") Long id){
        try {
            storeAccountService.removeById(id);
            return ResponseBuilder.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.failure("删除对象失败");
        }
    }

    /**
    * 查看单个信息
    * @return
    */
    @GetMapping(value = "/{id}")
    public ResponseModel<StoreAccount> get(@PathVariable("id") Long id) {
        return ResponseBuilder.success(storeAccountService.getById(id));
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping(value = "/list")
    public ResponseModel<List<StoreAccountSetVo>> listByDateStoreId(@RequestParam("accountDate") String accountDate,@RequestParam("storeId") Integer storeId){
        LambdaQueryWrapper<StoreAccount> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(StoreAccount::getAccountDate,accountDate);
//        queryWrapper.eq(StoreAccount::getStoreId,storeId);
        return ResponseBuilder.success(storeAccountService.listStoreAccountSetVoByDateStoreId(queryWrapper));
    }


    /**
    * 分页查询数据
    * @return PageList 分页对象
    */
    @GetMapping(value = "/page")
    public ResponseModel<IPage<StoreAccount>> page(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("size") Integer size,
            @RequestParam("pages") Integer pages) {
        IPage<StoreAccount> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(size);
        page.setPages(pages);
        return ResponseBuilder.success(storeAccountService.page(page));
    }
}