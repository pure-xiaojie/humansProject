package com.human.mapper.statistics;

import com.human.pojo.statistics.StatisticalKeyValue;
import com.human.pojo.statistics.StatisticalVisit;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/4/5 22:22
 * 招聘分析
 */
@Repository
public interface RecruitmentMapper {
    /**
     * 简历投递时间分布
     * @return
     */
    List<StatisticalKeyValue> getResumeStatistical();

    /**
     * 薪资分布
     * @return
     */
    List<StatisticalKeyValue> getSalaryStatistical();

    /**
     * 简历投递状态统计
     * @return
     */
    List<StatisticalKeyValue> getResumeStatusStatistical();

    /**
     * 获取职位访问量与投递比率
     * @return
     */
    List<StatisticalVisit> getJobVisitStatistical();
}
