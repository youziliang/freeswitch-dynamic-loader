package com.chuanglan.freeswitch.dynamic.loader.core.enums;

public enum FileExt {

    XLS(".xls"),

    XLSX(".xlsx"),

    CSV(".csv"),

    TXT(".txt");

    FileExt(String desc) {
        this.desc = desc;
    }

    private String desc;

    public String getDesc() {
        return desc;
    }

}
