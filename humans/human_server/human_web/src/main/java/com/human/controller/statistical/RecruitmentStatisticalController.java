package com.human.controller.statistical;

import com.human.pojo.statistics.StatisticalKeyValue;
import com.human.pojo.statistics.StatisticalVisit;
import com.human.service.statistics.RecruitmentStatisticalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/4/7 20:58
 */
@Api(tags = "招聘信息统计")
@RestController
@RequestMapping("/statistics/job")
public class RecruitmentStatisticalController {
    @Autowired
    private RecruitmentStatisticalService recruitmentStatisticalService;

    /**
     * 简历投递时间分布
     * @return
     */
    @ApiOperation("简历投递时间分布")
    @GetMapping("/resume")
    public List<StatisticalKeyValue> getResumeStatistical(){
        return recruitmentStatisticalService.getResumeStatistical();
    }

    /**
     * 薪资分布
     * @return
     */
    @ApiOperation("职位薪资分布")
    @GetMapping("/salary")
    public List<StatisticalKeyValue> getSalaryStatistical(){
        return recruitmentStatisticalService.getSalaryStatistical();
    }

    /**
     * 简历投递状态统计
     * @return
     */
    @ApiOperation("简历投递状态统计")
    @GetMapping("/status")
    public List<StatisticalKeyValue> getResumeStatusStatistical(){
        return recruitmentStatisticalService.getResumeStatusStatistical();
    }

    /**
     * 获取职位访问量与投递比率
     * @return
     */
    @ApiOperation("获取职位访问量与投递比率")
    @GetMapping("/visit")
    public List<StatisticalVisit> getJobVisitStatistical(){
        return recruitmentStatisticalService.getJobVisitStatistical();
    }
}
