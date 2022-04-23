package com.human.mapper.emp;

import com.human.pojo.emp.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/4 16:23
 */
@Repository
public interface EmployeeMapper {
    /**
     * 根据id删除职工
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 添加职工
     * @param record
     * @return
     */
    int insert(Employee record);

    /**
     * 插入一个新职工
     * @param record
     * @return
     */
    int insertSelective(Employee record);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Employee selectByPrimaryKey(Integer id);

    /**
     * 根据id查询Password
     * @param id
     * @return
     */
    Employee selectByPassword(Integer id);

    /**
     * 更新职工信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Employee record);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(Employee record);

    /**
     * 获取职工总记录数
     * @param employee 搜索关键字
     * @param beginDateScope 入职时间范围
     * @return
     */
    long getEmployeeTotal(@Param("emp") Employee employee, Date[] beginDateScope);

    /**
     * 查询职工数据
     * @param page  页码
     * @param size  条数
     * @param employee  搜索职工关键字
     * @param beginDateScope 入职时间范围
     * @return
     */
    List<Employee> getEmployeePageList(@Param("page") Integer page, @Param("size")Integer size,
                  @Param("emp") Employee employee,Date[] beginDateScope);

    /**
     * 获取最大工号
     * @return
     */
    Integer getMaxWorkId();

    /**
     * 插入职工数据
     * @param employeeList
     * @return
     */
    Integer insertEmployeeList(@Param("employeeList") List<Employee> employeeList);

    /**
     * 根据id查询职工
     * @param id
     * @return
     */
    Employee getEmployeeById(@Param("id") Integer id);

    /**
     * 分页获取员工账套信息
     * @param page
     * @param size
     * @param name
     * @return
     */
    List<Employee> getEmployeePageWithSalary(@Param("page") Integer page,
                                             @Param("size") Integer size,
                                             @Param("name") String name);

    /**
     * 更新职工账套
     * @param eid
     * @param sid
     * @return
     */
    Integer updateEmployeeSalaryById(@Param("eid") Integer eid, @Param("sid") Integer sid);

    /**
     * 根据用户名密码查询
     * @param phone
     * @param password
     * @return
     */
    Employee getEmployeeByPhoneAndPassword(@Param("phone") String phone
            , @Param("password") String password);
}