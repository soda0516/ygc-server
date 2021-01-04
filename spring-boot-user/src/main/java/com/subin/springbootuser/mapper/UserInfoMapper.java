package com.subin.springbootuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.subin.springbootuser.entity.UserInfo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-07-17
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    List<UserInfo> selectUserInfoWithUserRole();
    UserInfo selectUserInfoWithUserRoleById(@Param("id") int id);
}
