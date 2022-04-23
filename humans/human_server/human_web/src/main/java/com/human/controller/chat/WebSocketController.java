package com.human.controller.chat;

import com.human.pojo.ChatMsg;
import com.human.pojo.Hr;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * @author Li JieGui
 * @date 2020/12/11 19:41
 * 消息处理类
 */
@Controller
@Slf4j
public class WebSocketController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 消息处理类
     * @param authentication 用来获取当前登录用户
     * @param chatMsg 接收的消息
     */
    @MessageMapping("/ws/chat")
    public void handleMsg(Authentication authentication, ChatMsg chatMsg) {
        log.info("接收到了信息" + chatMsg.getMsg());
        Hr hr = (Hr) authentication.getPrincipal();
        chatMsg.setFrom(hr.getUsername());
        chatMsg.setNickname(hr.getName());
        chatMsg.setDate(new Date());
        simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(), "/queue/chat", chatMsg);
    }
}
