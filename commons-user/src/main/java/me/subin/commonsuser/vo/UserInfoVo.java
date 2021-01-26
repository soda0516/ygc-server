package me.subin.commonsuser.vo;

import lombok.Data;

import java.util.List;

/**
 * @author bin
 * @ClassName UserInfoVo
 * @Description TODO
 * @date 2021/1/20 19:32
 */
@Data
public class UserInfoVo {
    private Long id;
    private String name;
    private List<String> roles;
}
