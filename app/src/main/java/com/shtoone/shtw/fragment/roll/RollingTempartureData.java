package com.shtoone.shtw.fragment.roll;

import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 */

public class RollingTempartureData {


    /**
     * data : [{"tempRowNumber":1,"tmpshijian":"2017-04-26 09:07:53","tmpdata":"116.8","tmpid":2751,"banhezhanminchen":"S324五号压路机","tempColumn":0},{"tempRowNumber":2,"tmpshijian":"2017-04-26 09:07:35","tmpdata":"140.4","tmpid":2747,"banhezhanminchen":"S324四号压路机","tempColumn":0},{"tempRowNumber":3,"tmpshijian":"2017-04-26 09:07:29","tmpdata":"79.8","tmpid":2743,"banhezhanminchen":"S324三号压路机","tempColumn":0},{"tempRowNumber":4,"tmpshijian":"2017-04-26 09:07:23","tmpdata":"105.1","tmpid":2741,"banhezhanminchen":"S324五号压路机","tempColumn":0},{"tempRowNumber":5,"tmpshijian":"2017-04-26 09:07:05","tmpdata":"122.8","tmpid":2736,"banhezhanminchen":"S324四号压路机","tempColumn":0},{"tempRowNumber":6,"tmpshijian":"2017-04-26 09:07:00","tmpdata":"79.5","tmpid":2734,"banhezhanminchen":"S324三号压路机","tempColumn":0},{"tempRowNumber":7,"tmpshijian":"2017-04-26 09:06:53","tmpdata":"92.9","tmpid":2733,"banhezhanminchen":"S324五号压路机","tempColumn":0},{"tempRowNumber":8,"tmpshijian":"2017-04-26 09:06:33","tmpdata":"101.2","tmpid":2728,"banhezhanminchen":"S324四号压路机","tempColumn":0},{"tempRowNumber":9,"tmpshijian":"2017-04-26 09:06:30","tmpdata":"75.9","tmpid":2727,"banhezhanminchen":"S324三号压路机","tempColumn":0},{"tempRowNumber":10,"tmpshijian":"2017-04-26 09:06:22","tmpdata":"111.7","tmpid":2726,"banhezhanminchen":"S324五号压路机","tempColumn":0}]
     * chart : [{"wendu":"116.8","shijian":"2017-04-26 09:07:53"},{"wendu":"140.4","shijian":"2017-04-26 09:07:35"},{"wendu":"79.8","shijian":"2017-04-26 09:07:29"},{"wendu":"105.1","shijian":"2017-04-26 09:07:23"},{"wendu":"122.8","shijian":"2017-04-26 09:07:05"},{"wendu":"79.5","shijian":"2017-04-26 09:07:00"},{"wendu":"92.9","shijian":"2017-04-26 09:06:53"},{"wendu":"101.2","shijian":"2017-04-26 09:06:33"},{"wendu":"75.9","shijian":"2017-04-26 09:06:30"},{"wendu":"111.7","shijian":"2017-04-26 09:06:22"}]
     * success : true
     */

    private boolean success;
    private List<DataEntity>  data;
    private List<ChartEntity> chart;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public void setChart(List<ChartEntity> chart) {
        this.chart = chart;
    }

    public boolean getSuccess() {
        return success;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public List<ChartEntity> getChart() {
        return chart;
    }

    public static class DataEntity {
        /**
         * tempRowNumber : 1
         * tmpshijian : 2017-04-26 09:07:53
         * tmpdata : 116.8
         * tmpid : 2751
         * banhezhanminchen : S324五号压路机
         * tempColumn : 0
         */

        private int tempRowNumber;
        private String tmpshijian;
        private String tmpdata;
        private int    tmpid;
        private String banhezhanminchen;
        private int    tempColumn;

        public void setTempRowNumber(int tempRowNumber) {
            this.tempRowNumber = tempRowNumber;
        }

        public void setTmpshijian(String tmpshijian) {
            this.tmpshijian = tmpshijian;
        }

        public void setTmpdata(String tmpdata) {
            this.tmpdata = tmpdata;
        }

        public void setTmpid(int tmpid) {
            this.tmpid = tmpid;
        }

        public void setBanhezhanminchen(String banhezhanminchen) {
            this.banhezhanminchen = banhezhanminchen;
        }

        public void setTempColumn(int tempColumn) {
            this.tempColumn = tempColumn;
        }

        public int getTempRowNumber() {
            return tempRowNumber;
        }

        public String getTmpshijian() {
            return tmpshijian;
        }

        public String getTmpdata() {
            return tmpdata;
        }

        public int getTmpid() {
            return tmpid;
        }

        public String getBanhezhanminchen() {
            return banhezhanminchen;
        }

        public int getTempColumn() {
            return tempColumn;
        }
    }

    public static class ChartEntity {
        /**
         * wendu : 116.8
         * shijian : 2017-04-26 09:07:53
         */

        private String wendu;
        private String shijian;

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
        }

        public String getWendu() {
            return wendu;
        }

        public String getShijian() {
            return shijian;
        }
    }
}
