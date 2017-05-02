package com.shtoone.shtw.bean;

import java.util.List;

/**
 * Created by gesangdianzi on 2016/8/30.
 */
public class PitchFragmentRecyclerViewItemData {


    /**
     * data : [[{"banhezhanminchen":"全线超标总数","bhjCount":"2","bhzCount":"1","cblv":"61.21","cbps":"1586","changliang":"6605.5","dengji":"总","deptId":"f89b12c25636af3701563c5cc34e0019","deptName":"G345线玛久项目","panshu":"2591","reallv":"0.08","shebeibianhao":"G345lq0101"},{"banhezhanminchen":"G345线玛久1标1号沥青拌合机初级","bhjCount":"2","bhzCount":"1","cblv":"61.21","cbps":"1586","changliang":"6605.5","dengji":"初级","deptId":"f89b12c25636af3701563c5cc34e0019","deptName":"G345线玛久项目","panshu":"2591","reallv":"0.08","shebeibianhao":"G345lq0101"},{"banhezhanminchen":"G345线玛久1标1号沥青拌合机中级","bhjCount":"2","bhzCount":"1","cblv":"4.17","cbps":"108","changliang":"6605.5","dengji":"中级","deptId":"f89b12c25636af3701563c5cc34e0019","deptName":"G345线玛久项目","panshu":"2591","reallv":"0.08","shebeibianhao":"G345lq0101"},{"banhezhanminchen":"G345线玛久1标1号沥青拌合机高级","bhjCount":"2","bhzCount":"1","cblv":"0.00","cbps":"0","changliang":"6605.5","dengji":"高级","deptId":"f89b12c25636af3701563c5cc34e0019","deptName":"G345线玛久项目","panshu":"2591","reallv":"","shebeibianhao":"G345lq0101"}]]
     * success : true
     */

    private boolean success;
    /**
     * banhezhanminchen : 全线超标总数
     * bhjCount : 2
     * bhzCount : 1
     * cblv : 61.21
     * cbps : 1586
     * changliang : 6605.5
     * dengji : 总
     * deptId : f89b12c25636af3701563c5cc34e0019
     * deptName : G345线玛久项目
     * panshu : 2591
     * reallv : 0.08
     * shebeibianhao : G345lq0101
     */

    private List<List<DataEntity>> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<List<DataEntity>> getData() {
        return data;
    }

    public void setData(List<List<DataEntity>> data) {
        this.data = data;
    }

    public static class DataEntity {
        private String banhezhanminchen;
        private String bhjCount;
        private String bhzCount;
        private String cblv;
        private String cbps;
        private String changliang;
        private String dengji;
        private String deptId;
        private String deptName;
        private String panshu;
        private String reallv;
        private String shebeibianhao;

        public String getBanhezhanminchen() {
            return banhezhanminchen;
        }

        public void setBanhezhanminchen(String banhezhanminchen) {
            this.banhezhanminchen = banhezhanminchen;
        }

        public String getBhjCount() {
            return bhjCount;
        }

        public void setBhjCount(String bhjCount) {
            this.bhjCount = bhjCount;
        }

        public String getBhzCount() {
            return bhzCount;
        }

        public void setBhzCount(String bhzCount) {
            this.bhzCount = bhzCount;
        }

        public String getCblv() {
            return cblv;
        }

        public void setCblv(String cblv) {
            this.cblv = cblv;
        }

        public String getCbps() {
            return cbps;
        }

        public void setCbps(String cbps) {
            this.cbps = cbps;
        }

        public String getChangliang() {
            return changliang;
        }

        public void setChangliang(String changliang) {
            this.changliang = changliang;
        }

        public String getDengji() {
            return dengji;
        }

        public void setDengji(String dengji) {
            this.dengji = dengji;
        }

        public String getDeptId() {
            return deptId;
        }

        public void setDeptId(String deptId) {
            this.deptId = deptId;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public String getPanshu() {
            return panshu;
        }

        public void setPanshu(String panshu) {
            this.panshu = panshu;
        }

        public String getReallv() {
            return reallv;
        }

        public void setReallv(String reallv) {
            this.reallv = reallv;
        }

        public String getShebeibianhao() {
            return shebeibianhao;
        }

        public void setShebeibianhao(String shebeibianhao) {
            this.shebeibianhao = shebeibianhao;
        }
    }
}

