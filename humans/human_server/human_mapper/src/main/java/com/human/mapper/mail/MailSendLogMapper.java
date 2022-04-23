package com.human.mapper.mail;

import com.human.pojo.mail.MailSendLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/3/18 22:11
 */
@Repository
public interface MailSendLogMapper {
    /**
     * 更新邮箱发送状态
     * @param msgId
     * @param status
     * @return
     */
    Integer updateMailSendLogStatus(@Param("msgId")String msgId, @Param("status")Integer status);

    /**
     * 插入邮箱日志
     * @param mailSendLog
     * @return
     */
    Integer insert(MailSendLog mailSendLog);

    /**
     * 查询邮箱未成功的日志
     * @return
     */
    List<MailSendLog> getMailSendLogsByStatus();

    /**
     * 更新重试的时间
     * @param msgId
     * @param date
     * @return
     */
    Integer updateCountTime(@Param("msgId") String msgId, @Param("date") Date date);

    /**
     * 获取总数量
     * @return
     */
    long getLogTotal();

    /**
     * 分页获取
     * @param page
     * @param size
     * @return
     */
    List<MailSendLog> getMailLogListByPageSize(@Param("page") Integer page, @Param("size") Integer size);

    /**
     * 更加id删除
     * @param id
     * @return
     */
    int deleteLogById(@Param("id") String id);
}
