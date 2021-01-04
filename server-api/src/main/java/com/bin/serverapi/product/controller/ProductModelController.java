package com.bin.serverapi.product.controller;

import com.bin.serverapi.product.service.IProductModelService;
import com.bin.serverapi.product.entity.ProductModel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product/productModel")
public class ProductModelController {

    @Autowired
    public IProductModelService productModelService;

    /**
     * 保存和修改公用的
     * @param productModel  传递的实体
     * @return ResponseModel转换结果
     */
    @PostMapping
    public ResponseModel<String> save(@RequestBody ProductModel productModel){
        try {
            if(productModel.getId()!=null){
                productModelService.updateById(productModel);
            }else{
                productModelService.save(productModel);
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
            productModelService.removeById(id);
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
    public ResponseModel<ProductModel> get(@PathVariable("id") Long id) {
        return ResponseBuilder.success(productModelService.getById(id));
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping(value = "/list")
    public ResponseModel<List<ProductModel>> list(){
        return ResponseBuilder.success(productModelService.list());
    }


    /**
    * 分页查询数据
    * @return PageList 分页对象
    */
    @GetMapping(value = "/page")
    public ResponseModel<IPage<ProductModel>> page(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("size") Integer size,
            @RequestParam("pages") Integer pages) {
        IPage<ProductModel> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(size);
        page.setPages(pages);
        return ResponseBuilder.success(productModelService.page(page));
    }
}