package com.study.model.bean;

import org.json.JSONObject;

import java.util.Map;

public class MangementEvaluation {
    private Map<String,JSONObject> mangementEvaluation;

    public Map<String, JSONObject> getMangementEvaluation() {
        return mangementEvaluation;
    }

    public void setMangementEvaluation(Map<String, JSONObject> mangementEvaluation) {
        this.mangementEvaluation = mangementEvaluation;
    }
}
