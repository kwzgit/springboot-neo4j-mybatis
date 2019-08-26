package com.study.model;

/**
 * 药类和药
 */
public class DrugsMedicationModel {
    private String drugs;//药类
    private Integer dSort;//药类排序
    private String medication;//药名//大小类组合
    private String meRate;//药的使用率

    public String getDrugs() {
        return drugs;
    }

    public void setDrugs(String drugs) {
        this.drugs = drugs;
    }

    public Integer getdSort() {
        return dSort;
    }

    public void setdSort(Integer dSort) {
        this.dSort = dSort;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getMeRate() {
        return meRate;
    }

    public void setMeRate(String meRate) {
        this.meRate = meRate;
    }
}
