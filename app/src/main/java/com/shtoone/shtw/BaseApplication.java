package com.shtoone.shtw;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.github.moduth.blockcanary.BlockCanary;
import com.shtoone.shtw.bean.DepartmentData;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.bean.UserInfoData;
import com.socks.library.KLog;
import com.squareup.otto.Bus;

/**
 * Created by leguang on 2016/5/20 0031.
 */
public class BaseApplication extends Application {

    private static final String TAG = BaseApplication.class.getSimpleName();
    public static BaseApplication application;
    public static Context context;
    public static final Bus bus = new Bus();
    public static ParametersData parametersData = new ParametersData();
    public static RequestQueue mRequestQueue;
    public static UserInfoData mUserInfoData;
    public static DepartmentData mDepartmentData = new DepartmentData();
    public static boolean isExpand;

    @Override
    public void onCreate() {
        super.onCreate();
        //日志的开关和全局标签初始化
        KLog.init(true, "SHTW");
        application = this;
        context = getApplicationContext();
        // 程序异常交由AppExceptionHandler来处理
    //Thread.setDefaultUncaughtExceptionHandler(AppExceptionHandler.getInstance(this));
        //创建Volley的请求队列
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        //创建LeakCanary对象，观察内存泄漏
      //  mRefWatcher = LeakCanary.install(this);
        BlockCanary.install(this, new AppContext()).start();//创建BlockCanary对象，观察耗时操作
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this) ;
    }
}
