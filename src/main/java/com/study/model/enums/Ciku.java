package com.study.model.enums;

public enum Ciku {
    SYMPTOM("1","1,70"),VITAL_RESULT("2","35,70"),BIGlIS("3","12"),SUBLIS("subLis","13")
    ,RESULTLIS("resultLis","14,70"),PACS("4","16"),RESULTPACS("resultPacs","17,70"),DISEASE("5","18")
    ,HISTORY("6","18,70"),CAUSE("7","5,70"),PROGNOSIS("8","70"),OTHER("9","70");
    private String name;
    private String label;

    Ciku(String name, String label) {
        this.name = name;
        this.label = label;
    }
    public static Ciku getEnum(String name) {
        for (Ciku item : Ciku.values()) {
            if (item.name.equals(name) ) {
                return item;
            }
        }
        return null;
    }
    public static String getLabel(String name) {
        Ciku item = getEnum(name);
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
