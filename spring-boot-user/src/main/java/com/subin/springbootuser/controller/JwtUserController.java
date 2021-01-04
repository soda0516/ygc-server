package com.subin.springbootuser.controller;

import com.subin.springbootcore.response.ResponseBuilder;
import com.subin.springbootcore.response.ResponseModel;
import com.subin.springbootcore.util.JsonUtils;
import com.subin.springbootuser.constant.JwtConfigConstant;
import com.subin.springbootuser.constant.JwtDbConstant;
import com.subin.springbootuser.constant.ReturnMessage;
import com.subin.springbootuser.entity.UserInfo;
import com.subin.springbootuser.model.JwtStandardInfo;
import com.subin.springbootuser.model.JwtUser;
import com.subin.springbootuser.service.IJwtDbService;
import com.subin.springbootuser.service.IUserInfoService;
import com.subin.springbootuser.service.IUserRoleAuthService;
import com.subin.springbootuser.service.IUserTokenService;
import com.subin.springbootuser.util.JwtUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @Describe
 * @Author soda
 * @Create 2019/7/23 10:24
 **/
@Slf4j
@RestController
@RequestMapping("/jwt-user")
@Api(tags = "用于jwt登录和登出的功能")
public class JwtUserController {
    @Resource(name = JwtDbConstant.JWT_MYSQL_IMPL)
    IJwtDbService iJwtDbService;

    private final IUserInfoService iUserInfoService;
    private final IUserRoleAuthService iUserRoleAuthService;
    private final IUserTokenService iUserTokenService;

    public JwtUserController(IUserInfoService iUserInfoService,
                             IUserRoleAuthService iUserRoleAuthService,
                             IUserTokenService iUserTokenService){
        this.iUserInfoService = iUserInfoService;
        this.iUserRoleAuthService = iUserRoleAuthService;
        this.iUserTokenService = iUserTokenService;
    }
    @GetMapping("/json")
    public ResponseModel showJson(){
        return ResponseBuilder.success(new UserInfo().setUsername("测试m"));
    }

    /**
     * 用户单击登录，如果成功返回一个jwt信息
     * @param data
     * @return
     */
    @PostMapping("/login")
    public ResponseModel login(@RequestParam("data") String data){
        UserInfo userInfo = JsonUtils.transferToObject(data,UserInfo.class);
        if (userInfo == null || userInfo.getUsername() == null || userInfo.getPassword() == null){
            return ResponseBuilder.warning(ReturnMessage.USERNAME_OR_PASSWORD_ISNULL);
        }
        if (iUserInfoService.checkUserInfoByNameAndPassword(userInfo)){
            JwtStandardInfo jwtStandardInfo = new JwtStandardInfo();
            JwtUser jwtUserInfo = iUserRoleAuthService.getJwtUserInfo(userInfo);
            String secret = UUID.randomUUID().toString().replace("-","");
            String token = JwtUtils.generateJwt(secret,jwtStandardInfo,jwtUserInfo);
//            添加token到redis缓存里面,添加之前，先得清除当前用户的其他缓存
            iJwtDbService.delJwtTokenInDbByUserName(jwtUserInfo.getUsername());
            iJwtDbService.addJwtTokenInDb(token,jwtUserInfo.getUsername());
            return ResponseBuilder.success(token);
        }else {
            return ResponseBuilder.warning(ReturnMessage.USERNAME_OR_PASSWORD_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseModel login(HttpServletRequest httpRequest){
        String authHeader = httpRequest.getHeader(JwtConfigConstant.X_TOKEN);
        if (null == authHeader){
            return ResponseBuilder.failure();
        }
        String authString = authHeader.substring(JwtConfigConstant.TOKEN_HEAD.length());
        iJwtDbService.delJwtTokenInDb(authString);
        return ResponseBuilder.success();
    }
}
