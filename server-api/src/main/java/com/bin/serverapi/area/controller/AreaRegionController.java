package com.bin.serverapi.area.controller;

import com.bin.serverapi.area.service.IAreaRegionService;
import com.bin.serverapi.area.entity.AreaRegion;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.serverapi.product.entity.ProductDescription;
import lombok.extern.slf4j.Slf4j;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/area/areaRegion")
public class AreaRegionController {

    private BeanCopier beanCopier = BeanCopier.create(AreaRegion.class,AreaRegion.class,false);

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
     * @param name  传递的实体
     * @return ResponseModel转换结果
     */
    @PostMapping
    public ResponseModel<List<AreaRegion>> save(@RequestParam("region") String name){
        List<AreaRegion> list = areaRegionService.lambdaQuery()
                .eq(AreaRegion::getName, name)
                .list();
        if (list.isEmpty()){
            AreaRegion region = new AreaRegion();
            region.setName(name);
            areaRegionService.save(region);
            return ResponseBuilder.success(areaRegionService.list());
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
    public ResponseModel<AreaRegion> update(@RequestParam("item") String item){
        AreaRegion product = JsonConverterBin.transferToObject(item, AreaRegion.class);
        if (Objects.nonNull(product.getId())){
            AreaRegion byId = areaRegionService.getById(product.getId());
            if (Objects.nonNull(byId)){
                beanCopier.copy(product,byId,null);
                areaRegionService.updateById(byId);
                return ResponseBuilder.success(byId);
            }else {
                return ResponseBuilder.failure("没查询到对应的信息");
            }
        }else {
            return ResponseBuilder.failure();
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @DeleteMapping(value="/{id}")
    public ResponseModel<List<AreaRegion>> delete(@PathVariable("id") Long id){
        AreaRegion byId = areaRegionService.getById(id);
        if (Objects.nonNull(byId)){
            areaRegionService.removeById(byId.getId());
            return ResponseBuilder.success(areaRegionService.list());
        }else {
            return ResponseBuilder.failure("没有查询到要删除的信息");
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