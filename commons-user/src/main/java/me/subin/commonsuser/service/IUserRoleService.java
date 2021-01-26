package me.subin.commonsuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.subin.commonsuser.entity.UserRole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2019-07-17
 */
public interface IUserRoleService extends IService<UserRole> {
    List<UserRole> listUserRoleByUserId(Long id);
}
