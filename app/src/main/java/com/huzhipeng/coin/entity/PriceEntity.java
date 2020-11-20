package com.huzhipeng.coin.entity;

import java.math.BigDecimal;

public class PriceEntity {
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    private String symbol;
    private BigDecimal lastPrice;
    private PriceType priceType;
    private long timeStamp;
    public enum PriceType{
        Low,Hiht,Normal
    }
}
