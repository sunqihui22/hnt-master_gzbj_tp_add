package com.shtoone.shtw.fragment.tanpu;

import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 */
public class TanpuWenduFragmentData {


    /**
     * chart : [{"shijian":"2017-04-26 09:07:54","wendu":"133.7"},{"shijian":"2017-04-26 09:07:47","wendu":"141.9"},{"shijian":"2017-04-26 09:07:42","wendu":"157.1"},{"shijian":"2017-04-26 09:07:38","wendu":"146.0"},{"shijian":"2017-04-26 09:07:33","wendu":"142.6"},{"shijian":"2017-04-26 09:07:31","wendu":"142.6"},{"shijian":"2017-04-26 09:07:29","wendu":"143.4"},{"shijian":"2017-04-26 09:07:28","wendu":"145.1"},{"shijian":"2017-04-26 09:07:15","wendu":"152.1"},{"shijian":"2017-04-26 09:07:13","wendu":"147.5"}]
     * data : [{"banhezhanminchen":"S324一号摊铺机","tempColumn":0,"tempRowNumber":1,"tmpdata":"133.7","tmpid":2752,"tmpshijian":"2017-04-26 09:07:54"},{"banhezhanminchen":"S324一号摊铺机","tempColumn":0,"tempRowNumber":2,"tmpdata":"141.9","tmpid":2750,"tmpshijian":"2017-04-26 09:07:47"},{"banhezhanminchen":"S324二号摊铺机","tempColumn":0,"tempRowNumber":3,"tmpdata":"157.1","tmpid":2749,"tmpshijian":"2017-04-26 09:07:42"},{"banhezhanminchen":"S324一号摊铺机","tempColumn":0,"tempRowNumber":4,"tmpdata":"146.0","tmpid":2748,"tmpshijian":"2017-04-26 09:07:38"},{"banhezhanminchen":"S324一号摊铺机","tempColumn":0,"tempRowNumber":5,"tmpdata":"142.6","tmpid":2746,"tmpshijian":"2017-04-26 09:07:33"},{"banhezhanminchen":"S324一号摊铺机","tempColumn":0,"tempRowNumber":6,"tmpdata":"142.6","tmpid":2745,"tmpshijian":"2017-04-26 09:07:31"},{"banhezhanminchen":"S324一号摊铺机","tempColumn":0,"tempRowNumber":7,"tmpdata":"143.4","tmpid":2744,"tmpshijian":"2017-04-26 09:07:29"},{"banhezhanminchen":"S324一号摊铺机","tempColumn":0,"tempRowNumber":8,"tmpdata":"145.1","tmpid":2742,"tmpshijian":"2017-04-26 09:07:28"},{"banhezhanminchen":"S324一号摊铺机","tempColumn":0,"tempRowNumber":9,"tmpdata":"152.1","tmpid":2740,"tmpshijian":"2017-04-26 09:07:15"},{"banhezhanminchen":"S324一号摊铺机","tempColumn":0,"tempRowNumber":10,"tmpdata":"147.5","tmpid":2739,"tmpshijian":"2017-04-26 09:07:13"}]
     * success : true
     */

    private boolean success;
    private List<ChartBean> chart;
    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ChartBean> getChart() {
        return chart;
    }

    public void setChart(List<ChartBean> chart) {
        this.chart = chart;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class ChartBean {
        /**
         * shijian : 2017-04-26 09:07:54
         * wendu : 133.7
         */

        private String shijian;
        private String wendu;

        public String getShijian() {
            return shijian;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        @Override
        public String toString() {
            return "ChartBean{" +
                    "shijian='" + shijian + '\'' +
                    ", wendu='" + wendu + '\'' +
                    '}';
        }
    }

    public static class DataBean {
        /**
         * banhezhanminchen : S324一号摊铺机
         * tempColumn : 0
         * tempRowNumber : 1
         * tmpdata : 133.7
         * tmpid : 2752
         * tmpshijian : 2017-04-26 09:07:54
         */

        private String banhezhanminchen;
        private int tempColumn;
        private int tempRowNumber;
        private String tmpdata;
        private int tmpid;
        private String tmpshijian;

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

        public int getTempRowNumber() {
            return tempRowNumber;
        }

        public void setTempRowNumber(int tempRowNumber) {
            this.tempRowNumber = tempRowNumber;
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

        public String getTmpshijian() {
            return tmpshijian;
        }

        public void setTmpshijian(String tmpshijian) {
            this.tmpshijian = tmpshijian;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "banhezhanminchen='" + banhezhanminchen + '\'' +
                    ", tempColumn=" + tempColumn +
                    ", tempRowNumber=" + tempRowNumber +
                    ", tmpdata='" + tmpdata + '\'' +
                    ", tmpid=" + tmpid +
                    ", tmpshijian='" + tmpshijian + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TanpuWenduFragmentData{" +
                "success=" + success +
                ", chart=" + chart +
                ", data=" + data +
                '}';
    }
}
