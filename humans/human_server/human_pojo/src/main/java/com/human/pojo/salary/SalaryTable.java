package com.human.pojo.salary;

import com.human.pojo.emp.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/4/14 20:25
 * 薪资表
 */
public class SalaryTable {
    private Integer id;

    private Integer empId;

    private String empName;

    private Integer month;

    private Integer basicSalary;

    private Integer bonus;

    private Integer lunchSalary;

    private Integer trafficSalary;

    private Integer allSalary;

    private Float actualSalary;

    private Integer pensionBase;

    private Float pensionPer;

    private Integer medicalBase;

    private Float medicalPer;

    private Integer accumulationFundBase;

    private Float accumulationFundPer;

    public static List<SalaryTable> getSalaryList(List<Employee> employeeList, int month) {
        List<SalaryTable> salaryTableList = new ArrayList<>();

        //员工账套未设置的员工为默认
        employeeList.forEach(employee -> {
            if(employee.getSalary() == null) {
                employee.setSalary(new Salary());
            }
            Salary salary = employee.getSalary();

            SalaryTable salaryTable = new SalaryTable();
            salaryTable.setEmpId(employee.getId());
            salaryTable.setEmpName(employee.getName());
            salaryTable.setBasicSalary(salary.getBasicSalary());
            salaryTable.setBonus(salary.getBonus());
            salaryTable.setLunchSalary(salary.getLunchSalary());
            salaryTable.setTrafficSalary(salary.getTrafficSalary());
            salaryTable.setMonth(month);

            salaryTable.setPensionBase(salary.getPensionBase());
            salaryTable.setPensionPer(salary.getPensionPer());

            salaryTable.setMedicalBase(salary.getMedicalBase());
            salaryTable.setMedicalPer(salary.getMedicalPer());

            salaryTable.setAccumulationFundBase(salary.getAccumulationFundBase());
            salaryTable.setAccumulationFundPer(salary.getAccumulationFundPer());

            salaryTable.setAllSalary(salaryTable.getAllSalary());
            salaryTable.setActualSalary(salaryTable.getActualSalary());

            salaryTableList.add(salaryTable);
        });
        return salaryTableList;
    }

    public float getPensionSalary() {
        return this.pensionBase * this.pensionPer;
    }
    public float getMedicalSalary() {
        return this.medicalBase * this.medicalPer;
    }
    public float getAccumulationFundSalary() {
        return this.accumulationFundBase * this.accumulationFundPer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Integer basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public Integer getLunchSalary() {
        return lunchSalary;
    }

    public void setLunchSalary(Integer lunchSalary) {
        this.lunchSalary = lunchSalary;
    }

    public Integer getTrafficSalary() {
        return trafficSalary;
    }

    public void setTrafficSalary(Integer trafficSalary) {
        this.trafficSalary = trafficSalary;
    }

    public Integer getAllSalary() {
        return this.basicSalary + this.lunchSalary + this.trafficSalary +
                this.bonus;
    }

    public void setAllSalary(Integer allSalary) {
        this.allSalary = allSalary;
    }

    public Integer getPensionBase() {
        return pensionBase;
    }

    public void setPensionBase(Integer pensionBase) {
        this.pensionBase = pensionBase;
    }

    public Float getPensionPer() {
        return pensionPer;
    }

    public void setPensionPer(Float pensionPer) {
        this.pensionPer = pensionPer;
    }

    public Integer getMedicalBase() {
        return medicalBase;
    }

    public void setMedicalBase(Integer medicalBase) {
        this.medicalBase = medicalBase;
    }

    public Float getMedicalPer() {
        return medicalPer;
    }

    public void setMedicalPer(Float medicalPer) {
        this.medicalPer = medicalPer;
    }

    public Integer getAccumulationFundBase() {
        return accumulationFundBase;
    }

    public void setAccumulationFundBase(Integer accumulationFundBase) {
        this.accumulationFundBase = accumulationFundBase;
    }

    public Float getAccumulationFundPer() {
        return accumulationFundPer;
    }

    public void setAccumulationFundPer(Float accumulationFundPer) {
        this.accumulationFundPer = accumulationFundPer;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public float getActualSalary() {
        return this.getAllSalary() - (this.getPensionSalary() + this.getMedicalSalary() +
                this.getAccumulationFundSalary());
    }

    public void setActualSalary(Float actualSalary) {
        this.actualSalary = actualSalary;
    }

    @Override
    public String toString() {
        return "SalaryTable{" +
                "id=" + id +
                ", empId=" + empId +
                ", empName='" + empName + '\'' +
                ", month=" + month +
                ", basicSalary=" + basicSalary +
                ", bonus=" + bonus +
                ", lunchSalary=" + lunchSalary +
                ", trafficSalary=" + trafficSalary +
                ", allSalary=" + allSalary +
                ", actualSalary=" + actualSalary +
                ", pensionBase=" + pensionBase +
                ", pensionPer=" + pensionPer +
                ", medicalBase=" + medicalBase +
                ", medicalPer=" + medicalPer +
                ", accumulationFundBase=" + accumulationFundBase +
                ", accumulationFundPer=" + accumulationFundPer +
                '}';
    }
}
