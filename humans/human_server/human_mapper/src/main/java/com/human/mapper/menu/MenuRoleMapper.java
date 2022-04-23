package com.human.mapper.menu;

import com.human.pojo.MenuRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MenuRole record);

    int insertSelective(MenuRole record);

    MenuRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MenuRole record);

    int updateByPrimaryKey(MenuRole record);

    /**
     * 删除rid菜单角色表数据
     * @param rid
     */
    void deleteMenuRoleByRid(@Param("rid") Integer rid);

    /**
     * 更新菜单角色关联表
     * @param rid
     * @param menusId
     * @return
     */
    Integer insertMenuRole(@Param("rid") Integer rid, @Param("menusId") Integer[] menusId);
}