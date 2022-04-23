package com.human.controller.system.user;

import com.human.pojo.Hr;
import com.human.pojo.RespBean;
import com.human.pojo.Role;
import com.human.service.RoleService;
import com.human.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/3 16:48
 */
@Api(tags = "管理员信息")
@RestController
@RequestMapping("/system/user/hr")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 获取hr用户列表
     * @return
     */
    @ApiOperation("获取管理列表")
    @GetMapping("/")
    public List<Hr> getHrList(String keywords) {
        return userService.getHrList(keywords);
    }

    /**
     * 更新hr对象
     * @param hr
     * @return
     */
    @ApiOperation("更新管理员信息")
    @PutMapping("/")
    public RespBean updateHr(@RequestBody Hr hr) {
        if(userService.updateHr(hr) == 1) {
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    /**
     * 获取所有角色
     * @return
     */
    @ApiOperation("过去所有角色")
    @GetMapping("/roles")
    public List<Role> getRoleList() {
        return roleService.getRoleList();
    }

    /**
     * 更新hr对象
     * @param hrid
     * @param rids
     * @return
     */
    @ApiOperation("更新管理员角色")
    @PutMapping("/role")
    public RespBean updateHrRole(Integer hrid, Integer[] rids) {
        if(userService.updateHrRole(hrid,rids) == true) {
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    /**
     * 删除管理员
     * @param id
     * @return
     */
    @ApiOperation("删除管理员")
    @DeleteMapping("/{id}")
    public RespBean deleteHrAndRole(@PathVariable Integer id) {
        if(userService.deleteHrAndRole(id) == true) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
