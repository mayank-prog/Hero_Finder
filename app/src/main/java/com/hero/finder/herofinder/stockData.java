package com.hero.finder.herofinder;

public class stockData {
    String Low, High, Close, Open;
    String c_name;

    public stockData(String low, String high, String close, String open, String c_name) {
        Low = low;
        High = high;
        Close = close;
        Open = open;
        this.c_name = c_name;
    }

    public String getLow() {
        return Low;
    }

    public void setLow(String low) {
        Low = low;
    }

    public String getHigh() {
        return High;
    }

    public void setHigh(String high) {
        High = high;
    }

    public String getClose() {
        return Close;
    }

    public void setClose(String close) {
        Close = close;
    }

    public String getOpen() {
        return Open;
    }

    public void setOpen(String open) {
        Open = open;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }
}
