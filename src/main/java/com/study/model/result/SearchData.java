package com.study.model.result;

import com.study.model.bean.FeatureRate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchData {
    protected int length = 10;
    protected int age_start = 0;
    protected int age_end = 200;
    protected int age = 0;
    protected String sex;
    // 搜索结果的贝叶斯阈值
    protected String threshold = "0";
    //特征类别
    protected String featureType;
    //诊断类别
    protected Integer disType; //0:复诊,1:急诊
    //特征类别对","进行分割后数据
    protected String[] featureTypes;
    //门诊 住院分类
    protected String resourceType;
    //外部系统编码 用于返回映射数据，如果sysCode为空或null，则返回kl_standard_info标准名称
    protected String sysCode;
    protected String chief  = "";
    protected String symptom = "";
    protected String vital = "";
    protected String lis = "";
    protected String pacs = "";
    protected String diag = "";
    protected String past = "";
    protected String other = "";
    //大数据推送诊断结果信息
    protected List<FeatureRate> pushDiags = new ArrayList<FeatureRate>();
    //量表
    protected String scaleName = "";

    //指标结果
    protected String indications="";
    //模型
    protected String algorithmClassifyValue;
    //推送条件
    private Map<String, Map<String, String>> inputs = new HashMap<String, Map<String, String>>(10, 0.8f);
    //知识图谱推送条件
    private Map<String, Map<String, String>> graphInputs = new HashMap<String, Map<String, String>>(10, 0.8f);
    //阴性 页面录入数据需要对结果过滤的集合
    private Map<String, Map<String, String>> filters = new HashMap<String, Map<String, String>>(10, 0.8f);
    private List<String> inputsList = new ArrayList<String>();

    public Integer getDisType() {
        return disType;
    }

    public void setDisType(Integer disType) {
        this.disType = disType;
    }


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getAge_start() {
        return age_start;
    }

    public void setAge_start(int age_start) {
        this.age_start = age_start;
    }

    public int getAge_end() {
        return age_end;
    }

    public void setAge_end(int age_end) {
        this.age_end = age_end;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFeatureType() {
        return featureType;
    }

    public void setFeatureType(String featureType) {
        this.featureType = featureType;
    }

    public String[] getFeatureTypes() {
        return featureTypes;
    }

    public void setFeatureTypes(String[] featureTypes) {
        this.featureTypes = featureTypes;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public float getThreshold() { return Float.parseFloat(threshold); }

    public Map<String, Map<String, String>> getInputs() {
        return inputs;
    }

    public void setInputs(Map<String, Map<String, String>> inputs) {
        this.inputs = inputs;
    }

    public String getChief() {
        return chief;
    }

    public void setChief(String chief) {
        this.chief = chief;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getVital() {
        return vital;
    }

    public void setVital(String vital) {
        this.vital = vital;
    }

    public String getLis() {
        return lis;
    }

    public void setLis(String lis) {
        this.lis = lis;
    }

    public String getPacs() {
        return pacs;
    }

    public void setPacs(String pacs) {
        this.pacs = pacs;
    }

    public String getDiag() {
        return diag;
    }

    public void setDiag(String diag) {
        this.diag = diag;
    }

    public String getPast() {
        return past;
    }

    public void setPast(String past) {
        this.past = past;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getScaleName() {
        return scaleName;
    }

    public void setScaleName(String scaleName) {
        this.scaleName = scaleName;
    }

    public String getAlgorithmClassifyValue() {
        return algorithmClassifyValue;
    }

    public void setAlgorithmClassifyValue(String algorithmClassifyValue) {
        this.algorithmClassifyValue = algorithmClassifyValue;
    }

    public List<FeatureRate> getPushDiags() {
        return pushDiags;
    }

    public void setPushDiags(List<FeatureRate> pushDiags) {
        this.pushDiags = pushDiags;
    }

    public Map<String, Map<String, String>> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, Map<String, String>> filters) {
        this.filters = filters;
    }

    public String getIndications() {
        return indications;
    }

    public void setIndications(String indications) {
        this.indications = indications;
    }

    public Map<String, Map<String, String>> getGraphInputs() {
        return graphInputs;
    }

    public void setGraphInputs(Map<String, Map<String, String>> graphInputs) {
        this.graphInputs = graphInputs;
    }

    public List<String> getInputsList() {
        return inputsList;
    }

    public void setInputsList(List<String> inputsList) {
        this.inputsList = inputsList;
    }
}
