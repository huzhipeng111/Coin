package com.huzhipeng.coin.entity;


import java.math.BigDecimal;

public class SymbolTickerEvent {
    /**
     *
     */
    private String eventType;
    /**
     *
     */
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public SymbolTickerEvent() {
    }

    public SymbolTickerEvent(String symbol) {
        this.symbol = symbol;
    }

    /**
     * 事件时间
     */
    private Long eventTime;
    /**
     * 交易对名称
     */
    private String symbol;
    /**
     * 24小时价格变化
     */
    private BigDecimal priceChange;
    /**
     * 24小时价格变化(百分比)
     */
    private BigDecimal priceChangePercent;
    /**
     * 平均价格
     */
    private BigDecimal weightedAvgPrice;
    /**
     * 最新成交价格
     */
    private BigDecimal lastPrice;
    /**
     * 最新成交价格上的成交量
     */
    private BigDecimal lastQty;
    /**
     * 24小时内第一比成交的价格
     */
    private BigDecimal open;
    /**
     * 24小时内最高成交价
     */
    private BigDecimal high;
    /**
     * 24小时内最低成交加
     */
    private BigDecimal low;
    /**
     * 24小时内成交量
     */
    private BigDecimal totalTradedBaseAssetVolume;
    /**
     * 24小时内成交额
     */
    private BigDecimal totalTradedQuoteAssetVolume;
    /**
     * 统计开始时间
     */
    private Long openTime;
    /**
     * 统计关闭时间
     */
    private Long closeTime;
    /**
     * 24小时内第一笔成交交易ID
     */
    private Long firstId;
    /**
     * 24小时内最后一笔成交交易ID
     */
    private Long lastId;
    /**
     * 24小时内成交数
     */
    private Long count;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getEventTime() {
        return eventTime;
    }

    public void setEventTime(Long eventTime) {
        this.eventTime = eventTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(BigDecimal priceChange) {
        this.priceChange = priceChange;
    }

    public BigDecimal getPriceChangePercent() {
        return priceChangePercent;
    }

    public void setPriceChangePercent(BigDecimal priceChangePercent) {
        this.priceChangePercent = priceChangePercent;
    }

    public BigDecimal getWeightedAvgPrice() {
        return weightedAvgPrice;
    }

    public void setWeightedAvgPrice(BigDecimal weightedAvgPrice) {
        this.weightedAvgPrice = weightedAvgPrice;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public BigDecimal getLastQty() {
        return lastQty;
    }

    public void setLastQty(BigDecimal lastQty) {
        this.lastQty = lastQty;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getTotalTradedBaseAssetVolume() {
        return totalTradedBaseAssetVolume;
    }

    public void setTotalTradedBaseAssetVolume(BigDecimal totalTradedBaseAssetVolume) {
        this.totalTradedBaseAssetVolume = totalTradedBaseAssetVolume;
    }

    public BigDecimal getTotalTradedQuoteAssetVolume() {
        return totalTradedQuoteAssetVolume;
    }

    public void setTotalTradedQuoteAssetVolume(BigDecimal totalTradedQuoteAssetVolume) {
        this.totalTradedQuoteAssetVolume = totalTradedQuoteAssetVolume;
    }

    public Long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Long openTime) {
        this.openTime = openTime;
    }

    public Long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Long closeTime) {
        this.closeTime = closeTime;
    }

    public Long getFirstId() {
        return firstId;
    }

    public void setFirstId(Long firstId) {
        this.firstId = firstId;
    }

    public Long getLastId() {
        return lastId;
    }

    public void setLastId(Long lastId) {
        this.lastId = lastId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "SymbolTickerEvent{" +
                "eventType='" + eventType + '\'' +
                ", eventTime=" + eventTime +
                ", symbol='" + symbol + '\'' +
                ", priceChange=" + priceChange +
                ", priceChangePercent=" + priceChangePercent +
                ", weightedAvgPrice=" + weightedAvgPrice +
                ", lastPrice=" + lastPrice +
                ", lastQty=" + lastQty +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", totalTradedBaseAssetVolume=" + totalTradedBaseAssetVolume +
                ", totalTradedQuoteAssetVolume=" + totalTradedQuoteAssetVolume +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                ", firstId=" + firstId +
                ", lastId=" + lastId +
                ", count=" + count +
                '}';
    }
}
