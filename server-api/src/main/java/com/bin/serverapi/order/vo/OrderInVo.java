package com.bin.serverapi.order.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * @author bin
 * @ClassName OrderInDetailVo
 * @Description TODO
 * @date 2020/12/31 12:37
 */
@Data
public class OrderInVo {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String wellName;

    private Long wellId;

    private Long regionId;

    private String regionName;

    private Long centerId;

    private String centerName;

    private Long stationId;

    private String stationName;

    private String operItem;

    private String remark;

    private Long storeId;

    private Integer orderType;

    private Integer subjectType;

    private Long curatorId;

    private Long checkerId;

    private LocalDate orderDate;

    List<OrderInDetailVo> children;
}
