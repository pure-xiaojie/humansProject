package com.human.pojo;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/11/26 19:25
 * 分页查询实体类
 */
public class RespPageBean {
    private Long total;
    private List<?> data;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
