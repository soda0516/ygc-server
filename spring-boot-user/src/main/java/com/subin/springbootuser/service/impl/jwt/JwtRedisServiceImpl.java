package com.subin.springbootuser.service.impl.jwt;

import com.subin.springbootuser.constant.JwtConfigConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.subin.springbootuser.constant.JwtDbConstant;
import com.subin.springbootuser.constant.RedisConstant;
import com.subin.springbootuser.service.IJwtDbService;
import com.subin.springbootuser.util.JwtUtils;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @Describe
 * @Author soda
 * @Create 2019/8/5 21:15
 **/
@Service(value = JwtDbConstant.JWT_REDIS_IMPL)
public class JwtRedisServiceImpl implements IJwtDbService {


    private final RedisTemplate<String,Object> objectRedisTemplate;

    JwtRedisServiceImpl(RedisTemplate<String,Object> objectRedisTemplate){
        this.objectRedisTemplate = objectRedisTemplate;
    }

    /**
     * 添加jwt进入缓存，并返回一个boolean值
     * @param token
     * @return
     */
    public void addJwtTokenInDb(String token ,String username){
        objectRedisTemplate.opsForHash().put(RedisConstant.RedisTokenKey,token, LocalDateTime.now().plusHours(JwtConfigConstant.EXPIRY_HOURS));
    }

    /**
     * 刷新jwt进入缓存，并返回一个boolean值，其实跟添加是一样的 哈哈哈哈
     * @param token
     * @return
     */
    public void refreshJwtTokenInDb(String token){
        objectRedisTemplate.opsForHash().put(RedisConstant.RedisTokenKey,token, LocalDateTime.now().plusHours(JwtConfigConstant.EXPIRY_HOURS));
    }

    @Override
    public void delJwtTokenInDbByUserName(String username) {

    }

    /**
     * 删除同一个username的，所有的jwtToken
     * @param username
     * @return
     */
    public void delJwtTokenInDbByUserName(String username,String token){
        //                    说明缓存里面没有当前的jwtToken，需要清空当前用户的相关的jwtToken，确定缓存中没有当前用户的其他jwtToken
        Set<Object> objects = objectRedisTemplate.opsForHash().keys(RedisConstant.RedisTokenKey);
//                    清除缓存中当前用户名的jwtToken
        objects.forEach(value -> {
            String name = JwtUtils.getUserNameFromJwtToken(this.getJwtSecret(token),value.toString());
            if (null != name && name.equals(username)){
                objectRedisTemplate.opsForHash().delete(RedisConstant.RedisTokenKey,value);
            }
        });
    }

    /**
     * 删除一个JwtToken
     * @param token
     */
    public void delJwtTokenInDb(String token){
        objectRedisTemplate.opsForHash().delete(RedisConstant.RedisTokenKey,token, LocalDateTime.now().plusHours(JwtConfigConstant.EXPIRY_HOURS));
    }

    /**
     * 判断缓存里面有没有这个token
     * @param token
     * @return
     */
    public boolean isExistJwtTokenInDb(String token){
        return objectRedisTemplate.opsForHash().hasKey(RedisConstant.RedisTokenKey,token);
    }

    /**
     * 从token里面那LocalDataTime弄出来
     * @param token
     * @return
     */
    public LocalDateTime getTimeByJwtTokenInDb(String token){
        return (LocalDateTime) objectRedisTemplate.opsForHash().get(RedisConstant.RedisTokenKey,token);
    }

    @Override
    public String getJwtSecret(String token) {
        return null;
    }
}
