package me.subin.commonsuser.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.subin.commonsuser.constant.TokenConstant;
import me.subin.commonsuser.exception.TokenIsExpiredException;
import me.subin.commonsuser.util.JwtTokenUtils;
import me.subin.utils.JsonConverterBin;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import me.subin.commonsuser.constant.JwtDbConstant;
import me.subin.commonsuser.model.JwtUser;
import me.subin.commonsuser.service.IJwtDbService;
import me.subin.commonsuser.util.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Describe
 * @Author soda
 * @Create 2019/7/23 18:44
 **/
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private IJwtDbService iJwtDbService;

    private void putJwtUserToContext(Integer userId, String username, Long storeId,Collection<GrantedAuthority> authentication, HttpServletRequest httpServletRequest){
        JwtUser jwtUser = new JwtUser.Builder()
                .setId(userId)
                .setUsername(username)
                .setAuthorities(authentication).build();
        jwtUser.setStoreId(storeId);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                jwtUser, null, authentication);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info(JsonConverterBin.transferToJson(SecurityContextHolder.getContext()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        log.error(httpServletRequest.getHeader("authorization"));
        String authString = httpServletRequest.getHeader("authorization");
        // 如果请求头中没有Authorization信息则直接放行了
        if (StringUtils.isNotBlank(authString) && authString.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
            try {
                authString = StringUtils.substringAfter(authString,JwtTokenUtils.TOKEN_PREFIX);
                Integer userId = JwtUtil.getUserIdFromJwtToken(TokenConstant.COMMONS_SECRET,authString);
                String username = JwtUtil.getUserNameFromJwtToken(TokenConstant.COMMONS_SECRET,authString);
                Long storeId = JwtUtil.getStoreIdFromJwtToken(TokenConstant.COMMONS_SECRET,authString);
                List<GrantedAuthority> authentication = JwtUtil.getAuthenticationListFromJwtToken(TokenConstant.COMMONS_SECRET,authString);
                if (null!= userId && null != username) {
    //                经过验证，说明jwtToken是有效的，首先判断缓存里是否存在相应jwtToken，如果存在还需要判断jwtToken是否过期，只有都通过了，才对SecurityContextHolder进行赋值权限
                    if (iJwtDbService.isExistJwtTokenInDb(authString)){
                        LocalDateTime deadline =iJwtDbService.getTimeByJwtTokenInDb(authString);
    //                    没有过期，刷新缓存中的jwtToken，同时对
                        if (null != deadline && LocalDateTime.now().isBefore(deadline)){
                            this.putJwtUserToContext(userId,username,storeId,authentication,httpServletRequest);
                            //刷新当前token的失效日期，同时进行下一步操作，过期了，就得清除相应的jwtToken
                            iJwtDbService.refreshJwtTokenInDb(authString);
                        }else {
    //                        过期清除token
                            iJwtDbService.delJwtTokenInDb(authString);
                        }
                    }
                }
            } catch (Exception e) {
                // TODO: 2021/1/24 这块可以直接返回一个坚定权限失败
                e.printStackTrace();
            }
        }
        log.info(JsonConverterBin.transferToJson(SecurityContextHolder.getContext()));
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * 用户名密码验证
     * @param tokenHeader
     * @return
     * @throws TokenIsExpiredException
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) throws TokenIsExpiredException {
        String token = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
        boolean expiration = JwtTokenUtils.isExpiration(token);
        if (expiration) {
            throw new TokenIsExpiredException("token超时了");
        } else {
            String username = JwtTokenUtils.getUsername(token);
            String role = JwtTokenUtils.getUserRole(token);
            if (username != null) {
                return new UsernamePasswordAuthenticationToken(username, null,
                        Collections.singleton(new SimpleGrantedAuthority(role))
                );
            }
        }
        return null;
    }
}
