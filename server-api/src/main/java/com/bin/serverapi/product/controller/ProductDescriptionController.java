package com.bin.serverapi.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bin.serverapi.product.entity.ProductCategory;
import com.bin.serverapi.product.entity.ProductSpecification;
import com.bin.serverapi.product.service.IProductDescriptionService;
import com.bin.serverapi.product.entity.ProductDescription;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/product/productDescription")
public class ProductDescriptionController {

    private BeanCopier beanCopier = BeanCopier.create(ProductDescription.class,ProductDescription.class,false);

    @Autowired
    public IProductDescriptionService productDescriptionService;

    /**
     * 保存和修改公用的
     * @param name  传递的实体
     * @return ResponseModel转换结果
     */
    @PostMapping
    public ResponseModel<List<ProductDescription>> save(@RequestParam("name") String name,@RequestParam("categoryId") Long categoryId){
        Integer count = productDescriptionService.lambdaQuery()
                .eq(ProductDescription::getName, name)
                .count();
        if (count > 0){
            return ResponseBuilder.failure("输入的名称重复，请重新输入");
        }else {
            ProductDescription description = new ProductDescription();
            description.setProductCategoryId(categoryId);
            description.setName(name);
            boolean save = productDescriptionService.save(description);
            if (save){
                List<ProductDescription> list = productDescriptionService.lambdaQuery()
                        .eq(ProductDescription::getProductCategoryId, categoryId)
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
    public ResponseModel<ProductDescription> update(@RequestParam("item") String item){
        ProductDescription product = JsonConverterBin.transferToObject(item, ProductDescription.class);
        if (Objects.nonNull(product.getId())){
            ProductDescription byId = productDescriptionService.getById(product.getId());
            if (Objects.nonNull(byId)){
                beanCopier.copy(product, byId,null);
                productDescriptionService.updateById(byId);
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
        try {
            productDescriptionService.removeById(id);
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
    public ResponseModel<ProductDescription> get(@PathVariable("id") Long id) {
        return ResponseBuilder.success(productDescriptionService.getById(id));
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping(value = "/list")
    public ResponseModel<List<ProductDescription>> list(){
        return ResponseBuilder.success(productDescriptionService.list());
    }
    /**
     * 查看所有的员工信息
     * @return
     */
    @GetMapping(value = "/list/category/{id}")
    public ResponseModel<List<ProductDescription>> list(@PathVariable("id") Long id){
        LambdaQueryWrapper<ProductDescription> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ProductDescription::getProductCategoryId,id);
        return ResponseBuilder.success(productDescriptionService.list(lambdaQueryWrapper));
    }


    /**
    * 分页查询数据
    * @return PageList 分页对象
    */
    @GetMapping(value = "/page")
    public ResponseModel<IPage<ProductDescription>> page(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("size") Integer size,
            @RequestParam("pages") Integer pages) {
        IPage<ProductDescription> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(size);
        page.setPages(pages);
        return ResponseBuilder.success(productDescriptionService.page(page));
    }
}