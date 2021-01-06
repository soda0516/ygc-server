package com.bin.serverapi.store.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
 * @since 2021-01-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class StoreAccount extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 管材规格ID
     */
    private Long productDetailId;

    /**
     * 科目
     */
    private Long subjectId;

    /**
     * 库存ID
     */
    private Integer storeId;

    /**
     * 库存数量
     */
    private Integer storeNum;

    /**
     * 盘点日期
     */
    private LocalDate accountDate;

    /**
     * 填写人员
     */
    private String takeUser;


}
