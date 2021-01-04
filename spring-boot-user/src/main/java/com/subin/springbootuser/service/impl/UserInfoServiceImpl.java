package com.subin.springbootuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.subin.springbootuser.entity.UserInfo;
import com.subin.springbootuser.mapper.UserInfoMapper;
import com.subin.springbootuser.service.IUserInfoService;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 * @author jobob
 * @since 2019-07-17
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
    @Override
    public List<UserInfo> getUserInfoWithUserRole() {
        return this.baseMapper.selectUserInfoWithUserRole();
    }

    @Override
    public UserInfo getUserInfoWithUserRoleById(int id) {
        return this.baseMapper.selectUserInfoWithUserRoleById(id);
    }

    @Override
    public boolean checkUserInfoByNameAndPassword(UserInfo userInfo) {
        //        判空操作，放在controller层，以便返回一个Response结果
        LambdaQueryWrapper<UserInfo> userInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userInfoLambdaQueryWrapper
                .eq(UserInfo::getUsername,userInfo.getUsername())
                .eq(UserInfo::getPassword,userInfo.getPassword());
        UserInfo info = this.baseMapper.selectOne(userInfoLambdaQueryWrapper);
        if (Objects.nonNull(info)){
            return true;
        }else {
            return false;
        }
    }
}
