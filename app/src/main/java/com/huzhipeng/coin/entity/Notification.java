package com.huzhipeng.coin.entity;

public class Notification {
    private String content;
    private String title;
    private String twoMinuteMaxTrade;
    private boolean isBinance;

    public Notification(String content, String title, String twoMinuteMaxTrade, boolean isBinance) {
        this.content = content;
        this.title = title;
        this.twoMinuteMaxTrade = twoMinuteMaxTrade;
        this.isBinance = isBinance;
    }

    public boolean isBinance() {
        return isBinance;
    }

    public void setBinance(boolean binance) {
        isBinance = binance;
    }

    public Notification(String content, String title, String twoMinuteMaxTrade) {
        this.content = content;
        this.title = title;
        this.twoMinuteMaxTrade = twoMinuteMaxTrade;
    }

    public String getTwoMinuteMaxTrade() {
        return twoMinuteMaxTrade;
    }

    public void setTwoMinuteMaxTrade(String twoMinuteMaxTrade) {
        this.twoMinuteMaxTrade = twoMinuteMaxTrade;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Notification(String content, String title) {
        this.content = content;
        this.title = title;
    }
    public Notification(String content, String title, boolean isBinance) {
        this.content = content;
        this.title = title;
        this.isBinance = isBinance;
    }

    public Notification(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
