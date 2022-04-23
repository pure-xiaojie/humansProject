package com.human.controller.system.msg;

import com.human.pojo.MsgContent;
import com.human.pojo.RespBean;
import com.human.pojo.RespPageBean;
import com.human.service.msg.MsgContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Li JieGui
 * @date 2021/4/3 21:10
 * 公告信息
 */
@Api(tags = "公告信息")
@RestController
public class MsgContentController {
    @Autowired
    private MsgContentService msgContentService;

    /**
     * 分页获取
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("分页获取公告列表")
    @GetMapping("/content")
    public RespPageBean getMsgContentList(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size) {
        return msgContentService.getMsgContentList(page, size);
    }

    /**
     * 发布公告
     * @param msgContent
     * @return
     */
    @ApiOperation("发布公告")
    @PostMapping("/content")
    public RespBean insertMsgContent(@RequestBody MsgContent msgContent) {
        if(msgContentService.insertMsgContent(msgContent) == 1) {
            return RespBean.ok("发布成功！");
        }
        return RespBean.error("发布失败！");
    }

    /**
     * 修改公告
     * @param msgContent
     * @return
     */
    @ApiOperation("修改公告信息")
    @PutMapping("/content")
    public RespBean updateMsgContent(@RequestBody MsgContent msgContent) {
        if(msgContentService.updateMsgContent(msgContent) == 1) {
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    /**
     * 删除公告
     * @param id
     * @return
     */
    @ApiOperation("根据id删除公告")
    @DeleteMapping("/content/{id}")
    public RespBean deleteMsgContentById(@PathVariable Integer id) {
        if(msgContentService.deleteMsgContentById(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    /**
     * 批量删除公告
     * @param ids
     * @return
     */
    @ApiOperation("批量删除公告")
    @DeleteMapping("/content")
    public RespBean deleteMsgContentByIds(Integer[] ids) {
        if (msgContentService.deleteMsgContentByIds(ids) == ids.length) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
