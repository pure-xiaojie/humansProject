package com.ljg.mail.receiver;

import com.human.pojo.emp.Employee;
import com.human.pojo.job.Resume;
import com.human.pojo.mail.MailStatus;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

/**
 * @author Li JieGui
 * @date 2020/12/10 16:08
 * 消息接收
 */
@Component
public class MailReceiver {
    public static final Logger logger = LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    /**
     * 注入邮箱模板
     */
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 收到消息，给员工发送邮箱
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MailStatus.MAIL_QUEUE_NAME,durable = "true"),
            exchange = @Exchange(name=MailStatus.MAIL_EXCHANGE_NAME,durable = "true",type = "direct"),
            key = MailStatus.MAIL_ROUTING_KEY_WELCOME))
    @RabbitHandler
    public void handler(Message message, Channel channel) throws IOException {
        Employee employee = null;
        try {
            employee = (Employee) message.getPayload();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        MessageHeaders headers = message.getHeaders();
        headers.get("");
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        String msgId = (String)headers.get("spring_returned_message_correlation");

        //判断redis是否存在该msgId
        if (redisTemplate.opsForHash().entries("mail_log").containsKey(msgId)) {
            //redis 中包含该 key，说明该消息已经被消费过
            logger.info(msgId + ":消息已经被消费");

            //手动确认消息已消费
            channel.basicAck(tag, false);
            return;
        }
        //消息未被消费，发送邮件
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        try {
            helper.setTo(employee.getEmail());
            helper.setFrom(mailProperties.getUsername());
            helper.setSubject("入职通知书！——企业KK");
            helper.setSentDate(new Date());
            Context context = new Context();
            context.setVariable("name",employee.getName());
            context.setVariable("positionName",employee.getPosition().getName());
            context.setVariable("departmentName",employee.getDepartment().getName());
            String mail = templateEngine.process("entry", context);
            helper.setText(mail,true);

            javaMailSender.send(msg);
            //更改此消息的状态
            redisTemplate.opsForHash().put("mail_log", msgId, "human");
            channel.basicAck(tag, false);
            logger.info(msgId + ":邮件发送成功");
        } catch (MessagingException e) {
            //手动确认消息未消费成功
            channel.basicNack(tag, false, true);
            e.printStackTrace();
            logger.error("邮箱发送失败！" + e.getMessage());
        }
    }

    /**
     * 收到消息，给简历投递者发送邮箱
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MailStatus.MAIL_QUEUE_RESUME,durable = "true"),
            exchange = @Exchange(name=MailStatus.MAIL_EXCHANGE_RESUME,durable = "true",type = "direct"),
            key = MailStatus.MAIL_ROUTING_KEY_RESUME))
    @RabbitHandler
    public void handler2(Message message, Channel channel) throws IOException {
        Resume resume = (Resume) message.getPayload();

        MessageHeaders headers = message.getHeaders();
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(tag, false);

        Integer status = resume.getStatus();
        switch (status) {
            case 0 :
                sendResumeEmail(resume, channel, tag);
                break;
            case 1 :
                resumeSuccessEmail(resume, channel, tag);
                break;
            case 2 :
                thanksEmail(resume, channel, tag);
                break;
            case 3 :
                testEmail(resume, channel, tag);
                break;
            case 4 :
                interviewEmail(resume, channel, tag);
                break;
            default:
                System.out.println("状态有误！");
        }
    }

    /**
     * 简历投递邮箱
     */
    private void sendResumeEmail(Resume resume, Channel channel, Long tag) throws IOException {
        System.out.println("简历投递邮箱发送中……");
        System.out.println(resume);
        //消息未被消费，发送邮件
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        try {
            helper.setTo(resume.getEmail());
            helper.setFrom(mailProperties.getUsername());
            helper.setSubject("简历投递成功！——企业KK");
            helper.setSentDate(new Date());
            Context context = new Context();
            context.setVariable("name",resume.getName());
            context.setVariable("positionName",resume.getJob().getP_name());
            String mail = templateEngine.process("resume0", context);
            helper.setText(mail,true);

            javaMailSender.send(msg);
            channel.basicAck(tag, false);
            logger.info("简历投递邮件发送成功");
        } catch (MessagingException | IOException e) {
            //手动确认消息未消费成功
            channel.basicNack(tag, false, true);
            e.printStackTrace();
            logger.error("简历投递邮箱发送失败！" + e.getMessage());
        }
    }

    /**
     * 不符合要求，感谢信邮箱
     */
    private void resumeSuccessEmail(Resume resume, Channel channel, Long tag) throws IOException {
        System.out.println("简历通过邮箱发送中……");
        //消息未被消费，发送邮件
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        try {
            helper.setTo(resume.getEmail());
            helper.setFrom(mailProperties.getUsername());
            helper.setSubject("简历通过！——企业KK");
            helper.setSentDate(new Date());
            Context context = new Context();
            context.setVariable("name",resume.getName());
            context.setVariable("positionName",resume.getJob().getP_name());
            String mail = templateEngine.process("resume4", context);
            helper.setText(mail,true);

            javaMailSender.send(msg);
            channel.basicAck(tag, false);
            logger.info("简历通过发送成功");
        } catch (MessagingException | IOException e) {
            //手动确认消息未消费成功
            channel.basicNack(tag, false, true);
            e.printStackTrace();
            logger.error("简历通过发送失败！" + e.getMessage());
        }
    }

    /**
     * 不符合要求，感谢信邮箱
     */
    private void thanksEmail(Resume resume, Channel channel, Long tag) throws IOException {
        System.out.println("感谢信邮箱发送中……");
        //消息未被消费，发送邮件
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        try {
            helper.setTo(resume.getEmail());
            helper.setFrom(mailProperties.getUsername());
            helper.setSubject("感谢信！——企业KK");
            helper.setSentDate(new Date());
            Context context = new Context();
            context.setVariable("name",resume.getName());
            context.setVariable("positionName",resume.getJob().getP_name());
            String mail = templateEngine.process("resume1", context);
            helper.setText(mail,true);

            javaMailSender.send(msg);
            channel.basicAck(tag, false);
            logger.info("简历感谢信发送成功");
        } catch (MessagingException | IOException e) {
            //手动确认消息未消费成功
            channel.basicNack(tag, false, true);
            e.printStackTrace();
            logger.error("简历感谢信发送失败！" + e.getMessage());
        }
    }

    /**
     * 笔试邮箱
     * @param resume
     * @param channel
     * @param tag
     */
    private void testEmail(Resume resume, Channel channel, Long tag) throws IOException {
        System.out.println("笔试邮箱发送中……");
        //消息未被消费，发送邮件
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        try {
            helper.setTo(resume.getEmail());
            helper.setFrom(mailProperties.getUsername());
            helper.setSubject("笔试通知！——企业KK");
            helper.setSentDate(new Date());
            Context context = new Context();
            context.setVariable("name",resume.getName());
            context.setVariable("positionName",resume.getJob().getP_name());
            String mail = templateEngine.process("resume2", context);
            helper.setText(mail,true);

            javaMailSender.send(msg);
            channel.basicAck(tag, false);
            logger.info("笔试通知发送成功");
        } catch (MessagingException | IOException e) {
            //手动确认消息未消费成功
            channel.basicNack(tag, false, true);
            e.printStackTrace();
            logger.error("笔试通知发送失败！" + e.getMessage());
        }
    }

    /**
     * 面试邮箱
     * @param resume
     * @param channel
     * @param tag
     */
    private void interviewEmail(Resume resume, Channel channel, Long tag) throws IOException {
        System.out.println("面试邮箱发送中……");
        //消息未被消费，发送邮件
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        try {
            helper.setTo(resume.getEmail());
            helper.setFrom(mailProperties.getUsername());
            helper.setSubject("面试通知！——企业KK");
            helper.setSentDate(new Date());
            Context context = new Context();
            context.setVariable("name",resume.getName());
            context.setVariable("positionName",resume.getJob().getP_name());
            String mail = templateEngine.process("resume3", context);
            helper.setText(mail,true);

            javaMailSender.send(msg);
            channel.basicAck(tag, false);
            logger.info("面试通知发送成功");
        } catch (MessagingException | IOException e) {
            //手动确认消息未消费成功
            channel.basicNack(tag, false, true);
            e.printStackTrace();
            logger.error("面试通知发送失败！" + e.getMessage());
        }
    }
}
