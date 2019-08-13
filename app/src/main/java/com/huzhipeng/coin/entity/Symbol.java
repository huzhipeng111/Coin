package com.huzhipeng.coin.entity;

public class Symbol {
    public Symbol(String s) {
        this.s = s;
    }

    public Symbol() {
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "e='" + e + '\'' +
                ", E=" + E +
                ", s='" + s + '\'' +
                ", p='" + p + '\'' +
                ", P='" + P + '\'' +
                ", w='" + w + '\'' +
                ", x='" + x + '\'' +
                ", c='" + c + '\'' +
                ", Q='" + Q + '\'' +
                ", b='" + b + '\'' +
                ", B='" + B + '\'' +
                ", a='" + a + '\'' +
                ", A='" + A + '\'' +
                ", o='" + o + '\'' +
                ", h='" + h + '\'' +
                ", l='" + l + '\'' +
                ", v='" + v + '\'' +
                ", q='" + q + '\'' +
                ", O=" + O +
                ", C=" + C +
                ", F=" + F +
                ", L=" + L +
                ", n=" + n +
                ", index=" + index +
                '}';
    }

    /**
     * e : 24hrTicker
     * E : 1557456175990
     * s : ZECTUSD
     * p : -2.84000000
     * P : -4.873
     * w : 55.32432217
     * x : 58.28000000
     * c : 55.44000000
     * Q : 1.94892000
     * b : 55.23000000
     * B : 4.42199000
     * a : 56.06000000
     * A : 1.94892000
     * o : 58.28000000
     * h : 58.28000000
     * l : 54.31000000
     * v : 384.96502000
     * q : 21297.92879240
     * O : 1557369775988
     * C : 1557456175988
     * F : 12830
     * L : 12894
     * n : 65
     */

    private String e;
    private long E;
    private String s;
    private String p;
    private String P;
    private String w;
    private String x;
    private String c;
    private String Q;
    private String b;
    private String B;
    private String a;
    private String A;
    private String o;
    private String h;
    private String l;
    private String v;
    private String q;
    private long O;
    private long C;
    private int F;
    private int L;
    private int n;
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String gete() {
        return e;
    }

    public void setE(String e) {
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

    public String getp() {
        return p;
    }

    public void setp(String p) {
        this.p = p;
    }

    public String getP() {
        return P;
    }

    public void setP(String P) {
        this.P = P;
    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getc() {
        return c;
    }

    public void setc(String c) {
        this.c = c;
    }

    public String getQ() {
        return Q;
    }

    public void setQ(String Q) {
        this.Q = Q;
    }

    public String getb() {
        return b;
    }

    public void setb(String b) {
        this.b = b;
    }

    public String getB() {
        return B;
    }

    public void setB(String B) {
        this.B = B;
    }

    public String geta() {
        return a;
    }

    public void seta(String a) {
        this.a = a;
    }

    public String getA() {
        return A;
    }

    public void setA(String A) {
        this.A = A;
    }

    public String geto() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getl() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getq() {
        return q;
    }

    public void setq(String q) {
        this.q = q;
    }

    public long getO() {
        return O;
    }

    public void setO(long O) {
        this.O = O;
    }

    public long getC() {
        return C;
    }

    public void setC(long C) {
        this.C = C;
    }

    public int getF() {
        return F;
    }

    public void setF(int F) {
        this.F = F;
    }

    public int getL() {
        return L;
    }

    public void setL(int L) {
        this.L = L;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
