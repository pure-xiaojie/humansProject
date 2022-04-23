package com.human.mapper.job;

import com.human.pojo.job.Job;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/3/28 10:11
 * 职位信息持久层
 */
@Repository
public interface JobMapper {
    /**
     * 获取职位数量
     * @param job
     * @param beginDateScope
     * @return
     */
    long getJobTotal(@Param("job") Job job, Date[] beginDateScope);

    /**
     * 获取职位列表
     * @param page
     * @param size
     * @param job
     * @param beginDateScope
     * @return
     */
    List<Job> getJobPageList(@Param("page") Integer page,
                                  @Param("size") Integer size,
                                  @Param("job") Job job,
                                  Date[] beginDateScope);
    /**
     * 添加职位
     * @param job
     * @return
     */
    int addJob(Job job);

    /**
     * 删除
     * @param id
     * @return
     */
    int delJobById(@Param("id") Integer id);

    /**
     * 更新
     * @param job
     * @return
     */
    int updateJob(Job job);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Job getJobById(@Param("id") Integer id);
}
