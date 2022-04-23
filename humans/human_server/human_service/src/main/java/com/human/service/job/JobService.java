package com.human.service.job;

import com.human.mapper.job.JobMapper;
import com.human.pojo.RespBean;
import com.human.pojo.RespPageBean;
import com.human.pojo.job.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/3/28 9:55
 */
@Service
public class JobService {
    @Autowired
    private JobMapper jobMapper;

    /**
     * 职位搜索
     * @param page 页码
     * @param size 每页条数
     * @param job 查询对象
     * @param beginDateScope 日期
     * @return
     */
    public RespPageBean getJobPageList(Integer page, Integer size, Job job, Date[] beginDateScope) {
        //计算起始页
        if(page != null && size != null) {
            page = (page-1)*size;
        }
        //获取总条数和职工列表
        long total = jobMapper.getJobTotal(job, beginDateScope);
        List<Job> jobs = jobMapper.getJobPageList(page, size, job, beginDateScope);

        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(jobs);
        respPageBean.setTotal(total);
        return respPageBean;
    }

    /**
     * 发布职位信息
     * @param job
     * @return
     */
    public int addJob(Job job) {
        //设置发布时间
        job.setP_createDate(new Date());
        return jobMapper.addJob(job);
    }

    /**
     * 删除职位信息
     * @param id
     * @return
     */
    public int delJobById(Integer id) {
        return jobMapper.delJobById(id);
    }

    /**
     * 修改职位信息
     * @param job
     * @return
     */
    public int updateJob(Job job) {
        //设置更新时间
        job.setP_createDate(new Date());
        return jobMapper.updateJob(job);
    }

    /**
     * 根据id查询职位
     * @param id
     * @return
     */
    public Job getJobById(Integer id) {
        //访问量+1
        Job job = new Job();
        job.setId(id);
        job.setVisit(1);
        jobMapper.updateJob(job);
        return jobMapper.getJobById(id);
    }

    /**
     * 在职职位
     * @return
     */
    public RespBean getJobCount() {
        long jobTotal = jobMapper.getJobTotal(null, null);
        Job job = new Job();
        job.setStatus(1);
        long useTotal = jobMapper.getJobTotal(job, null);

        List<Long> res = new ArrayList<>();
        res.add(jobTotal);
        res.add(useTotal);

        return RespBean.build().setStatus(200).setObj(res);
    }
}
