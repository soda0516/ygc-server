package me.subin.commonsuser.vo;

import lombok.Data;

/**
 * @author bin
 * @ClassName UserInfoRoleDetailVo
 * @Description TODO
 * @date 2021/1/25 10:51
 */
@Data
public class UserInfoRoleDetailVo {
    private Long id;
    private Long roleId;
    private Long infoId;
    private String roleName;
}
