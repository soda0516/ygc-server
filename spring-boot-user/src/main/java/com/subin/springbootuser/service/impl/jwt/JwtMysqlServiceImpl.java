package com.subin.springbootuser.service.impl.jwt;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.subin.springbootuser.constant.JwtConfigConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.subin.springbootuser.constant.JwtDbConstant;
import com.subin.springbootuser.entity.UserToken;
import com.subin.springbootuser.service.IJwtDbService;
import com.subin.springbootuser.service.IUserTokenService;

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
    public void addJwtTokenInDb(String token,String username) {
        UserToken userToken = new UserToken();
        userToken.setJwtToken(token);
        userToken.setUserName(username);
        userToken.setExpireTime(LocalDateTime.now().plusHours(JwtConfigConstant.EXPIRY_HOURS));
        iUserTokenService.save(userToken);
    }

    @Override
    public void refreshJwtTokenInDb(String token) {
        QueryWrapper<UserToken> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserToken::getJwtToken,token);
        UserToken userToken = iUserTokenService.getOne(queryWrapper);
        userToken.setExpireTime(LocalDateTime.now().plusHours(JwtConfigConstant.EXPIRY_HOURS));
        iUserTokenService.updateById(userToken);
    }

    @Override
    public void delJwtTokenInDbByUserName(String username) {
        QueryWrapper<UserToken> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserToken::getUserName,username);
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

    @Override
    public String getJwtSecret(String token) {
        return null;
    }
}
