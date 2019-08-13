package com.huzhipeng.coin.entity;

public class Notification {
    private String content;
    private String title;
    private String twoMinuteMaxTrade;

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
