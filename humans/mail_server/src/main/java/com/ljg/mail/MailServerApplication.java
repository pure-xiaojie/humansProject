package com.ljg.mail;

import com.human.pojo.mail.MailStatus;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Li JieGui
 * @date 2020/12/10 16:08
 */
@SpringBootApplication
public class MailServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailServerApplication.class, args);
	}

	/**
	 * 注入消息队列queue
	 * @return
	 */
	@Bean
	Queue queue1() {
		return new Queue(MailStatus.MAIL_QUEUE_NAME);
	}

	@Bean
	Queue queue2() {
		return new Queue(MailStatus.MAIL_QUEUE_RESUME);
	}
}
