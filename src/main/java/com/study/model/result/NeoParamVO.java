package com.study.model.result;

import java.io.Serializable;
import java.util.List;

public class NeoParamVO implements Serializable {

    private List<String> inputs;//输入的词
    private String webDiag;
    protected Integer disType;

    public List<String> getInputs() {
        return inputs;
    }

    public void setInputs(List<String> inputs) {
        this.inputs = inputs;
    }

    public String getWebDiag() {
        return webDiag;
    }

    public void setWebDiag(String webDiag) {
        this.webDiag = webDiag;
    }

    public Integer getDisType() {
        return disType;
    }

    public void setDisType(Integer disType) {
        this.disType = disType;
    }
}
