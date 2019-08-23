package com.study.model;

import java.util.List;

/**
 * 疾病的bean
 */
public class DiseaseModel {
    private Long id;
    private String name;
    private Long disId;
    private Integer emergency;//是否为急诊
    private List<DiseaseModel> diseaseModels;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DiseaseModel> getDiseaseModels() {
        return diseaseModels;
    }

    public void setDiseaseModels(List<DiseaseModel> diseaseModels) {
        this.diseaseModels = diseaseModels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDisId() {
        return disId;
    }

    public void setDisId(Long disId) {
        this.disId = disId;
    }

    public Integer getEmergency() {
        return emergency;
    }

    public void setEmergency(Integer emergency) {
        this.emergency = emergency;
    }
}
