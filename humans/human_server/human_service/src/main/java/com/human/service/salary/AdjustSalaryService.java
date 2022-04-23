package com.human.service.salary;

import com.human.mapper.salary.AdjustSalaryMapper;
import com.human.pojo.salary.AdjustSalary;
import com.human.pojo.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/3/14 15:04
 */
@Service
public class AdjustSalaryService {
    @Autowired
    private AdjustSalaryMapper adjustSalaryMapper;

    /**
     * 分页获取调薪表
     * @param page
     * @param size
     * @return
     */
    public RespPageBean getAdjustSalaryPageList(Integer page, Integer size) {
        if(page != null && size != null) {
            page = (page - 1) * size;
        }

        long total = adjustSalaryMapper.getTotal();
        List<AdjustSalary> adjustSalaryList = adjustSalaryMapper.selectByPageList(page,size);

        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(adjustSalaryList);
        respPageBean.setTotal(total);
        return respPageBean;
    }

    /**
     * 添加调薪
     * @param adjustSalary
     * @return
     */
    public int addAdjustSalary(AdjustSalary adjustSalary) {
        return adjustSalaryMapper.insertSelective(adjustSalary);
    }

    /**
     * 删除调薪
     * @param id
     * @return
     */
    public int deleteAdjustSalary(Integer id) {
        return adjustSalaryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改调薪
     * @param adjustSalary
     * @return
     */
    public int updateAdjustSalary(AdjustSalary adjustSalary) {
        return adjustSalaryMapper.updateByPrimaryKeySelective(adjustSalary);
    }
}
