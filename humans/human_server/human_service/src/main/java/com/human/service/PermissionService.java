package com.human.service;

import com.human.mapper.RoleMapper;
import com.human.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/2 14:22
 */
@Service
public class PermissionService {
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 获取角色列表
     * @return
     */
    public List<Role> getRoleList() {
        return roleMapper.getRoleList();
    }
}
