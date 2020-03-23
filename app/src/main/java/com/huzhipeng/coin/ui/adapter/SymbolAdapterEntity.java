package com.huzhipeng.coin.ui.adapter;

import com.huzhipeng.coin.db.CoinEntity;
import com.huzhipeng.coin.entity.Symbol;

import java.math.BigDecimal;

public class SymbolAdapterEntity {
    public SymbolAdapterEntity() {
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public double getFiveSecondsAverageTradingVolume() {
        return fiveSecondsAverageTradingVolume;
    }

    public SymbolAdapterEntity(CoinEntity coinEntity, Symbol symbol) {
        this.coinEntity = coinEntity;
        this.symbol = symbol;
        mustHeighPrice = BigDecimal.ZERO;
    }

    public void setFiveSecondsAverageTradingVolume(double fiveSecondsAverageTradingVolume) {
        this.fiveSecondsAverageTradingVolume = fiveSecondsAverageTradingVolume;
    }


    public SymbolAdapterEntity(Symbol symbol) {
        this.symbol = symbol;
    }

    public CoinEntity getCoinEntity() {
        return coinEntity;
    }

    public void setCoinEntity(CoinEntity coinEntity) {
        this.coinEntity = coinEntity;
    }

    private CoinEntity coinEntity;
    private Symbol symbol;
    //过去5条数据中的平均交易量
    private double fiveSecondsAverageTradingVolume;

    //5秒内的标准差
    private double fiveSecondStandardDeviation;

    //最低价与现价的差值百分比
    private BigDecimal lowPrecent;

    //10秒之内是否有6秒的交易次数大于6
    private boolean reachLow;

    public BigDecimal getMustHeighPrice() {
        return mustHeighPrice;
    }

    public void setMustHeighPrice(BigDecimal mustHeighPrice) {
        this.mustHeighPrice = mustHeighPrice;
    }

    //24小时内的最高价
    private BigDecimal mustHeighPrice;

    //24小时内最低价
    private BigDecimal lowPrice;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    //当前的价格
    private BigDecimal price;

    public BigDecimal getLowPrecent() {
        return lowPrecent;
    }

    public void setLowPrecent(BigDecimal lowPrecent) {
        this.lowPrecent = lowPrecent;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    //5分钟之前的价格
    private BigDecimal fiveMinutePrice;
    //两分钟之内的交易次数
    private int twoMinuteTradeCount;

    public int getTwoMinuteTradeCount() {
        return twoMinuteTradeCount;
    }

    public void setTwoMinuteTradeCount(int twoMinuteTradeCount) {
        this.twoMinuteTradeCount = twoMinuteTradeCount;
    }

    public BigDecimal getFiveMinutePrice() {
        return fiveMinutePrice;
    }

    public void setFiveMinutePrice(BigDecimal fiveMinutePrice) {
        this.fiveMinutePrice = fiveMinutePrice;
    }

    public long getFiveMinuteTime() {
        return fiveMinuteTime;
    }

    public void setFiveMinuteTime(long fiveMinuteTime) {
        this.fiveMinuteTime = fiveMinuteTime;
    }

    //记录5分钟之前价格的时间
    private long fiveMinuteTime;


    public boolean isReachLow() {
        return reachLow;
    }

    public void setReachLow(boolean reachLow) {
        this.reachLow = reachLow;
    }

    public double getFiveSecondStandardDeviation() {
        return fiveSecondStandardDeviation;
    }

    public void setFiveSecondStandardDeviation(double fiveSecondStandardDeviation) {
        this.fiveSecondStandardDeviation = fiveSecondStandardDeviation;
    }

    //最后一秒的交易量
    private int lastTradingVolume;

    public BigDecimal getTenMinuteGain() {
        return tenMinuteGain;
    }

    public void setTenMinuteGain(BigDecimal tenMinuteGain) {
        this.tenMinuteGain = tenMinuteGain;
    }

    //十秒钟之内的涨幅
    private BigDecimal tenMinuteGain;

    public BigDecimal getGain5m() {
        return gain5m;
    }

    public void setGain5m(BigDecimal gain5m) {
        this.gain5m = gain5m;
    }

    //5分钟内的涨幅
    private BigDecimal gain5m;

    public BigDecimal getGain24() {
        return gain24;
    }

    public void setGain24(BigDecimal gain24) {
        this.gain24 = gain24;
    }

    //24小时的涨幅
    private BigDecimal gain24;

    public int getLastTradingVolume() {
        return lastTradingVolume;
    }

    public void setLastTradingVolume(int lastTradingVolume) {
        this.lastTradingVolume = lastTradingVolume;
    }
}
