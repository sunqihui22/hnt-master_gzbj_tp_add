package com.shtoone.shtw.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.bean.UserInfoData;
import com.shtoone.shtw.event.MyLocationListener;
import com.shtoone.shtw.utils.AESCryptUtils;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.HttpUtils;
import com.shtoone.shtw.utils.SharedPreferencesUtils;
import com.shtoone.shtw.utils.URL;
import com.socks.library.KLog;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;

import java.security.GeneralSecurityException;

public class SplashActivity extends BaseActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private UserInfoData userInfoData;
    private boolean isBackPressed;

    LocationClient mLocationClient;
    MyLocationListener myListener =new MyLocationListener(this) ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        XGPushConfig.enableDebug(this, true);
        Context context = getApplicationContext();
        XGPushManager.registerPush(context);

        // 2.36（不包括）之前的版本需要调用以下2行代码
        Intent service = new Intent(context, XGPushService.class);
        context.startService(service);
        initLocation();

        //延迟执行，尽量看到闪屏页
        new Handler().postDelayed(new Runnable() {
            public void run() {
                initView();
                initData();
            }
        }, 2500);
    }

    private void initView() {
    }



    private void initData() {
        String usernameEncrypted = (String) SharedPreferencesUtils.get(this, ConstantsUtils.USERNAME, "");
        String passwordEncrypted = (String) SharedPreferencesUtils.get(this, ConstantsUtils.PASSWORD, "");
//        String loginCheck = (String) SharedPreferencesUtils.get(this, ConstantsUtils.LOGINCHECK, "");
        KLog.e("username加密从sp中:" + usernameEncrypted);
        KLog.e("password加密从sp中:" + passwordEncrypted);
        //进行解密
        String username = null;
        String password = null;
        if (!(TextUtils.isEmpty(usernameEncrypted) && TextUtils.isEmpty(passwordEncrypted))) {
            try {
                username = AESCryptUtils.decrypt("leguang", usernameEncrypted);
                password = AESCryptUtils.decrypt("leguang", passwordEncrypted);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }

        KLog.e("username解密:" + username);
        KLog.e("password解密:" + password);

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            //联网校对密码正确后保存
            HttpUtils.getRequest(URL.loginCheck(username, password), new HttpUtils.HttpListener() {
                @Override
                public void onSuccess(String response) {
                    KLog.json(response);
                    if (!TextUtils.isEmpty(response)) {
                        userInfoData = new Gson().fromJson(response, UserInfoData.class);
                        if (null != userInfoData) {
                            if (userInfoData.isSuccess()) {
                                BaseApplication.mUserInfoData = userInfoData;
                                initParametersData();
                                //在跳转之前判断是否按了返回键返回桌面了，这代表用户不想进应用了
                                if (!isBackPressed) {
                                    jumpToMain();
                                }
                            } else {
                                //提示用户名或密码错误,有可能用户在Web端改了密码
                                if (!isBackPressed) {
                                    jumpToLogin();
                                }
                            }
                        } else {
                            //提示数据解析异常，与硬件和系统有关的问题，几乎不可能出现
                            if (!isBackPressed) {
                                jumpToLogin();
                            }
                        }
                    } else {
                        //提示返回数据异常，丢包的情况，几乎不会出现
                        if (!isBackPressed) {
                            jumpToLogin();
                        }
                    }
                }

                @Override
                public void onFailed(VolleyError error) {
                    //提示网络数据异常，无网络
                    if (!isBackPressed) {
                        jumpToLogin();
                    }
                }
            });
        } else {
            jumpToLogin();
        }
    }

    //进入LoginActivity
    private void jumpToLogin() {
        Boolean isFirstentry = (Boolean) SharedPreferencesUtils.get(this, ConstantsUtils.ISFIRSTENTRY, true);
        Intent intent;
        if (isFirstentry) {
            intent = new Intent(this, GuideActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }

    //进入MainActivity
    private void jumpToMain() {

        Boolean isFirstentry = (Boolean) SharedPreferencesUtils.get(this, ConstantsUtils.ISFIRSTENTRY, true);
        Intent intent = null;
        if (isFirstentry) {
            intent = new Intent(this, GuideActivity.class);

        } else {
            if ("GL".equals(userInfoData.getType())) {
                intent = new Intent(this, MainActivity.class);

            } else if ("SG".equals(userInfoData.getType())) {

                switch (userInfoData.getUserRole()) {
                    case "1":
                        intent = new Intent(this, ConcreteMainActivity.class);
                        break;
                    case "2":
                        intent = new Intent(this, PitchMainActivity.class);
                        break;
                }
            }
        }
        startActivity(intent);

        finish();
    }

    private void initParametersData() {
        BaseApplication.parametersData.userGroupID = userInfoData.getDepartId();
        BaseApplication.mDepartmentData.departmentID = userInfoData.getDepartId();
        BaseApplication.mDepartmentData.departmentName = userInfoData.getDepartName();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isBackPressed = true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);// 必须要调用这句
    }

    private void initLocation() {
        mLocationClient = new LocationClient(this);
        mLocationClient.start();
        LocationClientOption option = new LocationClientOption();
        mLocationClient.registerLocationListener(myListener);

        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(1000);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
}
