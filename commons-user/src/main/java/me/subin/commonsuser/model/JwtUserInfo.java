package me.subin.commonsuser.model;

import lombok.Data;
import me.subin.commonsuser.entity.UserAuth;
import me.subin.commonsuser.entity.UserInfo;
import me.subin.commonsuser.entity.UserRole;

import java.util.List;

/**
 * @Describe
 * @Author soda
 * @Create 2019/7/23 10:15
 **/
@Data
public class JwtUserInfo {
    private UserInfo userInfo;
    private UserRole userRole;
    private List<UserAuth> userAuthList;
}
