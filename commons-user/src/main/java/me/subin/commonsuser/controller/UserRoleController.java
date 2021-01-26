package me.subin.commonsuser.controller;


import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import me.subin.utils.JsonConverterBin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import me.subin.commonsuser.constant.ReturnMessage;
import me.subin.commonsuser.entity.UserRole;
import me.subin.commonsuser.service.IUserRoleService;

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
@RequestMapping("/user-role")
public class UserRoleController {
    private final IUserRoleService iUserRoleService;
    @Autowired
    UserRoleController(IUserRoleService iUserRoleService){
        this.iUserRoleService = iUserRoleService;
    }
    @GetMapping("/json")
    public ResponseModel showJson(){
        return ResponseBuilder.success(new UserRole().setId(1L).setRoleName("ROLE_TEST").setRoleNote("测试管理员"));
    }

    /**
     * 添加一个用户角色
     * @param data
     * @return
     */
    @PostMapping()
    public ResponseModel addRole(@RequestParam("data") String data){
        UserRole userRole = JsonConverterBin.transferToObject(data,UserRole.class);
        if (userRole == null || userRole.getRoleName() == null || userRole.getRoleNote() == null){
            return ResponseBuilder.failure(ReturnMessage.MESSAGE_ISNULL);
        }
        List<UserRole> roleList = iUserRoleService.lambdaQuery()
                .eq(UserRole::getRoleName,userRole.getRoleName())
                .or()
                .eq(UserRole::getRoleNote,userRole.getRoleNote())
                .list();
        if (roleList.size() > 0){
            return ResponseBuilder.failure(ReturnMessage.MESSAGE_EXIST);
        }
        if (iUserRoleService.save(userRole)){
            return ResponseBuilder.success();
        }else {
            return ResponseBuilder.failure(ReturnMessage.DB_OPERATE_ERROR);
        }
    }
    /**
     * 修改一个用户角色的信息
     */
    @PutMapping()
    public ResponseModel updateRole(@RequestParam("data") String data){
        UserRole userRole = JsonConverterBin.transferToObject(data,UserRole.class);
        if (userRole == null || userRole.getRoleName() == null || userRole.getRoleNote() == null){
            return ResponseBuilder.failure(ReturnMessage.MESSAGE_ISNULL);
        }
        if (iUserRoleService.lambdaQuery().eq(UserRole::getRoleName,userRole.getRoleName()).or().eq(UserRole::getRoleNote,userRole.getRoleNote()).list().size() > 0){
            return ResponseBuilder.failure(ReturnMessage.MESSAGE_EXIST);
        }
        if (iUserRoleService.updateById(userRole)){
            return ResponseBuilder.success();
        }else {
            return ResponseBuilder.failure(ReturnMessage.DB_OPERATE_ERROR);
        }
    }

    /**
     * 删除一个角色信息
     * @param id
     * @return
     */
    @DeleteMapping()
    public ResponseModel delRole(@RequestParam("id") String id){
        if (iUserRoleService.removeById(id)){
            return ResponseBuilder.success();
        }else {
            return ResponseBuilder.failure(ReturnMessage.DB_OPERATE_ERROR);
        }
    }

    /**
     * 获得所有的角色列表
     * @return
     */
    @GetMapping()
    public ResponseModel getAll(){
        return ResponseBuilder.success(iUserRoleService.list());
    }
}
