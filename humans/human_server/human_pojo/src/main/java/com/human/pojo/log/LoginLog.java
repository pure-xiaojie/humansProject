package com.human.pojo.log;

import java.util.Date;

/**
 * @author Li JieGui
 * @date 2021/4/8 22:18
 */
public class LoginLog {
    private Integer id;
    private String userName;
    private Integer status;
    private String ip;
    private String location;
    private String browser;
    private String os;
    private String msg;
    private Date createDate;

    public LoginLog() {

    }

    public LoginLog(String name, String ip, String browser, String msg, String address, int status, String os) {
        this.userName = name;
        this.ip = ip;
        this.browser = browser;
        this.msg = msg;
        this.location = address;
        this.status = status;
        this.os = os;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "LoginLog{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", status=" + status +
                ", ip='" + ip + '\'' +
                ", location='" + location + '\'' +
                ", browser='" + browser + '\'' +
                ", os='" + os + '\'' +
                ", msg='" + msg + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
