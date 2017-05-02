package com.shtoone.shtw.bean;

import java.io.Serializable;

public class SC_zongchanliang implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6098321351074036703L;
    private String Date;         //周期
    private Double changliang;   //产量
    private Double primarylv;    //低报警
    private Double middlelv;     //中报警
    private Double highlv;       //高报警


    public Double getPrimarylv() {
        return primarylv;
    }

    public void setPrimarylv(Double primarylv) {
        this.primarylv = primarylv;
    }

    public Double getMiddlelv() {
        return middlelv;
    }

    public void setMiddlelv(Double middlelv) {
        this.middlelv = middlelv;
    }

    public Double getHighlv() {
        return highlv;
    }

    public void setHighlv(Double highlv) {
        this.highlv = highlv;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Double getChangliang() {
        return changliang;
    }

    public void setChangliang(Double changliang) {
        this.changliang = changliang;
    }
}

