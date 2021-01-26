package me.subin.commonsuser.controller;


import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import me.subin.commonsuser.constant.ReturnMessage;
import me.subin.commonsuser.entity.UserRoleAuth;
import me.subin.commonsuser.service.IUserRoleAuthService;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-07-17
 */
@RestController
@RequestMapping("/user-role-auth")
public class UserRoleAuthController {
    private final IUserRoleAuthService iUserRoleAuthService;
    @Autowired
    UserRoleAuthController(IUserRoleAuthService iUserRoleAuthService){
        this.iUserRoleAuthService = iUserRoleAuthService;
    }
    @GetMapping("/json")
    public ResponseModel showJson(){
        return ResponseBuilder.success(new UserRoleAuth().setAuthId(0).setAuthId(0).setRoleId(0));
    }

    /**
     * 添加一条信息
     * @param data
     * @return
     */
    @PostMapping()
    public ResponseModel add(@RequestParam("data") String data){
        UserRoleAuth userRoleAuth = JsonConverterBin.transferToObject(data,UserRoleAuth.class);
        if (userRoleAuth == null || userRoleAuth.getRoleId() == null || userRoleAuth.getAuthId() == null){
            return ResponseBuilder.failure(ReturnMessage.MESSAGE_ISNULL);
        }
        List<UserRoleAuth> userRoleAuthList = iUserRoleAuthService.lambdaQuery().eq(UserRoleAuth::getRoleId,userRoleAuth.getRoleId()).eq(UserRoleAuth::getAuthId,userRoleAuth.getAuthId()).list();
        if (userRoleAuthList.size() > 0){
            return ResponseBuilder.failure(ReturnMessage.MESSAGE_EXIST);
        }
        if (iUserRoleAuthService.save(userRoleAuth)){
            return ResponseBuilder.success();
        }else {
            return ResponseBuilder.failure(ReturnMessage.DB_OPERATE_ERROR);
        }
    }

    /**
     * 更新一条信息
     * @param data
     * @return
     */
    @PutMapping()
    public ResponseModel update(@RequestParam("data") String data){
        UserRoleAuth userRoleAuth = JsonConverterBin.transferToObject(data,UserRoleAuth.class);
        if (userRoleAuth == null || userRoleAuth.getRoleId() == null || userRoleAuth.getAuthId() == null){
            return ResponseBuilder.failure(ReturnMessage.MESSAGE_ISNULL);
        }
        if (iUserRoleAuthService.lambdaQuery().eq(UserRoleAuth::getRoleId,userRoleAuth.getRoleId()).eq(UserRoleAuth::getAuthId,userRoleAuth.getAuthId()).list().size() > 0){
            return ResponseBuilder.failure(ReturnMessage.MESSAGE_EXIST);
        }
        if (iUserRoleAuthService.updateById(userRoleAuth)){
            return ResponseBuilder.success();
        }else {
            return ResponseBuilder.failure(ReturnMessage.DB_OPERATE_ERROR);
        }
    }

    /**
     * 删除一条信息
     * @param id
     * @return
     */
    @DeleteMapping()
    public ResponseModel del(@RequestParam("id") String id){
        if (iUserRoleAuthService.removeById(id)){
            return ResponseBuilder.success();
        }else {
            return ResponseBuilder.failure(ReturnMessage.DB_OPERATE_ERROR);
        }
    }
}