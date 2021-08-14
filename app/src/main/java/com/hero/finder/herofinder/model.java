package com.hero.finder.herofinder;

public class model {

    String c_name,close,high,low,open;

    public model() {

    }

    public model(String c_name, String close, String high, String low, String open) {
        this.c_name = c_name;
        this.close = close;
        this.high = high;
        this.low = low;
        this.open = open;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }
}
