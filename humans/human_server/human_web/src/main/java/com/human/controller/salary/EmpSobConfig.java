package com.human.controller.salary;

import com.human.pojo.RespBean;
import com.human.pojo.RespPageBean;
import com.human.pojo.salary.Salary;
import com.human.pojo.salary.SalaryTable;
import com.human.service.emp.EmployeeService;
import com.human.service.salary.SalaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/11 10:05
 * 员工账套设置 表现层
 */
@Api(tags = "员工账套")
@Slf4j
@RestController
@RequestMapping("/salary/sobcfg")
public class EmpSobConfig {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    SalaryService salaryService;

    /**
     * 分页获取员工账套
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("分页获取员工账套")
    @GetMapping("/")
    public RespPageBean getEmployeePageWithSalary(@DefaultValue("1") Integer page,
                                                  @DefaultValue("10") Integer size,
                                                  String name) {
        return employeeService.getEmployeePageWithSalary(page, size, name);
    }

    /**
     * 分页获取员工工资
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("分页获取员工工资")
    @GetMapping("/emp")
    public RespPageBean getEmployeePageWithSalaryDetail(@DefaultValue("1") Integer page,
                                                  @DefaultValue("10") Integer size,
                                                  String name) {
        return employeeService.getEmployeePageWithSalaryDetail(page, size, name);
    }

    /**
     * 更新职工账套
     * @param eid
     * @param sid
     * @return
     */
    @ApiOperation("更新职工账套")
    @PutMapping("/")
    public RespBean updateEmployeeSalaryById(Integer eid,Integer sid) {
        if(employeeService.updateEmployeeSalaryById(eid,sid) >= 1) {
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
    /**
     * 获取账套列表
     * @return
     */
    @ApiOperation("获取账套列表")
    @GetMapping("/salary")
    public List<Salary> getSalaryList() {
        return salaryService.getSalaryList();
    }

    /**
     * 根据月份获取薪资
     * @return
     */
    @ApiOperation("根据月份获取薪资")
    @GetMapping("/month/{month}")
    public List<SalaryTable> getSalaryByMonth(@PathVariable String month) {
        log.info("get month:" + month);
        return salaryService.getSalaryByMonth(month);
    }

    /**
     * 给所有员工发月工资
     * @return
     */
    @ApiOperation("给所有员工发月工资")
    @GetMapping("/month")
    public RespBean PostSalaryByMonth(Integer month) {
        log.info("post month:" + month);
        return salaryService.PostSalaryByMonth(month);
    }
}
