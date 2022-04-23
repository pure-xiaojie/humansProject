package com.human.mapper.statistics;

import com.human.pojo.statistics.StatisticalKeyValue;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/4/5 17:35
 * 人事信息统计
 */
@Repository
public interface EmpMapper {
    /**
     * 职工入职年份统计
     * @return
     */
    List<StatisticalKeyValue> getEmpBeginDateStatistical();

    /**
     * 职工专业词云统计
     * @return
     */
    List<StatisticalKeyValue> getEmpProfessionalStatistical();

    /**
     * 职工入性别统计
     * @return
     */
    List<StatisticalKeyValue> getEmpGenderStatistical();

    /**
     * 职工学历统计
     * @return
     */
    List<StatisticalKeyValue> getEmpEducationStatistical();

    /**
     * 政治面貌统计
     * @return
     */
    List<StatisticalKeyValue> getEmpPoliticsStatistical();

    /**
     * 民族统计
     * @return
     */
    List<StatisticalKeyValue> getEmpNationStatistical();
}
