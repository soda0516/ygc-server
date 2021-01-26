package me.subin.commonsuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.subin.commonsuser.bo.UserInfoDetailBo;
import org.apache.ibatis.annotations.Param;
import me.subin.commonsuser.entity.UserInfo;

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
    /**
     * 查询用户信息
     * @return
     */
    List<UserInfo> selectUserInfoWithUserRole();

    /**
     * 查询用户信息，包含用户Id的列表
     * @param id
     * @return
     */
    UserInfo selectUserInfoWithUserRoleById(@Param("id") long id);
    /**
     * 查询用户信息，包含用户Id的列表
     * @return
     */
    List<UserInfoDetailBo> selectUserInfoDetailBo();

    /**
     * 查询用户信息，包含用户Id的列表
     * @param id
     * @return
     */
    UserInfoDetailBo selectUserInfoDetailBoById(@Param("id") long id);
}
