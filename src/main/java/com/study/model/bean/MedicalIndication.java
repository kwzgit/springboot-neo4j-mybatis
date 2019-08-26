package com.study.model.bean;

import java.util.List;

/**
 * 右边的指标推送(肾功能不全)
 */
public class MedicalIndication {
    private String name;
    private List<MedicalIndicationDetail> details;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MedicalIndicationDetail> getDetails() {
        return details;
    }

    public void setDetails(List<MedicalIndicationDetail> details) {
        this.details = details;
    }
}
