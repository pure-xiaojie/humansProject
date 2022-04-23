package com.human.mapper.emp;

import com.human.pojo.emp.Employeeec;
import com.human.pojo.emp.Employeetrain;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/3/7 10:43
 */
@Repository
public interface EmployeetrainMapper {
    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入
     * @param record
     * @return
     */
    int insert(Employeetrain record);

    /**
     * 插入并判断
     * @param record
     * @return
     */
    int insertSelective(Employeetrain record);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Employeetrain selectByPrimaryKey(Integer id);

    /**
     * 更新并判断
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Employeetrain record);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(Employeetrain record);

    /**
     * 获取总数量
     * @return
     */
    long getTotal();

    /**
     * 分页获取培训列表
     * @param page
     * @param size
     * @return
     */
    List<Employeeec> selectByPageList(@Param("page") Integer page,
                                      @Param("size") Integer size);
}