package com.human.service.statistics;

import com.human.mapper.statistics.EmpMapper;
import com.human.pojo.statistics.StatisticalKeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/4/5 17:47
 */
@Service
public class EmpStatisticalService {
    @Autowired
    private EmpMapper empMapper;

    /**
     * 职工入职年份统计
     * @return
     */
    public List<StatisticalKeyValue> getEmpBeginDateStatistical(){
        return empMapper.getEmpBeginDateStatistical();
    }

    /**
     * 职工专业词云统计
     * @return
     */
    public List<StatisticalKeyValue> getEmpProfessionalStatistical(){
        return empMapper.getEmpProfessionalStatistical();
    }

    /**
     * 职工入性别统计
     * @return
     */
    public List<StatisticalKeyValue> getEmpGenderStatistical(){
        return empMapper.getEmpGenderStatistical();
    }

    /**
     * 职工学历统计
     * @return
     */
    public List<StatisticalKeyValue> getEmpEducationStatistical(){
        return empMapper.getEmpEducationStatistical();
    }

    /**
     * 政治面貌统计
     * @return
     */
    public List<StatisticalKeyValue> getEmpPoliticsStatistical() {
        return empMapper.getEmpPoliticsStatistical();
    }

    /**
     * 民族统计
     * @return
     */
    public List<StatisticalKeyValue> getEmpNationStatistical() {
        return empMapper.getEmpNationStatistical();
    }
}
