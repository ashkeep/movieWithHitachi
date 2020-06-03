package com.lk.movie.entity;


import org.apache.ibatis.type.Alias;

import java.util.Date;
@Alias("TMovie")
public class TMovie {
    private String mid;
    private String mname;
    private String length; //片长
    private String area;  //制作地区
    private String type;  //类型
    private String director; //导演
    private String actor; //主演
    private int scorenumz; // 评分整数
    private int scorenumx; // 评分小数
    private String state; //状态
    private String details; //影片简介
    private byte[] cover; //影片封面
    private Date bgDate;      //上映时间
    private String sealnum;//票房
    private int wantosee;//想看
    private int rownum;
    private double scorenum;

    public TMovie(){
        this.sealnum = "0";
    }


    public double getScorenum() {
        return scorenum;
    }
    public void setScorenum(double scorenum) {
        this.scorenum = scorenum;
    }
    public int getRownum() {
        return rownum;
    }
    public void setRownum(int rownum) {
        this.rownum = rownum;
    }
    public int getWantosee() {
        return wantosee;
    }
    public void setWantosee(int wantosee) {
        this.wantosee = wantosee;
    }
    public int getScorenumz() {
        return scorenumz;
    }
    public void setScorenumz(int scorenumz) {
        this.scorenumz = scorenumz;
    }
    public int getScorenumx() {
        return scorenumx;
    }
    public void setScorenumx(int scorenumx) {
        this.scorenumx = scorenumx;
    }
    public String getSealnum() {
        return sealnum;
    }
    public void setSealnum(String sealnum) {
        this.sealnum = sealnum;
    }
    public void setCover(byte[] cover) {
        this.cover = cover;
    }
    public String getMid() {
        return mid;
    }
    public void setMid(String mid) {
        this.mid = mid;
    }
    public String getMname() {
        return mname;
    }
    public void setMname(String mname) {
        this.mname = mname;
    }
    public String getLength() {
        return length;
    }
    public void setLength(String length) {
        this.length = length;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public String getActor() {
        return actor;
    }
    public void setActor(String actor) {
        this.actor = actor;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public byte[] getCover() {
        return cover;
    }
    public void setCovers(byte[] cover) {
        this.cover = cover;
    }
    public Date getBgDate() {
        return bgDate;
    }
    public void setBgDate(Date bgDate) {
        this.bgDate = bgDate;
    }
}
