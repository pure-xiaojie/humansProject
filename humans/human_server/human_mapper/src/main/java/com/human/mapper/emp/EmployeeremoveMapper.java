package com.human.mapper.emp;

import com.human.pojo.emp.Employeeremove;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/3/14 15:33
 * 员工调动持久层
 */
@Repository
public interface EmployeeremoveMapper {
    /**
     * 删除员工调动
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增
     * @param record
     * @return
     */
    int insert(Employeeremove record);

    /**
     * 新增并对字段判断
     * @param record
     * @return
     */
    int insertSelective(Employeeremove record);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    Employeeremove selectByPrimaryKey(Integer id);

    /**
     * 更新并判断字段
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Employeeremove record);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(Employeeremove record);

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
    List<Employeeremove> getPageList(@Param("page") Integer page, @Param("size") Integer size);
}