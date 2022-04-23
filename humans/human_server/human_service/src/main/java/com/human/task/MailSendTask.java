package com.human.task;

import com.human.pojo.emp.Employee;
import com.human.pojo.mail.MailSendLog;
import com.human.pojo.mail.MailStatus;
import com.human.service.emp.EmployeeService;
import com.human.service.mail.MailSendLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/3/19 21:47
 * 邮箱定时任务
 */
@Component
public class MailSendTask {
    @Autowired
    private MailSendLogService mailSendLogService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private EmployeeService employeeService;

    /**
     * 每隔10秒执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void mailSendTask() {
        //System.out.println("获取失败消息");
        List<MailSendLog> list = mailSendLogService.getMailSendLogsByStatus();
        list.forEach((sendLog) -> {
            //已经重试3次，更新为失败
            if(sendLog.getCount() >= 3) {
                mailSendLogService.updateMailSendLogStatus(sendLog.getMsgId(),2);
            }
            //重试发送
            else {
                //更新重试的时间
                mailSendLogService.updateCountTime(sendLog.getMsgId(), new Date());
                //获取员工实体并重新发送邮箱
                Employee employee = employeeService.getEmployeeById(sendLog.getEmpId());
                rabbitTemplate.convertAndSend(MailStatus.MAIL_EXCHANGE_NAME, MailStatus.MAIL_ROUTING_KEY_WELCOME, employee, new CorrelationData(sendLog.getMsgId()));
            }
        });
    }
}
