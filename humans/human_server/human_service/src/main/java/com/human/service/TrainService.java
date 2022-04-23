package com.human.service;

import com.human.mapper.emp.EmployeetrainMapper;
import com.human.pojo.emp.Employeeec;
import com.human.pojo.emp.Employeetrain;
import com.human.pojo.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/3/7 10:43
 * 调薪服务层
 */
@Service
public class TrainService {
    @Autowired
    EmployeetrainMapper employeetrainMapper;

    /**
     * 分页获取培训列表
     * @param page
     * @param size
     * @return
     */
    public RespPageBean getTrainPageList(Integer page, Integer size) {
        //计算起始页
        if(page != null && size != null) {
            page = (page - 1) * size;
        }

        long total = employeetrainMapper.getTotal();
        List<Employeeec> rewardsList = employeetrainMapper.selectByPageList(page,size);

        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(rewardsList);
        respPageBean.setTotal(total);
        return respPageBean;
    }

    /**
     * 新增职工培训
     * @param employeetrain
     * @return
     */
    public int addTrain(Employeetrain employeetrain) {
        return employeetrainMapper.insertSelective(employeetrain);
    }

    /**
     * 删除职工培训
     * @param id
     * @return
     */
    public int deleteTrain(Integer id) {
        return employeetrainMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新职工培训
     * @param employeetrain
     * @return
     */
    public int updateTrain(Employeetrain employeetrain) {
        return employeetrainMapper.updateByPrimaryKeySelective(employeetrain);
    }
}
