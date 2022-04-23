package com.human.controller.emp;

import com.human.pojo.emp.Employeeremove;
import com.human.pojo.RespBean;
import com.human.pojo.RespPageBean;
import com.human.service.emp.EmployeeRemoveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Li JieGui
 * @date 2021/3/14 14:40
 * 员工调动控制层
 */
@Api(tags = "员工调动")
@RestController
@RequestMapping("/personnel/remove")
public class EmployeeRemoveController {
    @Autowired
    private EmployeeRemoveService employeeRemoveService;
    
    /**
     * 分页获取员工调动信息
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("分页获取调动信息")
    @GetMapping("/")
    public RespPageBean getEmployeeRemovePageList(@RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer size) {
        return employeeRemoveService.getEmployeeRemovePageList(page,size);
    }

    /**
     * 添加职工调动信息
     * @param employeeremove
     * @return
     */
    @ApiOperation("添加职工调动信息")
    @PostMapping("/")
    public RespBean addEmployeeRemove(@RequestBody Employeeremove employeeremove) {
        if(employeeRemoveService.addEmployeeRemove(employeeremove) == 1) {
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    /**
     * 删除职工调动
     * @param id
     * @return
     */
    @ApiOperation("删除职工调动")
    @DeleteMapping("/{id}")
    public RespBean deleteEmployeeRemove(@PathVariable Integer id){
        if(employeeRemoveService.deleteEmployeeRemove(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    /**
     * 更新员工调动
     * @param employeeRemove
     * @return
     */
    @ApiOperation("修改员工调动")
    @PutMapping("/")
    public RespBean updateEmployeeRemove(@RequestBody Employeeremove employeeRemove) {
        if(employeeRemoveService.updateEmployeeRemove(employeeRemove) == 1) {
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
}
