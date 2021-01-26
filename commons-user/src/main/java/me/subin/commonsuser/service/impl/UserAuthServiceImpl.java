package me.subin.commonsuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import me.subin.commonsuser.entity.UserAuth;
import me.subin.commonsuser.mapper.UserAuthMapper;
import me.subin.commonsuser.service.IUserAuthService;

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
