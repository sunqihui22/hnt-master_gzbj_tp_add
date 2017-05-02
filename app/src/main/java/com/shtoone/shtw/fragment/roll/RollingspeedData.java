package com.shtoone.shtw.fragment.roll;

import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 */

public class RollingspeedData {


    /**
     * data : [{"tempRowNumber":1,"gpsid":2662,"sudu":"0.273","shijian":"2017-04-26 09:06:30:364","banhezhanminchen":"S324三号压路机","tempColumn":0},{"tempRowNumber":2,"gpsid":2553,"sudu":"0.204","shijian":"2017-04-26 08:57:28:453","banhezhanminchen":"S324三号压路机","tempColumn":0},{"tempRowNumber":3,"gpsid":2178,"sudu":"0.336","shijian":"2017-04-26 08:30:23:037","banhezhanminchen":"S324三号压路机","tempColumn":0},{"tempRowNumber":4,"gpsid":2081,"sudu":"0.127","shijian":"2017-04-26 08:25:52:445","banhezhanminchen":"S324三号压路机","tempColumn":0},{"tempRowNumber":5,"gpsid":2065,"sudu":"0.162","shijian":"2017-04-26 08:25:22:199","banhezhanminchen":"S324三号压路机","tempColumn":0},{"tempRowNumber":6,"gpsid":2031,"sudu":"0.082","shijian":"2017-04-26 08:22:52:297","banhezhanminchen":"S324三号压路机","tempColumn":0},{"tempRowNumber":7,"gpsid":2024,"sudu":"0.092","shijian":"2017-04-26 08:22:22:223","banhezhanminchen":"S324三号压路机","tempColumn":0},{"tempRowNumber":8,"gpsid":2020,"sudu":"0.056","shijian":"2017-04-26 08:21:52:071","banhezhanminchen":"S324三号压路机","tempColumn":0},{"tempRowNumber":9,"gpsid":1995,"sudu":"0.077","shijian":"2017-04-26 08:20:21:721","banhezhanminchen":"S324三号压路机","tempColumn":0},{"tempRowNumber":10,"gpsid":1968,"sudu":"0.059","shijian":"2017-04-26 08:18:51:449","banhezhanminchen":"S324三号压路机","tempColumn":0}]
     * chart : [{"sudu":"0.273","shijian":"2017-04-26 09:06:30:364"},{"sudu":"0.204","shijian":"2017-04-26 08:57:28:453"},{"sudu":"0.336","shijian":"2017-04-26 08:30:23:037"},{"sudu":"0.127","shijian":"2017-04-26 08:25:52:445"},{"sudu":"0.162","shijian":"2017-04-26 08:25:22:199"},{"sudu":"0.082","shijian":"2017-04-26 08:22:52:297"},{"sudu":"0.092","shijian":"2017-04-26 08:22:22:223"},{"sudu":"0.056","shijian":"2017-04-26 08:21:52:071"},{"sudu":"0.077","shijian":"2017-04-26 08:20:21:721"},{"sudu":"0.059","shijian":"2017-04-26 08:18:51:449"}]
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
         * gpsid : 2662
         * sudu : 0.273
         * shijian : 2017-04-26 09:06:30:364
         * banhezhanminchen : S324三号压路机
         * tempColumn : 0
         */

        private int tempRowNumber;
        private int    gpsid;
        private String sudu;
        private String shijian;
        private String banhezhanminchen;
        private int    tempColumn;

        public void setTempRowNumber(int tempRowNumber) {
            this.tempRowNumber = tempRowNumber;
        }

        public void setGpsid(int gpsid) {
            this.gpsid = gpsid;
        }

        public void setSudu(String sudu) {
            this.sudu = sudu;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
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

        public int getGpsid() {
            return gpsid;
        }

        public String getSudu() {
            return sudu;
        }

        public String getShijian() {
            return shijian;
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
         * sudu : 0.273
         * shijian : 2017-04-26 09:06:30:364
         */

        private String sudu;
        private String shijian;

        public void setSudu(String sudu) {
            this.sudu = sudu;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
        }

        public String getSudu() {
            return sudu;
        }

        public String getShijian() {
            return shijian;
        }
    }
}
