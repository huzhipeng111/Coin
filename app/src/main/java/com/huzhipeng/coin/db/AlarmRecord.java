package com.huzhipeng.coin.db;

import com.huzhipeng.coin.utils.TimeUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AlarmRecord {
    @Id(autoincrement = true)
    private Long id;
    //代币名称
    private String symbol;

    private long alarmTime;

    private String alarmPrice;

    //涨幅
    private String gain;

    //24小时涨幅
    private String gain24;

    private int alarmType;

    //最后一秒交易次数
    private int lastTradeVulm;

    //5秒内的标准差
    private int fiveSecondStandardDeviation;

    public int getFiveSecondStandardDeviation() {
        return fiveSecondStandardDeviation;
    }

    public int getFiveSecondsAverageTradingVolume() {
        return fiveSecondsAverageTradingVolume;
    }

    public void setFiveSecondsAverageTradingVolume(int fiveSecondsAverageTradingVolume) {
        this.fiveSecondsAverageTradingVolume = fiveSecondsAverageTradingVolume;
    }

    //5秒内的交易次数平均值
    public int fiveSecondsAverageTradingVolume;

    public void setFiveSecondStandardDeviation(int fiveSecondStandardDeviation) {
        this.fiveSecondStandardDeviation = fiveSecondStandardDeviation;
    }

    public int getLastTradeVulm() {
        return lastTradeVulm;
    }

    public void setLastTradeVulm(int lastTradeVulm) {
        this.lastTradeVulm = lastTradeVulm;
    }


    @Override
    public String toString() {
        return  " 交易对='" + symbol + '\'' +
                "\n 报警时间=" + TimeUtil.getTime(alarmTime) +
                "\n 报警时的价格='" + alarmPrice + '\'' +
                "\n 5分钟涨幅='" + gain + '\'' +
                "\n 24小时涨幅='" + gain24 + '\'' +
                "\n 报警类型=" + getType(alarmType) +
                "\n 最新一秒交易次数=" + yimiaojiaoyiliang +
                "\n 5秒内的标准差=" + fiveSecondStandardDeviation +
                "\n 5秒内的平均值=" + fiveSecondsAverageTradingVolume +
                "\n 收集数据的时间为=" + gainTimeJianju;
    }

    private String getType (int alarmType) {
        if (alarmType == 0) {
            return "平均交易次数";
        } else if (alarmType == 1) {
            return "标准差";
        } else if (alarmType == 2) {
            return "5分钟涨幅";
        } else if (alarmType == 3) {
            return "最新一秒交易次数超过 " + yimiaojiaoyiliang;
        }
        return "";
    }

    //获取的涨幅时间间距
    private int gainTimeJianju;

    //报警时的那一秒的交易笔数
    private int yimiaojiaoyiliang;

    @Generated(hash = 1726303114)
    public AlarmRecord(Long id, String symbol, long alarmTime, String alarmPrice, String gain,
            String gain24, int alarmType, int lastTradeVulm, int fiveSecondStandardDeviation,
            int fiveSecondsAverageTradingVolume, int gainTimeJianju, int yimiaojiaoyiliang) {
        this.id = id;
        this.symbol = symbol;
        this.alarmTime = alarmTime;
        this.alarmPrice = alarmPrice;
        this.gain = gain;
        this.gain24 = gain24;
        this.alarmType = alarmType;
        this.lastTradeVulm = lastTradeVulm;
        this.fiveSecondStandardDeviation = fiveSecondStandardDeviation;
        this.fiveSecondsAverageTradingVolume = fiveSecondsAverageTradingVolume;
        this.gainTimeJianju = gainTimeJianju;
        this.yimiaojiaoyiliang = yimiaojiaoyiliang;
    }

    @Generated(hash = 775679187)
    public AlarmRecord() {
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

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getAlarmTime() {
        return this.alarmTime;
    }

    public void setAlarmTime(long alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmPrice() {
        return this.alarmPrice;
    }

    public void setAlarmPrice(String alarmPrice) {
        this.alarmPrice = alarmPrice;
    }

    public String getGain() {
        return this.gain;
    }

    public void setGain(String gain) {
        this.gain = gain;
    }

    public int getGainTimeJianju() {
        return this.gainTimeJianju;
    }

    public void setGainTimeJianju(int gainTimeJianju) {
        this.gainTimeJianju = gainTimeJianju;
    }

    public String getGain24() {
        return this.gain24;
    }

    public void setGain24(String gain24) {
        this.gain24 = gain24;
    }

    public int getYimiaojiaoyiliang() {
        return this.yimiaojiaoyiliang;
    }

    public void setYimiaojiaoyiliang(int yimiaojiaoyiliang) {
        this.yimiaojiaoyiliang = yimiaojiaoyiliang;
    }

    public int getAlarmType() {
        return this.alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }
}
