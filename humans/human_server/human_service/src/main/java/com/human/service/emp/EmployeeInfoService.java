package com.human.service.emp;

import com.human.mapper.emp.EmployeeMapper;
import com.human.mapper.log.LoginLogMapper;
import com.human.pojo.RespBean;
import com.human.pojo.emp.Employee;
import com.human.pojo.log.LoginLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/4/12 22:57
 * 员工角色登录
 */
@Service
@Slf4j
public class EmployeeInfoService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private LoginLogMapper loginLogMapper;

    /**
     * 员工登录
     * @param phone
     * @param password
     * @return
     */
    public RespBean empLogin(String phone, String password) {
        Employee employee = employeeMapper.getEmployeeByPhoneAndPassword(phone, password);
        if(employee == null) {
            log.info("用户名或密码错误！");
            return RespBean.error("用户名或密码错误！");
        }
        return RespBean.ok("登录成功！", employee);
    }

    /**
     * 根据员工id获取
     * @param id
     * @return
     */
    public Employee getEmployeeById(Integer id) {
        return employeeMapper.getEmployeeById(id);
    }

    /**
     * 更新员工头像
     * @param id
     * @param url
     * @return
     */
    public int updateEmployeeFace(Integer id, String url) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setImgUrl(url);
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    /**
     * 更新职工信息
     * @param e
     * @return
     */
    public RespBean updateEmployee(Employee e) {
        if(employeeMapper.updateByPrimaryKeySelective(e) == 1) {
            return RespBean.ok("修改成功！");
        }
        return RespBean.error("修改失败！");
    }

    /**
     * 获取员工登录日志
     * @param phone
     * @return
     */
    public List<LoginLog> getLoginLogByPhone(String phone) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUserName(phone);

        return loginLogMapper.selectLoginLogList(loginLog);
    }

    /**
     * 更新密码
     * @param oldPass
     * @param newPass
     * @param id
     * @return
     */
    public Boolean updateEmployeePassword(String oldPass, String newPass, Integer id) {
        Employee employee = employeeMapper.selectByPassword(id);
        log.info("oldPass:" + oldPass);
        log.info("empPass:" + employee.getPassword());
        //判断旧密码与数据库密码是否一致
        if (oldPass.equals(employee.getPassword())) {
            employee.setPassword(newPass);
            //更新密码
            Integer result = employeeMapper.updateByPrimaryKeySelective(employee);
            if (result == 1) {
                return true;
            }
        }
        return false;
    }
}
