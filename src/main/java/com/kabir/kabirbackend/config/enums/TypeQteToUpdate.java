package com.kabir.kabirbackend.config.enums;

public enum TypeQteToUpdate {

    QTE_STOCK(0),
    QTE_STOCK_IMPORT(1),
    QTE_STOCK_FACTURER(2);

    private final int value;

    TypeQteToUpdate(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
