package com.human.pojo.emp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.human.pojo.Department;
import com.human.pojo.JobLevel;

import java.util.Date;

/**
 * @author Li JieGui
 * @date 2021/3/14 15:33
 * 员工调动实体
 */
public class Employeeremove {
    private Integer id;

    private Integer eid;

    private Integer afterDepId;

    private Integer afterJobId;

    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "Asia/shanghai")
    private Date removeDate;

    private String reason;

    private String remark;

    private Employee employee;

    private Department department;

    private JobLevel jobLevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getAfterDepId() {
        return afterDepId;
    }

    public void setAfterDepId(Integer afterDepId) {
        this.afterDepId = afterDepId;
    }

    public Integer getAfterJobId() {
        return afterJobId;
    }

    public void setAfterJobId(Integer afterJobId) {
        this.afterJobId = afterJobId;
    }

    public Date getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(Date removeDate) {
        this.removeDate = removeDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public JobLevel getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(JobLevel jobLevel) {
        this.jobLevel = jobLevel;
    }
}