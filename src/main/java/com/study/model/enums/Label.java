package com.study.model.enums;

public enum Label {
    SYMPTOM("1","Symptom"),VITAL_RESULT("2","Vital"),LIS("3","LIS"),PACS("4","PACS"),
    DISEASE("5","Disease"),HISTORY("6","History"),CAUSE("7","Cause"),PROGNOSIS("8","Other"),
    OTHER("9","Other"),CONDITION("诊断依据","Condition"),NI("92","Condition"),QUE("91","Condition"),
    HIGH("93","Condition"),DELETE("94","Condition"),
    LISRESULT("化验结果","LISResult"),PACSRESULT("辅检结果","PACSResult");
    private String name;
    private String label;

    Label(String name, String label) {
        this.name = name;
        this.label = label;
    }
    public static Label getEnum(String name) {
        for (Label item : Label.values()) {
            if (item.name.equals(name) ) {
                return item;
            }
        }
        return null;
    }
    public static String getLabel(String name) {
        Label item = getEnum(name);
        return item != null ? item.label : null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
