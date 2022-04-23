package com.human.mapper;

import com.human.pojo.Department;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/3 9:42
 */
@Repository
public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    /**
     * 获取所有部门信息
     * @param pid
     * @return
     */
    List<Department> getDepartmentListById(@Param("pid") Integer pid);

    /**
     * 添加部门
     * @param department
     */
    void addDepartment(Department department);

    /**
     * 删除部门
     * @param department
     */
    void deleteDepartmentById(Department department);

    /**
     * 获取所有部门
     * @return
     */
    List<Department> getAllDepartment();
}