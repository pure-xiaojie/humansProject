package com.human.service.statistics;

import com.human.mapper.statistics.RecruitmentMapper;
import com.human.pojo.statistics.StatisticalKeyValue;
import com.human.pojo.statistics.StatisticalVisit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/4/7 20:41
 * 招聘统计
 */
@Service
public class RecruitmentStatisticalService {
    @Autowired
    private RecruitmentMapper recruitmentMapper;

    /**
     * 简历投递时间分布
     * @return
     */
    public List<StatisticalKeyValue> getResumeStatistical(){
        List<StatisticalKeyValue> resumeStatistical = recruitmentMapper.getResumeStatistical();
        List<StatisticalKeyValue> res = new ArrayList<>(13);
        for(int i = 0; i < 12; i++) {
            StatisticalKeyValue item = new StatisticalKeyValue();
            item.setName(i + "月");
            item.setValue(0);
            res.add(item);
        }
        resumeStatistical.forEach(v->{
            String name = v.getName();
            int value = v.getValue();

            int i = Integer.parseInt(name);
            StatisticalKeyValue statisticalKeyValue = res.get(i);
            statisticalKeyValue.setValue(value);
            res.set(i, statisticalKeyValue);
        });
        return res;
    }

    /**
     * 薪资分布
     * @return
     */
    public List<StatisticalKeyValue> getSalaryStatistical(){
        return recruitmentMapper.getSalaryStatistical();
    }

    /**
     * 简历投递状态统计
     * @return
     */
    public List<StatisticalKeyValue> getResumeStatusStatistical(){
        List<StatisticalKeyValue> resumeStatusStatistical = recruitmentMapper.getResumeStatusStatistical();

        resumeStatusStatistical.forEach(item->{
            String name = item.getName();
            int i = Integer.parseInt(name);

            switch (i) {
                case 0: item.setName("投递");break;
                case 1: item.setName("简历筛选");break;
                case 2: item.setName("人才库");break;
                case 3: item.setName("笔试");break;
                case 4: item.setName("面试");break;
                case 5: item.setName("offer");break;
            }
        });
        return resumeStatusStatistical;
    }

    /**
     * 获取职位访问量与投递比率
     * @return
     */
    public List<StatisticalVisit> getJobVisitStatistical(){
        return recruitmentMapper.getJobVisitStatistical();
    }
}
