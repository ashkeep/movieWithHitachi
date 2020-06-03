package com.lk.movie.entity;

import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("Comments")
public class Comments {
    private String mid;
    private String uname;
    private double score;
    private String essay;
    private Date time;
    private int state;

    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public String getMid() {
        return mid;
    }
    public void setMid(String mid) {
        this.mid = mid;
    }
    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }
    public String getEssay() {
        return essay;
    }
    public void setEssay(String essay) {
        this.essay = essay;
    }
    public Date getTimes() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }

}
