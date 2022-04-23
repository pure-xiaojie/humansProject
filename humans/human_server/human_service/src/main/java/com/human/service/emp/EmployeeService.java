package com.human.service.emp;

import com.human.mapper.emp.EmployeeMapper;
import com.human.pojo.RespBean;
import com.human.pojo.emp.Employee;
import com.human.pojo.mail.MailSendLog;
import com.human.pojo.mail.MailStatus;
import com.human.pojo.RespPageBean;
import com.human.pojo.salary.Salary;
import com.human.pojo.salary.SalaryTable;
import com.human.service.mail.MailSendLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Li JieGui
 * @date 2020/12/4 16:12
 */
@Service
public class EmployeeService {
    public static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    /**
     * 时间格式处理
     */
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    DecimalFormat decimalFormat = new DecimalFormat("##.00");

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MailSendLogService mailSendLogService;

    /**
     * 分页查询员工信息
     * @param page
     * @param size
     * @return
     */
    public RespPageBean getEmployeePageList(Integer page, Integer size,Employee employee,Date[] beginDateScope) {
        //计算起始页
        if(page != null && size != null) {
            page = (page-1)*size;
        }
        //获取总条数和职工列表
        long total = employeeMapper.getEmployeeTotal(employee,beginDateScope);
        List<Employee> employees = employeeMapper.getEmployeePageList(page,size,employee,beginDateScope);

        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(employees);
        respPageBean.setTotal(total);
        return respPageBean;
    }

    /**
     * 添加职工
     * @param employee
     * @return
     */
    @Transactional
    public Integer addEmployee(Employee employee) {
        //获取合同期限，单位月
        Double term = getContractTerm(employee);
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(term/12)));
        int res = employeeMapper.insertSelective(employee);

        //添加成功，以消息队列发送一条消息，给员工发送邮箱
        if(res == 1) {
            Employee emp = employeeMapper.getEmployeeById(employee.getId());

            //构建邮件日志
            String msgId = UUID.randomUUID().toString();
            MailSendLog mailSendLog = new MailSendLog();
            mailSendLog.setMsgId(msgId);
            mailSendLog.setCreateTime(new Date());
            mailSendLog.setExchange(MailStatus.MAIL_EXCHANGE_NAME);
            mailSendLog.setRouteKey(MailStatus.MAIL_ROUTING_KEY_WELCOME);
            mailSendLog.setEmpId(emp.getId());
            mailSendLog.setTryTime(new Date(System.currentTimeMillis() + 1000 * 60 * MailStatus.MSG_TIMEOUT));
            mailSendLogService.insert(mailSendLog);

            rabbitTemplate.convertAndSend(MailStatus.MAIL_EXCHANGE_NAME,
                    MailStatus.MAIL_ROUTING_KEY_WELCOME, emp,
                    new CorrelationData(msgId));

            //消息发送失败测试,exchange错误，会在task中自动重试
            /*rabbitTemplate.convertAndSend("aa",
                    MailStatus.MAIL_ROUTING_KEY_NAME, emp,
                    new CorrelationData(msgId));*/

            //消息重发测试，避免发送两次邮箱，邮箱服务会通过redis去重
            /*rabbitTemplate.convertAndSend(MailStatus.MAIL_EXCHANGE_NAME,
                    MailStatus.MAIL_ROUTING_KEY_NAME, emp,
                    new CorrelationData(msgId));*/
        }
        return res;
    }

    /**
     * 获取最大工号
     * @return
     */
    public Integer getMaxWorkId() {
        return employeeMapper.getMaxWorkId();
    }

    /**
     * 删除职工
     * @param id
     * @return
     */
    public Integer delEmployeeById(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新职工信息
     * @param employee
     * @return
     */
    public Integer updateEmployee(Employee employee) {
        //获取合同期限，单位月
        Double term = getContractTerm(employee);
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(term/12)));
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    /**
     * 计算合同期限
     * @param employee
     * @return
     */
    public Double getContractTerm(Employee employee) {
        //获取合同起始日期和结束日期
        Date beginContract = employee.getBeginContract();
        Date endContract = employee.getEndContract();
        //计算合同期限
        Double term = (Double.parseDouble(yearFormat.format(endContract))-
                Double.parseDouble(yearFormat.format(beginContract)))*12+
                (Double.parseDouble(monthFormat.format(endContract))-
                        Double.parseDouble(monthFormat.format(beginContract)));
        return term;
    }

    /**
     * 插入职工数据到数据库
     * @param employeeList
     * @return
     */
    public Integer insertEmployeeList(List<Employee> employeeList) {
        return employeeMapper.insertEmployeeList(employeeList);
    }

    /**
     * 分页获取员工账套信息
     * @param page
     * @param size
     * @param name
     * @return
     */
    public RespPageBean getEmployeePageWithSalary(Integer page, Integer size, String name) {
        //计算起始页
        if(page != null && size != null) {
            page = (page-1)*size;
        }
        Employee emp = new Employee();
        emp.setName(name);

        //获取总条数和职工列表
        long total = employeeMapper.getEmployeeTotal(emp,null);
        List<Employee> employees = employeeMapper.getEmployeePageWithSalary(page, size, name);

        //员工账套未设置的员工为默认
        employees.forEach(employee -> {
            if(employee.getSalary() == null) {
                employee.setSalary(new Salary());
            }
        });
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(employees);
        respPageBean.setTotal(total);
        return respPageBean;
    }

    /**
     * 更新职工账套
     * @param eid
     * @param sid
     * @return
     */
    public Integer updateEmployeeSalaryById(Integer eid, Integer sid) {
        return employeeMapper.updateEmployeeSalaryById(eid,sid);
    }

    /**
     * 根据员工id获取
     * @param empId
     * @return
     */
    public Employee getEmployeeById(Integer empId) {
        return employeeMapper.getEmployeeById(empId);
    }

    /**
     * 获取在职人数
     * @return
     */
    public RespBean getEmpCount() {
        long employeeTotal = employeeMapper.getEmployeeTotal(null, null);

        return RespBean.build().setStatus(200).setObj(employeeTotal);
    }

    /**
     * 分页获取员工工资
     * @param page
     * @param size
     * @param name
     * @return
     */
    public RespPageBean getEmployeePageWithSalaryDetail(Integer page, Integer size, String name) {
        //计算起始页
        if(page != null && size != null) {
            page = (page-1)*size;
        }
        Employee emp = new Employee();
        emp.setName(name);

        //获取总条数和职工列表
        long total = employeeMapper.getEmployeeTotal(emp,null);
        List<Employee> employees = employeeMapper.getEmployeePageWithSalary(page, size, name);

        //提取工资表
        List<SalaryTable> salaryTables = SalaryTable.getSalaryList(employees, 0);

        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(salaryTables);
        respPageBean.setTotal(total);
        return respPageBean;
    }
}
