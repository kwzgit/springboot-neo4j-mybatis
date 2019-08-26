package com.study.model.bean;

import org.json.JSONObject;

public class MedicalIndicationDetail {
    private Integer type;//1-量表,2-公式,3-其他指标
    private JSONObject content;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public JSONObject getContent() {
        return content;
    }

    public void setContent(JSONObject content) {
        this.content = content;
    }
}
