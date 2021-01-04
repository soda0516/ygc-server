package com.subin.springbootuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.subin.springbootuser.entity.UserAuth;
import com.subin.springbootuser.mapper.UserAuthMapper;
import com.subin.springbootuser.service.IUserAuthService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-07-17
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements IUserAuthService {

}
