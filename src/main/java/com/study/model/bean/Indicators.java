package com.study.model.bean;

import java.util.List;

/**
 * 指标
 */
public class Indicators {
    private String name;
    private Integer controlType;//控件类型:1-单选,2-多选
    private List<Detail> details;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getControlType() {
        return controlType;
    }

    public void setControlType(Integer controlType) {
        this.controlType = controlType;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }
}
