package com.human.service;

import com.human.mapper.RoleMapper;
import com.human.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/2 19:45
 */
@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 添加角色
     * @param role
     * @return
     */
    public int addRole(Role role) {
        //若名称不以ROLE_开头，则加上
        if(!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        return roleMapper.insert(role);
    }

    /**
     * 删除角色
     * @param rid
     * @return
     */
    public int deleteRoleById(Integer rid) {
        return roleMapper.deleteByPrimaryKey(rid);
    }

    /**
     * 获取角色列表
     * @return
     */
    public List<Role> getRoleList() {
        return roleMapper.getRoleList();
    }
}
