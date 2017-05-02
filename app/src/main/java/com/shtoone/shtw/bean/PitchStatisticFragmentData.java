package com.shtoone.shtw.bean;

import java.util.List;

/**
 * Created by gesangdianzi on 2016/9/7.
 */
public class PitchStatisticFragmentData {


    /**
     * data : [{"changliang":"301631.3","highPer":"0.00","highps":"0","middlePer":"0.00","middleps":"0","panshu":"125","primaryPer":"0.00","primaryps":"0","xa":"2016","xb":"7"},{"changliang":"11581947.8","highPer":"0.34","highps":"15","middlePer":"0.34","middleps":"15","panshu":"4427","primaryPer":"0.47","primaryps":"21","xa":"2016","xb":"8"}]
     * success : true
     */

    private boolean success;
    /**
     * changliang : 301631.3
     * highPer : 0.00
     * highps : 0
     * middlePer : 0.00
     * middleps : 0
     * panshu : 125
     * primaryPer : 0.00
     * primaryps : 0
     * xa : 2016
     * xb : 7
     */

    private List<DataEntity> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        private String changliang;
        private String highPer;
        private String highps;
        private String middlePer;
        private String middleps;
        private String panshu;
        private String primaryPer;
        private String primaryps;
        private String xa;
        private String xb;

        public String getChangliang() {
            return changliang;
        }

        public void setChangliang(String changliang) {
            this.changliang = changliang;
        }

        public String getHighPer() {
            return highPer;
        }

        public void setHighPer(String highPer) {
            this.highPer = highPer;
        }

        public String getHighps() {
            return highps;
        }

        public void setHighps(String highps) {
            this.highps = highps;
        }

        public String getMiddlePer() {
            return middlePer;
        }

        public void setMiddlePer(String middlePer) {
            this.middlePer = middlePer;
        }

        public String getMiddleps() {
            return middleps;
        }

        public void setMiddleps(String middleps) {
            this.middleps = middleps;
        }

        public String getPanshu() {
            return panshu;
        }

        public void setPanshu(String panshu) {
            this.panshu = panshu;
        }

        public String getPrimaryPer() {
            return primaryPer;
        }

        public void setPrimaryPer(String primaryPer) {
            this.primaryPer = primaryPer;
        }

        public String getPrimaryps() {
            return primaryps;
        }

        public void setPrimaryps(String primaryps) {
            this.primaryps = primaryps;
        }

        public String getXa() {
            return xa;
        }

        public void setXa(String xa) {
            this.xa = xa;
        }

        public String getXb() {
            return xb;
        }

        public void setXb(String xb) {
            this.xb = xb;
        }
    }
}
