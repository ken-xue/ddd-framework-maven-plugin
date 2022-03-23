package io.ddd.framework.enums;

public enum DbType {

    MYSQL("mysql"),
    ORACLE("oracle");

    private String value;

    DbType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
