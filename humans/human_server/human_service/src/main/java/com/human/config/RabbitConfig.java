package com.human.config;

import com.human.pojo.mail.MailStatus;
import com.human.service.mail.MailSendLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Li JieGui
 * @date 2021/3/18 22:03
 */
@Configuration
public class RabbitConfig {
    public static final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    @Autowired
    CachingConnectionFactory cachingConnectionFactory;

    @Autowired
    MailSendLogService mailSendLogService;

    @Bean
    RabbitTemplate rabbitTemplate() {
        //int a = 1/0; 报错，重启后消息继续消费
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        //消息发送后的返回
        rabbitTemplate.setConfirmCallback((data, ack, cause) -> {
            String msgId = data.getId();
            if (ack) {
                logger.info(msgId + ":消息发送成功");
                //更新数据库的日志记录，消息投递成功
                mailSendLogService.updateMailSendLogStatus(msgId, 1);
            } else {
                logger.info(msgId + ":消息发送失败");
            }
        });
        rabbitTemplate.setReturnCallback((msg, repCode, repText, exchange, routingkey) -> {
            logger.info("消息发送失败");
        });

        return rabbitTemplate;
    }

    /**
     * 构建邮箱消息队列实例，同时也是消息队列载体
     * @return
     */
    @Bean
    Queue mailQueue() {
        return new Queue(MailStatus.MAIL_QUEUE_NAME, true);
    }

    /**
     * 初始化消息交换机，它指定消息按什么规则，路由到哪个队列
     * 完全根据key进行投递的交换机
     * 所有生产者提交的消息都由Exchange来接受，然后Exchange按照特定的策略转发到Queue进行存储
     * @return
     */
    @Bean
    DirectExchange mailExchange() {
        return new DirectExchange(MailStatus.MAIL_EXCHANGE_NAME, true, false);
    }

    /**
     * 绑定，它的作用就是把exchange和queue按照路由规则绑定起来
     * @return
     */
    @Bean
    Binding mailBinding() {
        return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(MailStatus.MAIL_ROUTING_KEY_WELCOME);
    }

    /**
     * 构建简历消息队列实例，同时也是消息队列载体
     * @return
     */
    @Bean
    Queue resumeQueue() {
        return new Queue(MailStatus.MAIL_QUEUE_RESUME, true);
    }
    @Bean
    DirectExchange resumeExchange() {
        return new DirectExchange(MailStatus.MAIL_EXCHANGE_RESUME, true, false);
    }
    @Bean
    Binding resumeBinding() {
        return BindingBuilder.bind(resumeQueue()).to(resumeExchange()).with(MailStatus.MAIL_ROUTING_KEY_RESUME);
    }
}
