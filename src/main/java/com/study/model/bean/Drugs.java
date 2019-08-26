package com.study.model.bean;

import java.util.LinkedList;

/**
 * 药类
 */
public class Drugs {
    private String bigdrugsName;//大药类名
    private String subdrugsName;//小药类名
    private Integer drugsForbidden;//药类的忌用和慎用
    private LinkedList<Medicition> medicitionsList;

    public String getBigdrugsName() {
        return bigdrugsName;
    }

    public void setBigdrugsName(String bigdrugsName) {
        this.bigdrugsName = bigdrugsName;
    }

    public Integer getDrugsForbidden() {
        return drugsForbidden;
    }

    public void setDrugsForbidden(Integer drugsForbidden) {
        this.drugsForbidden = drugsForbidden;
    }

    public String getSubdrugsName() {
        return subdrugsName;
    }

    public void setSubdrugsName(String subdrugsName) {
        this.subdrugsName = subdrugsName;
    }

    public LinkedList<Medicition> getMedicitionsList() {
        return medicitionsList;
    }

    public void setMedicitionsList(LinkedList<Medicition> medicitionsList) {
        this.medicitionsList = medicitionsList;
    }

    public Drugs() {
    }

    public Drugs(String bigdrugsName, String subdrugsName, LinkedList<Medicition> medicitionsList) {
        this.bigdrugsName = bigdrugsName;
        this.subdrugsName = subdrugsName;
        this.medicitionsList = medicitionsList;
    }
}
