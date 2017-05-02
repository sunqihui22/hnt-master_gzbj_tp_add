package com.shtoone.shtw.fragment.weatheractivity;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.android.volley.VolleyError;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.WannengjiDetailActivity;
import com.shtoone.shtw.adapter.OnItemClickListener;
import com.shtoone.shtw.adapter.WeatherAdapter;
import com.shtoone.shtw.adapter.YaLiJiFragmentViewPagerFragmentRecyclerViewAdapter;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.bean.WannengjiFragmentViewPagerFragmentRecyclerViewItemData;
import com.shtoone.shtw.bean.WeatherData;
import com.shtoone.shtw.event.EventData;
import com.shtoone.shtw.event.MyLocationListener;
import com.shtoone.shtw.fragment.base.BaseFragment;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.StringUtils;
import com.socks.library.KLog;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;


public class WeatherActivityWeatherFragment extends BaseFragment {

    private static final String TAG = WeatherActivityWeatherFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private WeatherAdapter mAdapter;
    private PageStateLayout mPageStateLayout;
    private PtrFrameLayout mPtrFrameLayout;
    private boolean isRegistered = false;
    private WeatherData weatherData;
    private List<WeatherData.ResultEntity.FutureEntity> futureEntityList;
    private WeatherData.ResultEntity.FutureEntity futureEntity;
    //private ParametersData mParametersData;
    private int lastVisibleItemPosition;
    private boolean isLoading;
    private Gson mGson;
    private LinearLayoutManager mLinearLayoutManager;
    private ScaleInAnimationAdapter mScaleInAnimationAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public static WeatherActivityWeatherFragment newInstance() {
        return new WeatherActivityWeatherFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!isRegistered) {
            BaseApplication.bus.register(this);
            isRegistered = true;
        }
        View view = inflater.inflate(R.layout.recyclerview, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.ptrframelayout);
        mPageStateLayout = (PageStateLayout) view.findViewById(R.id.pagestatelayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
    }

    private void initData() {
        mGson = new Gson();
       futureEntityList = new ArrayList<>();
        mLinearLayoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //设置动画与适配器
        SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new WeatherAdapter(_mActivity, futureEntityList));
        mSlideInLeftAnimationAdapter.setDuration(500);
        mSlideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator(.5f));
        mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mSlideInLeftAnimationAdapter);
        mRecyclerView.setAdapter(mScaleInAnimationAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 实现局部界面刷新, 这个view就是被点击的item布局对象
                //changeItemState(view);
                // jumpToYaLiJiDetailActivity(position);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //还有一个不完美的地方就是当规定的item个数时，最后一个item在屏幕外滑到可见时，其底部没有footview，这点以后再解决。
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == mAdapter.getItemCount() && futureEntityList.size() >= 4) {

                    if (!isLoading) {
                        isLoading = true;
                        mRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                            //    KLog.e("第" + mParametersData.currentPage + "页");
                                loadMore();
                              //  KLog.e("上拉加载更多下一页=" + mParametersData.currentPage);
                                isLoading = false;
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();

                if (dy > 5) {
                    BaseApplication.bus.post(new EventData(ConstantsUtils.YALIJIFABHIDE));
                } else if (dy < -5) {
                    BaseApplication.bus.post(new EventData(ConstantsUtils.YALIJIFABSHOW));
                }
            }
        });

        initPageStateLayout(mPageStateLayout);
        initPtrFrameLayout(mPtrFrameLayout);
    }


    @Override
    public void onRefreshSuccess(String response) {
        KLog.e(TAG,"onRefreshSuccess");
        if (!TextUtils.isEmpty(response)) {
            KLog.e(TAG,"response is not Empty");
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
                mPageStateLayout.showError();
                return;
            }
            if (jsonObject.optString("msg").equals("success")) {//判断json是否有msg这个属性
                KLog.e(TAG,"msg  exists");
                futureEntityList.clear();
                        futureEntityList.addAll((mGson.fromJson(response, WeatherData.class).getResult().get(0).getFuture()));
                        if (null != futureEntityList) {
                            KLog.e("fdwf^11122233333333333333333333333333311111^^");
                            if (futureEntityList.size() > 0) {
                                KLog.e("fdwf^111224444444444444444444444444444311111^^");
                                mPageStateLayout.showContent();
                            //    mRecyclerView.setAdapter(mScaleInAnimationAdapter);
                                mAdapter.notifyDataSetChanged();//adapter刷新
                                KLog.e("fdwf^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

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
        if (!NetworkUtils.isConnected(_mActivity)) {
            //提示网络异常,让用户点击设置网络
            mPageStateLayout.showNetError();
        } else {
            //服务器异常，展示错误页面，点击刷新
            mPageStateLayout.showError();
        }
    }


    @Override
    public String createRefreshULR() {
        mPageStateLayout.showLoading();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("chengshi", Context.MODE_PRIVATE);
        String cityName = sharedPreferences.getString("name", "");
        if (!cityName.equals("") && !(cityName.length() == 0)) {
            return "http://apicloud.mob.com/v1/weather/query?key=10fe7d0582836&city=" + StringUtils.getUTF8FromStr(cityName);
        } else {

            KLog.e(TAG,"createRefreshULR=null");
            return null;
        }
    }
    public String createLoadMoreULR() {
        return null;
    }

    public void onLoadMoreFailed(VolleyError error) {
        mAdapter.notifyItemRemoved(mAdapter.getItemCount());//去除recyclerview的最后一个item
        TastyToast.makeText(_mActivity.getApplicationContext(), "没有更多数据!", TastyToast.LENGTH_SHORT, TastyToast.INFO);
    }

    @Override
    public boolean isCanDoRefresh() {
        //判断是哪种状态的页面，都让其可下拉
        if (mPageStateLayout.isShowContent) {
            //判断RecyclerView是否在在顶部，在顶部则允许滑动下拉刷新
            if (null != mRecyclerView) {
                if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    LinearLayoutManager lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    int position = lm.findFirstVisibleItemPosition();
                    if (position >= 0) {
                        if (lm.findViewByPosition(position).getTop() > 0 && position == 0 ) {
                            KLog.e(TAG,"msg  exists");
                            return true;
                        }
                    }
                }
            } else {
                return true;
            }
            return false;
        } else {
            return true;
        }
    }
}