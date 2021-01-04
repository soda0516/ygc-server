package com.subin.springbootuser.controller;


import com.subin.springbootcore.exception.ForbiddenException;
import com.subin.springbootcore.response.ResponseBuilder;
import com.subin.springbootcore.response.ResponseModel;
import com.subin.springbootcore.util.JsonUtils;
import com.subin.springbootuser.constant.ReturnMessage;
import com.subin.springbootuser.entity.UserInfo;
import com.subin.springbootuser.model.JwtUser;
import com.subin.springbootuser.service.IUserInfoService;
import com.subin.springbootuser.service.IUserRoleService;
import com.subin.springbootuser.util.JwtUtils;
import com.subin.springbootuser.util.SecurityContextUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-07-17
 */
@Slf4j
@RestController
@RequestMapping("/user-info")
@Api(tags = "用户信息管理")
public class UserInfoController {

    private static Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    private final IUserInfoService iUserInfoService;
    private final IUserRoleService iUserRoleService;
    private final RedisTemplate<String,Object> objectRedisTemplate;

    UserInfoController(IUserInfoService iUserInfoService,
                       RedisTemplate<String,Object> objectRedisTemplate,
                       IUserRoleService iUserRoleService){
        this.iUserInfoService = iUserInfoService;
        this.objectRedisTemplate = objectRedisTemplate;
        this.iUserRoleService = iUserRoleService;
    }
    private Boolean userInfoNameIsExist(String name){
        if (iUserInfoService.lambdaQuery().eq(UserInfo::getUsername,name).list().size() > 0){
            return true;
        }else {
            return false;
        }
    }
    /**
     * 显示json格式的信息
     * @return
     */
    @GetMapping("/json")
    public ResponseModel showJson(){
        return ResponseBuilder.success(new UserInfo().setUsername("Json").setId(0).setPassword("123").setRoleId(0));
    }

    @GetMapping("/list")
    public ResponseModel<List<UserInfo>> getUserList(){
        return ResponseBuilder.success(iUserInfoService.list());
    }
    @GetMapping("/list/detail")
    public ResponseModel<List<UserInfo>> getUserInfoDetailList(){
        // TODO: 2019/10/3 过滤掉自己
//        JwtUser user = SecurityContextUtil.getJwtUserFromContext();
        List<UserInfo> userInfoList = iUserInfoService.getUserInfoWithUserRole();
//                .stream()
//                .filter(item -> item.getId() != user.getId())
//                .collect(Collectors.toList());
        return ResponseBuilder.success(userInfoList);
    }
    /**
     * 通过用户名查询一个信息
     * @param name
     * @return
     */
    @GetMapping("/name")
    public ResponseModel getInfoByName(@RequestParam(value = "name",required = false) String name){
        if (!userInfoNameIsExist(name)){
            return ResponseBuilder.failure(ReturnMessage.MESSAGE_NOTEXIST);
        }
        UserInfo userInfo = iUserInfoService.lambdaQuery().eq(UserInfo::getUsername,name).one();
        return ResponseBuilder.success(userInfo);
    }

    /**
     * 通过token查询一个用户的信息
     * @param token
     * @return
     */
    @GetMapping("/info")
    public ResponseModel<UserInfo> getUserInfoByToken(@RequestParam(value = "token",required = false) String token){
        int userIdFromJwtToken = JwtUtils.getUserIdFromJwtToken(token,token);
        UserInfo info = iUserInfoService.getUserInfoWithUserRoleById(userIdFromJwtToken);
        if (null == info){
            throw new ForbiddenException("通过当前token，没有查询到相关用户的信息!");
        }
        return ResponseBuilder.success(info);
    }

    /**
     * 添加一条用户信息
     * @param data
     * @return
     */
    @PostMapping("")
    public ResponseModel addInfo(@RequestParam("info") String data){
        UserInfo userInfo = JsonUtils.transferToObject(data,UserInfo.class);
        if (userInfo == null || userInfo.getUsername() == null){
            return ResponseBuilder.failure(ReturnMessage.MESSAGE_ISNULL);
        }
        if (userInfoNameIsExist(userInfo.getUsername())){
            return ResponseBuilder.failure(ReturnMessage.MESSAGE_EXIST);
        }
        if (iUserInfoService.save(userInfo)){
            return ResponseBuilder.success();
        }else {
            return ResponseBuilder.failure(ReturnMessage.DB_OPERATE_ERROR);
        }
    }

    /**
     * 更新一条用户的信息
     * @param data
     * @return
     */
    @PutMapping("")
    public ResponseModel updateInfoById(@RequestParam("data") String data){
        UserInfo info = JsonUtils.transferToObject(data,UserInfo.class);
        if (info == null || info.getUsername() == null || info.getId() == null){
            return ResponseBuilder.failure(ReturnMessage.MESSAGE_ISNULL);
        }
        if (iUserInfoService.updateById(info)){
            return ResponseBuilder.success();
        }else {
            return ResponseBuilder.failure(ReturnMessage.DB_OPERATE_ERROR);
        }
    }
    /**
     * 更新一条用户的信息
     * @param data
     * @return
     */
    @PutMapping("/password")
    public ResponseModel updatePasswordById(@RequestParam("data") String data){
        UserInfo info = JsonUtils.transferToObject(data,UserInfo.class);
        JwtUser user = SecurityContextUtil.getJwtUserFromContext();
        info.setId(user.getId());
//        if (info == null || info.getUsername() == null || info.getId() == null){
//            return ResponseBuilder.failure(ReturnMessage.MESSAGE_ISNULL);
//        }
        if (iUserInfoService.updateById(info)){
            return ResponseBuilder.success();
        }else {
            return ResponseBuilder.failure(ReturnMessage.DB_OPERATE_ERROR);
        }
    }

    /**
     * 删除一条用户信息
     * @param id
     * @return
     */
    @DeleteMapping("")
    public ResponseModel delInfoById(@RequestParam("id") String id){
        if (iUserInfoService.removeById(id)){
            return ResponseBuilder.success();
        }else {
            return ResponseBuilder.failure(ReturnMessage.DB_OPERATE_ERROR);
        }
    }
}
