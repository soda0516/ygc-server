package com.bin.serverapi.area.controller;

import com.bin.serverapi.area.entity.AreaRegion;
import com.bin.serverapi.area.service.IAreaCenterService;
import com.bin.serverapi.area.entity.AreaCenter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author subin
 */
@Slf4j
@RestController
@RequestMapping("/area/areaCenter")
public class AreaCenterController {

    @Autowired
    public IAreaCenterService areaCenterService;

    /**
     * 通过作业区获取中心站的列表
     * @return
     */
    @GetMapping(value = "/list/{id}")
    public ResponseModel<List<AreaCenter>> listByRegionId(@PathVariable("id") Long id){
        List<AreaCenter> list = areaCenterService.lambdaQuery()
                .eq(AreaCenter::getRegionId, id)
                .list();
        return ResponseBuilder.success(list);
    }

    /**
     * 保存和修改公用的
     * @param areaCenter  传递的实体
     * @return ResponseModel转换结果
     */
    @PostMapping
    public ResponseModel<String> save(@RequestBody AreaCenter areaCenter){
        try {
            if(areaCenter.getId()!=null){
                areaCenterService.updateById(areaCenter);
            }else{
                areaCenterService.save(areaCenter);
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
            areaCenterService.removeById(id);
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
    public ResponseModel<AreaCenter> get(@PathVariable("id") Long id) {
        return ResponseBuilder.success(areaCenterService.getById(id));
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping(value = "/list")
    public ResponseModel<List<AreaCenter>> list(){
        return ResponseBuilder.success(areaCenterService.list());
    }


    /**
    * 分页查询数据
    * @return PageList 分页对象
    */
    @GetMapping(value = "/page")
    public ResponseModel<IPage<AreaCenter>> page(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("size") Integer size,
            @RequestParam("pages") Integer pages) {
        IPage<AreaCenter> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(size);
        page.setPages(pages);
        return ResponseBuilder.success(areaCenterService.page(page));
    }
}