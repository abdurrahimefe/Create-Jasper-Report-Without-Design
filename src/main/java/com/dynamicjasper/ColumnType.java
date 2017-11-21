package com.dynamicjasper;

/**
 * Created by abdurrahim.efe on 21.11.2017.
 */
public enum ColumnType {
    HEADER("HEADER"),COLUMN("COLUMN");

    private String name;

    ColumnType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
