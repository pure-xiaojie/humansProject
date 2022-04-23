package com.human.mapper.job;

import com.human.pojo.job.Resume;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/3/31 22:48
 */
@Repository
public interface ResumeMapper {
    /**
     * 添加简历
     * @param resume
     * @return
     */
    int addResume(Resume resume);

    /**
     * 获取总条数
     * @param resume
     * @param beginDateScope
     * @return
     */
    long getResumeTotal(Resume resume, Date[] beginDateScope);

    /**
     * 获取投递列表
     * @param page
     * @param size
     * @param resume
     * @param beginDateScope
     * @return
     */
    List<Resume> getResumePageList(Integer page, Integer size, Resume resume, Date[] beginDateScope);

    /**
     * 更新求职状态
     * @param resume
     * @return
     */
    int updateResume(Resume resume);
}
