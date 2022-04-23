package com.human.controller.statistical;

import com.human.pojo.statistics.StatisticalKeyValue;
import com.human.service.statistics.PersonnelStatisticalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/3/20 13:10
 * 人事统计
 */
@Api(tags = "人事信息统计")
@RestController
@RequestMapping("/statistics/personnel")
public class PersonnelStatisticalController {
    @Autowired
    private PersonnelStatisticalService personnelStatisticalService;

    /**
     * 员工地区分布统计
     * @return
     */
    @ApiOperation("员工地区分布统计")
    @GetMapping("/area")
    public List<StatisticalKeyValue> getAreaStatistical() {
        return personnelStatisticalService.getAreaStatistical();
    }

    /**
     * 员工岗位分布统计
     * @return
     */
    @ApiOperation("员工岗位分布统计")
    @GetMapping("/position")
    public List<StatisticalKeyValue> getPositionStatistical() {
        return personnelStatisticalService.getPositionStatistical();
    }

    /**
     * 员工岗位分布统计
     * @return
     */
    @ApiOperation("员工岗位分布统计")
    @GetMapping("/professional")
    public List<StatisticalKeyValue> getProfessionalStatistical() {
        return personnelStatisticalService.getProfessionalStatistical();
    }
    /**
     * 地区前10
     * @return
     */
    @ApiOperation("员工所在地区数量前10")
    @GetMapping("/top10")
    public List<StatisticalKeyValue> getAreaTopStatistical() {
        return personnelStatisticalService.getAreaTopStatistical();
    }
}
