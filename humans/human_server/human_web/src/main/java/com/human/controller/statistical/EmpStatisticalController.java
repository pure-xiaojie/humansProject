package com.human.controller.statistical;

import com.human.pojo.statistics.StatisticalKeyValue;
import com.human.service.statistics.EmpStatisticalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/4/5 17:09
 * 员工信息统计
 */
@Api(tags = "员工信息统计")
@RestController
@RequestMapping("/statistics/emp")
public class EmpStatisticalController {
    @Autowired
    private EmpStatisticalService empStatisticalService;

    /**
     * 职工入职年份统计
     * @return
     */
    @ApiOperation("职工入职年份统计")
    @GetMapping("/begin")
    List<StatisticalKeyValue> getEmpBeginDateStatistical(){
        return empStatisticalService.getEmpBeginDateStatistical();
    }

    /**
     * 职工专业词云统计
     * @return
     */
    @ApiOperation("职工专业词云统计")
    @GetMapping("/word")
    List<StatisticalKeyValue> getEmpProfessionalStatistical(){
        return empStatisticalService.getEmpProfessionalStatistical();
    }

    /**
     * 职工入性别统计
     * @return
     */
    @ApiOperation("职工入性别统计")
    @GetMapping("/gender")
    List<StatisticalKeyValue> getEmpGenderStatistical(){
        return empStatisticalService.getEmpGenderStatistical();
    }

    /**
     * 职工学历统计
     * @return
     */
    @ApiOperation("职工学历统计")
    @GetMapping("/graduation")
    List<StatisticalKeyValue> getEmpEducationStatistical(){
        return empStatisticalService.getEmpEducationStatistical();
    }

    /**
     * 政治面貌统计
     * @return
     */
    @ApiOperation("职工政治面貌统计")
    @GetMapping("/politics")
    List<StatisticalKeyValue> getEmpPoliticsStatistical(){
        return empStatisticalService.getEmpPoliticsStatistical();
    }

    /**
     * 民族统计
     * @return
     */
    @ApiOperation("职工民族统计")
    @GetMapping("/nation")
    List<StatisticalKeyValue> getEmpNationStatistical(){
        return empStatisticalService.getEmpNationStatistical();
    }
}
