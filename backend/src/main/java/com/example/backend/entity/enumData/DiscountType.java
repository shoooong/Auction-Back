package com.example.backend.entity.enumData;

public enum DiscountType {
    PERCENT("%"),
    FIXED("원");

    private String symbol;

    DiscountType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol(){
        return symbol;
    }


}