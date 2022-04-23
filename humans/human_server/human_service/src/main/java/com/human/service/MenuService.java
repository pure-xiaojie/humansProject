package com.human.service;

import com.human.mapper.menu.MenuMapper;
import com.human.mapper.menu.MenuRoleMapper;
import com.human.pojo.Hr;
import com.human.pojo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/11/28 14:26
 */
@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    /**
     * 根据登录的用户获取对应的菜单列表
     * @return
     */
    public List<Menu> getMenusByHrId() {
        //获取已登录的Hr对象
        Hr hr = (Hr)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return menuMapper.getMenusByHrId(hr.getId());
    }

    /**
     * 获取某个请求应该具备哪些角色
     * @return
     *  //@Cacheable 缓存数据  需要配置redis
     */
    public List<Menu> getAllMenusWithRole() {
        return menuMapper.getAllMenusWithRole();
    }

    /**
     * 获取所有菜单项
     * @return
     */
    public List<Menu> getMenuList() {
        return menuMapper.getMenuList();
    }

    /**
     * 根据角色选择可操作的菜单id
     * @param rid
     * @return
     */
    public List<Integer> getMenuIdsByRoleId(Integer rid) {
        return menuMapper.getMenuIdsByRoleId(rid);
    }

    /**
     * 更新菜单与角色的关联表
     * @param rid
     * @param menusId
     * @return
     */
    @Transactional
    public Boolean updateMenuRole(Integer rid, Integer[] menusId) {
        menuRoleMapper.deleteMenuRoleByRid(rid);
        //一个菜单项也无，直接返回
        if(menusId == null || menusId.length == 0) {
            return true;
        }
        Integer res = menuRoleMapper.insertMenuRole(rid,menusId);
        return res == menusId.length;
    }
}
