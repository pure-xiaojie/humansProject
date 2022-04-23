package com.human.pojo.mail;

/**
 * @author Li JieGui
 * @date 2021/3/18 21:59
 */
public class MailStatus {
    /**
     * 消息投递中
     */
    public static final Integer DELIVERING = 0;

    /**
     * 消息投递成功
     */
    public static final Integer SUCCESS = 1;

    /**
     * 消息投递失败
     */
    public static final Integer FAILURE = 2;

    /**
     * 最大重试次数
     */
    public static final Integer MAX_TRY_COUNT = 3;

    /**
     * 消息超时时间
     */
    public static final Integer MSG_TIMEOUT = 1;

    /**
     * 入职消息队列
     */
    public static final String MAIL_QUEUE_NAME = "human.mail.queue";
    public static final String MAIL_EXCHANGE_NAME = "human.mail.exchange";
    public static final String MAIL_ROUTING_KEY_WELCOME = "human.mail.welcome";

    /**
     * 简历投递
     */
    public static final String MAIL_QUEUE_RESUME = "human.mail.resume";
    public static final String MAIL_EXCHANGE_RESUME = "human.resume.exchange";
    public static final String MAIL_ROUTING_KEY_RESUME = "human.mail.resume";
}
