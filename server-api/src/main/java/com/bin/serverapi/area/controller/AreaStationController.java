package com.bin.serverapi.area.controller;

import com.bin.serverapi.area.entity.AreaCenter;
import com.bin.serverapi.area.service.IAreaStationService;
import com.bin.serverapi.area.entity.AreaStation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/area/areaStation")
public class AreaStationController {

    @Autowired
    public IAreaStationService areaStationService;

    /**
     * 通过中心站获取小站站的列表
     * @return
     */
    @GetMapping(value = "/list/{id}")
    public ResponseModel<List<AreaStation>> listByCenterId(@PathVariable("id") Long id){
        List<AreaStation> list = areaStationService.lambdaQuery()
                .eq(AreaStation::getCenterId, id)
                .list();
        return ResponseBuilder.success(list);
    }

    /**
     * 保存和修改公用的
     * @param areaStation  传递的实体
     * @return ResponseModel转换结果
     */
    @PostMapping
    public ResponseModel<String> save(@RequestBody AreaStation areaStation){
        try {
            if(areaStation.getId()!=null){
                areaStationService.updateById(areaStation);
            }else{
                areaStationService.save(areaStation);
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
            areaStationService.removeById(id);
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
    public ResponseModel<AreaStation> get(@PathVariable("id") Long id) {
        return ResponseBuilder.success(areaStationService.getById(id));
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping(value = "/list")
    public ResponseModel<List<AreaStation>> list(){
        return ResponseBuilder.success(areaStationService.list());
    }


    /**
    * 分页查询数据
    * @return PageList 分页对象
    */
    @GetMapping(value = "/page")
    public ResponseModel<IPage<AreaStation>> page(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("size") Integer size,
            @RequestParam("pages") Integer pages) {
        IPage<AreaStation> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(size);
        page.setPages(pages);
        return ResponseBuilder.success(areaStationService.page(page));
    }
}