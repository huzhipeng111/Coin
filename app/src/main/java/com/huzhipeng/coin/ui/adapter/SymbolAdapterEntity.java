package com.huzhipeng.coin.ui.adapter;

import com.huzhipeng.coin.db.CoinEntity;
import com.huzhipeng.coin.entity.Symbol;
import com.huzhipeng.coin.entity.SymbolTickerEvent;

import java.math.BigDecimal;

public class SymbolAdapterEntity {
    public SymbolAdapterEntity() {
    }

    public SymbolTickerEvent getSymbol() {
        return symbol;
    }

    public void setSymbol(SymbolTickerEvent symbol) {
        this.symbol = symbol;
    }

    public SymbolAdapterEntity(CoinEntity coinEntity, SymbolTickerEvent symbol) {
        this.coinEntity = coinEntity;
        this.symbol = symbol;
        mustHeighPrice = BigDecimal.ZERO;
    }




    public SymbolAdapterEntity(SymbolTickerEvent symbol) {
        this.symbol = symbol;
    }

    public CoinEntity getCoinEntity() {
        return coinEntity;
    }

    public void setCoinEntity(CoinEntity coinEntity) {
        this.coinEntity = coinEntity;
    }

    private CoinEntity coinEntity;
    private SymbolTickerEvent symbol;


    public BigDecimal getMustHeighPrice() {
        return mustHeighPrice;
    }

    public void setMustHeighPrice(BigDecimal mustHeighPrice) {
        this.mustHeighPrice = mustHeighPrice;
    }

    //24小时内的最高价
    private BigDecimal mustHeighPrice;

    //5分钟之前的价格
    private BigDecimal fiveMinutePrice;




    public BigDecimal getFiveMinutePrice() {
        return fiveMinutePrice;
    }

    public void setFiveMinutePrice(BigDecimal fiveMinutePrice) {
        this.fiveMinutePrice = fiveMinutePrice;
    }


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
}
