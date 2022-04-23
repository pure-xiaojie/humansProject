package com.human.pojo;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Li JieGui
 * @date 2020/11/27 15:06
 * 民族实体
 */
public class Nation implements Serializable {
    private Integer id;

    private String name;

    public Nation() {
    }

    public Nation(String value) {
        this.name = value;
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
        Nation nation = (Nation) o;
        return Objects.equals(name, nation.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}