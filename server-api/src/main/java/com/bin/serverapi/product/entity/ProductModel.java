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
public class ProductModel extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long productCategoryId;

    private String name;

}
