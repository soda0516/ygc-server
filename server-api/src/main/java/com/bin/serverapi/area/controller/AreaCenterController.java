package com.bin.serverapi.area.controller;

import com.bin.serverapi.area.entity.AreaRegion;
import com.bin.serverapi.area.service.IAreaCenterService;
import com.bin.serverapi.area.entity.AreaCenter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author subin
 */
@Slf4j
@RestController
@RequestMapping("/area/areaCenter")
public class AreaCenterController {

    private BeanCopier beanCopier = BeanCopier.create(AreaCenter.class,AreaCenter.class,false);

    @Autowired
    public IAreaCenterService areaCenterService;

    @PostMapping
    public ResponseModel<List<AreaCenter>> save(@RequestParam("regionId") Long regionId, @RequestParam("name") String name){
        List<AreaCenter> list = areaCenterService.lambdaQuery()
                .eq(AreaCenter::getRegionId, regionId)
                .eq(AreaCenter::getName, name)
                .list();
        if (list.isEmpty()){
            AreaCenter center = new AreaCenter();
            center.setRegionId(regionId);
            center.setName(name);
            areaCenterService.save(center);
            List<AreaCenter> centerList = areaCenterService.lambdaQuery()
                    .eq(AreaCenter::getRegionId, regionId)
                    .list();
            return ResponseBuilder.success(centerList);
        }else {
            return ResponseBuilder.failure("输入的名称重复，请重新输入！");
        }
    }

    /**
     * 保存和修改公用的
     * @param item  传递的实体
     * @return ResponseModel转换结果
     */
    @PutMapping
    public ResponseModel<AreaCenter> update(@RequestParam("item") String item){
        AreaCenter center = JsonConverterBin.transferToObject(item, AreaCenter.class);
        if (Objects.nonNull(center.getId())){
            AreaCenter byId = areaCenterService.getById(center.getId());
            if (Objects.nonNull(byId)){
                beanCopier.copy(center,byId,null);
                areaCenterService.updateById(byId);
                return ResponseBuilder.success(byId);
            }else {
                return ResponseBuilder.failure("没查询到对应的信息");
            }
        }else {
            return ResponseBuilder.failure();
        }
    }

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
     * 通过作业区获取中心站的列表
     * @return
     */
    @GetMapping(value = "/list")
    public ResponseModel<List<AreaCenter>> listAll(){
        return ResponseBuilder.success(areaCenterService.list());
    }
    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @DeleteMapping(value="/{id}")
    public ResponseModel<String> delete(@PathVariable("id") Long id){
        AreaCenter byId = areaCenterService.getById(id);
        if (Objects.nonNull(byId)){
            areaCenterService.removeById(byId.getId());
            return ResponseBuilder.success();
        }else {
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