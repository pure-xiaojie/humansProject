package com.human.mapper.menu;

import com.human.pojo.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/11/28 14:26
 */
@Repository
public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    /**
     * 根据hr的id获取菜单列表
     * @param id
     * @return
     */
    List<Menu> getMenusByHrId(Integer id);

    /**
     *获取所有菜单所具有的角色
     * @return
     */
    List<Menu> getAllMenusWithRole();

    /**
     * 获取所有菜单项
     * @return
     */
    List<Menu> getMenuList();

    /**
     * 根据角色选择可操作的菜单id
     * @param rid
     * @return
     */
    List<Integer> getMenuIdsByRoleId(@Param("rid") Integer rid);
}