package com.human.controller.system.log;

import com.human.pojo.RespBean;
import com.human.pojo.RespPageBean;
import com.human.service.mail.MailSendLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Li JieGui
 * @date 2021/4/7 22:24
 * 邮箱日志
 */
@Api(tags = "邮箱日志")
@RestController
@RequestMapping("/system/log")
public class MailSendLogController {
    @Autowired
    private MailSendLogService mailSendLogService;

    /**
     * 分页获取邮箱日志
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("分页获取邮箱日志")
    @GetMapping("/")
    public RespPageBean getMailLogListByPageSize(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer size) {
        return mailSendLogService.getMailLogListByPageSize(page, size);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @ApiOperation("根据id删除")
    @DeleteMapping("/{id}")
    public RespBean deleteLogById(@PathVariable String id) {
        if(mailSendLogService.deleteLogById(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
