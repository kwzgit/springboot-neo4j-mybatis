package com.study.model;

/**
 * 推送的诊断类型
 * 包含警惕、确诊、拟诊
 */
public class PushDiagnoseModel {
    private Long id;
    private String name;
    private Long disId;
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
