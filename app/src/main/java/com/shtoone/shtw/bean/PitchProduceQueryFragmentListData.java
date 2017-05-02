package com.shtoone.shtw.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gesangdianzi on 2016/8/31.
 */
public class PitchProduceQueryFragmentListData {


    /**
     * data : [{"bianhao":8237,"clwd":"166","deptId":"f89b12c25636af3701563c5cc34e0019","shebeibianhao":"G345lq0101","shijian":"2016-08-14 13:31:44","sjlq":"104.699996948242","sjysb":"4.49"},{"bianhao":8236,"clwd":"161","deptId":"f89b12c25636af3701563c5cc34e0019","shebeibianhao":"G345lq0101","shijian":"2016-08-14 13:30:51","sjlq":"105.800003051758","sjysb":"4.52"},{"bianhao":8235,"clwd":"160","deptId":"f89b12c25636af3701563c5cc34e0019","shebeibianhao":"G345lq0101","shijian":"2016-08-14 13:29:58","sjlq":"104.699996948242","sjysb":"4.51"},{"bianhao":8234,"clwd":"160","deptId":"f89b12c25636af3701563c5cc34e0019","shebeibianhao":"G345lq0101","shijian":"2016-08-14 13:29:05","sjlq":"104.800003051758","sjysb":"4.52"},{"bianhao":8233,"clwd":"159","deptId":"f89b12c25636af3701563c5cc34e0019","shebeibianhao":"G345lq0101","shijian":"2016-08-14 13:28:12","sjlq":"105.900001525879","sjysb":"4.51"},{"bianhao":8232,"clwd":"160","deptId":"f89b12c25636af3701563c5cc34e0019","shebeibianhao":"G345lq0101","shijian":"2016-08-14 13:27:19","sjlq":"106.800003051758","sjysb":"4.5"},{"bianhao":8231,"clwd":"161","deptId":"f89b12c25636af3701563c5cc34e0019","shebeibianhao":"G345lq0101","shijian":"2016-08-14 13:26:26","sjlq":"108","sjysb":"4.54"},{"bianhao":8230,"clwd":"160","deptId":"f89b12c25636af3701563c5cc34e0019","shebeibianhao":"G345lq0101","shijian":"2016-08-14 13:25:33","sjlq":"106","sjysb":"4.52"},{"bianhao":8229,"clwd":"155","deptId":"f89b12c25636af3701563c5cc34e0019","shebeibianhao":"G345lq0101","shijian":"2016-08-14 13:23:08","sjlq":"104.900001525879","sjysb":"4.51"},{"bianhao":8228,"clwd":"155","deptId":"f89b12c25636af3701563c5cc34e0019","shebeibianhao":"G345lq0101","shijian":"2016-08-14 13:22:05","sjlq":"105.900001525879","sjysb":"4.5"},{"bianhao":8227,"clwd":"156","deptId":"f89b12c25636af3701563c5cc34e0019","shebeibianhao":"G345lq0101","shijian":"2016-08-14 13:21:04","sjlq":"106.099998474121","sjysb":"4.52"},{"bianhao":8226,"clwd":"156","deptId":"f89b12c25636af3701563c5cc34e0019","shebeibianhao":"G345lq0101","shijian":"2016-08-14 13:20:04","sjlq":"105.800003051758","sjysb":"4.52"},{"bianhao":8225,"clwd":"158","deptId":"f89b12c25636af3701563c5cc34e0019","shebeibianhao":"G345lq0101","shijian":"2016-08-14 13:19:03","sjlq":"107","sjysb":"4.54"},{"bianhao":8224,"clwd":"157","deptId":"f89b12c25636af3701563c5cc34e0019","shebeibianhao":"G345lq0101","shijian":"2016-08-14 13:18:01","sjlq":"105.900001525879","sjysb":"4.51"},{"bianhao":8223,"clwd":"157","deptId":"f89b12c25636af3701563c5cc34e0019","shebeibianhao":"G345lq0101","shijian":"2016-08-14 13:16:58","sjlq":"104.699996948242","sjysb":"4.49"}]
     * success : true
     */

    private boolean success;
    /**
     * bianhao : 8237
     * clwd : 166
     * deptId : f89b12c25636af3701563c5cc34e0019
     * shebeibianhao : G345lq0101
     * shijian : 2016-08-14 13:31:44
     * sjlq : 104.699996948242
     * sjysb : 4.49
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

    public static class DataEntity implements Serializable {
        private int id;
        private String clwd;
        private String deptId;
        private String shebeibianhao;
        private String shijian;
        private String sjlq;
        private String sjysb;

        public int getId() {
            return id;
        }

        public void setBianhao(int bianhao) {
            this.id = id;
        }

        public String getClwd() {
            return clwd;
        }

        public void setClwd(String clwd) {
            this.clwd = clwd;
        }

        public String getDeptId() {
            return deptId;
        }

        public void setDeptId(String deptId) {
            this.deptId = deptId;
        }

        public String getShebeibianhao() {
            return shebeibianhao;
        }

        public void setShebeibianhao(String shebeibianhao) {
            this.shebeibianhao = shebeibianhao;
        }

        public String getShijian() {
            return shijian;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
        }

        public String getSjlq() {
            return sjlq;
        }

        public void setSjlq(String sjlq) {
            this.sjlq = sjlq;
        }

        public String getSjysb() {
            return sjysb;
        }

        public void setSjysb(String sjysb) {
            this.sjysb = sjysb;
        }
    }
}
