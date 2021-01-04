package com.bin.serverapi.order.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author bin
 * @ClassName OrderInDetailVo
 * @Description TODO
 * @date 2020/12/31 12:39
 */
@Data
public class OrderInDetailVo {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long orderId;

    private Long productCategoryId;

    private Long productId;

    private String productName;

    private Integer requestNum;

    private Integer actualNum;

    private String remark;

    private String extraRemark;

    private BigDecimal unitPrice;

    private BigDecimal totalFee;

    private Long storeId;

    private Integer orderType;

    private Integer subjectId;

    private LocalDate orderDate;
}
