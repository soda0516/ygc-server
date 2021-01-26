package com.bin.serverapi.product.controller;

import com.bin.serverapi.product.service.IProductCategoryService;
import com.bin.serverapi.product.entity.ProductCategory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.serverapi.product.vo.detail.ProductCategoryVo;
import lombok.extern.slf4j.Slf4j;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/product/productCategory")
public class ProductCategoryController {

    @Autowired
    public IProductCategoryService productCategoryService;

    @GetMapping(value = "/list/detail")
    public ResponseModel<List<ProductCategoryVo>> listAllWithChildren(){
        log.info("listAllWithChildren");
        return ResponseBuilder.success(productCategoryService.listAllWithChildren());
    }

    /**
     * 保存和修改公用的
     * @param name  传递的实体
     * @return ResponseModel转换结果
     */
    @PostMapping
    public ResponseModel<List<ProductCategory>> save(@RequestParam("name") String name){
        Integer count = productCategoryService.lambdaQuery()
                .eq(ProductCategory::getName, name)
                .count();
        if (count > 0){
            return ResponseBuilder.failure("输入的名称重复，请重新输入");
        }else {
            ProductCategory category = new ProductCategory();
            category.setName(name);
            boolean save = productCategoryService.save(category);
            if (save){
                return ResponseBuilder.success(productCategoryService.list());
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
    public ResponseModel<ProductCategory> update(@RequestParam("item") String item){
        ProductCategory productCategory = JsonConverterBin.transferToObject(item, ProductCategory.class);
        boolean b = productCategoryService.saveOrUpdate(productCategory);
        if (b){
            return ResponseBuilder.success(productCategoryService.getById(productCategory.getId()));
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
            ProductCategory byId = productCategoryService.getById(id);
            if (Objects.nonNull(byId)){
                productCategoryService.removeById(byId.getId());
            }
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
    public ResponseModel<ProductCategory> get(@PathVariable("id") Long id) {
        return ResponseBuilder.success(productCategoryService.getById(id));
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping(value = "/list")
    public ResponseModel<List<ProductCategory>> list(){
        return ResponseBuilder.success(productCategoryService.list());
    }


    /**
    * 分页查询数据
    * @return PageList 分页对象
    */
    @GetMapping(value = "/page")
    public ResponseModel<IPage<ProductCategory>> page(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("size") Integer size,
            @RequestParam("pages") Integer pages) {
        IPage<ProductCategory> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(size);
        page.setPages(pages);
        return ResponseBuilder.success(productCategoryService.page(page));
    }
}