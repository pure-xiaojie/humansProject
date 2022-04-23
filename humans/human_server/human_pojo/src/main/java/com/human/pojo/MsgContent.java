package com.human.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
/**
 * @author Li JieGui
 * @date 2021/4/3 21:25
 * 公告实体类
 */
public class MsgContent {
    private Integer id;

    private String title;

    private String message;

    private String img;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "MsgContent{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", img='" + img + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}