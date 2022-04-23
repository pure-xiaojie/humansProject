package com.human.mapper.salary;

import com.human.pojo.salary.AdjustSalary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/3/7 10:43
 * 调薪持久层
 */
@Repository
public interface AdjustSalaryMapper {
    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入
     * @param record
     * @return
     */
    int insert(AdjustSalary record);

    /**
     * 插入并判断
     * @param record
     * @return
     */
    int insertSelective(AdjustSalary record);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    AdjustSalary selectByPrimaryKey(Integer id);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(AdjustSalary record);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(AdjustSalary record);

    /**
     * 获取总数量
     * @return
     */
    long getTotal();

    /**
     * 分页获取
     * @param page
     * @param size
     * @return
     */
    List<AdjustSalary> selectByPageList(@Param("page") Integer page, @Param("size") Integer size);
}