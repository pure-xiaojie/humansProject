package com.human.service;

import com.human.mapper.JobLevelMapper;
import com.human.pojo.JobLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/2 9:22
 * 职称服务层
 */
@Service
public class JobLevelService {
    @Autowired
    private JobLevelMapper jobLevelMapper;

    /**
     * 获取职称列表
     * @return
     */
    public List<JobLevel> getJobLevelList() {
        return jobLevelMapper.getJobLevelList();
    }

    /**
     * 添加一个职称
     * @param jobLevel
     * @return
     */
    public int addJobLevel(JobLevel jobLevel) {
        return jobLevelMapper.insertSelective(jobLevel);
    }

    /**
     * 更新职称信息
     * @param jobLevel
     * @return
     */
    public int updateJobLevel(JobLevel jobLevel) {
        return jobLevelMapper.updateByPrimaryKeySelective(jobLevel);
    }

    /**
     * 根据id删除职称
     * @param id
     * @return
     */
    public int deleteJobLevelById(Integer id) {
        return jobLevelMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除职称信息
     * @param ids
     * @return
     */
    public int deleteJobLevelByIds(Integer[] ids) {
        return jobLevelMapper.deleteJobLevelByIds(ids);
    }
}
