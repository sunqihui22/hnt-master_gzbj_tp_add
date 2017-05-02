package com.shtoone.shtw.utils;

import android.text.TextUtils;

import com.socks.library.KLog;

public class URL {

    public static final String TAG = "URL";

    /**
     * 常用接口、url
     */
    private URL() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 更新地址
     */
    public static final String UpdateURL = "http://120.27.146.66:8083/nxsy/appVersion/version.xml";

    /**
     * 基础地址
     */
//    public static final String BaseURL = "http://120.27.146.66:8083/nxsy/";
//    public static final String BaseURL = "http://192.168.11.112:8080/zgjjqms/";
    //public static final String BaseURL = "http://127.0.0.1:8080/zgjjqms/";
//    public static final String BaseURL = "http://192.168.11.106:8081/zgjjqms/";
    //  public static final String BaseURL = "http://192.168.10.53:8080/zgjjqms/";
    // public static final String BaseURL = "http://192.168.11.131:8080/qhttqms/";
//    public static final String BaseURL = "http://192.168.11.114:8082/qhttqms/";
//    public static final String BaseURL = "http://192.168.11.108:8081/qhttqms/";
//    public static final String BaseURL = " http://121.40.150.65:8083/zgjjqms/";
//  public static final String BaseURL = "http://192.168.11.115:8081/zgjjqmsbak/";


    //测试地址
//    public static final String BaseURL = "http://192.168.11.109:8080/njpfqms/";

    //测试地址
//    public static final String BaseURL = "http://116.62.30.27:8082/njpfsix/";


    //平台地址
    public static final String BaseURL = "http://116.62.30.27:8082/njpfsix/";

//    public static final String BaseURL = "http://115.29.173.91:8083/gzbjqms/";

