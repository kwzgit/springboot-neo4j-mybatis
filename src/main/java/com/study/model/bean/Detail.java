package com.study.model.bean;

/**
 * 指标的详细信息
 */
public class Detail {
    private String name;
    private Integer value;//0-未选中，1-选中

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
