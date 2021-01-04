package com.subin.springbootuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.subin.springbootuser.entity.UserRole;
import com.subin.springbootuser.mapper.UserRoleMapper;
import com.subin.springbootuser.service.IUserRoleService;

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

}
