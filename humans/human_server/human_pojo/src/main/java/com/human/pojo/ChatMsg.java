package com.human.pojo;

import java.util.Date;

/**
 * @author Li JieGui
 * @date 2020/12/11 19:42
 * 聊天实体类
 */
public class ChatMsg {
    private String from;
    private String to;
    private String msg;
    private String nickname;
    private Date date;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
