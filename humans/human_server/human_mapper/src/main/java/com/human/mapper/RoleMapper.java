package com.human.mapper;

import com.human.pojo.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/2 20:45
 */
@Repository
public interface RoleMapper {
    /**
     * 根据id删除角色
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入角色
     * @param role
     * @return
     */
    int insert(Role role);

    int insertSelective(Role role);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role role);

    int updateByPrimaryKey(Role record);

    /**
     * 获取角色列表
     * @return
     */
    List<Role> getRoleList();
}