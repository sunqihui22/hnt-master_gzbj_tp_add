package com.shtoone.shtw.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gesangdianzi on 2016/9/2.
 */
public class PitchDayProdeceRecyclerViewItemData {

    /**
     * data : [{"dailycl":"3983.10","dailymd":"","dailykd":"","dailyxzcl":"","dailyps":"1","dailyxh":"","dailybeizhu":"","dailybuwei":"","dailyrq":"2016-06-22","dailysbbh":"test01","dailyid":353,"dailyhd":"","dailycd":"","dailysjhd":""}]
     * success : true
     */

    private boolean success;
    /**
     * dailycl : 3983.10
     * dailymd :
     * dailykd :
     * dailyxzcl :
     * dailyps : 1
     * dailyxh :
     * dailybeizhu :
     * dailybuwei :
     * dailyrq : 2016-06-22
     * dailysbbh : test01
     * dailyid : 353
     * dailyhd :
     * dailycd :
     * dailysjhd :
     */

    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        private static final long serialVersionUID = 4810973990417773304L;
        private String dailycl;
        private String dailymd;
        private String dailykd;
        private String dailyxzcl;
        private String dailyps;
        private String dailyxh;
        private String dailybeizhu;
        private String dailybuwei;
        private String dailyrq;
        private String dailysbbh;
        private int dailyid;
        private String dailyhd;
        private String dailycd;
        private String dailysjhd;

        public String getDailycl() {
            return dailycl;
        }

        public void setDailycl(String dailycl) {
            this.dailycl = dailycl;
        }

        public String getDailymd() {
            return dailymd;
        }

        public void setDailymd(String dailymd) {
            this.dailymd = dailymd;
        }

        public String getDailykd() {
            return dailykd;
        }

        public void setDailykd(String dailykd) {
            this.dailykd = dailykd;
        }

        public String getDailyxzcl() {
            return dailyxzcl;
        }

        public void setDailyxzcl(String dailyxzcl) {
            this.dailyxzcl = dailyxzcl;
        }

        public String getDailyps() {
            return dailyps;
        }

        public void setDailyps(String dailyps) {
            this.dailyps = dailyps;
        }

        public String getDailyxh() {
            return dailyxh;
        }

        public void setDailyxh(String dailyxh) {
            this.dailyxh = dailyxh;
        }

        public String getDailybeizhu() {
            return dailybeizhu;
        }

        public void setDailybeizhu(String dailybeizhu) {
            this.dailybeizhu = dailybeizhu;
        }

        public String getDailybuwei() {
            return dailybuwei;
        }

        public void setDailybuwei(String dailybuwei) {
            this.dailybuwei = dailybuwei;
        }

        public String getDailyrq() {
            return dailyrq;
        }

        public void setDailyrq(String dailyrq) {
            this.dailyrq = dailyrq;
        }

        public String getDailysbbh() {
            return dailysbbh;
        }

        public void setDailysbbh(String dailysbbh) {
            this.dailysbbh = dailysbbh;
        }

        public int getDailyid() {
            return dailyid;
        }

        public void setDailyid(int dailyid) {
            this.dailyid = dailyid;
        }

        public String getDailyhd() {
            return dailyhd;
        }

        public void setDailyhd(String dailyhd) {
            this.dailyhd = dailyhd;
        }

        public String getDailycd() {
            return dailycd;
        }

        public void setDailycd(String dailycd) {
            this.dailycd = dailycd;
        }

        public String getDailysjhd() {
            return dailysjhd;
        }

        public void setDailysjhd(String dailysjhd) {
            this.dailysjhd = dailysjhd;
        }
    }
}