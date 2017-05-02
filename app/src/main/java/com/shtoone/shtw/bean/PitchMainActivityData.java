package com.shtoone.shtw.bean;

/**
 * Created by Administrator on 2017/4/23.
 */
public class PitchMainActivityData {


    /**
     * data : {"dailycl":"0","zcbps":1170,"czps":0,"dailyps":"0","panshu":"0","dczps":1170,"cblv":0,"ljchangliang":"0"}
     * success : true
     */

    private DataBean data;
    private boolean success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * dailycl : 0
         * zcbps : 1170
         * czps : 0
         * dailyps : 0
         * panshu : 0
         * dczps : 1170
         * cblv : 0
         * ljchangliang : 0
         */

        private String dailycl;
        private int zcbps;
        private int czps;
        private String dailyps;
        private String panshu;
        private int dczps;
        private int cblv;
        private String ljchangliang;

        public String getDailycl() {
            return dailycl;
        }

        public void setDailycl(String dailycl) {
            this.dailycl = dailycl;
        }

        public int getZcbps() {
            return zcbps;
        }

        public void setZcbps(int zcbps) {
            this.zcbps = zcbps;
        }

        public int getCzps() {
            return czps;
        }

        public void setCzps(int czps) {
            this.czps = czps;
        }

        public String getDailyps() {
            return dailyps;
        }

        public void setDailyps(String dailyps) {
            this.dailyps = dailyps;
        }

        public String getPanshu() {
            return panshu;
        }

        public void setPanshu(String panshu) {
            this.panshu = panshu;
        }

        public int getDczps() {
            return dczps;
        }

        public void setDczps(int dczps) {
            this.dczps = dczps;
        }

        public int getCblv() {
            return cblv;
        }

        public void setCblv(int cblv) {
            this.cblv = cblv;
        }

        public String getLjchangliang() {
            return ljchangliang;
        }

        public void setLjchangliang(String ljchangliang) {
            this.ljchangliang = ljchangliang;
        }
    }
}
