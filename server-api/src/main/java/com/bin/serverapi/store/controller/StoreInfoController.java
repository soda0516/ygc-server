package com.bin.serverapi.store.controller;

import com.bin.serverapi.store.service.IStoreInfoService;
import com.bin.serverapi.store.entity.StoreInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store/storeInfo")
public class StoreInfoController {

    @Autowired
    public IStoreInfoService storeInfoService;

    /**
     * 保存和修改公用的
     * @param storeInfo  传递的实体
     * @return ResponseModel转换结果
     */
    @PostMapping
    public ResponseModel<String> save(@RequestBody StoreInfo storeInfo){
        try {
            if(storeInfo.getId()!=null){
                storeInfoService.updateById(storeInfo);
            }else{
                storeInfoService.save(storeInfo);
            }
            return ResponseBuilder.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.failure("保存对象失败");
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @DeleteMapping(value="/{id}")
    public ResponseModel<String> delete(@PathVariable("id") Long id){
        try {
            storeInfoService.removeById(id);
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
    public ResponseModel<StoreInfo> get(@PathVariable("id") Long id) {
        return ResponseBuilder.success(storeInfoService.getById(id));
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping(value = "/list")
    public ResponseModel<List<StoreInfo>> list(){
        return ResponseBuilder.success(storeInfoService.list());
    }


    /**
    * 分页查询数据
    * @return PageList 分页对象
    */
    @GetMapping(value = "/page")
    public ResponseModel<IPage<StoreInfo>> page(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("size") Integer size,
            @RequestParam("pages") Integer pages) {
        IPage<StoreInfo> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(size);
        page.setPages(pages);
        return ResponseBuilder.success(storeInfoService.page(page));
    }
}