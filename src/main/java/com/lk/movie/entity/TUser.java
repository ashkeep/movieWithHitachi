package com.lk.movie.entity;

import org.apache.ibatis.type.Alias;

@Alias("TUser")
public class TUser {
    private String uname;
    private String pwd;
    private int role;
    private String tel;
    private double balance;  //账户余额
    private byte[] userPic; //用户头像
    private String usertxt;  //个性签名

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public byte[] getUserPic() {
        return userPic;
    }
    public void setUserPics(byte[] userPic) {
        this.userPic = userPic;
    }
    public String getUsertxt() {
        return usertxt;
    }
    public void setUsertxt(String usertxt) {
        this.usertxt = usertxt;
    }
    public void setRole(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}