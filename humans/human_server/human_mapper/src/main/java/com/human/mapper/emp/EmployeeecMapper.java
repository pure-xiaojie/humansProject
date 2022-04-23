package com.human.mapper.emp;

import com.human.pojo.emp.Employeeec;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/2/26 16:12
 */
@Repository
public interface EmployeeecMapper {
    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增职工奖惩
     * @param record
     * @return
     */
    int insert(Employeeec record);

    /**
     * 新增职工奖惩
     * @param record
     * @return
     */
    int insertSelective(Employeeec record);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    Employeeec selectByPrimaryKey(Integer id);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Employeeec record);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(Employeeec record);

    /**
     * 获取奖惩总记录数
     * @return
     */
    long getTotal();

    /**
     * 分页查询奖惩记录
     * @param page
     * @param size
     * @return
     */
    List<Employeeec> selectByPageList(@Param("page") Integer page, @Param("size") Integer size);
}