package com.huzhipeng.coin.entity;

public class Trade {

    /**
     * e : trade
     * E : 1591622084695
     * s : BTCUSDT
     * t : 337249979
     * p : 9714.84000000
     * q : 0.00803800
     * b : 2424285101
     * a : 2424285137
     * T : 1591622084694
     * m : true
     * M : true
     */

    private String e;
    private long E;
    private String s;
    private int t;
    private String p;
    private String q;
    private long b;
    private long a;
    private long T;
    private boolean m;
    private boolean M;

    public String gete() {
        return e;
    }

    public void sete(String e) {
        this.e = e;
    }

    public long getE() {
        return E;
    }

    public void setE(long E) {
        this.E = E;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public int gett() {
        return t;
    }

    public void sett(int t) {
        this.t = t;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public long getB() {
        return b;
    }

    public void setB(long b) {
        this.b = b;
    }

    public long getA() {
        return a;
    }

    public void setA(long a) {
        this.a = a;
    }

    public long getT() {
        return T;
    }

    public void setT(long T) {
        this.T = T;
    }

    public boolean ism() {
        return m;
    }

    public void setm(boolean m) {
        this.m = m;
    }

    public boolean isM() {
        return M;
    }

    public void setM(boolean M) {
        this.M = M;
    }
}
