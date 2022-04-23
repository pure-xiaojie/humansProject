package com.human.controller.chat;

import com.human.pojo.Hr;
import com.human.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/11 19:17
 */
@RestController
@RequestMapping("/chat")
@Api(tags = "聊天窗口")
public class ChatController {
    @Autowired
    UserService userService;

    /**
     * 获取所有的hr用户
     * @return
     */
    @GetMapping("/users")
    @ApiOperation("获取聊天好友列表")
    public List<Hr> getUserListExceptCurrentUser() {
        return userService.getUserListExceptCurrentUser();
    }
}
