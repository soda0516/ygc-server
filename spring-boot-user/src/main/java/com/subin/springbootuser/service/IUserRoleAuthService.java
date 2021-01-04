package com.subin.springbootuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.subin.springbootuser.entity.UserInfo;
import com.subin.springbootuser.entity.UserRoleAuth;
import com.subin.springbootuser.model.JwtUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2019-07-17
 */
public interface IUserRoleAuthService extends IService<UserRoleAuth> {
    boolean checkUserInfoByNameAndPassword(UserInfo userInfo);
    JwtUser getJwtUserInfo(UserInfo userInfo);
}
