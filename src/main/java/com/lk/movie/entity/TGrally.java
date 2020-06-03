package com.lk.movie.entity;

import org.apache.ibatis.type.Alias;

@Alias("TGrally")
public class TGrally {
    private String bid;
    private String title;
    private String subtxt;
    private String detailstxt;
    private String banner;


    public String getBid() {
        return bid;
    }
    public void setBid(String bid) {
        this.bid = bid;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSubtxt() {
        return subtxt;
    }
    public void setSubtxt(String subtxt) {
        this.subtxt = subtxt;
    }
    public String getDetailstxt() {
        return detailstxt;
    }
    public void setDetailstxt(String detailstxt) {
        this.detailstxt = detailstxt;
    }
    public String getBanner() {
        return banner;
    }
    public void setBanner(String banner) {
        this.banner = banner;
    }


}
