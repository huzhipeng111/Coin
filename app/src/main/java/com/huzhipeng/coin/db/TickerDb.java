package com.huzhipeng.coin.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TickerDb {

    /**
     * e : 24hrTicker
     * E : 1590917967037
     * s : BTCUSDT
     * p : 66.98000000
     * P : 0.706
     * w : 9572.90707589
     * x : 9493.05000000
     * c : 9559.99000000
     * Q : 0.26278100
     * b : 9559.99000000
     * B : 0.99900000
     * a : 9560.00000000
     * A : 0.30972200
     * o : 9493.01000000
     * h : 9740.00000000
     * l : 9400.00000000
     * v : 52275.37528200
     * q : 500427309.93204661
     * O : 1590831567026
     * C : 1590917967026
     * F : 331227998
     * L : 331873883
     * n : 645886
     */

    /**
     * {
     *   "e": "24hrTicker",  // 事件类型
     *   "E": 123456789,     // 事件时间
     *   "s": "BNBBTC",      // 交易对
     *   "p": "0.0015",      // 24小时价格变化
     *   "P": "250.00",      // 24小时价格变化（百分比）
     *   "w": "0.0018",      // 平均价格
     *   "x": "0.0009",      // 整整24小时之前，向前数的最后一次成交价格
     *   "c": "0.0025",      // 最新成交价格
     *   "Q": "10",          // 最新成交交易的成交量
     *   "b": "0.0024",      // 目前最高买单价
     *   "B": "10",          // 目前最高买单价的挂单量
     *   "a": "0.0026",      // 目前最低卖单价
     *   "A": "100",         // 目前最低卖单价的挂单量
     *   "o": "0.0010",      // 整整24小时前，向后数的第一次成交价格
     *   "h": "0.0025",      // 24小时内最高成交价
     *   "l": "0.0010",      // 24小时内最低成交加
     *   "v": "10000",       // 24小时内成交量
     *   "q": "18",          // 24小时内成交额
     *   "O": 0,             // TODO 0 ??? 统计开始时间？？？
     *   "C": 86400000,      // TODO 3600*24*1000，24小时的毫秒数 ??? 统计关闭时间？？？
     *   "F": 0,             // 24小时内第一笔成交交易ID
     *   "L": 18150,         // 24小时内最后一笔成交交易ID
     *   "n": 18151          // 24小时内成交数
     * }
     *
     */

    @Id(autoincrement = true)
    private Long id;

    @Generated(hash = 411809145)
    public TickerDb(Long id) {
        this.id = id;
    }

    @Generated(hash = 2027430985)
    public TickerDb() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
