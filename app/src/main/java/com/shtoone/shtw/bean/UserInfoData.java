package com.shtoone.shtw.bean;


import java.util.List;

/**
 * 用户实体类
 * Created by leguang on 2016/5/11 0031.
 */
public class UserInfoData {


    /**
     * SMSGroup : []
     * departId : 297ee90c4447f8a4014447fbba1e0015
     * departName : 南京六的平方信息技术有限公司
     * quanxian : {"hntchaobiaoReal":true,"hntchaobiaoSp":true,"lqchaobiaoReal":false,"syschaobiaoReal":false}
     * success : true
     * type : GL
     * updateDepartTime : 2016-07-23 13:41:36
     * userFullName : test
     * userPhoneNum :
     * userRole : 4
     * xmmc : 项目APP
     */

    private String departId;
    private String departName;
    private QuanxianBean quanxian;
    private boolean success;
    private String type;
    private String updateDepartTime;
    private String userFullName;
    private String userPhoneNum;
    private String userRole;
    private String xmmc;
    private List<?> SMSGroup;

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public QuanxianBean getQuanxian() {
        return quanxian;
    }

    public void setQuanxian(QuanxianBean quanxian) {
        this.quanxian = quanxian;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdateDepartTime() {
        return updateDepartTime;
    }

    public void setUpdateDepartTime(String updateDepartTime) {
        this.updateDepartTime = updateDepartTime;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getXmmc() {
        return xmmc;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }

    public List<?> getSMSGroup() {
        return SMSGroup;
    }

    public void setSMSGroup(List<?> SMSGroup) {
        this.SMSGroup = SMSGroup;
    }

    public static class QuanxianBean {
        /**
         * hntchaobiaoReal : true
         * hntchaobiaoSp : true
         * lqchaobiaoReal : false
         * syschaobiaoReal : false
         */

        private boolean hntchaobiaoReal;
        private boolean hntchaobiaoSp;
        private boolean lqchaobiaoReal;
        private boolean syschaobiaoReal;

        public boolean isHntchaobiaoReal() {
            return hntchaobiaoReal;
        }

        public void setHntchaobiaoReal(boolean hntchaobiaoReal) {
            this.hntchaobiaoReal = hntchaobiaoReal;
        }

        public boolean isHntchaobiaoSp() {
            return hntchaobiaoSp;
        }

        public void setHntchaobiaoSp(boolean hntchaobiaoSp) {
            this.hntchaobiaoSp = hntchaobiaoSp;
        }

        public boolean isLqchaobiaoReal() {
            return lqchaobiaoReal;
        }

        public void setLqchaobiaoReal(boolean lqchaobiaoReal) {
            this.lqchaobiaoReal = lqchaobiaoReal;
        }

        public boolean isSyschaobiaoReal() {
            return syschaobiaoReal;
        }

        public void setSyschaobiaoReal(boolean syschaobiaoReal) {
            this.syschaobiaoReal = syschaobiaoReal;
        }
    }

    @Override
    public String toString() {
        return "UserInfoData{" +
                "departId='" + departId + '\'' +
                ", departName='" + departName + '\'' +
                ", quanxian=" + quanxian +
                ", success=" + success +
                ", type='" + type + '\'' +
                ", updateDepartTime='" + updateDepartTime + '\'' +
                ", userFullName='" + userFullName + '\'' +
                ", userPhoneNum='" + userPhoneNum + '\'' +
                ", userRole='" + userRole + '\'' +
                ", xmmc='" + xmmc + '\'' +
                ", SMSGroup=" + SMSGroup +
                '}';
    }
}
