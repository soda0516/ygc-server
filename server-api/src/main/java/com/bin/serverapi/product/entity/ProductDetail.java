package com.bin.serverapi.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.subin.base.entity.BaseEntity;

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
@Accessors(chain = true)
public class ProductDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long categoryId;

    private String categoryName;

    private Long descriptionId;

    private String descriptionName;

    private Long modelId;

    private String modelName;

    private Long specificationId;

    private String specificationName;

    private String remark;


}
