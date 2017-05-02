package com.shtoone.shtw.utils;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class Configuration {
    /**
     * 是否是调试环境
     */
    private static boolean debug;
    /**
     * 是否打印网络参数
     */
    private static boolean isShowNetworkParams = true;

    private static boolean cacheResponse = false;

    public static void setIsShowNetworkParams(boolean isShowNetworkParams) {
        Configuration.isShowNetworkParams = isShowNetworkParams;
    }

    public static boolean isCacheResponse() {
        return cacheResponse;
    }

    public static void enableCacheResponse() {
        Configuration.cacheResponse = true;
    }

    public static void disableCacheResponse() {
        Configuration.cacheResponse = false;
    }

    /**
     * 是否是调试环境
     */
    public static boolean isDebug() {
        return debug;
    }

    /**
     * 设置调试环境
     */
    public static void setDebug(boolean debug) {
        Configuration.debug = debug;
    }

    /**
     * 开启打印网络请求参数
     */
    public static void enableLoggingNetworkParams() {
        isShowNetworkParams = true;
    }

    /**
     * 关闭打印网络请求参数
     */
    public static void disableLoggingNetworkParams() {
        isShowNetworkParams = false;
    }

    /**
     * 是否打印网络请求参数
     */
    public static boolean isShowNetworkParams() {
        return isShowNetworkParams;
    }
}
