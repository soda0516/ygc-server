package me.subin.commonsuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.subin.commonsuser.bo.UserInfoBo;
import me.subin.commonsuser.bo.UserInfoDetailBo;
import me.subin.commonsuser.entity.UserInfoRole;
import me.subin.commonsuser.entity.UserRole;
import me.subin.commonsuser.service.IUserInfoRoleService;
import me.subin.commonsuser.service.IUserRoleService;
import me.subin.response.service.ServiceResponse;
import me.subin.response.service.ServiceResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import me.subin.commonsuser.entity.UserInfo;
import me.subin.commonsuser.mapper.UserInfoMapper;
import me.subin.commonsuser.service.IUserInfoService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.awt.SystemColor.info;

/**
 * <p>
 *  服务实现类
 * </p>
 * @author jobob
 * @since 2019-07-17
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
    @Autowired
    IUserRoleService iUserRoleService;
    @Autowired
    IUserInfoRoleService iUserInfoRoleService;
    @Override
    public List<UserInfo> getUserInfoWithUserRole() {
        return this.baseMapper.selectUserInfoWithUserRole();
    }

    @Override
    public UserInfo getUserInfoWithUserRoleById(long id) {
        return this.baseMapper.selectUserInfoWithUserRoleById(id);
    }

    @Override
    public Long checkUserInfoByNameAndPassword(UserInfo userInfo) {
        //        判空操作，放在controller层，以便返回一个Response结果
        LambdaQueryWrapper<UserInfo> userInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userInfoLambdaQueryWrapper
                .eq(UserInfo::getUsername,userInfo.getUsername())
                .eq(UserInfo::getPassword,userInfo.getPassword());
        List<UserInfo> userInfos = this.baseMapper.selectList(userInfoLambdaQueryWrapper);
        if (userInfos.isEmpty()){
            return 0L;
        } else {
            return userInfos.get(0).getId();
        }
    }

    @Override
    public UserInfoBo getUserInfoBo(Long id) {
        UserInfo userInfo = this.baseMapper.selectById(id);
        if (Objects.isNull(userInfo)){
            return null;
        }
        UserInfoBo userInfoBo = new UserInfoBo();
        userInfoBo.setId(userInfo.getId());
        userInfoBo.setUsername(userInfo.getUsername());
        userInfoBo.setStoreId(userInfo.getStoreId());
        List<UserRole> roleList = iUserRoleService.listUserRoleByUserId(id);
        List<String> roleStr = new ArrayList<>();
        for (UserRole role:roleList
             ) {
            roleStr.add(role.getRoleName());
        }
        userInfoBo.setRoles(roleStr);
        return userInfoBo;
    }

    @Override
    public List<UserInfoDetailBo> listUserInfoDetailBo() {
        return this.baseMapper.selectUserInfoDetailBo();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResponse<Long> updateByUserInfoBo(UserInfoDetailBo userInfoDetailBo) {
        LambdaQueryWrapper<UserInfo> infoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        infoLambdaQueryWrapper.eq(UserInfo::getUsername,userInfoDetailBo.getUsername());
        infoLambdaQueryWrapper.ne(UserInfo::getId,userInfoDetailBo.getId());
        Integer integer = this.baseMapper.selectCount(infoLambdaQueryWrapper);
        if (integer > 0) {
            return ServiceResponseBuilder.error("修改的用户名有重复，请换一个用户名");
        }
        UserInfo userInfo = this.baseMapper.selectById(userInfoDetailBo.getId());
        userInfo.setUsername(userInfoDetailBo.getUsername());
        userInfo.setPassword(userInfoDetailBo.getPassword());
        userInfo.setStoreId(userInfoDetailBo.getStoreId() == null? 0 : userInfoDetailBo.getStoreId());
        this.baseMapper.updateById(userInfo);
        List<UserInfoRole> list = iUserInfoRoleService.lambdaQuery()
                .eq(UserInfoRole::getInfoId, userInfoDetailBo.getId())
                .list();
        for (UserInfoRole role:list
        ) {
            if (!userInfoDetailBo.getRoleIds().contains(role.getRoleId())){
                iUserInfoRoleService.removeById(role.getId());
            }
        }
        List<Long> collect = list.stream().map(UserInfoRole::getRoleId).collect(Collectors.toList());
        for (Long id:userInfoDetailBo.getRoleIds()
        ) {
            if (!collect.contains(id)){
                UserInfoRole infoRole = new UserInfoRole();
                infoRole.setInfoId(userInfoDetailBo.getId());
                infoRole.setRoleId(id);
                iUserInfoRoleService.save(infoRole);
            }
        }
        return ServiceResponseBuilder.success(userInfoDetailBo.getId());
    }

    @Override
    public UserInfoDetailBo getUserInfoDetailBoById(long id) {
        return this.baseMapper.selectUserInfoDetailBoById(id);
    }
}
