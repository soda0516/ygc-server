package me.subin.commonsuser.controller;


import lombok.extern.slf4j.Slf4j;
import me.subin.commonsuser.bo.UserInfoBo;
import me.subin.commonsuser.constant.TokenConstant;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import me.subin.commonsuser.constant.JwtDbConstant;
import me.subin.commonsuser.constant.ReturnMessage;
import me.subin.commonsuser.entity.UserInfo;
import me.subin.commonsuser.model.JwtStandardInfo;
import me.subin.commonsuser.model.JwtUser;
import me.subin.commonsuser.service.IJwtDbService;
import me.subin.commonsuser.service.IUserInfoService;
import me.subin.commonsuser.service.IUserRoleAuthService;
import me.subin.commonsuser.service.IUserTokenService;
import me.subin.commonsuser.util.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @Describe
 * @Author soda
 * @Create 2019/7/23 10:24
 **/
@Slf4j
@RestController
@RequestMapping("/jwt-user")
//@Api(tags = "用于jwt登录和登出的功能")
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
        log.info("JwtUserController");
        return ResponseBuilder.success(new UserInfo().setUsername("测试m"));
    }

    /**
     * 用户单击登录，如果成功返回一个jwt信息
     * @param data
     * @return
     */
    @PostMapping("/login")
    public ResponseModel login(@RequestParam("data") String data){
        UserInfo userInfo = JsonConverterBin.transferToObject(data,UserInfo.class);
        if (userInfo == null || userInfo.getUsername() == null || userInfo.getPassword() == null){
            return ResponseBuilder.warning(ReturnMessage.USERNAME_OR_PASSWORD_ISNULL);
        }
        Long aLong = iUserInfoService.checkUserInfoByNameAndPassword(userInfo);
        if (aLong > 0){
            JwtStandardInfo jwtStandardInfo = new JwtStandardInfo();
            UserInfoBo userInfoBo = iUserInfoService.getUserInfoBo(aLong);
            String token = JwtUtil.generateJwt(TokenConstant.COMMONS_SECRET,jwtStandardInfo,userInfoBo);
            log.info(token);
            iJwtDbService.delJwtTokenInDbByUserName(userInfoBo.getId());
            iJwtDbService.addJwtTokenInDb(token, userInfoBo.getUsername(), userInfoBo.getId());
            return ResponseBuilder.success(token);
        }else {
            return ResponseBuilder.failure(ReturnMessage.USERNAME_OR_PASSWORD_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseModel login(HttpServletRequest httpRequest){
        log.info("能进可耐");
        String authHeader = httpRequest.getHeader(TokenConstant.X_TOKEN);
//        if (null == authHeader){
//            return ResponseBuilder.failure();
//        }
//        String authString = authHeader.substring(TokenConstant.TOKEN_HEADER.length());
//        iJwtDbService.delJwtTokenInDb(authString);
        return ResponseBuilder.success();
    }

    /**
     * 验证jwt-token是否还有效
     * @param token
     * @return
     */
    @PostMapping("/jwt-token/validate")
    public ResponseModel tokenValidate(@RequestParam("token") String token){
        if (iJwtDbService.isExistJwtTokenInDb(token)){
            LocalDateTime deadline =iJwtDbService.getTimeByJwtTokenInDb(token);
//                    没有过期，刷新缓存中的jwtToken，同时对
            if (null != deadline && LocalDateTime.now().isBefore(deadline)){
                //刷新当前token的失效日期，同时进行下一步操作，过期了，就得清除相应的jwtToken
                iJwtDbService.refreshJwtTokenInDb(token);
                return ResponseBuilder.success("jwt-token验证成功！");
            }else {
//                        过期清除token
                iJwtDbService.delJwtTokenInDb(token);
            }
        }
        return ResponseBuilder.failure("jwt-token验证失败！");
    }
}
