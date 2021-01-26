package me.subin.commonsuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.subin.commonsuser.entity.UserInfoRole;
import me.subin.commonsuser.service.IUserInfoRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import me.subin.commonsuser.entity.UserRole;
import me.subin.commonsuser.mapper.UserRoleMapper;
import me.subin.commonsuser.service.IUserRoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-07-17
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
    @Autowired
    IUserInfoRoleService iUserInfoRoleService;
    @Override
    public List<UserRole> listUserRoleByUserId(Long id) {
        List<UserInfoRole> list = iUserInfoRoleService.lambdaQuery()
                .eq(UserInfoRole::getInfoId, id)
                .list();
        if (list.isEmpty()){
            return new ArrayList<>();
        } else {
            List<Long> collect = list.stream().map(UserInfoRole::getRoleId).collect(Collectors.toList());
            LambdaQueryWrapper<UserRole> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
            objectLambdaQueryWrapper.in(UserRole::getId,collect);
            return this.baseMapper.selectList(objectLambdaQueryWrapper);
        }
    }
}
