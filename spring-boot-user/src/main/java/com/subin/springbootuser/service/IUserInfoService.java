package com.subin.springbootuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.subin.springbootuser.entity.UserInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2019-07-17
 */
public interface IUserInfoService extends IService<UserInfo> {
    List<UserInfo> getUserInfoWithUserRole();
    UserInfo getUserInfoWithUserRoleById(int id);
    boolean checkUserInfoByNameAndPassword(UserInfo userInfo);
}
