package com.human.controller.system.log;

import com.human.pojo.RespBean;
import com.human.pojo.RespPageBean;
import com.human.pojo.log.LoginLog;
import com.human.service.log.LoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/4/9 20:44
 * 登录日志
 */
@Api(tags = "登录日志")
@RestController
@RequestMapping("/log")
public class LoginLogController {
    @Autowired
    private LoginLogService loginLogService;

    /**
     * 根据用户名获取
     * @param userName
     * @return
     */
    @ApiOperation("根据用户名获取登录日志")
    @GetMapping("/loginLog")
    public List<LoginLog> getLoginLogByUserName(String userName) {
        return loginLogService.getLoginLogByUserName(userName);
    }

    /**
     * 分页获取
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("分页获取登录日志")
    @GetMapping("/page")
    public RespPageBean getLoginLogByPage(@RequestParam(defaultValue = "1")Integer page,
                                          @RequestParam(defaultValue = "10")Integer size,
                                          String userName) {
        return loginLogService.getLoginLogByPage(page, size, userName);
    }

    /**
     * 删除登录日志
     * @param id
     * @return
     */
    @ApiOperation("删除登录日志")
    @DeleteMapping("/login/{id}")
    public RespBean deleteLoginLogById(@PathVariable Integer id) {
        if(loginLogService.deleteLoginLogById(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
