package com.bin.serverapi.area.controller;

import com.bin.serverapi.area.entity.AreaCenter;
import com.bin.serverapi.area.entity.AreaRegion;
import com.bin.serverapi.area.service.IAreaStationService;
import com.bin.serverapi.area.entity.AreaStation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/area/areaStation")
public class AreaStationController {

    private BeanCopier beanCopier = BeanCopier.create(AreaStation.class,AreaStation.class,false);

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
     * @param name  传递的实体
     * @return ResponseModel转换结果
     */
    @PostMapping
    public ResponseModel<List<AreaStation>> save(@RequestParam("centerId") Long centerId,@RequestParam("name") String name){
        List<AreaStation> list = areaStationService.lambdaQuery()
                .eq(AreaStation::getCenterId,centerId)
                .eq(AreaStation::getName, name)
                .list();
        if (list.isEmpty()){
            AreaStation station = new AreaStation();
            station.setCenterId(centerId);
            station.setName(name);
            areaStationService.save(station);
            List<AreaStation> stationList = areaStationService.lambdaQuery()
                    .eq(AreaStation::getCenterId, centerId)
                    .list();
            return ResponseBuilder.success(stationList);
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
    public ResponseModel<AreaStation> update(@RequestParam("item") String item){
        AreaStation station = JsonConverterBin.transferToObject(item, AreaStation.class);
        if (Objects.nonNull(station.getId())){
            AreaStation byId = areaStationService.getById(station.getId());
            if (Objects.nonNull(byId)){
                beanCopier.copy(station,byId,null);
                areaStationService.updateById(byId);
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
    public ResponseModel<String> delete(@PathVariable("id") Long id){
        AreaStation byId = areaStationService.getById(id);
        if (Objects.nonNull(byId)){
            areaStationService.removeById(byId.getId());
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