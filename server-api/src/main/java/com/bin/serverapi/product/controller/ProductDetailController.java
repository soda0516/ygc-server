package com.bin.serverapi.product.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bin.serverapi.product.entity.ProductCategory;
import com.bin.serverapi.product.entity.ProductDescription;
import com.bin.serverapi.product.entity.ProductModel;
import com.bin.serverapi.product.entity.ProductSpecification;
import com.bin.serverapi.product.service.IProductCategoryService;
import com.bin.serverapi.product.service.IProductDescriptionService;
import com.bin.serverapi.product.service.IProductDetailService;
import com.bin.serverapi.product.entity.ProductDetail;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.serverapi.product.service.IProductModelService;
import com.bin.serverapi.product.service.IProductSpecificationService;
import javafx.collections.ObservableList;
import lombok.extern.slf4j.Slf4j;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/product/productDetail")
public class ProductDetailController {

    @Autowired
    private IProductDetailService productDetailService;

    @Autowired
    private IProductCategoryService productCategoryService;

    @Autowired
    private IProductModelService productModelService;

    @Autowired
    private IProductDescriptionService productDescriptionService;

    @Autowired
    private IProductSpecificationService productSpecificationService;

    /**
     * 保存和修改公用的
     * @return ResponseModel转换结果
     */
    @PostMapping
    public ResponseModel<List<ProductDetail>> save(
            @RequestParam("category") String category,
            @RequestParam("model") String model,
            @RequestParam("spec") String spec,
            @RequestParam("desc") String desc){
        ProductCategory productCategory = JsonConverterBin.transferToObject(category, ProductCategory.class);
        ProductModel productModel = JsonConverterBin.transferToObject(model, ProductModel.class);
        ProductSpecification productSpecification = JsonConverterBin.transferToObject(spec, ProductSpecification.class);
        ProductDescription productDescription = JsonConverterBin.transferToObject(desc, ProductDescription.class);
        ProductDetail detail = new ProductDetail();
        LambdaQueryWrapper<ProductDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(productCategory.getId())){
            detail.setCategoryId(productCategory.getId());
            detail.setCategoryName(productCategory.getName());
            lambdaQueryWrapper.eq(ProductDetail::getCategoryId,productCategory.getId());
        }
        if (Objects.nonNull(productModel.getId())){
            detail.setModelId(productModel.getId());
            detail.setModelName(productModel.getName());
            lambdaQueryWrapper.eq(ProductDetail::getModelId,productModel.getId());
        }
        if (Objects.nonNull(productSpecification.getId())){
            detail.setSpecificationId(productSpecification.getId());
            detail.setSpecificationName(productSpecification.getName());
            lambdaQueryWrapper.eq(ProductDetail::getSpecificationId,productSpecification.getId());
        }
        if (Objects.nonNull(productDescription.getId())){
            detail.setDescriptionId(productDescription.getId());
            detail.setDescriptionName(productDescription.getName());
            lambdaQueryWrapper.eq(ProductDetail::getDescriptionId,productDescription.getId());
        }
        List<ProductDetail> list = productDetailService.list(lambdaQueryWrapper);
        log.info("转换之前");
        log.info(JsonConverterBin.transferToJson(detail));
        if (list.isEmpty()){
            productDetailService.save(detail);
        }else {
            for (ProductDetail det:list
                 ) {
                productDetailService.updateById(det);
                log.info("转换之后");
                log.info(JsonConverterBin.transferToJson(det));
            }
        }
        return ResponseBuilder.success(productDetailService.list());
    }
    /**
     * 保存和修改公用的
     * @param item  传递的实体
     * @return ResponseModel转换结果
     */
    @PutMapping
    public ResponseModel<ProductDetail> update(@RequestParam("item") String item){
        log.info(item);
        ProductDetail product = JsonConverterBin.transferToObject(item, ProductDetail.class);
        if (Objects.nonNull(product.getId())){
            ProductDetail byId = productDetailService.getById(product.getId());
            if (Objects.nonNull(byId)){
                BeanCopier beanCopier = BeanCopier.create(ProductDetail.class,ProductDetail.class,true);
                beanCopier.copy(product, byId, (o, aClass, o1) -> {
                    if (o == null){
                        if (aClass == Integer.class ){
                            return 0;
                        }
                        if (aClass == Long.class ){
                            return 0L;
                        }
                    }
                    return o;
                });
                ProductCategory productCategory = productCategoryService.getById(byId.getCategoryId());
                if (Objects.nonNull(productCategory)){
                    byId.setCategoryName(productCategory.getName());
                }else {
                    byId.setCategoryName("");
                }
                ProductModel productModel = productModelService.getById(byId.getModelId());
                if (Objects.nonNull(productModel)){
                    byId.setModelName(productModel.getName());
                }else {
                    byId.setModelName("");
                }
                ProductSpecification productSpecification = productSpecificationService.getById(byId.getSpecificationId());
                if (Objects.nonNull(productSpecification)){
                    byId.setSpecificationName(productSpecification.getName());
                }else {
                    byId.setSpecificationName("");
                }
                ProductDescription productDescription = productDescriptionService.getById(byId.getDescriptionId());
                if (Objects.nonNull(productDescription)){
                    byId.setDescriptionName(productDescription.getName());
                }else {
                    byId.setDescriptionName("");
                }
                productDetailService.updateById(byId);
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
            productDetailService.removeById(id);
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
    public ResponseModel<ProductDetail> get(@PathVariable("id") Long id) {
        return ResponseBuilder.success(productDetailService.getById(id));
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping(value = "/list")
    public ResponseModel<List<ProductDetail>> list(){
        return ResponseBuilder.success(productDetailService.list());
    }


    /**
    * 分页查询数据
    * @return PageList 分页对象
    */
    @GetMapping(value = "/page")
    public ResponseModel<IPage<ProductDetail>> page(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("size") Integer size,
            @RequestParam("pages") Integer pages) {
        IPage<ProductDetail> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(size);
        page.setPages(pages);
        return ResponseBuilder.success(productDetailService.page(page));
    }
}