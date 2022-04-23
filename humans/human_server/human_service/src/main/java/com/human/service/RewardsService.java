package com.human.service;

import com.human.mapper.emp.EmployeeecMapper;
import com.human.pojo.emp.Employeeec;
import com.human.pojo.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/2/26 16:12
 * 员工奖惩服务类
 */
@Service
public class RewardsService {
    @Autowired
    private EmployeeecMapper employeeecMapper;


    /**
     * 分页获取员工奖惩信息
     * @param page
     * @param size
     * @return
     */
    public RespPageBean getRewardsPageList(Integer page, Integer size) {
        //计算起始页
        if(page != null && size != null) {
            page = (page - 1) * size;
        }

        long total = employeeecMapper.getTotal();
        List<Employeeec> rewardsList = employeeecMapper.selectByPageList(page,size);

        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(rewardsList);
        respPageBean.setTotal(total);
        return respPageBean;
    }

    /**
     * 新增职工奖惩
     * @param employeeec
     * @return
     */
    public Integer addEmployeeRewards(Employeeec employeeec) {
        return employeeecMapper.insertSelective(employeeec);
    }

    /**
     * 删除奖惩
     * @param id
     * @return
     */
    public int deleteEmployeeec(Integer id) {
        return employeeecMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新奖惩
     * @param employeeec
     * @return
     */
    public int updateEmployeeec(Employeeec employeeec) {
        return employeeecMapper.updateByPrimaryKeySelective(employeeec);
    }
}
