package com.human.pojo;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Li JieGui
 * @date 2020/11/27 15:06
 * 政治面貌实体
 */
public class Politicsstatus implements Serializable {
    private Integer id;

    private String name;

    public Politicsstatus() {
    }

    public Politicsstatus(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Politicsstatus that = (Politicsstatus) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}