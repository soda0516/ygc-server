package me.subin.commonsuser.controller;

import me.subin.commonsuser.service.IUserInfoRoleService;
import me.subin.commonsuser.entity.UserInfoRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.subin.response.controller.ResponseBuilder;
import me.subin.response.controller.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("//userInfoRole")
public class UserInfoRoleController {

    @Autowired
    public IUserInfoRoleService userInfoRoleService;

    /**
     * 保存和修改公用的
     * @param userInfoRole  传递的实体
     * @return ResponseModel转换结果
     */
    @PostMapping
    public ResponseModel<String> save(@RequestBody UserInfoRole userInfoRole){
        try {
            if(userInfoRole.getId()!=null){
                userInfoRoleService.updateById(userInfoRole);
            }else{
                userInfoRoleService.save(userInfoRole);
            }
            return ResponseBuilder.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.failure("保存对象失败");
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @DeleteMapping(value="/{id}")
    public ResponseModel<String> delete(@PathVariable("id") Long id){
        try {
            userInfoRoleService.removeById(id);
            return ResponseBuilder.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.failure("删除对象失败");
        }
    }

    /**
    * 查看单个信息
    * @return
    */
    @GetMapping(value = "/{id}")
    public ResponseModel<UserInfoRole> get(@PathVariable("id") Long id) {
        return ResponseBuilder.success(userInfoRoleService.getById(id));
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping(value = "/list")
    public ResponseModel<List<UserInfoRole>> list(){
        return ResponseBuilder.success(userInfoRoleService.list());
    }


    /**
    * 分页查询数据
    * @return PageList 分页对象
    */
    @GetMapping(value = "/page")
    public ResponseModel<IPage<UserInfoRole>> page(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("size") Integer size,
            @RequestParam("pages") Integer pages) {
        IPage<UserInfoRole> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(size);
        page.setPages(pages);
        return ResponseBuilder.success(userInfoRoleService.page(page));
    }
}