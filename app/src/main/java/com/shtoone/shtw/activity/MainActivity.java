package com.shtoone.shtw.activity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.bean.WeatherData;
import com.shtoone.shtw.fragment.mainactivity.ConcreteFragment;
import com.shtoone.shtw.fragment.mainactivity.PitchFragment;
import com.shtoone.shtw.fragment.tanpu.PaveSiteFragment;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.SharedPreferencesUtils;
import com.shtoone.shtw.utils.StringUtils;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportFragment;
import zhy.com.highlight.HighLight;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle toggle;
    private SupportFragment[] mFragments = new SupportFragment[3];
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private AHBottomNavigation bottomNavigation;
    private int bottomNavigationPreposition = 0;
    private HighLight mHightLight;
    private TextView tv_username;
    private FrameLayout fl_container;
    private TextView tv_phone_number;
    private TextView city;
    private TextView tempreture;
    private TextView weather;
    RequestQueue mQueue;
    WeatherData weatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            if (savedInstanceState == null) {
            mFragments[0] = ConcreteFragment.newInstance();
            mFragments[1] = PitchFragment.newInstance();
            mFragments[2] = PaveSiteFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_container_main_activity, 0, mFragments[0], mFragments[1],mFragments[2]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[0] = findFragment(ConcreteFragment.class);
            mFragments[1] = findFragment(PitchFragment.class);
            mFragments[2] = findFragment(PaveSiteFragment.class);

        }

        initView();
        initData();
        getWeather();
    }

    public void getWeather() {


        mQueue = Volley.newRequestQueue(this);
        SharedPreferences sharedPreferences = this.getSharedPreferences("chengshi", Context.MODE_PRIVATE);
        final String cityName = sharedPreferences.getString("name", "");
        Log.e("TAG", cityName);
        String url = "http://apicloud.mob.com/v1/weather/query?key=10fe7d0582836&city=" + StringUtils.getUTF8FromStr(cityName);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());

                        Gson gson = new Gson();
                        weatherData = gson.fromJson(response.toString(), WeatherData.class);
                        if (weatherData != null && weatherData.getRetCode().equals("200")) {
                            city.setText(cityName);
                            tempreture.setText(weatherData.getResult().get(0).getTemperature());
                            weather.setText(weatherData.getResult().get(0).getWeather());
                        } else {
                            Toast.makeText(getApplicationContext(), "请求天气数据失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
                //Toast.makeText(getApplicationContext(), "2" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(jsonObjectRequest);

    }

    private void initView() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView_main_activity);
        mNavigationView.setNavigationItemSelectedListener(this);
        fl_container = (FrameLayout) findViewById(R.id.fl_container_main_activity);
        LinearLayout llNavHeader = (LinearLayout) mNavigationView.getHeaderView(0);
        tv_username = (TextView) llNavHeader.findViewById(R.id.tv_username_nav_header_main);
        tv_phone_number = (TextView) llNavHeader.findViewById(R.id.tv_phone_number_nav_header_main);
        city = (TextView) llNavHeader.findViewById(R.id.navigation_header_container_city);
        tempreture = (TextView) llNavHeader.findViewById(R.id.iv_header_nav_header_tempreture);
        weather = (TextView) llNavHeader.findViewById(R.id.navigation_header_container_weather);
        llNavHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                mDrawer.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 250);
            }
        });
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation_main_activity);

    }

    public void initToolBar(Toolbar toolbar) {
        toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();
    }


    public void initData() {
        if (null != BaseApplication.mUserInfoData) {
            if (!TextUtils.isEmpty(BaseApplication.mUserInfoData.getUserFullName())) {
                tv_username.setText("用户：" + BaseApplication.mUserInfoData.getUserFullName());
            }
            if (!TextUtils.isEmpty(BaseApplication.mUserInfoData.getUserPhoneNum())) {
                tv_phone_number.setText("电话：" + BaseApplication.mUserInfoData.getUserPhoneNum());
            }
        }

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.concrete, R.drawable.ic_bhz, R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.asphalt, R.drawable.ic_android_black_24dp, R.color.white);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.pavesite, R.drawable.road, R.color.white);
        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.white));
        bottomNavigation.setBehaviorTranslationEnabled(true);
        bottomNavigation.setColored(true);
        bottomNavigation.setForceTint(false);
        bottomNavigation.setAccentColor(getResources().getColor(R.color.base_color));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.gray));
        bottomNavigation.setForceTitlesDisplay(true);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                showHideFragment(mFragments[position], mFragments[bottomNavigationPreposition]);
                bottomNavigationPreposition = position;
                if (position ==2) {
                    KLog.e(TAG, "22222");
                }
                if (!wasSelected && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = (fl_container.getLeft() + fl_container.getRight()) / 2;
                    int cy = fl_container.getBottom();
                    int radius = Math.max(fl_container.getWidth(), fl_container.getHeight());
                    Animator mAnimator = ViewAnimationUtils.createCircularReveal(fl_container, cx, cy, 0, radius);
                    mAnimator.setDuration(300);
                    mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    mAnimator.start();
                }
            }
        });
        bottomNavigation.setCurrentItem(0);
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
    public boolean onNavigationItemSelected(final MenuItem item) {
        mDrawer.closeDrawer(GravityCompat.START);
        mDrawer.postDelayed(new Runnable() {
            @Override
            public void run() {
                int id = item.getItemId();

                if (id == R.id.message_drawer_main_activity) {
                    JumpToMessageActivity();
                } else if (id == R.id.logout_drawer_main_activity) {
                    JumpToLoginActivity();
                } else if (id == R.id.weather_drawer_main_activity) {
                    KLog.e(TAG, "JumpToWeatherActivity()");
                    JumpToWeatherActivity();

                } else if (id == R.id.about_drawer_main_activity) {
                    JumpToAboutActivity();
                } else if (id == R.id.version_drawer_main_activity) {
                    JumpToVersionActivity();
                }
            }
        }, 250);
        return true;
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

    private void JumpToWeatherActivity() {
        KLog.e(TAG, "JumpToWeatherActivity()0");
        Intent intent = new Intent(this, WeatherActivity.class);
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
    protected void onResume() {
        if ((Boolean) SharedPreferencesUtils.get(MainActivity.this, ConstantsUtils.ISFIRSTGUIDE, true)) {
            showTipMask();
        }
        super.onResume();
    }

    private void showTipMask() {
        mHightLight = new HighLight(MainActivity.this)//
                .addHighLight(R.id.action_hierarchy, R.layout.info_gravity_right_up, new HighLight.OnPosCallback() {
                    @Override
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
                        marginInfo.rightMargin = 1;
                        marginInfo.topMargin = rectF.bottom;
                    }
                }).setClickCallback(new HighLight.OnClickCallback() {
                    @Override
                    public void onClick() {
                        mHightLight = new HighLight(MainActivity.this);
                        mHightLight.addHighLight(R.id.fab, R.layout.info_down, new HighLight.OnPosCallback() {
                            @Override
                            public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
                                marginInfo.rightMargin = rectF.width() / 2 + 1;
                                marginInfo.bottomMargin = bottomMargin + rectF.height();
                            }
                        }).setClickCallback(new HighLight.OnClickCallback() {
                            @Override
                            public void onClick() {
                                SharedPreferencesUtils.put(MainActivity.this, ConstantsUtils.ISFIRSTGUIDE, false);
                            }
                        }).show();
                    }
                });
        mHightLight.show();
    }

    @Override
    public boolean swipeBackPriority() {
        return false;
    }


}
