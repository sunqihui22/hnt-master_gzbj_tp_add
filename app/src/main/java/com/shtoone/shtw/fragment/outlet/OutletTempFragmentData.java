package com.shtoone.shtw.fragment.outlet;

import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 */

public class OutletTempFragmentData {


    /**
     * data : [{"tempRowNumber":1,"tmpshijian":"2017-04-26 03:56:59","tmpdata":"10.6","tmpid":1554,"banhezhanminchen":"S324沥青站出料口","tempColumn":0},{"tempRowNumber":2,"tmpshijian":"2017-04-26 03:56:29","tmpdata":"10.6","tmpid":1553,"banhezhanminchen":"S324沥青站出料口","tempColumn":0},{"tempRowNumber":3,"tmpshijian":"2017-04-26 03:55:59","tmpdata":"10.6","tmpid":1552,"banhezhanminchen":"S324沥青站出料口","tempColumn":0},{"tempRowNumber":4,"tmpshijian":"2017-04-26 03:55:29","tmpdata":"10.6","tmpid":1551,"banhezhanminchen":"S324沥青站出料口","tempColumn":0},{"tempRowNumber":5,"tmpshijian":"2017-04-26 03:54:58","tmpdata":"10.6","tmpid":1550,"banhezhanminchen":"S324沥青站出料口","tempColumn":0},{"tempRowNumber":6,"tmpshijian":"2017-04-26 03:54:28","tmpdata":"10.6","tmpid":1549,"banhezhanminchen":"S324沥青站出料口","tempColumn":0},{"tempRowNumber":7,"tmpshijian":"2017-04-26 03:53:58","tmpdata":"10.6","tmpid":1548,"banhezhanminchen":"S324沥青站出料口","tempColumn":0},{"tempRowNumber":8,"tmpshijian":"2017-04-26 03:53:28","tmpdata":"9.7","tmpid":1547,"banhezhanminchen":"S324沥青站出料口","tempColumn":0},{"tempRowNumber":9,"tmpshijian":"2017-04-26 03:52:58","tmpdata":"10.8","tmpid":1546,"banhezhanminchen":"S324沥青站出料口","tempColumn":0},{"tempRowNumber":10,"tmpshijian":"2017-04-26 03:52:28","tmpdata":"10.8","tmpid":1545,"banhezhanminchen":"S324沥青站出料口","tempColumn":0}]
     * chart : [{"wendu":"10.6","shijian":"2017-04-26 03:56:59"},{"wendu":"10.6","shijian":"2017-04-26 03:56:29"},{"wendu":"10.6","shijian":"2017-04-26 03:55:59"},{"wendu":"10.6","shijian":"2017-04-26 03:55:29"},{"wendu":"10.6","shijian":"2017-04-26 03:54:58"},{"wendu":"10.6","shijian":"2017-04-26 03:54:28"},{"wendu":"10.6","shijian":"2017-04-26 03:53:58"},{"wendu":"9.7","shijian":"2017-04-26 03:53:28"},{"wendu":"10.8","shijian":"2017-04-26 03:52:58"},{"wendu":"10.8","shijian":"2017-04-26 03:52:28"}]
     * success : true
     */

    private boolean success;
    private List<DataBean> data;
    private List<ChartBean> chart;

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

    public List<ChartBean> getChart() {
        return chart;
    }

    public void setChart(List<ChartBean> chart) {
        this.chart = chart;
    }

    public static class DataBean {
        /**
         * tempRowNumber : 1
         * tmpshijian : 2017-04-26 03:56:59
         * tmpdata : 10.6
         * tmpid : 1554
         * banhezhanminchen : S324沥青站出料口
         * tempColumn : 0
         */

        private int tempRowNumber;
        private String tmpshijian;
        private String tmpdata;
        private int tmpid;
        private String banhezhanminchen;
        private int tempColumn;

        public int getTempRowNumber() {
            return tempRowNumber;
        }

        public void setTempRowNumber(int tempRowNumber) {
            this.tempRowNumber = tempRowNumber;
        }

        public String getTmpshijian() {
            return tmpshijian;
        }

        public void setTmpshijian(String tmpshijian) {
            this.tmpshijian = tmpshijian;
        }

        public String getTmpdata() {
            return tmpdata;
        }

        public void setTmpdata(String tmpdata) {
            this.tmpdata = tmpdata;
        }

        public int getTmpid() {
            return tmpid;
        }

        public void setTmpid(int tmpid) {
            this.tmpid = tmpid;
        }

        public String getBanhezhanminchen() {
            return banhezhanminchen;
        }

        public void setBanhezhanminchen(String banhezhanminchen) {
            this.banhezhanminchen = banhezhanminchen;
        }

        public int getTempColumn() {
            return tempColumn;
        }

        public void setTempColumn(int tempColumn) {
            this.tempColumn = tempColumn;
        }
    }

    public static class ChartBean {
        /**
         * wendu : 10.6
         * shijian : 2017-04-26 03:56:59
         */

        private String wendu;
        private String shijian;

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public String getShijian() {
            return shijian;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
        }
    }
}
