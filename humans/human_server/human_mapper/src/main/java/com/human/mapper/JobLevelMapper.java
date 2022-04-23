package com.human.mapper;

import com.human.pojo.JobLevel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/2 09:35
 * 职称持久层
 */
@Repository
public interface JobLevelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JobLevel record);

    int insertSelective(JobLevel record);

    JobLevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JobLevel record);

    int updateByPrimaryKey(JobLevel record);

    /**
     * 获取职称列表
     * @return
     */
    List<JobLevel> getJobLevelList();

    /**
     * 根据ids批量删除职称
     * @param ids
     * @return
     */
    int deleteJobLevelByIds(@Param("ids") Integer[] ids);
}