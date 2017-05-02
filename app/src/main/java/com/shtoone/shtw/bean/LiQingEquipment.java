package com.shtoone.shtw.bean;

import java.util.List;

/**
 * Created by gesangdianzi on 2016/9/5.
 */
public class LiQingEquipment {


    /**
     * data : [{"banhezhanminchen":"G345线玛久1标1号沥青拌合机","departid":"f89b12c25636af3701563c5e51ed001b","gprsbianhao":"G345lq0101"}]
     * success : true
     */

    private boolean success;
    /**
     * banhezhanminchen : G345线玛久1标1号沥青拌合机
     * departid : f89b12c25636af3701563c5e51ed001b
     * gprsbianhao : G345lq0101
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
        private String banhezhanminchen;
        private String departid;
        private String gprsbianhao;

        public String getBanhezhanminchen() {
            return banhezhanminchen;
        }

        public void setBanhezhanminchen(String banhezhanminchen) {
            this.banhezhanminchen = banhezhanminchen;
        }

        public String getDepartid() {
            return departid;
        }

        public void setDepartid(String departid) {
            this.departid = departid;
        }

        public String getGprsbianhao() {
            return gprsbianhao;
        }

        public void setGprsbianhao(String gprsbianhao) {
            this.gprsbianhao = gprsbianhao;
        }
    }
}
