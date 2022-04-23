package com.human.controller.salary;

import com.human.pojo.RespBean;
import com.human.pojo.RespPageBean;
import com.human.pojo.salary.Salary;
import com.human.service.salary.SalaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Li JieGui
 * @date 2020/12/10 19:14
 * 工资账套管理 表现层
 */
@Api(tags = "薪资账套")
@RestController
@RequestMapping("/salary/sob")
public class SalaryController {
    @Autowired
    SalaryService salaryService;

    /**
     * 分页获取工资账套信息
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("分页获取工资账套信息")
    @GetMapping("/")
    public RespPageBean getSalaryPageList(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10")Integer size) {
        return salaryService.getSalaryPageList(page, size);
    }


    /**
     * 添加工资账套
     * @param salary
     * @return
     */
    @ApiOperation("添加工资账套信息")
    @PostMapping("/")
    public RespBean addSalary(@RequestBody Salary salary) {
        if(salaryService.addSalary(salary) == 1) {
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    /**
     * 修改工资账套
     * @param salary
     * @return
     */
    @ApiOperation("修改工资账套信息")
    @PutMapping("/")
    public RespBean updateSalary(@RequestBody Salary salary) {
        if(salaryService.updateSalary(salary) == 1) {
            return RespBean.ok("修改成功！");
        }
        return RespBean.error("修改失败！");
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @ApiOperation("根据id删除账套")
    @DeleteMapping("/{id}")
    public RespBean deleteSalaryById(@PathVariable Integer id) {
        if(salaryService.deleteSalaryById(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除成功！");
    }
}
