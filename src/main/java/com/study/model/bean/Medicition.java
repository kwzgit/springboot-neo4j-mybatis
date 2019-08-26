package com.study.model.bean;

/**
 * 药
 */
public class Medicition {
    private String medicitionName;//药名
    private String rate;//使用率
    private Integer isShow;//0不展示,1展示
    private Integer forbidden;// 0:正常,1:慎用,2:禁忌"

    public String getMedicitionName() {
        return medicitionName;
    }

    public void setMedicitionName(String medicitionName) {
        this.medicitionName = medicitionName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getForbidden() {
        return forbidden;
    }

    public void setForbidden(Integer forbidden) {
        this.forbidden = forbidden;
    }

    public Medicition() {
    }

    public Medicition(String medicitionName, String rate, Integer isShow, Integer forbidden) {
        this.medicitionName = medicitionName;
        this.rate = rate;
        this.isShow = isShow;
        this.forbidden = forbidden;
    }
}
