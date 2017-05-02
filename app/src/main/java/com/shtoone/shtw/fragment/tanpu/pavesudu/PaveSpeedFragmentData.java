package com.shtoone.shtw.fragment.tanpu.pavesudu;

import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 */

public class PaveSpeedFragmentData {


    /**
     * data : [{"tempRowNumber":1,"gpsid":2687,"sudu":"0.313","shijian":"2017-04-26 09:07:54:573","banhezhanminchen":"S324一号摊铺机","tempColumn":0},{"tempRowNumber":2,"gpsid":2685,"sudu":"0.329","shijian":"2017-04-26 09:07:47:853","banhezhanminchen":"S324一号摊铺机","tempColumn":0},{"tempRowNumber":3,"gpsid":2684,"sudu":"0.153","shijian":"2017-04-26 09:07:42:915","banhezhanminchen":"S324二号摊铺机","tempColumn":0},{"tempRowNumber":4,"gpsid":2683,"sudu":"0.324","shijian":"2017-04-26 09:07:38:833","banhezhanminchen":"S324一号摊铺机","tempColumn":0},{"tempRowNumber":5,"gpsid":2681,"sudu":"0.324","shijian":"2017-04-26 09:07:33:516","banhezhanminchen":"S324一号摊铺机","tempColumn":0},{"tempRowNumber":6,"gpsid":2680,"sudu":"0.427","shijian":"2017-04-26 09:07:31:531","banhezhanminchen":"S324一号摊铺机","tempColumn":0},{"tempRowNumber":7,"gpsid":2679,"sudu":"0.353","shijian":"2017-04-26 09:07:29:558","banhezhanminchen":"S324一号摊铺机","tempColumn":0},{"tempRowNumber":8,"gpsid":2677,"sudu":"0.349","shijian":"2017-04-26 09:07:28:882","banhezhanminchen":"S324一号摊铺机","tempColumn":0},{"tempRowNumber":9,"gpsid":2675,"sudu":"0.306","shijian":"2017-04-26 09:07:15:737","banhezhanminchen":"S324一号摊铺机","tempColumn":0},{"tempRowNumber":10,"gpsid":2674,"sudu":"0.327","shijian":"2017-04-26 09:07:13:613","banhezhanminchen":"S324一号摊铺机","tempColumn":0}]
     * chart : [{"sudu":"0.313","shijian":"2017-04-26 09:07:54:573"},{"sudu":"0.329","shijian":"2017-04-26 09:07:47:853"},{"sudu":"0.153","shijian":"2017-04-26 09:07:42:915"},{"sudu":"0.324","shijian":"2017-04-26 09:07:38:833"},{"sudu":"0.324","shijian":"2017-04-26 09:07:33:516"},{"sudu":"0.427","shijian":"2017-04-26 09:07:31:531"},{"sudu":"0.353","shijian":"2017-04-26 09:07:29:558"},{"sudu":"0.349","shijian":"2017-04-26 09:07:28:882"},{"sudu":"0.306","shijian":"2017-04-26 09:07:15:737"},{"sudu":"0.327","shijian":"2017-04-26 09:07:13:613"}]
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
         * gpsid : 2687
         * sudu : 0.313
         * shijian : 2017-04-26 09:07:54:573
         * banhezhanminchen : S324一号摊铺机
         * tempColumn : 0
         */

        private int tempRowNumber;
        private int gpsid;
        private String sudu;
        private String shijian;
        private String banhezhanminchen;
        private int tempColumn;

        public int getTempRowNumber() {
            return tempRowNumber;
        }

        public void setTempRowNumber(int tempRowNumber) {
            this.tempRowNumber = tempRowNumber;
        }

        public int getGpsid() {
            return gpsid;
        }

        public void setGpsid(int gpsid) {
            this.gpsid = gpsid;
        }

        public String getSudu() {
            return sudu;
        }

        public void setSudu(String sudu) {
            this.sudu = sudu;
        }

        public String getShijian() {
            return shijian;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
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
         * sudu : 0.313
         * shijian : 2017-04-26 09:07:54:573
         */

        private String sudu;
        private String shijian;

        public String getSudu() {
            return sudu;
        }

        public void setSudu(String sudu) {
            this.sudu = sudu;
        }

        public String getShijian() {
            return shijian;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
        }
    }
}
