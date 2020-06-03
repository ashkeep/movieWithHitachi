package com.lk.movie.dto;


import org.apache.ibatis.type.Alias;

@Alias("RondaInfo")
public class RondaInfo {
    private String cname;   //影院名称
    private String cid;
    private String hno;     //影厅编号
    private int allrow;
    private int allcol;
    private String mid; 	//电影编号
    private String mname;   //影片名称
    private String length;  //片长
    private double price;
    private String language;
    private String aid; 	//场次编号
    private String begintime;
    private String endtime;



    public String getCid() {
        return cid;
    }
    public void setCid(String cid) {
        this.cid = cid;
    }
    public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }
    public String getLength() {
        return length;
    }
    public void setLength(String length) {
        this.length = length;
    }
    public String getMname() {
        return mname;
    }
    public void setMname(String mname) {
        this.mname = mname;
    }
    public int getAllrow() {
        return allrow;
    }
    public void setAllrow(int allrow) {
        this.allrow = allrow;
    }
    public int getAllcol() {
        return allcol;
    }
    public void setAllcol(int allcol) {
        this.allcol = allcol;
    }
    public String getEndtime() {
        return endtime;
    }
    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
    public String getAid() {
        return aid;
    }
    public void setAid(String aid) {
        this.aid = aid;
    }
    public String getHno() {
        return hno;
    }
    public void setHno(String hno) {
        this.hno = hno;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }


    public String getBegintime() {
        return begintime;
    }
    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }
    public String getMid() {
        return mid;
    }
    public void setMid(String mid) {
        this.mid = mid;
    }


}
