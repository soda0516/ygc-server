package com.subin.springbootuser.util;

import com.subin.springbootcore.exception.UnAuthorizedException;
import org.springframework.security.core.context.SecurityContextHolder;
import com.subin.springbootuser.model.JwtUser;

/**
 * @Describe
 * @Author soda
 * @Create 2019/8/11 12:48
 **/
public class SecurityContextUtil {
    public static JwtUser getJwtUserFromContext(){
        JwtUser jwtUser;
        try {
            jwtUser = (JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new UnAuthorizedException("没查询到相关用户信息");
        }
        return jwtUser;
    }
}
