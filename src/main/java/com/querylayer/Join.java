package com.querylayer;

public class Join {
    public String joinType;
    public String table;
    public String column1;
    public String column2;

    public Join(String joinType, String table, String column1, String column2) {
        this.joinType = joinType;
        this.table = table;
        this.column1 = column1;
        this.column2 = column2;
    }
}