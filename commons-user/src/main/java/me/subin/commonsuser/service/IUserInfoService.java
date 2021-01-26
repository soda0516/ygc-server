package me.subin.commonsuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.subin.commonsuser.bo.UserInfoBo;
import me.subin.commonsuser.bo.UserInfoDetailBo;
import me.subin.commonsuser.entity.UserInfo;
import me.subin.response.service.ServiceResponse;
import org.apache.ibatis.annotations.Param;

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
    UserInfo getUserInfoWithUserRoleById(long id);
    Long checkUserInfoByNameAndPassword(UserInfo userInfo);
    UserInfoBo getUserInfoBo(Long id);
    /**
     * 查询用户信息，包含用户Id的列表
     * @return
     */
    List<UserInfoDetailBo> listUserInfoDetailBo();

    /**
     * 通过ID获取单个用户的信息
     * @param id
     * @return
     */
    UserInfoDetailBo getUserInfoDetailBoById(long id);

    /**
     * 通过userInfoDetailBo来更新一个用户信息
     * @param userInfoDetailBo
     * @return
     */
    ServiceResponse<Long> updateByUserInfoBo(UserInfoDetailBo userInfoDetailBo);
}
