package com.shtoone.shtw.utils;


import com.shtoone.shtw.BaseApplication;

import java.io.File;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class Constants {

    /**
     * 不允许new
     */
    private Constants() {
        throw new Error("Do not need instantiate!");
    }

    //SD卡路径
    public static final String PATH_DATA = DirectoryUtils.getDiskCacheDirectory(BaseApplication.context, "data").getAbsolutePath();
    public static final String PATH_CACHE = PATH_DATA + File.separator + "NetCache";
    public static final String PATH_NET_CACHE = PATH_DATA + File.separator + "NetCache";
    public static final String PATH_APK_CACHE = PATH_DATA + File.separator + "ApkCache";

    //基地址
    //public static final String BASE_URL = "http://112.124.50.253:8081/hnlymss/app/";
   public static final String BASE_URL = "http://192.168.11.119:8082/qhttqms/";



    //登录地址
    public static final String LOGIN_URL = BASE_URL + "app.do?AppLogin&userName=%1&userPwd=%2&OSType=2";

    public static final String DOMAIN_1 = "shtoone.com";
    public static final String DOMAIN_2 = "sh-toone";
    public static final String ISFIRSTENTRY = "is_first_entry";
    public static final String ISFIRSTGUIDE = "is_first_guide";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String USER_INFO_BEAN = "user_info_bean";
    public static final int DEFAULT_TIMEOUT = 5;

    public static final String USER_ID = "user_id";

    public static final String REGISTER_CODE = "register_code";


    //作为登录的参数，固定这个写法
    public static final String OSTYPE = "3";
    public static final String PRESS_AGAIN = "再按一次退出";
    public static final String ENCRYPT_KEY = "leguang";

    public static final String PARAMETERS = "parameters";
    public static final String USERGROUPID = "usergroupid";
    public static final String DEPARTMENT = "department";

    public static final String ABOUTAPP = "http://note.youdao.com/share/?id=37e5d8602c49af15d7589d7f91bd548b&type=note";
    public static final String ABOUTCOMPANY = "http://en.ccccltd.cn/ccccltd/";

    //检测App升级
    public static final int CHECKUPDATE = 0;




    public static final int FROM_SPLASH = 0;
    public static final int FROM_MAIN = 1;
    public static final int FROM_GUIDE = 2;

    //paramentData 的fromto
    public static final int  LABORATORYFRAGMENT=1;
    public static final int  PITCHFRAGMENT=2;
    public static final int  MARSHALLWHENDINGDUFRAGMENT=3;
    public static final int  YANDUFRAGMENT=4;
    public static final int  RUANHUADIANFRAGMENT=5;
    public static final int  ZHENRUDUFRAGMENT=6;
    public static final int  PARAMETERSFRAGMENT=7;
    public static final int  ORGANIZATIONFRAGMENT=8;


    public static final String ABOUTWHAT = "aboutwhat";
    public static final String FROM_TO = "from_to";

    //departmentData的funtype
    public static final String SHIYANSHI = "3";
    public static final String LIQINGBHZ = "2";




}
