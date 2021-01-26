package com.bin.serverapi.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bin.serverapi.product.entity.ProductCategory;
import com.bin.serverapi.product.entity.ProductDescription;
import com.bin.serverapi.product.service.IProductModelService;
import com.bin.serverapi.product.entity.ProductModel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.utils.JsonConverterBin;
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
     * @param name  传递的实体
     * @return ResponseModel转换结果
     */
    @PostMapping
    public ResponseModel<List<ProductModel>> save(@RequestParam("name") String name,@RequestParam("categoryId") Long categoryId){
        Integer count = productModelService.lambdaQuery()
                .eq(ProductModel::getName, name)
                .count();
        if (count > 0){
            return ResponseBuilder.failure("输入的名称重复，请重新输入");
        }else {
            ProductModel category = new ProductModel();
            category.setProductCategoryId(categoryId);
            category.setName(name);
            boolean save = productModelService.save(category);
            if (save){
                List<ProductModel> list = productModelService.lambdaQuery()
                        .eq(ProductModel::getProductCategoryId, categoryId)
                        .list();
                return ResponseBuilder.success(list);
            }else {
                return ResponseBuilder.failure("添加名称失败");
            }
        }
    }
    /**
     * 保存和修改公用的
     * @param item  传递的实体
     * @return ResponseModel转换结果
     */
    @PutMapping
    public ResponseModel<ProductModel> update(@RequestParam("item") String item){
        ProductModel product = JsonConverterBin.transferToObject(item, ProductModel.class);
        boolean b = productModelService.saveOrUpdate(product);
        if (b){
            return ResponseBuilder.success(productModelService.getById(product.getId()));
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
     * 查看所有的员工信息
     * @return
     */
    @GetMapping(value = "/list/category/{id}")
    public ResponseModel<List<ProductModel>> list(@PathVariable("id") Long id){
        LambdaQueryWrapper<ProductModel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ProductModel::getProductCategoryId,id);
        return ResponseBuilder.success(productModelService.list(lambdaQueryWrapper));
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