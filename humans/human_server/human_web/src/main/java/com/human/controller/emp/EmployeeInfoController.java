package com.human.controller.emp;

import com.human.mapper.log.LoginLogMapper;
import com.human.pojo.RespBean;
import com.human.pojo.emp.Employee;
import com.human.pojo.log.LoginLog;
import com.human.service.emp.EmployeeInfoService;
import com.human.util.FastDfsUtils;
import com.human.util.IpUtils;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author Li JieGui
 * @date 2021/4/12 20:24
 */
@Api(tags = "员工角色")
@RestController
@RequestMapping("/empInfo")
@Slf4j
public class EmployeeInfoController {
    @Autowired
    private EmployeeInfoService employeeInfoService;

    @Autowired
    private LoginLogMapper loginLogMapper;

    /**
     * 图片访问代理地址
     */
    @Value("${fastdfs.nginx.host}")
    String nginxHost;

    /**
     * 员工登录
     * @param username
     * @param password
     * @param code
     * @return
     */
    @ApiOperation("员工登录")
    @PostMapping("/login")
    public RespBean empLogin(HttpServletRequest request, String username, String password, String code) {
        HttpSession session = request.getSession(true);
        String verify_code = (String) session.getAttribute("verify_code");
        log.info("verify_code: " + verify_code);
        log.info("code: " + code);
        if (code == null || verify_code == null || "".equals(code) || !verify_code.toLowerCase().equals(code.toLowerCase())) {
            //验证码不正确
            return RespBean.error("验证码错误！");
        }
        RespBean respBean = employeeInfoService.empLogin(username, password);
        if(respBean.getStatus() == 200) {
            //新增登录日志
            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
            String ip = IpUtils.getIpAddr(request);
            String address = "内网IP";
            String os = userAgent.getOperatingSystem().getName();
            String browser = userAgent.getBrowser().getName();

            LoginLog loginLog = new LoginLog(username, ip, browser,
                    respBean.getMsg(), address, 1, os);

            loginLogMapper.insertLoginLog(loginLog);
        }

        return respBean;
    }

    /**
     * 获取员工信息
     * @param id
     * @return
     */
    @ApiOperation("员工获取个人信息")
    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Integer id) {
        return employeeInfoService.getEmployeeById(id);
    }

    /**
     * 修改头像
     * @param file
     * @param id
     * @return
     */
    @ApiOperation("修改员工头像")
    @PostMapping("/userface")
    public RespBean updateEmployeeFace(MultipartFile file, Integer id) {
        String fileId = FastDfsUtils.upload(file);
        String url = nginxHost + fileId;
        if (employeeInfoService.updateEmployeeFace(id, url) == 1) {
            return RespBean.ok("更新成功!", url);
        }
        return RespBean.error("更新失败!");
    }

    /**
     * 修改用户信息
     * @param e
     * @return
     */
    @ApiOperation("修改个人信息")
    @PutMapping("/info")
    public RespBean updateEmployee(@RequestBody Employee e) {
        return employeeInfoService.updateEmployee(e);
    }

    /**
     * 根据用户名获取
     * @param phone
     * @return
     */
    @ApiOperation("根据用户名获取登录日志")
    @GetMapping("/loginLog")
    public List<LoginLog> getLoginLogByPhone(String phone) {
       return employeeInfoService.getLoginLogByPhone(phone);
    }

    /**
     * 修改用户密码
     * @param info
     * @return
     */
    @ApiOperation("修改个人密码")
    @PutMapping("/pass")
    public RespBean updateEmployeePassword(@RequestBody Map<String, Object> info) {
        String oldPass = (String) info.get("oldPass");
        String newPass = (String) info.get("newPass");
        Integer id = (Integer) info.get("id");
        if(employeeInfoService.updateEmployeePassword(oldPass, newPass, id)) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
}
