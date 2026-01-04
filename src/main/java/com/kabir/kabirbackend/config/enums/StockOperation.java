package com.kabir.kabirbackend.config.enums;

public enum StockOperation {

    DELETE_FROM_STOCK(1),
    ADD_TO_STOCK(2),
    EDIT_STOCK(3);


    private final int value;

    StockOperation(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