    /**
     * 登录地址
     */
    public static final String Login_URL = BaseURL + "app.do?AppLogin&userName=%1&userPwd=%2&OSType=2";

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return 返回拼凑后的url
     */
    public static String loginCheck(String username, String password) {
        String url = Login_URL.replace("%1", username).replace("%2", password);
        KLog.e(TAG, "登录验证 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 组织结构面板
     */
    public static final String ORGANIZATION = BaseURL + "app.do?AppDepartTree&updateDepartTime=%1&funtype=%2&userGroupId=%3&type=%4";

    /**
     * 组织结构面板
     *
     * @param dateTime    时间
     * @param type        部门类型（试验室或者拌合站）
     * @param userGroupID 组织ID
     * @param userRole    组织ID
     * @return 返回拼凑后的url
     */
    public static String getOrganizationData(String dateTime, String type, String userGroupID, String userRole) {
        dateTime = DateUtils.ChangeTimeToLong(dateTime);
        //如果开始时间大于结束时间，返回null
        String url = ORGANIZATION.replace("%1", dateTime).replace("%2", type).replace("%3", userGroupID).replace("%4", userRole);
        KLog.e(TAG, "组织结构URL :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }


    /**
     * 主界面地址 TODO
     */
    public static final String Main_URL = BaseURL + "app.do?AppHntMain&departId=%1";

    /**
     * 拌合站设备列表
     */
    public static final String COMM_DEVICE = BaseURL + "app.do?getShebeiList&userGroupId=%1";

    /**
     * 拌合站设备列表
     *
     * @param userGroupID 用户组ID
     * @return 返回拼凑后的url
     */
    public static String getBHZEquipment(String userGroupID) {
        String url = COMM_DEVICE.replace("%1", userGroupID);
        KLog.e(TAG, "拌合站设备列表:" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 按试验种类获取条目
     */
    public static final String SYS_Items = BaseURL + "sysController.do?sysHome&userGroupId=%1&startTime=%2&endTime=%3";

    /**
     * 试验室主页
     *
     * @param userGroupID 组织ID
     * @param startTime   查询的起始时间
     * @param endTime     查询的结束时间
     * @return 返回拼凑后的url
     */
    public static String getSYSLingdaoData(String userGroupID, String startTime, String endTime) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = SYS_Items.replace("%1", userGroupID).replace("%2", startTime).replace("%3", endTime);
            KLog.e(TAG, "试验室主界面URL :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }


    /**
     * 拌合站菜单界面
     */
    public static final String Menu_SYS = BaseURL + "sysController.do?sysMainLogo&userGroupId=%1";

    /**
     * 拌合站菜单界面
     */
    public static final String Menu_BHZ = BaseURL + "app.do?hntMainLogo&userGroupId=%1";

    /**
     * 拌合站统计信息
     */
    public static final String BHZ_Lingdao = BaseURL + "app.do?AppHntMain&departId=%1&startTime=%2&endTime=%3";

    /**
     * 混凝土主页
     *
     * @param userGroupID 组织ID
     * @param startTime   查询的起始时间
     * @param endTime     查询的结束时间
     * @return 返回拼凑后的url
     */
    public static String getBHZLingdaoData(String userGroupID, String startTime, String endTime) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = BHZ_Lingdao.replace("%1", userGroupID).replace("%2", startTime).replace("%3", endTime);
            KLog.e(TAG, "混凝土拌合站主界面URL :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }

    /**
     * 混泥土强度列表地址
     */
    public static final String HNT_URL = BaseURL + "sysController.do?hntkangya&userGroupId=%1&isQualified=%2&startTime=%3&endTime=%4&pageNo=%5&shebeibianhao=%6&isReal=%7&maxPageItems=30&testId=%8";


    /**
     * 得到压力机列表数据
     *
     * @param userGroupID    组织结构ID
     * @param isQualified    是否合格
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param current_PageNo 当前页码
     * @param deviceNo       设备编号
     * @param isReal
     * @param testType       试验类型
     * @return url
     */
    public static String getYalijiTestList(String userGroupID, String isQualified, String startTime, String endTime, String current_PageNo, String deviceNo, String isReal, String testType) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = HNT_URL.replace("%1", userGroupID).replace("%2", isQualified).replace("%3", startTime).replace("%4", endTime).replace("%5", current_PageNo).replace("%6", deviceNo).replace("%7", isReal).replace("%8", testType);
            KLog.e(TAG, "试验室压力试验列表 :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }

    /**
     * 混泥土强度详情地址
     */
    public static final String HNTXQ_URL = BaseURL + "sysController.do?hntkangyaDetail&SYJID=%1";

    /**
     * 压力试验详情
     *
     * @param detailID 详情ID
     * @return 返回拼凑后的url
     */
    public static String getYalijiDetailData(String detailID) {
        String url = HNTXQ_URL.replace("%1", detailID);
        KLog.e(TAG, "压力试验详情 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }


    /**
     * 请求沥青设备列表
     */
    public static final String Lqing_device_list = BaseURL + "lqSysController.do?getLqShebeiList&userGroupId=%1";


    /**
     * 钢筋拉力列表地址
     */
    public static final String GJLL_URL = BaseURL + "sysController.do?gangjin&userGroupId=%1&isQualified=%2&startTime=%3&endTime=%4&pageNo=%5&shebeibianhao=%6&isReal=%7&maxPageItems=15";

    /**
     * 万能机试验详情
     */
    public static final String GJLLXQ_URL = BaseURL + "sysController.do?gangjinDetail&SYJID=%1";

    /**
     * 万能机试验详情
     *
     * @param detailID 详情ID
     * @return 返回拼凑后的url
     */
    public static String getWannengjiDetailData(String detailID) {
        String url = GJLLXQ_URL.replace("%1", detailID);
        KLog.e(TAG, "万能机试验详情 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 钢筋焊接接头列表地址
     */
    public static final String GJHJJT_URL = BaseURL + "sysController.do?gangjinhanjiejietou&userGroupId=%1&isQualified=%2&startTime=%3&endTime=%4&pageNo=%5&shebeibianhao=%6&isReal=%7&maxPageItems=15";

    /**
     * 钢筋焊接接头详情地址
     */
    public static final String GJHJJTXQ_URL = BaseURL + "sysController.do?gangjinhanjiejietouDetail&SYJID=%1";

    /**
     * 钢筋机械连接接头列表地址
     */
    public static final String GJJXLJJT_URL = BaseURL + "sysController.do?gangjinlianjiejietou&userGroupId=%1&isQualified=%2&startTime=%3&endTime=%4&pageNo=%5&shebeibianhao=%6&isReal=%7&maxPageItems=15";

    /**
     * 拌合站生产数据查询
     */
    public static final String BHZ_SCDATA_URL = BaseURL + "app.do?AppHntXiangxiList&departId=%1&startTime=%2&endTime=%3&pageNo=%4&shebeibianhao=%5&maxPageItems=30";

    /**
     * 拌合站生产数据查询
     *
     * @param userGroupID    组织结构ID
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param current_PageNo 当前页码
     * @param deviceNo       设备编号
     * @return url
     */
    public static String getProduceData(String userGroupID, String startTime, String endTime, String current_PageNo, String deviceNo) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = BHZ_SCDATA_URL.replace("%1", userGroupID).replace("%2", startTime).replace("%3", endTime).replace("%4", current_PageNo).replace("%5", deviceNo);
            KLog.e(TAG, "拌合站生产数据查询URL :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }


    /**
     * 拌合站生产数据详情查询
     */
    public static final String BHZ_SCDATA_DETAIL_URL = BaseURL + "app.do?AppHntXiangxiDetail&bianhao=%1";

    /**
     * 拌合站生产数据详情查询
     *
     * @param detailID 详情ID
     * @return 返回拼凑后的url
     */
    public static String getProduceDetailData(String detailID) {
        String url = BHZ_SCDATA_DETAIL_URL.replace("%1", detailID);
        KLog.e(TAG, "拌合站生产数据详情查询 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }


    /**
     * 拌合站待处置超标列表
     */
    public static final String BHZ_CHAOBIAO_LIST_URL = BaseURL + "app.do?AppHntChaobiaoList&departId=%1&startTime=%2&endTime=%3&dengji=%4&chuzhileixing=%5&pageNo=%6&shebeibianhao=%7&maxPageItems=30";

    /**
     * 拌合站待处置超标列表
     *
     * @param userGroupID    组织结构ID
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param current_PageNo 当前页码
     * @param deviceNo       设备编号
     * @return url
     */
    public static String getOverproofData(String userGroupID, String startTime, String endTime, String dengji, String chuzhileixing, String current_PageNo, String deviceNo) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = BHZ_CHAOBIAO_LIST_URL.replace("%1", userGroupID).replace("%2", startTime).replace("%3", endTime).replace("%4", dengji).replace("%5", chuzhileixing).replace("%6", current_PageNo).replace("%7", deviceNo);
            KLog.e(TAG, "拌合站待处置超标列表:" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }


    /**
     * 拌合站待处置超标详情
     */
    public static final String BHZ_CHAOBIAO_DETAIL_URL = BaseURL + "app.do?AppHntChaobiaoDetail&bianhao=%1";

    /**
     * 拌合站待处置超标详情
     *
     * @param detailID 详情ID
     * @return 返回拼凑后的url
     */
    public static String getOverproofDetailData(String detailID) {
        String url = BHZ_CHAOBIAO_DETAIL_URL.replace("%1", detailID);
        KLog.e(TAG, "拌合站待处置超标详情 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 混凝土超标处置
     */
    public static final String BHZ_CHAOBIAO_DO_URL = BaseURL + "app.do?AppHntChaobiaoChuzhi&jieguobianhao=%1&chaobiaoyuanyin=%2&chuzhifangshi=%3&chuzhijieguo=%4&chuzhiren=%5&chuzhishijian=%6";

    /**
     * 混凝土综合统计分析
     */
    public static final String BHZ_ZONGHT_TJ_URL = BaseURL + "app.do?hntCountAnalyze&userGroupId=%1&startTime=%2&endTime=%3&shebeibianhao=%4&cllx=%5";

    /**
     * 混凝土材料用量
     */
    public static final String BHZ_CAILIAO_URL = BaseURL + "app.do?AppHntMaterial&departId=%1&startTime=%2&endTime=%3&shebeibianhao=%4";

    /**
     * 混凝土材料用量
     *
     * @param userGroupID 组织ID
     * @param startTime   查询的起始时间
     * @param endTime     查询的结束时间
     * @return 返回拼凑后的url
     */
    public static String getBHZCailiaoyongliang(String userGroupID, String startTime, String endTime, String equipmentID) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = BHZ_CAILIAO_URL.replace("%1", userGroupID).replace("%2", startTime).replace("%3", endTime).replace("%4", equipmentID);
            KLog.e(TAG, "混凝土材料用量URL :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }


    /**
     * 试验室超标处置
     */
    public static final String SYS_CHAOBIAO_DO_URL = BaseURL + "sysController.do?hntkangyaPost";

    /**
     * 拌合站状态
     */
    public static final String COMM_BHZ_STS = BaseURL + "app.do?AppHntBanhejiState&departId=%1&pageNo=%2&maxPageItems=30";

    /**
     * 拌合站超标审批
     */
//    public static final String BHZ_CHAOBIAO_SP = BaseURL + "app.do?AppHntChaobiaoShenpi&jieguobianhao=%1&jianliresult=%2&jianlishenpi=%3&confirmdate=%4&shenpiren=%5&shenpidate=%6";
    public static final String BHZ_CHAOBIAO_SP = BaseURL + "app.do?AppHntChaobiaoShenpi";
    /**
     * 试验室设备列表
     */
    public static final String SYS_SHEBEI_LIST = BaseURL + "sysController.do?getSysShebeiList&userGroupId=%1";

    /**
     * 登录验证
     *
     * @param userGroupID 用户组ID
     * @return 返回拼凑后的url
     */
    public static String getEquipment(String userGroupID) {
        String url = SYS_SHEBEI_LIST.replace("%1", userGroupID);
        KLog.e(TAG, "获取设备的URL:" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 试验室试验类型列表
     */
    public static final String SYS_SHEBEI_TEST_LIST = BaseURL + "sysController.do?getSyLx";

    public static String getTestType(String userGroupID) {
        String url = SYS_SHEBEI_TEST_LIST.replace("%1", userGroupID);
        KLog.e(TAG, "试验室试验类型列表URL:" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 试验室综合统计分析
     */
    public static final String SYS_TONGJI_FENXI = BaseURL + "sysController.do?sysCountAnalyze&userGroupId=%1&startTime=%2&endTime=%3";

    public static String getLaboratoryStatistic(String userGroupID, String startTime, String endTime) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = SYS_TONGJI_FENXI.replace("%1", userGroupID).replace("%2", startTime).replace("%3", endTime);
            KLog.e(TAG, "试验室统计分析 :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }

    /**
     * 钢筋列表地址
     */
    public static final String GJ_URL = BaseURL + "sysController.do?gangjin&userGroupId=%1&isQualified=%2&startTime=%3&endTime=%4&pageNo=%5&shebeibianhao=%6&isReal=%7&maxPageItems=30&testId=%8";

    /**
     * 得到万能机列表数据
     *
     * @param userGroupID    组织结构ID
     * @param isQualified    是否合格
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param current_PageNo 页码
     * @param deviceNo       设备编号
     * @param isReal         是否处置
     * @param testType       试验类型
     * @return url
     */
    public static String getWannengjiTestList(String userGroupID, String isQualified, String startTime, String endTime, String current_PageNo, String deviceNo, String isReal, String testType) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = GJ_URL.replace("%1", userGroupID).replace("%2", isQualified).replace("%3", startTime).replace("%4", endTime).replace("%5", current_PageNo).replace("%6", deviceNo).replace("%7", isReal).replace("%8", testType);
            KLog.e("万能机列表 :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }

    /**
     * 获取试验室设备和试验类型
     */
    public static final String EQUIPMENT_TESTTYPE_URL = BaseURL + "sysController.do?getSyTpAndMc&userGroupId=%1";

    /**
     * 获取试验室设备和试验类型
     *
     * @param userGroupID 用户组ID
     * @return 返回拼凑后的url
     */
    public static String getLibEquipmentTest(String userGroupID) {
        String url = EQUIPMENT_TESTTYPE_URL.replace("%1", userGroupID);
        KLog.e(TAG, "试验室设备和试验类型:" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }


    /**
     * 获取沥青设备和试验类型
     *
     * @param userGroupID 用户组ID
     * @return 返回拼凑后的url
     */
    public static String getLiQingEqument(String userGroupID) {
        String url = Lqing_device_list.replace("%1", userGroupID);
        KLog.e(TAG, "试验室设备和试验类型:" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 试验室施工用户主页数据
     */
    public static final String SG_SYS_MAIN = BaseURL + "sysController.do?hntSysMainLogo&userGroupId=%1";

    public static String getLibSGMain(String userGroupID) {
        String url = SG_SYS_MAIN.replace("%1", userGroupID);
        KLog.e(TAG, "试验室施工用户主页数据:" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 拌和站施工用户主页数据
     */
    public static final String SG_BHZ_MAIN = BaseURL + "app.do?hntBhzMainLogo&userGroupId=%1";

    public static String getBHZSGMain(String userGroupID) {
        String url = SG_BHZ_MAIN.replace("%1", userGroupID);
        KLog.e(TAG, "拌和站施工用户主页数据:" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 沥青主页url
     */
    public static final String Liqing_Lingdao_Tongji = BaseURL + "lqChaoBiaoController.do?lqChaoBiaoCount&userGroupId=%1&startTime=%2&endTime=%3";

    /**
     * 请求沥青生产数据查询列表数据
     */
    public static final String Liqing_Produce_Query = BaseURL + "lqScsjcxController.do?getScsjcxList&userGroupId=%1&shebeibianhao=%2&startTime=%3&endTime=%4&pageNo=%5";
    /**
     * 请求沥青生产数据查询数据详情页
     */
    public static final String Liqing_Produce_Query_Detail = BaseURL + "lqScsjcxController.do?getScsjcxDetail&shebeibianhao=%1&bianhao=%2";


    /**
     * 请求沥青日产量
     */
    public static final String liqing_Day_Produce_Amount = BaseURL + "lqclDailyController.do?dayproducecount&userGroupId=%1&shebeibianhao=%2&startTime=%3&endTime=%4&pageNo=%5";


    /**
     * 沥青待处置超标详情处置提交
     */
    public static final String LIQING_CHAOBIAO_DETAIL_CHUZHI_SUBMIT_URL = BaseURL + "lqChaoBiaoChuZhiController.do?appLqChaobiaoChuzhi&xxid=%1&chaobiaoyuanyin=%2&chuzhifangshi=%3&chuzhijieguo=%4&chuzhiren=%5&chuzhishijian=%6";

    /**
     * 沥青待处置超标列表查询
     */
    public static final String LIQING_CHAOBIAO_LIST_URL = BaseURL + "lqChaoBiaoChuZhiController.do?appLqChaobiaoList&dengji=%1&chuzhileixing=%2&pageNo=%3&shebeibianhao=%4&userGroupId=%5&startTime=%6&endTime=%7";

    /**
     * 沥青施工主页
     */

    public static final String LIQING_SHIGONG_URL = BaseURL + "lqSysController.do?produceMenu&userGroupId=%1";

    /**
     * 沥青施工主页
     *
     * @param userGroupID 组织ID
     *                    //     * @param startTime   查询的起始时间
     *                    //     * @param endTime     查询的结束时间
     * @return 返回拼凑后的url
     */
    public static String getLQLingShigongData(String userGroupID) {

        String url = LIQING_SHIGONG_URL.replace("%1", userGroupID);
        KLog.e(TAG, "沥青施工主页 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }


    /**
     * 沥青超标等级查询
     *
     * @param
     * @return 返回拼凑后的url
     */
    public static String getPitchChaobiaoListData(String dengji, String chuzhileixing, String pageNo, String shebeibianhao, String userGroupId, String startTime, String endTime) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        String url = LIQING_CHAOBIAO_LIST_URL.replace("%1", dengji).replace("%2", chuzhileixing).replace("%3", pageNo).replace("%4", shebeibianhao).replace("%5", userGroupId).replace("%6", startTime).replace("%7", endTime);
        KLog.e(TAG, "沥青超标等级查询 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 沥青生产数据详情查询
     *
     * @param
     * @return 返回拼凑后的url
     */
    public static String getPitchProduceDetailData(String shebeibianhao, int bianhao) {
        String url = Liqing_Produce_Query_Detail.replace("%1", shebeibianhao).replace("%2", String.valueOf(bianhao));
        KLog.e(TAG, "沥青生产数据详情查询 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }


    /**
     * 沥青超标处置详情页uil
     */
    public static final String LIQING_CHAOBIAOCHUZHI_DETAIL_URL = BaseURL + "lqChaoBiaoChuZhiController.do?appLqChaobiaoDetail&xxid=%1&shebeibianhao=%2";

    /**
     * 沥青超标详情查询单项
     *
     * @param
     * @return 返回拼凑后的url
     */
    public static String getPitchOverProofDetailData(int bianhao, String shebeibianhao) {
        String url = LIQING_CHAOBIAOCHUZHI_DETAIL_URL.replace("%1", String.valueOf(bianhao)).replace("%2", shebeibianhao);
        KLog.e(TAG, "沥青生产数据详情查询 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }


    /**
     * 沥青主页
     *
     * @param userGroupID 组织ID
     * @param startTime   查询的起始时间
     * @param endTime     查询的结束时间
     * @return 返回拼凑后的url
     */
    public static String getLQLingdaoData(String userGroupID, String startTime, String endTime) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = Liqing_Lingdao_Tongji.replace("%1", userGroupID).replace("%2", startTime).replace("%3", endTime);
            KLog.e(TAG, "沥青主界面URL :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }


    /**
     * 沥青日生产量
     *
     * @param userGroupID    组织结构ID
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param current_PageNo 当前页码
     * @param deviceNo       设备编号
     * @return url
     */
    public static String getDayLQProduceData(String userGroupID, String startTime, String endTime, String current_PageNo, String deviceNo) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        deviceNo = DateUtils.ChangeTimeToLong(deviceNo);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = liqing_Day_Produce_Amount.replace("%1", userGroupID).replace("%2", deviceNo).replace("%3", startTime).replace("%4", endTime).replace("%5", current_PageNo);
            KLog.e(TAG, "沥青日生产量查询URL :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }

    /**
     * 沥青生产数据查询
     *
     * @param userGroupID    组织结构ID
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param current_PageNo 当前页码
     * @param deviceNo       设备编号
     * @return url
     */
    public static String getLQProduceData(String userGroupID, String startTime, String endTime, String current_PageNo, String deviceNo) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        deviceNo = DateUtils.ChangeTimeToLong(deviceNo);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = Liqing_Produce_Query.replace("%1", userGroupID).replace("%2", deviceNo).replace("%3", startTime).replace("%4", endTime).replace("%5", current_PageNo);
            KLog.e(TAG, "沥青生产数据查询URL :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }

    /**
     * 请求沥青日产量详情
     */
    public static final String LQING_RICHANLIANG_POST = BaseURL + "lqclDailyController.do?dayproducecountadd";

    /**
     * 沥青总产量与超标率统计
     */
    public static final String LIQING_ZONGCHANLIANGTONGJI_URL = BaseURL + "lqSCcounController.do?allchangliangcount&shebeibianhao=%1&startTime=%2&endTime=%3&userGroupId=%4&leixing=%5";


    /**
     * 沥青材料用量核算
     */
    public static final String LIQING_CAILIAOYONGLIANGHESUAN = BaseURL + "lqSCcounController.do?materialcount&shebeibianhao=%1&startTime=%2&endTime=%3&userGroupId=%4";


    public static String getPitchStatisticUrl(String equipmentID, String userGroupID, String startTime, String endTime) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = URL.LIQING_CAILIAOYONGLIANGHESUAN.replace("%1", equipmentID).replace("%2", startTime).replace("%3", endTime).replace("%4", userGroupID);
            KLog.e(TAG, "试验室统计分析 :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }


    /**
     * 摊铺机设备列表
     */
    public static final String TANPUJI_DEVICE = BaseURL + "app.do?getShebeiList&userGroupId=%1";

    /**
     * 摊铺机设备列表
     *
     * @param userGroupID 用户组ID
     * @return 返回拼凑后的url
     */
    public static String getTPJEquipment(String userGroupID) {
        String url = TANPUJI_DEVICE.replace("%1", userGroupID);
        KLog.e(TAG, "摊铺机设备列表:" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 摊铺温度查询
     */
    public static final String TANPU_WENDU_URL = BaseURL + "xcTanpuwenduController.do?appXcTanpuwenduList&userGroupId=%1&startTime=%2&endTime=%3&shebeibianhao=%4&pageNo=%5&maxPageItems=%6";

    /**
     * 摊铺温度查询
    */
    public static String getTanpuWendu(String userGroupID, String startTime, String endTime, String shebeibiaohao,String pageNo,String maxPageItems){
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = TANPU_WENDU_URL.replace("%1", userGroupID).replace("%2", startTime).replace("%3", endTime).replace("%4", shebeibiaohao).replace("%5", pageNo).replace("%6", maxPageItems);
            KLog.e(TAG, "摊铺温度查询URL :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }

    /**
     * 摊铺速度查询
     */
    public static final String TANPU_SUDU_URL = BaseURL + "xcTanpuwenduController.do?appXcTanpusuduList&userGroupId=%1&startTime=%2&endTime=%3&shebeibianhao=%4&pageNo=%5&maxPageItems=%6";

    /**
     * 摊铺速度查询
     */
    public static String getTanpuSudu(String userGroupID, String startTime, String endTime, String shebeibiaohao,String pageNo,String maxPageItems){
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = TANPU_SUDU_URL.replace("%1", userGroupID).replace("%2", startTime).replace("%3", endTime).replace("%4", shebeibiaohao).replace("%5", pageNo).replace("%", maxPageItems);
            KLog.e(TAG, "摊铺温度查询URL :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }


    /**
     * 出料口温度查询
     */
    public static final String OUTLET_TEMP_URL = BaseURL + "xcTanpuwenduController.do?appXcChuliaokouwenduList&userGroupId=%1&startTime=%2&endTime=%3&shebeibianhao=%4&pageNo=%5&maxPageItems=%6";

    /**
     * 出料口温度查询
     */
    public static String getOutletTemp(String userGroupID, String startTime, String endTime, String shebeibiaohao,String pageNo,String maxPageItems){
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = OUTLET_TEMP_URL.replace("%1", userGroupID).replace("%2", startTime).replace("%3", endTime).replace("%4", shebeibiaohao).replace("%5", pageNo).replace("%", maxPageItems);
            KLog.e(TAG, "摊铺温度查询URL :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }

    /**
     * 碾压温度查询数据查询
     */
    public static final String ROLLING_TEMPURTURE_URL = BaseURL + "xcNianYaController.do?appNianYaWDList&userGroupId=%1&shebeibianhao=%2&startTime=%3&endTime=%4&pageNo=%5&maxPageItems=10";

    /**
     * 碾压温度查询数据查询
     *
     * @param userGroupID    组织结构ID
     * @param shebeibianhao  设备编号
     * @param startTime       开始时间
     * @param endTime         结束时间
     * @param pageNo          当前页
     * @return url
     */
    public static String getRollingTemputureData(String userGroupID, String shebeibianhao, String startTime, String endTime, String pageNo) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = ROLLING_TEMPURTURE_URL.replace("%1", userGroupID).replace("%2", shebeibianhao).replace("%3", startTime).replace("%4", endTime).replace("%5", pageNo);
            KLog.e(TAG, "碾压温度查询数据查询 :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }

    /**
     * 碾压速度查询数据查询
     */
    public static final String ROLLING_SPEED_URL = BaseURL + "xcNianYaController.do?appNianYaSDList&userGroupId=%1&shebeibianhao=%2&startTime=%3&endTime=%4&pageNo=%5&maxPageItems=10";

    /**
     * 碾压速度查询数据查询
     *
     * @param userGroupID    组织结构ID
     * @param shebeibianhao  设备编号
     * @param startTime       开始时间
     * @param endTime         结束时间
     * @param pageNo          当前页
     * @return url
     */
    public static String getRollingspeedData(String userGroupID, String shebeibianhao, String startTime, String endTime, String pageNo) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = ROLLING_SPEED_URL.replace("%1", userGroupID).replace("%2", shebeibianhao).replace("%3", startTime).replace("%4", endTime).replace("%5", pageNo);
            KLog.e(TAG, "碾压温度查询数据查询 :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }

    /**
     * 设备定位查询 */
    public static final String Device_Location = BaseURL + "xcNianYaController.do?SheBeiDW&userGroupId=%1&shebeibianhao=%2";

    public static String getDeviceLocation(String userGroupId,String shebeibianhao){
        String url = Device_Location.replace("%1",userGroupId).replace("%2",shebeibianhao);
        KLog.e(TAG, "设备定位查询 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 设备定位查询 */
    public static final String Device_Locations = BaseURL + "xcNianYaController.do?SheBeiDW&userGroupId=%1";

    public static String getDeviceLocations(String userGroupId){
        String url = Device_Locations.replace("%1",userGroupId);
        KLog.e(TAG, "所有设备定位查询 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 设备接口 */
    public static final String DEVICE = BaseURL + "xcTanpuwenduController.do?getMachineList&userGroupId=%1&machineType1=%2";

    public static String getDevice(String userGroupId,String machineType1){
        String url = DEVICE.replace("%1",userGroupId).replace("%2",machineType1);
        KLog.e(TAG, "设备接口查询 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

}