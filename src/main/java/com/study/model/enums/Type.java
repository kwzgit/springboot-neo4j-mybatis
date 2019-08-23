package com.study.model.enums;

/**
 * excel
 * 类型(1：症状，2：体征，3：化验，4：辅检，5：鉴别诊断，6：病史，7：诱因，8：病程，9：其他，91：确诊，92：拟诊，93：警惕)
 */
public enum Type {
    SYMPTOM(1,"症状"),VITAL_RESULT(2,"体征结果"),LIS(3,"化验"),PACS(4,"辅检"),
    DISEASE(5,"鉴别诊断"),HISTORY(6,"病史"),CAUSE(7,"诱因"),PROGNOSIS(8,"病程"),
    OTHER(9,"其他"),QUE(91,"确诊"),NI(92,"拟诊"),HIGH(93,"警惕"),DELETE(94,"删除条件");
    private Integer key;
    private String name;

    Type(Integer key, String name) {
        this.key = key;
        this.name = name;
    }
    public static Type getEnum(Integer key) {
        for (Type item : Type.values()) {
            if (item.key == key) {
                return item;
            }
        }
        return null;
    }
    public static String getName(Integer key) {
        Type item = getEnum(key);
        return item != null ? item.name : null;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
