package me.subin.commonsuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import me.subin.commonsuser.entity.UserRole;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-07-17
 */
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {
    UserRole getAllRoleWithAuthList();
}
