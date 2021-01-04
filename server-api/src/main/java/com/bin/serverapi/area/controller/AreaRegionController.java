package com.bin.serverapi.area.controller;

import com.bin.serverapi.area.service.IAreaRegionService;
import com.bin.serverapi.area.entity.AreaRegion;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/area/areaRegion")
public class AreaRegionController {

    @Autowired
    public IAreaRegionService areaRegionService;

    /**
     * 获取所有的列表
     * @return
     */
    @GetMapping(value = "/list/all")
    public ResponseModel<List<AreaRegion>> listAll(){
        log.info("listAll");
        return ResponseBuilder.success(areaRegionService.list());
    }
    /**
     * 保存和修改公用的
     * @param areaRegion  传递的实体
     * @return ResponseModel转换结果
     */
    @PostMapping
    public ResponseModel<String> save(@RequestBody AreaRegion areaRegion){
        try {
            if(areaRegion.getId()!=null){
                areaRegionService.updateById(areaRegion);
            }else{
                areaRegionService.save(areaRegion);
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
            areaRegionService.removeById(id);
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
    public ResponseModel<AreaRegion> get(@PathVariable("id") Long id) {
        return ResponseBuilder.success(areaRegionService.getById(id));
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping(value = "/list")
    public ResponseModel<List<AreaRegion>> list(){
        return ResponseBuilder.success(areaRegionService.list());
    }


    /**
    * 分页查询数据
    * @return PageList 分页对象
    */
    @GetMapping(value = "/page")
    public ResponseModel<IPage<AreaRegion>> page(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("size") Integer size,
            @RequestParam("pages") Integer pages) {
        IPage<AreaRegion> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(size);
        page.setPages(pages);
        return ResponseBuilder.success(areaRegionService.page(page));
    }
}