package com.human.mapper.salary;

import com.human.pojo.salary.SalaryTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/4/14 22:38
 * 薪资表
 */
@Repository
public interface SalaryTableMapper {
    /**
     * 发放工资
     * @param salaryTables
     * @return
     */
    int payEmpSalary(List<SalaryTable> salaryTables);

    /**
     * 获取月份工资总数量
     * @param month
     * @return
     */
    long getSalaryTableTotal(@Param("month") Integer month);

    /**
     * 按月份分页获取
     * @param page
     * @param size
     * @param month
     * @return
     */
    List<SalaryTable> getSalaryTablePageByMonth(Integer page, Integer size, Integer month);

    /**
     * 员工查询工资
     * @param id
     * @return
     */
    List<SalaryTable> getSalaryByEmpId(@Param("id") Integer id);
}
