package com.human.service.salary;

import com.human.mapper.emp.EmployeeMapper;
import com.human.mapper.salary.SalaryMapper;
import com.human.mapper.salary.SalaryTableMapper;
import com.human.pojo.RespBean;
import com.human.pojo.RespPageBean;
import com.human.pojo.emp.Employee;
import com.human.pojo.salary.Salary;
import com.human.pojo.salary.SalaryTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/10 19:31
 * 工资服务层
 */
@Service
@Slf4j
public class SalaryService {
    @Autowired
    SalaryMapper salaryMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    SalaryTableMapper salaryTableMapper;

    /**
     * 分页获取工资账套信息
     * @param page
     * @param size
     * @return
     */
    public RespPageBean getSalaryPageList(Integer page, Integer size) {
        //计算起始页
        if(page != null && size != null) {
            page = (page-1)*size;
        }

        long total = salaryMapper.getSalaryTotal();
        List<Salary> salaryList = salaryMapper.getSalaryPageList(page, size);

        RespPageBean pageBean = new RespPageBean();
        pageBean.setTotal(total);
        pageBean.setData(salaryList);
        return pageBean;
    }

    /**
     * 添加薪资账套
     * @param salary
     * @return
     */
    public Integer addSalary(Salary salary) {
        salary.setCreateDate(new Date());
        return salaryMapper.insertSelective(salary);
    }

    /**
     * 修改薪资账套
     * @param salary
     * @return
     */
    public Integer updateSalary(Salary salary) {
        return salaryMapper.updateByPrimaryKeySelective(salary);
    }

    /**
     * 根据id删除工作账套
     * @param id
     * @return
     */
    public Integer deleteSalaryById(Integer id) {
        return salaryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取工资账套列表
     * @return
     */
    public List<Salary> getSalaryList() {
        return salaryMapper.getSalaryPageList(null,null);
    }

    /**
     * 根据月份获取发放的工资
     * @param month
     * @return
     */
    public List<SalaryTable> getSalaryByMonth(String month) {
        return new ArrayList<>();
    }

    /**
     * 给所有员工发放工资
     * @param month
     * @return
     */
    public RespBean PostSalaryByMonth(Integer month) {
        List<Employee> employees = employeeMapper.getEmployeePageWithSalary(null, null, null);

        //提取工资表
        List<SalaryTable> salaryTables = SalaryTable.getSalaryList(employees, month);

        log.info("salaryTables.size()" + String.valueOf(salaryTables.size()));
        log.info(String.valueOf(salaryTables.get(0)));

        if(salaryTableMapper.payEmpSalary(salaryTables) == salaryTables.size()) {
            return RespBean.ok("发放成功！");
        }
        return RespBean.ok("发放失败！");
    }
}
