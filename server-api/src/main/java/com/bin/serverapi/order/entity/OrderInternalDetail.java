package com.bin.serverapi.order.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import me.subin.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Soda
 * @since 2020-12-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class OrderInternalDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long orderId;

    private Long productCategoryId;

    private Long productId;

    private Integer requestNum;

    private Integer actualNum;

    private String remark;

    private String extraRemark;

    private BigDecimal unitPrice;

    private BigDecimal totalFee;

    private Long storeId;

    private Integer orderType;

    private Integer addSubject;

    private Integer subtractSubject;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate internalDate;

    private Integer operType;


}
