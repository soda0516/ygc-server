package me.subin.commonsuser.service.impl.jwt;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.subin.commonsuser.constant.TokenConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import me.subin.commonsuser.constant.JwtDbConstant;
import me.subin.commonsuser.entity.UserToken;
import me.subin.commonsuser.service.IJwtDbService;
import me.subin.commonsuser.service.IUserTokenService;

import java.time.LocalDateTime;

/**
 * @Describe
 * @Author soda
 * @Create 2019/8/9 10:21
 **/
@Service(value = JwtDbConstant.JWT_MYSQL_IMPL)
public class JwtMysqlServiceImpl implements IJwtDbService {


    private final IUserTokenService iUserTokenService;

    JwtMysqlServiceImpl(IUserTokenService iUserTokenService){
        this.iUserTokenService = iUserTokenService;
    }

    @Override
    public void addJwtTokenInDb(String token,String username, Long userId) {
        UserToken userToken = new UserToken();
        userToken.setJwtToken(token);
        userToken.setUserName(username);
        userToken.setUserId(userId);
        userToken.setExpireTime(LocalDateTime.now().plusHours(TokenConstant.EXPIRYHOURS));
        iUserTokenService.save(userToken);
    }

    @Override
    public void refreshJwtTokenInDb(String token) {
        QueryWrapper<UserToken> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserToken::getJwtToken,token);
        UserToken userToken = iUserTokenService.getOne(queryWrapper);
        userToken.setExpireTime(LocalDateTime.now().plusHours(TokenConstant.EXPIRYHOURS));
        iUserTokenService.updateById(userToken);
    }

    @Override
    public void delJwtTokenInDbByUserName(Long userId) {
        QueryWrapper<UserToken> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserToken::getUserId,userId);
        iUserTokenService.remove(queryWrapper);
    }

    @Override
    public void delJwtTokenInDb(String token) {
        QueryWrapper<UserToken> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserToken::getJwtToken,token);
        iUserTokenService.remove(queryWrapper);
    }

    @Override
    public boolean isExistJwtTokenInDb(String token) {
        QueryWrapper<UserToken> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserToken::getJwtToken,token);
        return iUserTokenService.count(queryWrapper) > 0;
    }

    @Override
    public LocalDateTime getTimeByJwtTokenInDb(String token) {
        QueryWrapper<UserToken> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserToken::getJwtToken,token);
        UserToken userToken = iUserTokenService.getOne(queryWrapper);
        return userToken.getExpireTime();
    }
}
