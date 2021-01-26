package me.subin.commonsuser.mapper;

import me.subin.commonsuser.entity.UserInfoRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Soda
 * @since 2021-01-20
 */
public interface UserInfoRoleMapper extends BaseMapper<UserInfoRole> {
    List<Long> listUserRoleIdByInfoId();
}
