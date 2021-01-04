package com.subin.springbootuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.subin.springbootuser.entity.UserToken;
import com.subin.springbootuser.mapper.UserTokenMapper;
import com.subin.springbootuser.service.IUserTokenService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author soda
 * @since 2019-10-04
 */
@Service
public class UserTokenServiceImpl extends ServiceImpl<UserTokenMapper, UserToken> implements IUserTokenService {

}
