package com.shtoone.shtw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.bean.PitchMainActivityData;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.SharedPreferencesUtils;
import com.shtoone.shtw.utils.URL;

import org.json.JSONException;
import org.json.JSONObject;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2017/4/23.
 */
public class PitchMainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = PitchMainActivity.class.getSimpleName();
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle toggle;
    private TextView tv_username;
    private TextView tv_phone_number;
    private Toolbar mToolbar;
    private LinearLayout llNavHeader;
    private CardView pitch_product_query, pitch_overproof, pitch_statistic;
    private TextView tv_ever_product, tv_ever_panshu, tv_total_panshu,tv_total_product, tv_total_overproof, tv_handle_panshu, tv_pend_panshu,tv_chuzhilv;
    private PitchMainActivityData data;
    private PtrFrameLayout        mPtrFrameLayout;
    private PageStateLayout       mPageStateLayout;
    private TextView              tv_produce_query_time;
    private TextView              tv_overproof_time;
    private TextView              tv_statistic_time;
    private Gson                  mGson;
    private ParametersData        mParametersData;
    private boolean isRegistered = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitch_main);

        initView();
        initData();
    }

    public void initView(){
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView_pitch_main_activity);
        mNavigationView.setNavigationItemSelectedListener(this);
        llNavHeader = (LinearLayout) mNavigationView.getHeaderView(0);
        tv_username = (TextView) llNavHeader.findViewById(R.id.tv_username_nav_header_main);
        tv_phone_number = (TextView) llNavHeader.findViewById(R.id.tv_phone_number_nav_header_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_toolbar);
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.ptr_pitch_main_activity);
        mPageStateLayout = (PageStateLayout) findViewById(R.id.psl_pitch_main_activity);

        pitch_product_query = (CardView) findViewById(R.id.cv_produce_query_pitch_main_activity);
        pitch_overproof = (CardView) findViewById(R.id.cv_overproof_pitch_main_activity);
        pitch_statistic = (CardView) findViewById(R.id.cv_statistic_pitch_main_activity);

        tv_ever_product = (TextView) findViewById(R.id.tv_ever_product_pitch_main_activity);
        tv_ever_panshu = (TextView) findViewById(R.id.tv_ever_panshu_pitch_main_activity);
        tv_total_panshu = (TextView) findViewById(R.id.tv_total_panshu_pitch_main_activity);
        tv_total_product = (TextView) findViewById(R.id.tv_total_product_pitch_main_activity);

        tv_total_overproof = (TextView) findViewById(R.id.tv_total_overproof_pitch_main_activity);
        tv_handle_panshu = (TextView) findViewById(R.id.tv_handle_panshu_pitch_main_activity);
        tv_pend_panshu = (TextView) findViewById(R.id.tv_pend_panshu_pitch_main_activity);
        tv_chuzhilv = (TextView) findViewById(R.id.tv_chuzhilv_pitch_main_activity);

        tv_produce_query_time = (TextView) findViewById(R.id.tv_produce_query_time_pitch_main_activity);
        tv_overproof_time = (TextView) findViewById(R.id.tv_overproof_time_pitch_main_activity);
        tv_statistic_time = (TextView) findViewById(R.id.tv_statistic_time_pitch_main_activity);

        tv_produce_query_time.setText(BaseApplication.parametersData.endDateTime);
        tv_overproof_time.setText(BaseApplication.parametersData.endDateTime);
        tv_statistic_time.setText(BaseApplication.parametersData.endDateTime);

    }

    public void initData() {
        mGson = new Gson();
        toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        if (null != mToolbar && null != BaseApplication.mUserInfoData && !TextUtils.isEmpty(BaseApplication.mUserInfoData.getDepartName())) {
            StringBuffer sb = new StringBuffer(BaseApplication.mUserInfoData.getDepartName() + " > ");
            sb.append(getString(R.string.banhezhan)).trimToSize();
            mToolbar.setTitle(sb.toString());
        }

        if (null != BaseApplication.mUserInfoData) {
            if (!TextUtils.isEmpty(BaseApplication.mUserInfoData.getUserFullName())) {
                tv_username.setText("用户：" + BaseApplication.mUserInfoData.getUserFullName());
            }
            if (!TextUtils.isEmpty(BaseApplication.mUserInfoData.getUserPhoneNum())) {
                tv_phone_number.setText("电话：" + BaseApplication.mUserInfoData.getUserPhoneNum());
            }
        }

        llNavHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                mDrawer.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 500);
            }
        });

        pitch_product_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PitchMainActivity.this, PitchActivity.class);
                intent.putExtra("SG", "producequery");
                startActivity(intent);
            }
        });

        pitch_overproof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PitchMainActivity.this, PitchActivity.class);
                intent.putExtra("SG", "overproof");
                startActivity(intent);
            }
        });

        pitch_statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PitchMainActivity.this, PitchActivity.class);
                intent.putExtra("SG", "statistic");
                startActivity(intent);
            }
        });

        initPageStateLayout(mPageStateLayout);
        initPtrFrameLayout(mPtrFrameLayout);
    }

    @Override
    public String createRefreshULR() {
        mPageStateLayout.showLoading();
        //return URL.getBHZSGMain(BaseApplication.mDepartmentData.departmentID);
        return URL.getLQLingShigongData(BaseApplication.mDepartmentData.departmentID);
    }

    @Override
    public void onRefreshSuccess(String response) {
        if (!TextUtils.isEmpty(response)) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
                mPageStateLayout.showError();
                return;
            }
            if (jsonObject.optBoolean("success")) {
                data = mGson.fromJson(response, PitchMainActivityData.class);
                if (null != data) {
                    if (data.isSuccess()) {
                        mPageStateLayout.showContent();
                        setAdapter();

                    } else {
                        //提示数据为空，展示空状态
                        mPageStateLayout.showEmpty();
                    }
                } else {
                    //提示数据解析异常，展示错误页面
                    mPageStateLayout.showError();
                }
            } else {
                //提示数据为空，展示空状态
                mPageStateLayout.showEmpty();
            }
        } else {
            //提示返回数据异常，展示错误页面
            mPageStateLayout.showError();
        }
    }

    @Override
    public void onRefreshFailed(VolleyError error) {
        //提示网络数据异常，展示网络错误页面。此时：1.可能是本机网络有问题，2.可能是服务器问题
        if (!NetworkUtils.isConnected(this)) {
            //提示网络异常,让用户点击设置网络
            mPageStateLayout.showNetError();
        } else {
            //服务器异常，展示错误页面，点击刷新
            mPageStateLayout.showError();
        }
    }

    private void setAdapter() {

        tv_ever_product = (TextView) findViewById(R.id.tv_ever_product_pitch_main_activity);
        tv_ever_panshu = (TextView) findViewById(R.id.tv_ever_panshu_pitch_main_activity);
        tv_total_panshu = (TextView) findViewById(R.id.tv_total_panshu_pitch_main_activity);
        tv_total_product = (TextView) findViewById(R.id.tv_total_product_pitch_main_activity);

        tv_total_overproof = (TextView) findViewById(R.id.tv_total_overproof_pitch_main_activity);
        tv_handle_panshu = (TextView) findViewById(R.id.tv_handle_panshu_pitch_main_activity);
        tv_pend_panshu = (TextView) findViewById(R.id.tv_pend_panshu_pitch_main_activity);
        tv_chuzhilv = (TextView) findViewById(R.id.tv_chuzhilv_pitch_main_activity);

        if (!TextUtils.isEmpty(data.getData().getDailycl())) {
            tv_ever_product.setText(data.getData().getDailycl());
        }
        if (!TextUtils.isEmpty(data.getData().getDailyps())) {
            tv_ever_panshu.setText(data.getData().getDailyps());
        }
        if (!TextUtils.isEmpty(data.getData().getPanshu())) {
            tv_total_panshu.setText(data.getData().getPanshu());
        }
        if (!TextUtils.isEmpty(data.getData().getLjchangliang())) {
            tv_total_product.setText(data.getData().getLjchangliang());
        }
        if (!TextUtils.isEmpty(data.getData().getZcbps()+"")) {
            tv_total_overproof.setText(data.getData().getZcbps()+"");
        }
        if (!TextUtils.isEmpty(data.getData().getCzps()+"")) {
            tv_handle_panshu.setText(data.getData().getCzps()+"");
        }
        if (!TextUtils.isEmpty(data.getData().getDczps()+"")) {
            tv_pend_panshu.setText(data.getData().getDczps()+"");
        }
        if (!TextUtils.isEmpty(data.getData().getCblv()+"")) {
            tv_chuzhilv.setText(data.getData().getCblv()+"");
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                finish();
            } else {
                TOUCH_TIME = System.currentTimeMillis();
                Toast.makeText(this, R.string.press_again_exit, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.message_drawer_main_activity) {
//            JumpToMessageActivity();
        } else if (id == R.id.logout_drawer_main_activity) {
            JumpToLoginActivity();
        } else if (id == R.id.about_drawer_main_activity) {
            JumpToAboutActivity();
        } else if (id == R.id.version_drawer_main_activity) {
            JumpToVersionActivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void JumpTo() {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    private void JumpToLoginActivity() {
        //清除已存的用户信息
        SharedPreferencesUtils.put(this, ConstantsUtils.USERNAME, "");
        SharedPreferencesUtils.put(this, ConstantsUtils.PASSWORD, "");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void JumpToMessageActivity() {
        Intent intent = new Intent(this, MessageActivity.class);
        startActivity(intent);
    }

    private void JumpToAboutActivity() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void JumpToVersionActivity() {
        Intent intent = new Intent(this, VersionActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean swipeBackPriority() {
        return false;
    }
}
