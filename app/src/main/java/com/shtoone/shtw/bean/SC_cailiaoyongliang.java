package com.shtoone.shtw.bean;

/**
 * Created by leguang on 2016/7/7 0007.
 */

import java.io.Serializable;
import java.text.DecimalFormat;

public class SC_cailiaoyongliang implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 7050015244614710355L;
    private String cailiaomingcheng;
    private Double shijiKg;
    private Double peibiKg;
    private Double wuchaKg;
    private Double wuchaPst;
    DecimalFormat df=new DecimalFormat(".##");
    public String getCailiaomingcheng() {
        return cailiaomingcheng;
    }
    public void setCailiaomingcheng(String cailiaomingcheng) {
        this.cailiaomingcheng = cailiaomingcheng;
    }
    public Double getShijiKg() {
        return Double.valueOf(df.format(shijiKg));
    }
    public void setShijiKg(Double shijiKg) {
        this.shijiKg = shijiKg;
    }
    public Double getpeibiKg() {
        return Double.valueOf(df.format(peibiKg));
    }
    public void setpeibiKg(Double peibiKg) {
        this.peibiKg = peibiKg;
    }
    public Double getWuchaKg() {
        return Double.valueOf(df.format(wuchaKg));
    }
    public void setWuchaKg(Double wuchaKg) {
        this.wuchaKg = wuchaKg;
    }
    public Double getWuchaPst() {
        return Double.valueOf(df.format(wuchaPst)) ;
    }
    public void setWuchaPst(Double wuchaPst) {
        this.wuchaPst = wuchaPst;
    }
}
