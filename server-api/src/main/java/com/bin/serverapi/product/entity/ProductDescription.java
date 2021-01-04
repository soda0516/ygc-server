package com.bin.serverapi.product.entity;

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
public class ProductDescription extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

}
