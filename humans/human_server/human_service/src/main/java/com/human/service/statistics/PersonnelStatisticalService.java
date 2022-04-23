package com.human.service.statistics;

import com.human.mapper.statistics.PersonnelMapper;
import com.human.pojo.statistics.StatisticalKeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/3/20 13:26
 * 人事统计服务层
 */
@Service
public class PersonnelStatisticalService {
    @Autowired
    private PersonnelMapper personnelMapper;

    /**
     * 获取员工地区分布统计
     * @return
     */
    public List<StatisticalKeyValue> getAreaStatistical() {
        return personnelMapper.getAreaStatistical();
    }

    /**
     * 岗位分布
     * @return
     */
    public List<StatisticalKeyValue> getPositionStatistical() {
        return personnelMapper.getPositionStatistical();
    }

    /**
     * 专业分布
     * @return
     */
    public List<StatisticalKeyValue> getProfessionalStatistical() {
        return personnelMapper.getProfessionalStatistical();
    }

    /**
     * 地区前10
     * @return
     */
    public List<StatisticalKeyValue> getAreaTopStatistical() {
        return  personnelMapper.getAreaTopStatistical();
    }
}
