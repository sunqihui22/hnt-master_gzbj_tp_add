package com.shtoone.shtw.bean;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by leguang on 2016/5/31 0031.
 * 请求参数实体类
 */
public class ParametersData implements Cloneable, Serializable {
    private static final String TAG = ParametersData.class.getSimpleName();
    public String startDateTime = "2015-03-01 00:00:00";
    public String endDateTime = "2016-06-01 00:00:00";
    public String userGroupID = "";
    public String deviceType = "";
    public String testTypeID = "";
    public String disposition = "";
    public String level = "";
    public String isQualified = "";
    public String equipmentID = "";
    public String alarmLevel = "";
    public String handleType = "";
    public String currentPage = "1";
    public String isReal = "";
    public String detailID = "";
    public int fromTo;
    public String tanpujiId="";
    public String maxPageItems="10";


    public ParametersData() {
        initParametersData();
    }

    private void initParametersData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Calendar rld = Calendar.getInstance();
        endDateTime = sdf.format(rld.getTime());
        rld.add(Calendar.MONTH, -3);
        startDateTime = sdf.format(rld.getTime());
    }

    //克隆
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "ParametersData{" +
                "startDateTime='" + startDateTime + '\'' +
                ", endDateTime='" + endDateTime + '\'' +
                ", userGroupID='" + userGroupID + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", testTypeID='" + testTypeID + '\'' +
                ", disposition='" + disposition + '\'' +
                ", level='" + level + '\'' +
                ", isQualified='" + isQualified + '\'' +
                ", equipmentID='" + equipmentID + '\'' +
                ", alarmLevel='" + alarmLevel + '\'' +
                ", handleType='" + handleType + '\'' +
                ", currentPage='" + currentPage + '\'' +
                ", isReal='" + isReal + '\'' +
                ", detailID='" + detailID + '\'' +
                ", fromTo=" + fromTo +
                '}';
    }
}
