package me.subin.commonsuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.subin.commonsuser.entity.UserInfoRole;
import me.subin.commonsuser.service.IUserInfoRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import me.subin.commonsuser.entity.UserInfo;
import me.subin.commonsuser.entity.UserRole;
import me.subin.commonsuser.entity.UserRoleAuth;
import me.subin.commonsuser.mapper.UserAuthMapper;
import me.subin.commonsuser.mapper.UserRoleAuthMapper;
import me.subin.commonsuser.mapper.UserRoleMapper;
import me.subin.commonsuser.model.JwtUser;
import me.subin.commonsuser.service.IUserInfoService;
import me.subin.commonsuser.service.IUserRoleAuthService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

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
    private final IUserInfoRoleService iUserInfoRoleService;
    @Autowired
    UserRoleAuthServiceImpl(PasswordEncoder passwordEncoder,
                            IUserInfoService iUserInfoService,
                            UserRoleMapper userRoleMapper,
                            UserRoleAuthMapper userRoleAuthMapper,
                            UserAuthMapper userAuthMapper,
                            IUserInfoRoleService iUserInfoRoleService){
        this.passwordEncoder = passwordEncoder;
        this.iUserInfoService = iUserInfoService;
        this.userRoleMapper = userRoleMapper;
        this.userRoleAuthMapper = userRoleAuthMapper;
        this.userAuthMapper = userAuthMapper;
        this.iUserInfoRoleService = iUserInfoRoleService;
    }
    @Override
    public boolean checkUserInfoByNameAndPassword(UserInfo userInfo) {
//        判空操作，放在controller层，以便返回一个Response结果
        UserInfo info = iUserInfoService.lambdaQuery()
                .eq(UserInfo::getUsername,userInfo.getUsername())
                .eq(UserInfo::getPassword,userInfo.getPassword())
                .one();
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
        List<UserInfoRole> list = iUserInfoRoleService.lambdaQuery()
                .eq(UserInfoRole::getInfoId, info.getId())
                .list();
        List<Long> collect = list.stream().map(UserInfoRole::getRoleId).collect(Collectors.toList());

        List<GrantedAuthority> authentication = new ArrayList<>();
        if (!collect.isEmpty()){
            QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
            userRoleQueryWrapper.lambda().in(UserRole::getId,collect);
            List<UserRole> userRoleList = userRoleMapper.selectList(userRoleQueryWrapper);
            for (UserRole role:userRoleList
            ) {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getRoleName());
                authentication.add(simpleGrantedAuthority);
            }
        }
        return new JwtUser.Builder()
                .setId(info.getId())
                .setUsername(info.getUsername())
                .setAuthorities(authentication)
                .build();
    }
}
