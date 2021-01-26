package me.subin.commonsuser.bo;

import lombok.Data;

import java.util.List;

/**
 * @author bin
 * @ClassName UserInfoBo
 * @Description TODO
 * @date 2021/1/24 20:00
 */
@Data
public class UserInfoBo {
    private Long id;
    private String username;
    private Long storeId;
    private List<String> roles;
}
