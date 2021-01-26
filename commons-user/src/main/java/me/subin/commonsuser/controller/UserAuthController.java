package me.subin.commonsuser.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.utils.JsonConverterBin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import me.subin.commonsuser.constant.ReturnMessage;
import me.subin.commonsuser.entity.UserAuth;
import me.subin.commonsuser.entity.UserRoleAuth;
import me.subin.commonsuser.service.IUserAuthService;
import me.subin.commonsuser.service.IUserRoleAuthService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-07-17
 */
@RestController
@RequestMapping("/user-auth")
public class UserAuthController {
    private final IUserAuthService iUserAuthService;
    private final IUserRoleAuthService iUserRoleAuthService;

    UserAuthController(IUserAuthService iUserAuthService,
                       IUserRoleAuthService iUserRoleAuthService){
        this.iUserAuthService = iUserAuthService;
        this.iUserRoleAuthService = iUserRoleAuthService;
    }
    @GetMapping("/json")
    public ResponseModel<UserAuth> showJson(){
        return ResponseBuilder.success(new UserAuth().setAuthName("测试").setId(1));
    }
    private Boolean userAuthIsNotNull(UserAuth userAuth){
//        判断输入的用户名json实体类是否为空，要是空的返回一个错误结果
        return (userAuth != null && userAuth.getAuthName() !=null);
    }
    private Boolean userAuthIsExist(UserAuth userAuth){
//        通过查询数据库，判断当前的用户名是否存在
        QueryWrapper<UserAuth> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserAuth::getAuthName,userAuth.getAuthName());
        return null != iUserAuthService.getMap(queryWrapper);
    }

    /**
     * 添加一条用户授权信息
     * @param data
     * @return
     */
    @PostMapping()
    public ResponseModel addAuth(@RequestParam("data") String data){
        UserAuth userAuth = JsonConverterBin.transferToObject(data, UserAuth.class);
        if (!userAuthIsNotNull(userAuth)){
            return ResponseBuilder.failure(ReturnMessage.MESSAGE_ISNULL);
        }
        if (userAuth != null &&userAuthIsExist(userAuth)){
            return ResponseBuilder.failure(ReturnMessage.MESSAGE_EXIST);
        }
//        如果不存在相同的用户名，则添加一条信息成功就返回成功，失败就返回失败
        if (iUserAuthService.save(userAuth)){
            return ResponseBuilder.success();
        }else {
            return ResponseBuilder.failure(ReturnMessage.DB_OPERATE_ERROR);
        }
    }
    /**
     * 修改一个用户权限信息
     * @param data
     * @return
     */
    @PutMapping()
    public ResponseModel updateAuth(@RequestParam("data") String data){
        UserAuth userAuth = JsonConverterBin.transferToObject(data,UserAuth.class);
        if (!userAuthIsNotNull(userAuth)){
            return ResponseBuilder.failure(ReturnMessage.MESSAGE_ISNULL);
        }
        if (userAuth != null &&userAuthIsExist(userAuth)){
            return ResponseBuilder.failure(ReturnMessage.MESSAGE_EXIST);
        }
        if (iUserAuthService.updateById(userAuth)){
            return ResponseBuilder.success();
        }else {
            return ResponseBuilder.failure(ReturnMessage.DB_OPERATE_ERROR);
        }
    }
    /**
     * 删除一个权限的信息
     * @param data
     * @return
     */
    @DeleteMapping()
    public ResponseModel delAuth(@RequestParam("data") String data){
        UserAuth userAuth = JsonConverterBin.transferToObject(data,UserAuth.class);
        if (userAuth == null || userAuth.getId() == null){
            return ResponseBuilder.failure(ReturnMessage.MESSAGE_ISNULL);
        }
        if (iUserAuthService.removeById(userAuth.getId())){
            return ResponseBuilder.success();
        }else {
            return ResponseBuilder.failure(ReturnMessage.DB_OPERATE_ERROR);
        }
    }

    /**
     * 查询所有的权限信息
     * @return
     */
    @GetMapping()
    public ResponseModel getAllAuth(){
        return ResponseBuilder.success(iUserAuthService.list());
    }

    /**
     *  通过角色id获得一个权限列表
     * @param roleId
     * @return
     */
    @GetMapping("/role")
    public ResponseModel<List<UserAuth>> getAuthListByRoleId(@RequestParam("id") int roleId){
        List<UserAuth> userAuthList = null;
        List<UserRoleAuth> roleAuthList = iUserRoleAuthService
                .lambdaQuery()
                .eq(UserRoleAuth::getRoleId,roleId)
                .list();
        if (roleAuthList.size() > 0){
            List<Integer> idList = roleAuthList
                    .stream()
                    .map(UserRoleAuth::getAuthId)
                    .collect(Collectors.toList());

            userAuthList = iUserAuthService
                    .lambdaQuery()
                    .in(UserAuth::getId,idList)
                    .list();
        }
        return ResponseBuilder.success(userAuthList);
    }

}
