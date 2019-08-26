package com.study.model.bean;

import java.util.ArrayList;
import java.util.Map;

public class FuzhenFilnlly {
    private Map<String,Map<String,Integer>> adverseEvent;
    private ArrayList<Drugs> treatment;

    public ArrayList<Drugs> getTreatment() {
        return treatment;
    }

    public void setTreatment(ArrayList<Drugs> treatment) {
        this.treatment = treatment;
    }

    public Map<String, Map<String, Integer>> getAdverseEvent() {
        return adverseEvent;
    }

    public void setAdverseEvent(Map<String, Map<String, Integer>> adverseEvent) {
        this.adverseEvent = adverseEvent;
    }
}
