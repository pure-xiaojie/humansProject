package com.human.controller.job;

import com.human.pojo.RespBean;
import com.human.pojo.RespPageBean;
import com.human.pojo.job.Job;
import com.human.service.job.JobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author Li JieGui
 * @date 2021/3/28 9:47
 * 招聘信息
 */
@Api(tags = "招聘信息")
@RestController
@RequestMapping("/recurit/position")
public class JobController {
    @Autowired
    private JobService jobService;

    /**
     * 分页查询员工信息，根据搜索字段模糊查询
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("分页查询员工信息，可根据搜索字段模糊查询")
    @GetMapping("/")
    public RespPageBean getJobPageList(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer size,
                                       Job job, Date[] beginDateScope) {
        return jobService.getJobPageList(page,size,job,beginDateScope);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @ApiOperation("根据id查询职位详细信息")
    @GetMapping("/{id}")
    public Job getJobById(@PathVariable Integer id) {
        return jobService.getJobById(id);
    }

    /**
     * 添加职位
     * @param job
     * @return
     */
    @ApiOperation("添加职位信息")
    @PostMapping("/")
    public RespBean addJob(@RequestBody Job job) {
        if(jobService.addJob(job) == 1) {
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    /**
     * 删除职位
     * @param id
     * @return
     */
    @ApiOperation("删除职位信息")
    @DeleteMapping("/{id}")
    public RespBean delJobById(@PathVariable Integer id) {
        if(jobService.delJobById(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    /**
     * 更新职位
     * @param job
     * @return
     */
    @ApiOperation("修改职位信息")
    @PutMapping("/")
    public RespBean updateJob(@RequestBody Job job) {
        if(jobService.updateJob(job) == 1) {
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败!");
    }

    /**
     * 职位数量
     * @return
     */
    @ApiOperation("获取职位数量")
    @GetMapping("/count")
    public RespBean getJobCount() {
        return jobService.getJobCount();
    }

}
