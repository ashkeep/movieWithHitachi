package com.lk.movie.dto;

import org.apache.ibatis.type.Alias;

@Alias("RondaSeatInfo")
public class RondaSeatInfo {
    private String seatno;
    private String hno;			//影厅编号
    private String rowno;		//行号
    private String colno;		//列号
    private int state;			//座位可用状态
    private int rid;	    	//场次号
    private int sellState;      //售票和选座状态

    public String getSeatno() {
        return seatno;
    }
    public void setSeatno(String seatno) {
        this.seatno = seatno;
    }
    public String getHno() {
        return hno;
    }
    public void setHno(String hno) {
        this.hno = hno;
    }
    public String getRowno() {
        return rowno;
    }
    public void setRowno(String rowno) {
        this.rowno = rowno;
    }
    public String getColno() {
        return colno;
    }
    public void setColno(String colno) {
        this.colno = colno;
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public int getRid() {
        return rid;
    }
    public void setRid(int rid) {
        this.rid = rid;
    }
    public int getSellState() {
        return sellState;
    }
    public void setSellState(int sellState) {
        this.sellState = sellState;
    }

}
