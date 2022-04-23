package com.human.mapper.statistics;

import com.human.pojo.statistics.StatisticalKeyValue;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/3/20 13:30
 * 人事统计持久层
 */
@Repository
public interface PersonnelMapper {
    /**
     * 员工地区分布统计
     * @return
     */
    List<StatisticalKeyValue> getAreaStatistical();

    /**
     * 岗位分布
     * @return
     */
    List<StatisticalKeyValue> getPositionStatistical();

    /**
     * 专业分布
     * @return
     */
    List<StatisticalKeyValue> getProfessionalStatistical();

    /**
     * 地区前10
     * @return
     */
    List<StatisticalKeyValue> getAreaTopStatistical();
}
