package com.example.demo.models;


public enum IndonesiaTaxEnum {
    SINGLE("TK",25000000),MARRIED("K0",50000000),MARRIED_HAVE_CHILD("K!",75000000);
    private String code;
    private int value;

    IndonesiaTaxEnum(String code, int value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public int getValue() {
        return value;
    }
}