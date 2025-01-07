package com.querylayer;

public class Condition {
    public String column;
    public String operator;
    public String value;

    public Condition(String column, String operator, String value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }
}
