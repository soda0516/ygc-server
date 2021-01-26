package com.bin.serverapi.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.subin.base.entity.BaseEntity;
import me.subin.jackson.serializer.ZeroToNullSerializer;

/**
 * <p>
 * 
 * </p>
 *
 * @author Soda
 * @since 2020-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long categoryId;

    private String categoryName;

    @JsonSerialize(using = ZeroToNullSerializer.class)
    private Long descriptionId;

    private String descriptionName;

    @JsonSerialize(using = ZeroToNullSerializer.class)
    private Long modelId;

    private String modelName;

    @JsonSerialize(using = ZeroToNullSerializer.class)
    private Long specificationId;

    private String specificationName;

    private String remark;


}
