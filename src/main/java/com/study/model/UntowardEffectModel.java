package com.study.model;

import java.util.List;

/**
 * 不良反应的bean
 */
public class UntowardEffectModel {
    private String name;//诊断名字
    private String utName; //诊断对应的不良反应名字

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUtName() {
        return utName;
    }

    public void setUtName(String utName) {
        this.utName = utName;
    }
}
