package com.human.pojo.job;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.human.pojo.Hr;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Li JieGui
 * @date 2021/3/28 9:56
 * 招聘信息实体
 */
public class Job implements Serializable {
    private Integer id;

    private Integer hr_id;

    private String p_name;

    private String p_key;

    private String p_salary;

    private String p_location;

    private String p_languages;

    private String p_years;

    private String p_tags;

    private String p_jobDetail;

    private String p_jobContent;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date p_createDate;

    private Integer status;

    private Integer sort;

    private Integer visit;
    /**
     * 职位所属的一对一信息
     */
    private Hr hr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHr_id() {
        return hr_id;
    }

    public void setHr_id(Integer hr_id) {
        this.hr_id = hr_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_key() {
        return p_key;
    }

    public void setP_key(String p_key) {
        this.p_key = p_key;
    }

    public String getP_salary() {
        return p_salary;
    }

    public void setP_salary(String p_salary) {
        this.p_salary = p_salary;
    }

    public String getP_location() {
        return p_location;
    }

    public void setP_location(String p_location) {
        this.p_location = p_location;
    }

    public String getP_languages() {
        return p_languages;
    }

    public void setP_languages(String p_languages) {
        this.p_languages = p_languages;
    }

    public String getP_years() {
        return p_years;
    }

    public void setP_years(String p_years) {
        this.p_years = p_years;
    }

    public String getP_tags() {
        return p_tags;
    }

    public void setP_tags(String p_tags) {
        this.p_tags = p_tags;
    }

    public String getP_jobDetail() {
        return p_jobDetail;
    }

    public void setP_jobDetail(String p_jobDetail) {
        this.p_jobDetail = p_jobDetail;
    }

    public String getP_jobContent() {
        return p_jobContent;
    }

    public void setP_jobContent(String p_jobContent) {
        this.p_jobContent = p_jobContent;
    }

    public Date getP_createDate() {
        return p_createDate;
    }

    public void setP_createDate(Date p_createDate) {
        this.p_createDate = p_createDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Hr getHr() {
        return hr;
    }

    public void setHr(Hr hr) {
        this.hr = hr;
    }

    public Integer getVisit() {
        return visit;
    }

    public void setVisit(Integer visit) {
        this.visit = visit;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", hr_id=" + hr_id +
                ", p_name='" + p_name + '\'' +
                ", p_key='" + p_key + '\'' +
                ", p_salary='" + p_salary + '\'' +
                ", p_location='" + p_location + '\'' +
                ", p_languages='" + p_languages + '\'' +
                ", p_years='" + p_years + '\'' +
                ", p_tags='" + p_tags + '\'' +
                ", p_jobDetail='" + p_jobDetail + '\'' +
                ", p_jobContent='" + p_jobContent + '\'' +
                ", p_createDate=" + p_createDate +
                ", status=" + status +
                ", sort=" + sort +
                ", visit=" + visit +
                ", hr=" + hr +
                '}';
    }
}
