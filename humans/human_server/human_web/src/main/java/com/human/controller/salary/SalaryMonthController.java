package com.human.controller.salary;

import com.human.pojo.RespPageBean;
import com.human.pojo.salary.SalaryTable;
import com.human.service.salary.SalaryMonthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/4/15 20:51
 * 月工资查询
 */
@Api(tags = "月工资")
@RestController
@RequestMapping("/salaryMonth")
public class SalaryMonthController {
    @Autowired
    private SalaryMonthService salaryMonthService;

    /**
     * 分页获取员工工资
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("按月份分页获取员工工资")
    @GetMapping("/month")
    public RespPageBean getEmployeePageByMonth(@DefaultValue("1") Integer page,
                                               @DefaultValue("10") Integer size,
                                               @DefaultValue("1") Integer month) {
        return salaryMonthService.getSalaryTablePageByMonth(page, size, month);
    }

    @ApiOperation("员工查询工资")
    @GetMapping("/{id}")
    public List<SalaryTable> getSalaryByEmpId(@PathVariable Integer id) {
        return salaryMonthService.getSalaryByEmpId(id);
    }
}
