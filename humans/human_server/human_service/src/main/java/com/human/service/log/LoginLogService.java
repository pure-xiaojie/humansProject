package com.human.service.log;

import com.human.mapper.log.LoginLogMapper;
import com.human.pojo.RespBean;
import com.human.pojo.RespPageBean;
import com.human.pojo.log.LoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/4/9 20:48
 * 登录日志
 */
@Service
public class LoginLogService {
    @Autowired
    private LoginLogMapper loginLogMapper;

    /**
     * 根据用户名获取登录日志
     * @param userName
     * @return
     */
    public List<LoginLog> getLoginLogByUserName(String userName) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUserName(userName);

        return loginLogMapper.selectLoginLogList(loginLog);
    }

    /**
     * 分页获取
     * @param page
     * @param size
     * @return
     */
    public RespPageBean getLoginLogByPage(Integer page, Integer size, String userName) {
        if(page != null && size != null) {
            page = (page - 1) * size;
        }
        long total = loginLogMapper.getTotal(userName);
        List<LoginLog> loginLogs = loginLogMapper.selectLoginLogListByPage(page, size, userName);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setTotal(total);
        respPageBean.setData(loginLogs);

        return respPageBean;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public Integer deleteLoginLogById(Integer id) {
        return loginLogMapper.deleteLoginLogById(id);
    }
}
