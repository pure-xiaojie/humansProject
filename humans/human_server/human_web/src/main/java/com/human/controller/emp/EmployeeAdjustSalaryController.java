package com.human.controller.emp;

import com.human.pojo.salary.AdjustSalary;
import com.human.pojo.RespBean;
import com.human.pojo.RespPageBean;
import com.human.service.salary.AdjustSalaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Li JieGui
 * @date 2021/3/14 14:37
 * 员工调薪控制层
 */
@Api(tags = "员工调薪")
@RestController
@RequestMapping("/personnel/salary")
public class EmployeeAdjustSalaryController {
    @Autowired
    private AdjustSalaryService adjustSalaryService;
    
    /**
     * 分页获取员工调薪信息
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("分页获取调薪")
    @GetMapping("/")
    public RespPageBean getAdjustSalaryPageList(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size) {
        return adjustSalaryService.getAdjustSalaryPageList(page,size);
    }

    /**
     * 添加职工调薪信息
     * @param adjustSalary
     * @return
     */
    @ApiOperation("为职工调薪")
    @PostMapping("/")
    public RespBean addAdjustSalary(@RequestBody AdjustSalary adjustSalary) {
        if(adjustSalaryService.addAdjustSalary(adjustSalary) == 1) {
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    /**
     * 删除职工调薪
     * @param id
     * @return
     */
    @ApiOperation("删除调薪")
    @DeleteMapping("/{id}")
    public RespBean deleteAdjustSalary(@PathVariable Integer id){
        if(adjustSalaryService.deleteAdjustSalary(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    /**
     * 更新调薪
     * @param adjustSalary
     * @return
     */
    @ApiOperation("更新调薪")
    @PutMapping("/")
    public RespBean updateAdjustSalary(@RequestBody AdjustSalary adjustSalary) {
        if(adjustSalaryService.updateAdjustSalary(adjustSalary) == 1) {
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
}
