package com.bin.serverapi.product.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class ProductDescription extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private Long productCategoryId;

}
