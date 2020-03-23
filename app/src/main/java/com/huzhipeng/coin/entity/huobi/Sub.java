package com.huzhipeng.coin.entity.huobi;

public class Sub {
    private String sub;
    private String id;

    public Sub(String sub, String id) {
        this.sub = sub;
        this.id = id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
