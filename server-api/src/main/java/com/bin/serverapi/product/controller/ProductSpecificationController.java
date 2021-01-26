package com.bin.serverapi.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bin.serverapi.product.entity.ProductCategory;
import com.bin.serverapi.product.entity.ProductModel;
import com.bin.serverapi.product.service.IProductSpecificationService;
import com.bin.serverapi.product.entity.ProductSpecification;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product/productSpecification")
public class ProductSpecificationController {

    @Autowired
    public IProductSpecificationService productSpecificationService;

    /**
     * 保存和修改公用的
     * @param name  传递的实体
     * @return ResponseModel转换结果
     */
    @PostMapping
    public ResponseModel<List<ProductSpecification>> save(@RequestParam("name") String name,@RequestParam("categoryId") Long categoryId){
        Integer count = productSpecificationService.lambdaQuery()
                .eq(ProductSpecification::getName, name)
                .count();
        if (count > 0){
            return ResponseBuilder.failure("输入的名称重复，请重新输入");
        }else {
            ProductSpecification category = new ProductSpecification();
            category.setProductCategoryId(categoryId);
            category.setName(name);
            boolean save = productSpecificationService.save(category);
            if (save){
                List<ProductSpecification> list = productSpecificationService.lambdaQuery()
                        .eq(ProductSpecification::getProductCategoryId, categoryId)
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
    public ResponseModel<ProductSpecification> update(@RequestParam("item") String item){
        ProductSpecification product = JsonConverterBin.transferToObject(item, ProductSpecification.class);
        boolean b = productSpecificationService.saveOrUpdate(product);
        if (b){
            return ResponseBuilder.success(productSpecificationService.getById(product.getId()));
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
            productSpecificationService.removeById(id);
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
    public ResponseModel<ProductSpecification> get(@PathVariable("id") Long id) {
        return ResponseBuilder.success(productSpecificationService.getById(id));
    }

    /**
     * 查看所有的员工信息
     * @return
     */
    @GetMapping(value = "/list/category/{id}")
    public ResponseModel<List<ProductSpecification>> list(@PathVariable("id") Long id){
        LambdaQueryWrapper<ProductSpecification> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ProductSpecification::getProductCategoryId,id);
        return ResponseBuilder.success(productSpecificationService.list(lambdaQueryWrapper));
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping(value = "/list")
    public ResponseModel<List<ProductSpecification>> list(){
        return ResponseBuilder.success(productSpecificationService.list());
    }


    /**
    * 分页查询数据
    * @return PageList 分页对象
    */
    @GetMapping(value = "/page")
    public ResponseModel<IPage<ProductSpecification>> page(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("size") Integer size,
            @RequestParam("pages") Integer pages) {
        IPage<ProductSpecification> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(size);
        page.setPages(pages);
        return ResponseBuilder.success(productSpecificationService.page(page));
    }
}