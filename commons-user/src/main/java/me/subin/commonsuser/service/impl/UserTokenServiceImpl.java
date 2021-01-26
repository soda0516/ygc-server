package me.subin.commonsuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import me.subin.commonsuser.entity.UserToken;
import me.subin.commonsuser.mapper.UserTokenMapper;
import me.subin.commonsuser.service.IUserTokenService;

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
