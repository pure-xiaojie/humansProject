package com.human.pojo.salary;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.human.pojo.Department;
import com.human.pojo.Position;
import com.human.pojo.emp.Employee;

import java.util.Date;

/**
 * @author Li JieGui
 * @date 2021/3/14 14:37
 * 员工调薪实体
 */
public class AdjustSalary {
    private Integer id;

    private Integer eid;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/shanghai")
    private Date asDate;

    private Integer beforeSalary;

    private Integer afterSalary;

    private String reason;

    private String remark;

    private Employee employee;

    private Department department;

    private Position position;

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

    public Date getAsDate() {
        return asDate;
    }

    public void setAsDate(Date asDate) {
        this.asDate = asDate;
    }

    public Integer getBeforeSalary() {
        return beforeSalary;
    }

    public void setBeforeSalary(Integer beforeSalary) {
        this.beforeSalary = beforeSalary;
    }

    public Integer getAfterSalary() {
        return afterSalary;
    }

    public void setAfterSalary(Integer afterSalary) {
        this.afterSalary = afterSalary;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}