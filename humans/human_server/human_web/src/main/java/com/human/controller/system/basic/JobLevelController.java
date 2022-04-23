package com.human.controller.system.basic;

import com.human.pojo.JobLevel;
import com.human.pojo.RespBean;
import com.human.service.JobLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/2 9:18
 * 职称控制层
 */
@Api(tags = "职称信息")
@RestController
@RequestMapping("/system/basic/job")
public class JobLevelController {
    @Autowired
    private JobLevelService jobLevelService;
    
    /**
     * 获取职称列表
     * @return
     */
    @ApiOperation("获取职称列表")
    @GetMapping("/")
    public List<JobLevel> getJobLevelList() {
        return jobLevelService.getJobLevelList();
    }

    /**
     * 添加一个职称
     * @param position
     * @return
     */
    @ApiOperation("添加一个职称")
    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody JobLevel position) {
        if (jobLevelService.addJobLevel(position) == 1) {
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    /**
     * 更新职称
     * @param position
     * @return
     */
    @ApiOperation("更新职称")
    @PutMapping("/")
    public RespBean updateJobLevel(@RequestBody JobLevel position) {
        if (jobLevelService.updateJobLevel(position) == 1) {
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    /**
     * 根据id删除职称
     * @param id
     * @return
     */
    @ApiOperation("根据id删除职称")
    @DeleteMapping("/{id}")
    public RespBean deleteJobLevelById(@PathVariable Integer id) {
        if (jobLevelService.deleteJobLevelById(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    /**
     * 批量删除职称
     * @param ids
     * @return
     */
    @ApiOperation("批量删除职称")
    @DeleteMapping("/")
    public RespBean deleteJobLevelByIds(Integer[] ids) {
        if (jobLevelService.deleteJobLevelByIds(ids) == ids.length) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
