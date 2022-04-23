package com.human.mapper.salary;

import com.human.pojo.salary.Salary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/10 19:37
 * 工资账套持久层
 */
@Repository
public interface SalaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Salary record);

    /**
     * 添加薪资账套
     * @param record
     * @return
     */
    int insertSelective(Salary record);

    Salary selectByPrimaryKey(Integer id);

    /**
     * 更新薪资套账
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Salary record);

    int updateByPrimaryKey(Salary record);

    /**
     * 获取薪资套账总记录数
     * @return
     */
    long getSalaryTotal();

    /**
     * 分页获取薪资套账信息
     * @param page
     * @param size
     * @return
     */
    List<Salary> getSalaryPageList(@Param("page") Integer page
            , @Param("size") Integer size);
}