package com.human.mapper;

import com.human.pojo.Hr;
import com.human.pojo.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/11/25 21:37
 */
@Repository
public interface HrMapper {
    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入
     * @param record
     * @return
     */
    int insert(Hr record);

    /**
     * 插入
     * @param record
     * @return
     */
    int insertSelective(Hr record);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Hr selectByPrimaryKey(Integer id);

    /**
     * 更新hr
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Hr record);

    /**
     * 更新hr
     * @param record
     * @return
     */
    int updateByPrimaryKey(Hr record);

    /**
     * 根据hr名称查询对象
     * @param username
     * @return
     */
    Hr loadUserByUsername(String username);

    /**
     * 根据用户id获取所具备的角色
     * @param id
     * @return
     */
    List<Role> getRolesById(Integer id);

    /**
     * 返回除了当前用户的所有hr用户
     * @param keywords
     * @param id
     * @return
     */
    List<Hr> getHrListById(@Param("keywords") String keywords,@Param("id") Integer id);

    /**
     * 获取hr列表
     * @param id
     * @return
     */
    List<Hr> getUserListById(@Param("id") Integer id);

    /**
     * 修改密码
     * @param id
     * @param encodePass
     * @return
     */
    Integer updatePasswd(@Param("id") Integer id, String encodePass);

    /**
     * 修改头像
     * @param id
     * @param url
     * @return
     */
    Integer updateUserFace(@Param("id") Integer id, String url);
}