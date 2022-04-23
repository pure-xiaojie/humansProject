package com.human.service.emp;

import com.human.mapper.emp.EmployeeremoveMapper;
import com.human.pojo.emp.Employeeremove;
import com.human.pojo.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/3/14 15:33
 * 员工调动服务层
 */
@Service
public class EmployeeRemoveService {
    @Autowired
    private EmployeeremoveMapper employeeremoveMapper;

    /**
     * 分页获取员工调动列表
     * @param page
     * @param size
     * @return
     */
    public RespPageBean getEmployeeRemovePageList(Integer page, Integer size) {
        if(page != null && size != null) {
            page = (page - 1) * size;
        }

        long total = employeeremoveMapper.getTotal();
        List<Employeeremove> employeeRemoveList = employeeremoveMapper.getPageList(page, size);

        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setTotal(total);
        respPageBean.setData(employeeRemoveList);
        return respPageBean;
    }

    /**
     * 调用员工
     * @param employeeremove
     * @return
     */
    public int addEmployeeRemove(Employeeremove employeeremove) {
        return employeeremoveMapper.insertSelective(employeeremove);
    }

    /**
     * 删除员工调动
     * @param id
     * @return
     */
    public int deleteEmployeeRemove(Integer id) {
        return employeeremoveMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新员工调动
     * @param employeeRemove
     * @return
     */
    public int updateEmployeeRemove(Employeeremove employeeRemove) {
        return employeeremoveMapper.updateByPrimaryKeySelective(employeeRemove);
    }
}
