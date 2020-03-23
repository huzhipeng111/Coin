package com.huzhipeng.coin.entity.huobi;

public class Unsub {

    public Unsub(String op, String topic, String cid) {
        this.op = op;
        this.topic = topic;
        this.cid = cid;
    }

    /**
     * op : unsub
     * topic : accounts
     * cid : 40sG903yz80oDFWr
     */

    private String op;
    private String topic;
    private String cid;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
