package com.study.model.bean;

/**
 * Created by fyeman on 2018/1/17.
 */
public class FeatureRate {
    private String featureName;
    private String extraProperty;
    private String desc;
    private String rate;
    private String source;

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getExtraProperty() {
        return extraProperty;
    }

    public void setExtraProperty(String extraProperty) {
        this.extraProperty = extraProperty;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
