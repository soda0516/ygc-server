package me.subin.commonsuser.controller;


import lombok.extern.slf4j.Slf4j;
import me.subin.commonsuser.bo.UserInfoDetailBo;
import me.subin.commonsuser.constant.TokenConstant;
import me.subin.commonsuser.entity.UserInfoRole;
import me.subin.commonsuser.entity.UserRole;
import me.subin.commonsuser.service.IUserInfoRoleService;
import me.subin.commonsuser.vo.UserInfoVo;
import me.subin.exception.ForbiddenException;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.response.service.ServiceResponse;
import me.subin.utils.JsonConverterBin;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import me.subin.commonsuser.constant.ReturnMessage;
import me.subin.commonsuser.entity.UserInfo;
import me.subin.commonsuser.model.JwtUser;
import me.subin.commonsuser.service.IUserInfoService;
import me.subin.commonsuser.service.IUserRoleService;
import me.subin.commonsuser.util.JwtUtil;
import me.subin.commonsuser.util.SecurityContextUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.awt.SystemColor.info;

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
public class UserInfoController {

    private final IUserInfoService iUserInfoService;
    private final IUserRoleService iUserRoleService;
    private final IUserInfoRoleService iUserInfoRoleService;
//    private final RedisTemplate<String,Object> objectRedisTemplate;

    UserInfoController(IUserInfoService iUserInfoService,
//                       RedisTemplate<String,Object> objectRedisTemplate,
                       IUserRoleService iUserRoleService,
                       IUserInfoRoleService iUserInfoRoleService){
        this.iUserInfoService = iUserInfoService;
//        this.objectRedisTemplate = objectRedisTemplate;
        this.iUserRoleService = iUserRoleService;
        this.iUserInfoRoleService = iUserInfoRoleService;
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
        return ResponseBuilder.success(new UserInfo().setUsername("Json").setId(0L).setPassword("123"));
    }

    @GetMapping("/list")
    public ResponseModel<List<UserInfo>> getUserList(){
        return ResponseBuilder.success(iUserInfoService.list());
    }
    @GetMapping("/list/all")
    public ResponseModel<List<UserInfo>> getUserListWithRole(){
        return ResponseBuilder.success(iUserInfoService.list());
    }

    @GetMapping("/list/detail")
    public ResponseModel<List<UserInfoDetailBo>> getUserInfoDetailList(){
        List<UserInfoDetailBo> userInfoDetailBos = iUserInfoService.listUserInfoDetailBo();
        return ResponseBuilder.success(userInfoDetailBos);
    }
    @GetMapping("/list/detail/{id}")
    public ResponseModel<UserInfoDetailBo> getUserInfoDetailListById(@PathVariable("id") Long id){
        if (Objects.isNull(id)){
            return ResponseBuilder.failure("请输入一个用户的ID");
        }
        return ResponseBuilder.success(iUserInfoService.getUserInfoDetailBoById(id));
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
    public ResponseModel<UserInfoVo> getUserInfoByToken(@RequestParam(value = "token",required = false) String token){
        JwtUser jwtUserFromContext = SecurityContextUtil.getJwtUserFromContext();
        List<String> roles = new ArrayList<>();
        long id = jwtUserFromContext.getId();
        List<UserInfoRole> list = iUserInfoRoleService.lambdaQuery()
                .eq(UserInfoRole::getInfoId, id)
                .list();
        List<Long> collect = list.stream().map(UserInfoRole::getRoleId).collect(Collectors.toList());
        List<UserRole> roleList = iUserRoleService.lambdaQuery()
                .in(UserRole::getId, collect)
                .list();
        for (UserRole role:roleList
             ) {
            roles.add(role.getRoleName());
        }
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setId(id);
        userInfoVo.setName(jwtUserFromContext.getUsername());
        userInfoVo.setRoles(roles);
        return ResponseBuilder.success(userInfoVo);
    }

    /**
     * 添加一条用户信息
     * @param username
     * @return
     */
    @PostMapping()
    public ResponseModel addInfo(@RequestParam("username") String username){
        Integer count = iUserInfoService.lambdaQuery()
                .eq(UserInfo::getUsername, username)
                .count();
        if (count > 0) {
            return ResponseBuilder.failure("输入的用户名重复，请重新输入一个用户名");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        iUserInfoService.save(userInfo);
        return ResponseBuilder.success();
    }

    /**
     * 更新一条用户的信息
     * @param data
     * @return
     */
    @PutMapping("")
    public ResponseModel updateInfoById(@RequestParam("data") String data){
        UserInfo info = JsonConverterBin.transferToObject(data,UserInfo.class);
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
    @PutMapping("/bo")
    public ResponseModel<Long> updateInfoByUserInfoBo(@RequestParam("data") String data){
        UserInfoDetailBo userInfoDetailBo = JsonConverterBin.transferToObject(data, UserInfoDetailBo.class);
        ServiceResponse<Long> listServiceResponse =
                iUserInfoService.updateByUserInfoBo(userInfoDetailBo);
        if (listServiceResponse.getSuccess()){
            return ResponseBuilder.success(listServiceResponse.getData());
        }else {
            return ResponseBuilder.failure(listServiceResponse.getMessage());
        }
    }
    /**
     * 更新一条用户的信息
     * @param data
     * @return
     */
    @PutMapping("/password")
    public ResponseModel updatePasswordById(@RequestParam("data") String data){
        UserInfo info = JsonConverterBin.transferToObject(data,UserInfo.class);
        JwtUser user = SecurityContextUtil.getJwtUserFromContext();
        info.setId(user.getId());
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
