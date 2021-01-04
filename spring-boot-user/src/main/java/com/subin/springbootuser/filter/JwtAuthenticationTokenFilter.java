package com.subin.springbootuser.filter;

import com.subin.springbootuser.constant.JwtConfigConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.subin.springbootuser.constant.JwtDbConstant;
import com.subin.springbootuser.model.JwtUser;
import com.subin.springbootuser.service.IJwtDbService;
import com.subin.springbootuser.util.JwtUtils;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Describe
 * @Author soda
 * @Create 2019/7/23 18:44
 **/
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource(name = JwtDbConstant.JWT_REDIS_IMPL)
    private IJwtDbService iJwtDbService;


    private void putJwtUserToContext(Integer userId,String username,List<GrantedAuthority> authentication,HttpServletRequest httpServletRequest){
        JwtUser jwtUser = new JwtUser.Builder()
                .setId(userId)
                .setUsername(username)
                .setAuthorities(authentication).build();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                jwtUser, null, jwtUser.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authString = httpServletRequest.getHeader(JwtConfigConstant.X_TOKEN);
        if (!StringUtils.isBlank(authString)) {
//            String authString = authHeader.substring(tokenHead.length());
//            先进性jwtToken有效性的验证，然后操作缓存
            Integer userId = JwtUtils.getUserIdFromJwtToken(iJwtDbService.getJwtSecret(authString),authString);
            String username = JwtUtils.getUserNameFromJwtToken(iJwtDbService.getJwtSecret(authString),authString);
            List<GrantedAuthority> authentication = JwtUtils.getAuthenticationListFromJwtToken(iJwtDbService.getJwtSecret(authString),authString);
//            && null == SecurityContextHolder.getContext()
            if (null!= userId && null != username && null != authentication) {
//                经过验证，说明jwtToken是有效的，首先判断缓存里是否存在相应jwtToken，如果存在还需要判断jwtToken是否过期，只有都通过了，才对SecurityContextHolder进行赋值权限
                if (iJwtDbService.isExistJwtTokenInDb(authString)){
                    LocalDateTime deadline =iJwtDbService.getTimeByJwtTokenInDb(authString);
//                    没有过期，刷新缓存中的jwtToken，同时对
                    if (null != deadline && LocalDateTime.now().isBefore(deadline)){
                        this.putJwtUserToContext(userId,username,authentication,httpServletRequest);
                        //刷新当前token的失效日期，同时进行下一步操作，过期了，就得清除相应的jwtToken
                        iJwtDbService.refreshJwtTokenInDb(authString);
                    }else {
//                        过期清除token
                        iJwtDbService.delJwtTokenInDb(authString);
                    }
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
