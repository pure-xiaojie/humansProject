package com.human.mapper.log;

import com.human.pojo.log.LoginLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/4/8 22:21
 * 登录日志记录
 */
@Repository
public interface LoginLogMapper {
    /**
     * 新增系统登录日志
     *
     * @param loginLog 访问日志对象
     */
    void insertLoginLog(LoginLog loginLog);

    /**
     * 查询系统登录日志集合
     *
     * @param loginLog 访问日志对象
     * @return 登录记录集合
     */
    List<LoginLog> selectLoginLogList(LoginLog loginLog);

    /**
     * 删除系统登录日志
     * @param id 需要删除的数据
     * @return 结果
     */
    int deleteLoginLogById(@Param("id") Integer id);

    /**
     * 分页获取
     * @param page
     * @param size
     * @param userName
     * @return
     */
    List<LoginLog> selectLoginLogListByPage(Integer page, Integer size, String userName);

    /**
     * 获取总数
     * @param userName
     * @return
     */
    long getTotal(String userName);
}
