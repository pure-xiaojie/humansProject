package com.human.controller.job;

import com.human.pojo.RespBean;
import com.human.pojo.RespPageBean;
import com.human.pojo.job.Resume;
import com.human.service.job.ResumeService;
import com.human.util.FastDfsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

/**
 * @author Li JieGui
 * @date 2021/3/28 9:50
 * 职位申请,简历投递
 */
@Api(tags = "简历信息")
@RestController
@RequestMapping("/recurit/apply")
public class ResumeApplyController {
    /**
     * 简历访问代理地址
     */
    @Value("${fastdfs.nginx.host}")
    String nginxHost;

    @Autowired
    private ResumeService resumeService;

    /**
     * 上传简历到服务器
     * @param file
     * @return
     * @throws IOException
     */
    @ApiOperation("简历上传")
    @PostMapping("/import")
    public RespBean importResume(MultipartFile file) throws IOException {
        String fileId = FastDfsUtils.upload(file);
        String url = nginxHost + fileId;
        System.out.println(url);
        return RespBean.ok("上传成功！",url);
    }

    /**
     * 提交简历到数据库
     * @return
     */
    @ApiOperation("简历投递")
    @PostMapping("/")
    public RespBean addResume(@RequestBody Resume resume) {
        if(resumeService.addResume(resume) == 1) {
            return RespBean.ok("投递成功！");
        }
        return RespBean.error("投递失败！");
    }

    /**
     * 分页查询投递信息，可根据姓名，投递日期模糊查询
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("分页获取简历信息")
    @GetMapping("/")
    public RespPageBean getResumePageList(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer size,
                                       Resume resume, Date[] beginDateScope) {
        return resumeService.getResumePageList(page,size,resume,beginDateScope);
    }

    /**
     * 更新求职状态（人才库、笔试、面试、录取）
     * @param resume
     * @return
     */
    @ApiOperation("简历状态修改")
    @PutMapping("/")
    public RespBean updateResume(@RequestBody Resume resume) {
        if(resumeService.updateResume(resume) == 1) {
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败!");
    }

    /**
     * 今日简历投递人数
     * @return
     */
    @ApiOperation("获取当天简历投递人数")
    @GetMapping("/count")
    public RespBean getResumeCount() {
        return resumeService.getResumeCount();
    }
}
