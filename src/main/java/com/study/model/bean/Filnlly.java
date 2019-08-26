package com.study.model.bean;

import java.util.ArrayList;
import java.util.List;

public class Filnlly {
    private List<Indicators> adverseEvent;
    private ArrayList<Drugs> treatment;


    public List<Indicators> getAdverseEvent() {
        return adverseEvent;
    }

    public void setAdverseEvent(List<Indicators> adverseEvent) {
        this.adverseEvent = adverseEvent;
    }

    public ArrayList<Drugs> getTreatment() {
        return treatment;
    }

    public void setTreatment(ArrayList<Drugs> treatment) {
        this.treatment = treatment;
    }

}
