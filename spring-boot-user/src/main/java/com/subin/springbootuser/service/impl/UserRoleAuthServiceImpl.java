package com.subin.springbootuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.subin.springbootuser.entity.UserInfo;
import com.subin.springbootuser.entity.UserRole;
import com.subin.springbootuser.entity.UserRoleAuth;
import com.subin.springbootuser.mapper.UserAuthMapper;
import com.subin.springbootuser.mapper.UserRoleAuthMapper;
import com.subin.springbootuser.mapper.UserRoleMapper;
import com.subin.springbootuser.model.JwtUser;
import com.subin.springbootuser.service.IUserInfoService;
import com.subin.springbootuser.service.IUserRoleAuthService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-07-17
 */
@Service
public class UserRoleAuthServiceImpl extends ServiceImpl<UserRoleAuthMapper, UserRoleAuth> implements IUserRoleAuthService {
    private final PasswordEncoder passwordEncoder;
    private final IUserInfoService iUserInfoService;
    private final UserRoleMapper userRoleMapper;
    private final UserRoleAuthMapper userRoleAuthMapper;
    private final UserAuthMapper userAuthMapper;
    @Autowired
    UserRoleAuthServiceImpl(PasswordEncoder passwordEncoder,
                            IUserInfoService iUserInfoService,
                            UserRoleMapper userRoleMapper,
                            UserRoleAuthMapper userRoleAuthMapper,
                            UserAuthMapper userAuthMapper){
        this.passwordEncoder = passwordEncoder;
        this.iUserInfoService = iUserInfoService;
        this.userRoleMapper = userRoleMapper;
        this.userRoleAuthMapper = userRoleAuthMapper;
        this.userAuthMapper = userAuthMapper;
    }
    @Override
    public boolean checkUserInfoByNameAndPassword(UserInfo userInfo) {
//        判空操作，放在controller层，以便返回一个Response结果
        System.out.println(userInfo.getUsername().equals("第一个账号"));
        System.out.println(userInfo.getPassword().equals("123456"));
        UserInfo info = iUserInfoService.lambdaQuery()
                .eq(UserInfo::getUsername,userInfo.getUsername())
                .eq(UserInfo::getPassword,userInfo.getPassword())
                .one();
        System.out.println(info);
        System.out.println(userInfo.getUsername().equals("第一个账号"));
        if (null != info && userInfo.getUsername().equals(info.getUsername()) && userInfo.getPassword().equals(info.getPassword())){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public JwtUser getJwtUserInfo(UserInfo userInfo) {
        UserInfo info = iUserInfoService.lambdaQuery()
                .eq(UserInfo::getUsername,userInfo.getUsername())
                .eq(UserInfo::getPassword,userInfo.getPassword())
                .one();
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.lambda().eq(UserRole::getId,info.getRoleId());
        UserRole userRole = userRoleMapper.selectOne(userRoleQueryWrapper);
        List<GrantedAuthority> authentication = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userRole.getRoleName());
        authentication.add(simpleGrantedAuthority);

        return new JwtUser.Builder()
                .setId(info.getId())
                .setUsername(info.getUsername())
                .setAuthorities(authentication)
                .build();
    }
}
