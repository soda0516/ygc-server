package com.subin.springbootuser.model;

import lombok.Data;
import com.subin.springbootuser.entity.UserAuth;
import com.subin.springbootuser.entity.UserInfo;
import com.subin.springbootuser.entity.UserRole;

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
