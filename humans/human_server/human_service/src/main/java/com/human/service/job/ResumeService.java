package com.human.service.job;

import com.human.mapper.job.JobMapper;
import com.human.mapper.job.ResumeMapper;
import com.human.pojo.RespBean;
import com.human.pojo.RespPageBean;
import com.human.pojo.job.Job;
import com.human.pojo.job.Resume;
import com.human.pojo.mail.MailStatus;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Li JieGui
 * @date 2021/3/31 22:45
 * 简历提交服务层
 */
@Service
public class ResumeService {
    @Autowired
    private ResumeMapper resumeMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private JobMapper jobMapper;

    /**
     * 简历投递
     * @param resume
     * @return
     */
    public int addResume(Resume resume) {
        resume.setCreateDate(new Date());
        int res = resumeMapper.addResume(resume);

        Job job = jobMapper.getJobById(resume.getPositionId());
        resume.setJob(job);
        //简历投递，邮件通知
        if(res == 1) {
            String msgId = UUID.randomUUID().toString();
            rabbitTemplate.convertAndSend(MailStatus.MAIL_EXCHANGE_RESUME,
                    MailStatus.MAIL_ROUTING_KEY_RESUME, resume,
                    new CorrelationData(msgId));
        }
        return res;
    }

    /**
     *分页查询投递信息，可根据姓名，投递日期模糊查询
     * @param page
     * @param size
     * @param resume
     * @param beginDateScope
     * @return
     */
    public RespPageBean getResumePageList(Integer page, Integer size, Resume resume, Date[] beginDateScope) {
        //计算起始页
        if(page != null && size != null) {
            page = (page-1)*size;
        }
        //获取总条数和投递列表
        long total = resumeMapper.getResumeTotal(resume, beginDateScope);
        List<Resume> resumes = resumeMapper.getResumePageList(page, size, resume, beginDateScope);

        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(resumes);
        respPageBean.setTotal(total);
        return respPageBean;
    }

    /**
     *更新求职状态（人才库、笔试、面试、录取）
     * @param resume
     * @return
     */
    public int updateResume(Resume resume) {
        resume.setUpdateDate(new Date());
        int res = resumeMapper.updateResume(resume);

        //状态更改，邮件通知
        if(res == 1) {
            String msgId = UUID.randomUUID().toString();
            rabbitTemplate.convertAndSend(MailStatus.MAIL_EXCHANGE_RESUME,
                    MailStatus.MAIL_ROUTING_KEY_RESUME, resume,
                    new CorrelationData(msgId));
        }
        return res;
    }

    /**
     * 获取当天简历投递数
     * @return
     */
    public RespBean getResumeCount() {
        long resumeTotal = resumeMapper.getResumeTotal(null, null);

        return RespBean.build().setStatus(200).setObj(resumeTotal);
    }
}
