package com.shtoone.shtw.fragment.roll;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ScrollView;

import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.DialogActivity;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.event.EventData;
import com.shtoone.shtw.fragment.base.BaseFragment;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.URL;
import com.socks.library.KLog;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;


/**
 * Created by Administrator on 2017/4/26.
 */

public class Rollingtemparture extends BaseFragment {

    private static final String TAG = Rollingtemparture.class.getSimpleName();

    private Toolbar                       mToolbar;
    private NestedScrollView              mNestedScrollView;
    private PageStateLayout               mPageStateLayout;
    private PtrFrameLayout                mPtrFrameLayout;
    private Typeface                      mTf;
    private RollingTemerratureRecylerView mAdapter;
    private FloatingActionButton          fab;
    private RecyclerView                  mRecyclerView;
    private boolean isRegistered = false;

    private LineChart mRollingTempareChart;
    private RollingTempartureData data;

    private ParametersData      mParametersData;
    private  Gson                mGson;
    private  LinearLayoutManager mLinearLayoutManager;
    private List<RollingTempartureData.DataEntity> mListData;
    private boolean isLoading;
    private int lastVisibleItemPosition;




    public static Rollingtemparture newInstance() {
        return new Rollingtemparture();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!isRegistered) {
            BaseApplication.bus.register(this);
            isRegistered = true;
        }
        View view = inflater.inflate(R.layout.fragment_rollingtemparture_statistic, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_toolbar);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        mNestedScrollView = (NestedScrollView) view.findViewById(R.id.nsv_rollingtempare_statistic_fragment);
        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.ptr_rollingtempare_statistic_fragment);
        mPageStateLayout = (PageStateLayout) view.findViewById(R.id.psl_rollingtempare_statistic_fragment);
 //       mRollingTempareChart = (LineChart) view.findViewById(R.id.linechart1_rollingtempare_statistic_fragment);



        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_rolling_statistic_fragment);
        initPageStateLayout(mPageStateLayout);
        initPtrFrameLayout(mPtrFrameLayout);
    }



    private void initData() {

        mGson = new Gson();
        mParametersData = (ParametersData) BaseApplication.parametersData.clone();
        mParametersData.userGroupID = BaseApplication.mDepartmentData.departmentID;
        mParametersData.fromTo = ConstantsUtils.TANBUXIANCHANG;
        mListData = new ArrayList<>();
        setToolbarTitle();
        initToolbarBackNavigation(mToolbar);




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_mActivity, DialogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstantsUtils.PARAMETERS, mParametersData);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //还有一个不完美的地方就是当规定的item个数时，最后一个item在屏幕外滑到可见时，其底部没有footview，这点以后再解决。
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == mAdapter.getItemCount() && mListData.size() >= 4) {
                    if (!isLoading) {
                        isLoading = true;
                        mRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                KLog.e("第" + mParametersData.currentPage + "页");
                                loadMore();
                                KLog.e("上拉加载更多下一页=" + mParametersData.currentPage);
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
                    fab.hide();
                } else if (dy < -5) {
                    fab.show();
                }
            }
        });



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
                        if (lm.findViewByPosition(position).getTop() > 0 && position == 0) {
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

    @Override
    public String createRefreshULR() {
        mPageStateLayout.showLoading();

        String userGroupID = "";
        String startDateTime = "";
        String endDateTime = "";
        String equipmentID = "";
        String currentPage = "";
        mParametersData.currentPage ="1";
        if (null != mParametersData) {
            userGroupID = mParametersData.userGroupID;
            startDateTime = mParametersData.startDateTime;
            endDateTime = mParametersData.endDateTime;
            equipmentID = mParametersData.equipmentID;
            currentPage = mParametersData.currentPage;
        }

        if (null != mListData) {
            mListData.clear();
        }

        return URL.getRollingTemputureData(userGroupID,equipmentID, startDateTime,endDateTime,currentPage);
    }

    @Override
    public String createLoadMoreULR() {
        mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) + 1) + "";//默认都是第一页
        String userGroupID = "";
        String startDateTime = "";
        String endDateTime = "";
        String equipmentID = "";
        String currentPage = "";
        if (null != mParametersData) {
            userGroupID = mParametersData.userGroupID;
            startDateTime = mParametersData.startDateTime;
            endDateTime = mParametersData.endDateTime;
            equipmentID = mParametersData.equipmentID;
            currentPage = mParametersData.currentPage;
        }
        return URL.getRollingTemputureData(userGroupID,equipmentID, startDateTime,endDateTime,currentPage);
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
                data = mGson.fromJson(response, RollingTempartureData.class);
                if (null != data) {
                    if (data.getSuccess() && data.getChart().size() > 0) {
                        mListData.addAll(data.getData());
                        mPageStateLayout.showContent();
                        setViewData();
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
    public void onLoadMoreSuccess(String response) {
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
                data = mGson.fromJson(response, RollingTempartureData.class);
                if (null != data) {
                    if (data.getSuccess() && data.getChart().size() > 0) {
                        if (null != mListData) {
                            mListData.addAll(data.getData());
                            if (mListData.size() > 0) {
                                mPageStateLayout.showContent();
                                mAdapter.notifyDataSetChanged();
                            } else {
                                TastyToast.makeText(_mActivity.getApplicationContext(), "无更多数据!", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                                mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
                                mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                            }
                        } else {
                            TastyToast.makeText(_mActivity.getApplicationContext(), "数据异常!", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                            mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
                            mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                        }
                    } else {
                        TastyToast.makeText(_mActivity.getApplicationContext(), "无更多数据!", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                        mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                    }
                } else {
                    TastyToast.makeText(_mActivity.getApplicationContext(), "解析异常!", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
                    mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                }
            } else {
                TastyToast.makeText(_mActivity.getApplicationContext(), "无更多数据!", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
                mAdapter.notifyItemRemoved(mAdapter.getItemCount());
            }
        } else {
            //提示返回数据异常，展示错误页面
            TastyToast.makeText(_mActivity.getApplicationContext(), "数据异常!", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
            mAdapter.notifyItemRemoved(mAdapter.getItemCount());
        }
    }

    @Subscribe
    public void updateSearch(ParametersData mParametersData) {
        if (mParametersData != null) {
            if (mParametersData.fromTo == ConstantsUtils.TANBUXIANCHANG) {
                this.mParametersData.startDateTime = mParametersData.startDateTime;
                this.mParametersData.endDateTime = mParametersData.endDateTime;
                this.mParametersData.equipmentID = mParametersData.equipmentID;
                KLog.e("mParametersData:" + mParametersData.startDateTime);
                KLog.e("mParametersData:" + mParametersData.endDateTime);
                KLog.e("mParametersData:" + mParametersData.equipmentID);
                mPtrFrameLayout.autoRefresh(true);
            }
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

    private void setViewData() {

//        mRollingTempareChart.setDescription("");
//        mRollingTempareChart.setDrawGridBackground(true);
//        mRollingTempareChart.setNoDataTextDescription("暂无数据表……");
//        mRollingTempareChart.setTouchEnabled(true);
//        mRollingTempareChart.setDragEnabled(true);
//        mRollingTempareChart.setScaleEnabled(true);
//        mRollingTempareChart.setPinchZoom(true);
//        mRollingTempareChart.animateX(1500);
//        mRollingTempareChart.getAxisRight().setEnabled(false);
//
//        MyMarkerView mv = new MyMarkerView(_mActivity, R.layout.custom_marker_view);
//        mRollingTempareChart.setMarkerView(mv);
//
//        Typeface tf = Typeface.createFromAsset(_mActivity.getAssets(), "OpenSans-Light.ttf");
//        Legend l = mRollingTempareChart.getLegend();
//        l.setTypeface(tf);
//        YAxis leftAxis = mRollingTempareChart.getAxisLeft();
//        leftAxis.setTypeface(tf);
//        leftAxis.setAxisMinValue(0f);
//        leftAxis.removeAllLimitLines();
//        leftAxis.setTypeface(tf);
//        leftAxis.setTextColor(Color.RED);
//
//        XAxis xAxis = mRollingTempareChart.getXAxis();
//        xAxis.setEnabled(true);
//        xAxis.setTypeface(tf);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTextColor(Color.BLUE);




        mLinearLayoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //设置动画与适配器
        SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new RollingTemerratureRecylerView(_mActivity,mListData));
        mSlideInLeftAnimationAdapter.setFirstOnly(true);
        mSlideInLeftAnimationAdapter.setDuration(500);
        mSlideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator(.5f));
        ScaleInAnimationAdapter mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mSlideInLeftAnimationAdapter);
        mScaleInAnimationAdapter.setFirstOnly(true);
        mRecyclerView.setAdapter(mScaleInAnimationAdapter);

    }

    private void setChartData() {
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();
//        String[] x = data.getX();
        List<RollingTempartureData.DataEntity> dataEntity = data.getData();
        for (int i = 0; i < dataEntity.size(); i++) {
            RollingTempartureData.DataEntity dataEntity1 = dataEntity.get(i);
            String tmpshijian = dataEntity1.getTmpshijian();
            String tmpdata = dataEntity1.getTmpdata();
            xVals.add(tmpshijian+"");
            yVals.add(new Entry(Float.parseFloat(tmpdata),i));
        }


//        String[] y = data.getY();

//        for (int i = 0; i < y.length; i++) {
//            yVals.add(new Entry(Float.parseFloat(y[i]), i));
//        }

        LineDataSet mLineDataSet = new LineDataSet(yVals, "曲线图");

        if (Utils.getSDKInt() >= 18) {
            Drawable drawable = ContextCompat.getDrawable(_mActivity, R.drawable.fade_red);
            mLineDataSet.setFillDrawable(drawable);
        }
        //设置样式
        mLineDataSet.enableDashedLine(10f, 5f, 0f);
        mLineDataSet.enableDashedHighlightLine(10f, 5f, 0f);
        mLineDataSet.setColor(Color.BLACK);
        mLineDataSet.setCircleColor(Color.BLUE);
        mLineDataSet.setLineWidth(1f);
        mLineDataSet.setCircleRadius(0f);
        mLineDataSet.setHighLightColor(Color.BLACK);
        mLineDataSet.setDrawCircleHole(true);
        mLineDataSet.setValueTextSize(7f);
        mLineDataSet.setDrawFilled(true);
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(mLineDataSet);
        LineData data = new LineData(xVals, dataSets);
        mRollingTempareChart.setData(data);


    }

    @Subscribe
    public void go2TopOrRefresh(EventData event) {
        if (event.position == 2) {
            mNestedScrollView.fullScroll(ScrollView.SCROLL_INDICATOR_TOP);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //返回到看见此fragment时，fab显示
        fab.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        //防止屏幕旋转后重画时fab显示
        fab.hide();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.bus.unregister(this);
    }

    private void setToolbarTitle() {
        if (null != mToolbar && null != BaseApplication.mDepartmentData && !TextUtils.isEmpty(BaseApplication.mDepartmentData.departmentName)) {
            StringBuffer sb = new StringBuffer(BaseApplication.mDepartmentData.departmentName + " > ");
            sb.append(getString(R.string.paving_site) + " > ");
            sb.append(getString(R.string.rolling_temperture)).trimToSize();
            mToolbar.setTitle(sb.toString());
        }

    }

}
