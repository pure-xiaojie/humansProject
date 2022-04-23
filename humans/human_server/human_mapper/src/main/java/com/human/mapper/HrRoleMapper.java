package com.human.mapper;

import com.human.pojo.HrRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Li JieGui
 * @date 2020/12/04 11:21
 */
@Repository
public interface HrRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HrRole record);

    int insertSelective(HrRole record);

    HrRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HrRole record);

    int updateByPrimaryKey(HrRole record);

    /**
     * 根据hr id 删除用户角色
     * @param hrid
     */
    void deleteByHrid(@Param("hrid") Integer hrid);

    /**
     * 给hrid 关联新角色列表
     * @param hrid
     * @param rids
     * @return
     */
    int insertHrRole(@Param("hrid") Integer hrid,@Param("rids") Integer[] rids);
}