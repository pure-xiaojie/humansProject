package com.human.service.mail;

import com.human.mapper.mail.MailSendLogMapper;
import com.human.pojo.RespPageBean;
import com.human.pojo.mail.MailSendLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/3/18 22:01
 * 邮件日志服务层
 */
@Service
public class MailSendLogService {
    @Autowired
    MailSendLogMapper mailSendLogMapper;

    /**
     * 更新邮箱发送状态
     * @param msgId
     * @param status
     * @return
     */
    public Integer updateMailSendLogStatus(String msgId, Integer status) {
        return mailSendLogMapper.updateMailSendLogStatus(msgId, status);
    }

    /**
     * 插入邮箱日志
     * @param mailSendLog
     * @return
     */
    public Integer insert(MailSendLog mailSendLog) {
        return mailSendLogMapper.insert(mailSendLog);
    }

    /**
     * 获取邮箱发送失败的日志
     * @return
     */
    public List<MailSendLog> getMailSendLogsByStatus() {
        return mailSendLogMapper.getMailSendLogsByStatus();
    }

    /**
     * 更新重试时间
     * @param msgId
     * @param date
     * @return
     */
    public Integer updateCountTime(String msgId, Date date) {
        return mailSendLogMapper.updateCountTime(msgId,date);
    }

    /**
     * 分页获取
     * @param page
     * @param size
     * @return
     */
    public RespPageBean getMailLogListByPageSize(Integer page, Integer size) {
        if(page != null && size != null) {
            page = (page - 1) * size;
        }
        long total = mailSendLogMapper.getLogTotal();
        List<MailSendLog> mailSendLogList = mailSendLogMapper.getMailLogListByPageSize(page,size);

        RespPageBean res = new RespPageBean();
        res.setTotal(total);
        res.setData(mailSendLogList);
        return res;
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    public int deleteLogById(String id) {
        return mailSendLogMapper.deleteLogById(id);
    }
}
