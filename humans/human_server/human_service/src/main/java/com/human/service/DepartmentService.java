package com.human.service;

import com.human.mapper.DepartmentMapper;
import com.human.pojo.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/3 9:42
 */
@Service
public class DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 根据父节点id，获取所有部门信息
     * @return
     */
    public List<Department> getDepartmentList() {
        return departmentMapper.getDepartmentListById(-1);
    }

    /**
     * 添加部门
     * @param department
     */
    public void addDepartment(Department department) {
        department.setEnabled(true);
        departmentMapper.addDepartment(department);
    }

    /**
     * 删除部门
     * @param department
     */
    public void deleteDepartment(Department department) {
        departmentMapper.deleteDepartmentById(department);
    }

    /**
     * 获取所有部门
     * @return
     */
    public List<Department> getAllDepartment() {
        return departmentMapper.getAllDepartment();
    }
}
