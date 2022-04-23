package com.human.service.salary;

import com.human.mapper.salary.SalaryTableMapper;
import com.human.pojo.RespPageBean;
import com.human.pojo.emp.Employee;
import com.human.pojo.salary.SalaryTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/4/15 20:54
 * 月工资
 */
@Service
public class SalaryMonthService {
    @Autowired
    private SalaryTableMapper salaryTableMapper;

    /**
     * 按月查询工资表
     * @param page
     * @param size
     * @param month
     * @return
     */
    public RespPageBean getSalaryTablePageByMonth(Integer page, Integer size, Integer month) {
        //计算起始页
        if(page != null && size != null) {
            page = (page-1)*size;
        }

        //获取总条数和职工列表
        long total = salaryTableMapper.getSalaryTableTotal(month);
        List<SalaryTable> salaryTables = salaryTableMapper.getSalaryTablePageByMonth(page, size, month);

        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(salaryTables);
        respPageBean.setTotal(total);
        return respPageBean;
    }

    /**
     * 员工查询工资
     * @param id
     * @return
     */
    public List<SalaryTable> getSalaryByEmpId(Integer id) {
        return salaryTableMapper.getSalaryByEmpId(id);
    }
}
