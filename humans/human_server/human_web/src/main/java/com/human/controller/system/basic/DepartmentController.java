package com.human.controller.system.basic;

import com.human.pojo.Department;
import com.human.pojo.RespBean;
import com.human.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/3 9:40
 */
@Api(tags = "部门信息")
@RestController
@RequestMapping("/system/basic/depart")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /**
     * 获取所有部门信息
     * @return
     */
    @ApiOperation("获取所有部门信息")
    @GetMapping("/")
    public List<Department> getDepartmentList() {
        return departmentService.getDepartmentList();
    }

    /**
     * 添加一个部门
     * @param department
     * @return
     */
    @ApiOperation("添加一个部门")
    @PostMapping("/")
    public RespBean addDepartment(@RequestBody Department department) {
        departmentService.addDepartment(department);

        if(department.getResult() == 1) {
            return RespBean.ok("添加成功！",department);
        }
        return RespBean.error("添加失败！");
    }

    /**
     * 删除一个部门
     * @param id
     * @return
     */
    @ApiOperation("删除一个部门")
    @DeleteMapping("/{id}")
    public RespBean deleteDepartment(@PathVariable Integer id) {
        Department department = new Department();
        department.setId(id);
        departmentService.deleteDepartment(department);

        if(department.getResult() == -2) {
            return RespBean.error("删除失败！此部门含子部门……");
        } else if(department.getResult() == -1) {
            return RespBean.error("删除失败！此部门含有员工……");
        } else if(department.getResult() == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
