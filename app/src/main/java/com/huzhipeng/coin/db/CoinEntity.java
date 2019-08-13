package com.huzhipeng.coin.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.junit.Ignore;

@Entity
public class CoinEntity {
    @Id(autoincrement = true)
    private Long id;
    //代币名称
    private String symbol;
    //代币类型，0 主流币，1 垃圾币不考虑的币， 2 需要监测的币
    private int coinType;
    //解锁的时间戳
    private long unIngnoreTime;
    //是否在忽略期间
    private boolean inIgnore;
    private int decimal;
    @Generated(hash = 177697228)
    public CoinEntity() {
    }
    @Generated(hash = 258337967)
    public CoinEntity(Long id, String symbol, int coinType, long unIngnoreTime,
            boolean inIgnore, int decimal) {
        this.id = id;
        this.symbol = symbol;
        this.coinType = coinType;
        this.unIngnoreTime = unIngnoreTime;
        this.inIgnore = inIgnore;
        this.decimal = decimal;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSymbol() {
        return this.symbol;
    }
    public void setSymbol(String Symbol) {
        this.symbol = Symbol;
    }
    public int getCoinType() {
        return this.coinType;
    }
    public void setCoinType(int coinType) {
        this.coinType = coinType;
    }
    public long getUnIngnoreTime() {
        return this.unIngnoreTime;
    }
    public void setUnIngnoreTime(long unIngnoreTime) {
        this.unIngnoreTime = unIngnoreTime;
    }
    public boolean getInIgnore() {
        return this.inIgnore;
    }
    public void setInIgnore(boolean inIgnore) {
        this.inIgnore = inIgnore;
    }
    public int getDecimal() {
        return this.decimal;
    }
    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }
}