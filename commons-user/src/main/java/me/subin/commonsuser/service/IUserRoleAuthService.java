package me.subin.commonsuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.subin.commonsuser.entity.UserInfo;
import me.subin.commonsuser.entity.UserRoleAuth;
import me.subin.commonsuser.model.JwtUser;

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
