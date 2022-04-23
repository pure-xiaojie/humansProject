package com.human.controller.system.basic;

import com.human.pojo.Menu;
import com.human.pojo.RespBean;
import com.human.pojo.Role;
import com.human.service.MenuService;
import com.human.service.PermissionService;
import com.human.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/2 14:20
 * 权限组：获取角色
 */
@Api(tags = "角色信息")
@RestController
@RequestMapping("/system/basic/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    /**
     * 获取角色列表
     * @return
     */
    @ApiOperation("获取角色列表")
    @GetMapping("/")
    public List<Role> getRoleList() {
        return permissionService.getRoleList();
    }

    /**
     * 获取所有菜单项
     * @return
     */
    @ApiOperation("获取所有菜单项")
    @GetMapping("/menus")
    public List<Menu> getMenuList() {
        return menuService.getMenuList();
    }

    /**
     * 根据角色选择可操作的菜单id
     * @param rid
     * @return
     */
    @ApiOperation("根据角色选择可操作的菜单id")
    @GetMapping("/menu/{rid}")
    public List<Integer> getMenuIdsByRoleId(@PathVariable Integer rid) {
        return menuService.getMenuIdsByRoleId(rid);
    }

    /**
     * 更新菜单与角色的关联表
     * @param rid
     * @param menusId
     * @return
     */
    @ApiOperation("更新菜单与角色的关联表")
    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid, Integer[] menusId) {
        if(menuService.updateMenuRole(rid,menusId) == true) {
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @ApiOperation("添加角色")
    @PostMapping("/role")
    public RespBean addRole(@RequestBody Role role) {
        if(roleService.addRole(role) == 1) {
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    /**
     * 删除一个角色
     * @param rid
     * @return
     */
    @ApiOperation("删除一个角色")
    @DeleteMapping("/{rid}")
    public RespBean deleteRole(@PathVariable Integer rid) {
        if(roleService.deleteRoleById(rid) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
