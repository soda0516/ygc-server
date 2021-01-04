package com.bin.serverapi.order.entity;

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
public class OrderInternal extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String handOverCompany;

    private String receiptCompany;

    private String handOverUser;

    private String receiptUser;

    private String remark;

    private Long storeId;

    private Integer orderType;

    private Integer operType;

    private Integer addSubject;

    private Integer subtractSubject;

    private Long curatorId;

    private Long checkerId;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate internalDate;


}
