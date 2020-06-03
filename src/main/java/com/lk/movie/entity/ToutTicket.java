package com.lk.movie.entity;


import org.apache.ibatis.type.Alias;

import java.util.Date;
@Alias("ToutTicket")

public class ToutTicket {
    private int xho;
    private String uname;
    private int rid;
    private String seatno;
    private double realpay;
    private Date payTime;
    private int state;

    public int getXho() {
        return xho;
    }
    public void setXho(int xho) {
        this.xho = xho;
    }
    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    public int getRid() {
        return rid;
    }
    public void setRid(int rid) {
        this.rid = rid;
    }
    public String getSeatno() {
        return seatno;
    }
    public void setSeatno(String seatno) {
        this.seatno = seatno;
    }
    public double getRealpay() {
        return realpay;
    }
    public void setRealpay(double realpay) {
        this.realpay = realpay;
    }
    public Date getPayTime() {
        return payTime;
    }
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
}
