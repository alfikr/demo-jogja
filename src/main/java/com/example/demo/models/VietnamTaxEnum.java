package com.example.demo.models;

public enum VietnamTaxEnum {
    SINGLE(15000000),MARRIED(30000000);

    private Integer value;

    VietnamTaxEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}