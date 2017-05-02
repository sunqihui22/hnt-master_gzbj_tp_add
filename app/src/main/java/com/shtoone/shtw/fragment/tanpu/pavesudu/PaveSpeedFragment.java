package com.shtoone.shtw.fragment.tanpu.pavesudu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.LineChart;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.DialogActivity;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.fragment.base.BaseFragment;
import com.shtoone.shtw.fragment.tanpu.TanpuWenduFragmentData;
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

public class PaveSpeedFragment extends BaseFragment {

    private static final String TAG = PaveSpeedFragment.class.getSimpleName();
    private boolean isRegistered = false;
    private Toolbar mToolbar;
    private NestedScrollView mNestedScrollView;
    private PageStateLayout mPageStateLayout;
    private PtrFrameLayout mPtrFrameLayout;
    private FloatingActionButton fab;
    private RecyclerView mRecyclerView;
    private LineChart lineChart;
    private Gson mGson;
    private ParametersData mParametersData;
    private boolean isLoading;
    private int lastVisibleItemPosition;
    private LinearLayoutManager mLinearLayoutManager;
    private PaveSpeedFragmentRecyclerViewAdapter mAdapter;
    private List<PaveSpeedFragmentData.DataBean> listData;
    private List<TanpuWenduFragmentData.ChartBean> paveSpeedCharList;
    private PaveSpeedFragmentData data;


    public static PaveSpeedFragment newInstance() {
        return new PaveSpeedFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!isRegistered) {
            BaseApplication.bus.register(this);
            isRegistered = true;
        }
        View view = inflater.inflate(R.layout.fragment_pave_speed, container, false);
        initView(view);
        initData();
        return view;
    }

    public void initView(View view){
        listData=new ArrayList<>();
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_toolbar);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
//        mNestedScrollView = (NestedScrollView) view.findViewById(R.id.nsv_pave_speed_fragment);
//        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.ptr_pave_speed_fragment);
//        mPageStateLayout = (PageStateLayout) view.findViewById(R.id.psl_pave_speed_fragment);
//        lineChart = (LineChart) view.findViewById(R.id.linechart0_pave_speed_fragment);
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_pave_speed_fragment);
        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.ptrframelayout);
        mPageStateLayout = (PageStateLayout) view.findViewById(R.id.pagestatelayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        initPageStateLayout(mPageStateLayout);
        initPtrFrameLayout(mPtrFrameLayout);
    }

    public void initData(){

        mGson = new Gson();
        mParametersData = (ParametersData) BaseApplication.parametersData.clone();
        mParametersData.userGroupID = BaseApplication.mDepartmentData.departmentID;
        mParametersData.fromTo = ConstantsUtils.PAVESPEEDFRAGMENT;
        setToolbarTitle();
        initToolbarBackNavigation(mToolbar);
//        initToolbarMenu(mToolbar);

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
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == mAdapter.getItemCount() && listData.size() >= 4) {
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

//    @Override
//    public boolean isCanDoRefresh() {
//        //判断是哪种状态的页面，都让其可下拉
//        if (mNestedScrollView.getScrollY() == 0) {
//            return true;
//        }
//        return false;
//    }

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
        String maxPageItems="";
        mParametersData.currentPage = "1";//默认都是第一页
        if (null != mParametersData) {
            userGroupID = mParametersData.userGroupID;
            startDateTime = mParametersData.startDateTime;
            endDateTime = mParametersData.endDateTime;
            equipmentID = mParametersData.equipmentID;
            currentPage = mParametersData.currentPage;
            maxPageItems=mParametersData.maxPageItems;
        }
        return URL.getTanpuSudu(userGroupID, startDateTime, endDateTime, equipmentID,currentPage,maxPageItems);
    }

    @Override
    public String createLoadMoreULR() {
        mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) + 1) + "";//默认都是第一页
        String userGroupID = "";
        String startDateTime = "";
        String endDateTime = "";
        String currentPage = "";
        String equipmentID = "";
        String maxPageItems="";
        if (null != mParametersData) {
            userGroupID = mParametersData.userGroupID;
            startDateTime = mParametersData.startDateTime;
            endDateTime = mParametersData.endDateTime;
            currentPage = mParametersData.currentPage;
            equipmentID = mParametersData.equipmentID;
            maxPageItems=mParametersData.maxPageItems;
        }
        return URL.getTanpuSudu(userGroupID, startDateTime, endDateTime, equipmentID,currentPage,maxPageItems);
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
                data = mGson.fromJson(response, PaveSpeedFragmentData.class);
                if (null != data) {
                    if (data.isSuccess() && data.getData().size() > 0 ) {
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
                data = mGson.fromJson(response, PaveSpeedFragmentData.class);
                if (null != data) {
                    if (data.isSuccess() && data.getData().size() > 0) {
                        if (null != listData) {
                            listData.addAll(data.getData());
                            if (listData.size() > 0) {
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

    @Override
    public void onLoadMoreFailed(VolleyError error) {
        super.onLoadMoreFailed(error);
        mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
    }

    private void setViewData() {
//        setChart();
//        setChartData();
        mLinearLayoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //设置动画与适配器
        SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new PaveSpeedFragmentRecyclerViewAdapter(_mActivity, data.getData()));
        mSlideInLeftAnimationAdapter.setFirstOnly(true);
        mSlideInLeftAnimationAdapter.setDuration(500);
        mSlideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator(.5f));
        ScaleInAnimationAdapter mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mSlideInLeftAnimationAdapter);
        mScaleInAnimationAdapter.setFirstOnly(true);
        mRecyclerView.setAdapter(mScaleInAnimationAdapter);
    }

//    private void setChart() {
//        lineChart.setDescription("");
//        lineChart.setDrawGridBackground(true);
//        lineChart.setNoDataTextDescription("暂无数据表……");
//        lineChart.setTouchEnabled(true);
//        lineChart.setDragEnabled(true);
//        lineChart.setScaleEnabled(true);
//        lineChart.setPinchZoom(true);
//        lineChart.animateX(1500);
//        lineChart.getAxisRight().setEnabled(false);
//
//        MyMarkerView mv = new MyMarkerView(_mActivity, R.layout.custom_marker_view);
//        lineChart.setMarkerView(mv);
//
//        Typeface tf = Typeface.createFromAsset(_mActivity.getAssets(), "OpenSans-Light.ttf");
//
//        YAxis leftAxis = lineChart.getAxisLeft();
//        leftAxis.setTypeface(tf);
//        leftAxis.setAxisMinValue(0f);
//        leftAxis.removeAllLimitLines();
//        leftAxis.setTypeface(tf);
//        leftAxis.setTextColor(Color.RED);
//
//        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setEnabled(true);
//        xAxis.setTypeface(tf);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTextColor(Color.BLUE);
//        xAxis.setAvoidFirstLastClipping(true);
//        xAxis.setLabelRotationAngle(10f);
//
//        Legend l = lineChart.getLegend();
//        l.setTypeface(tf);
////        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
//        l.setXEntrySpace(4f);
//        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART_INSIDE);//设置图例的位置
//        l.setTextSize(11f);//设置文字大小
//        l.setForm(Legend.LegendForm.CIRCLE);//正方形，圆形或线
//        l.setFormSize(9f); // 设置Form的大小
//        l.setWordWrapEnabled(true);//是否支持自动换行 目前只支持BelowChartLeft, BelowChartRight, BelowChartCenter
//    }
//
//    private void setChartData() {
//        ArrayList<String> xVals = new ArrayList<String>();
//        //   String[] x = data.getX();
//        for (int i = 0; i < paveSpeedCharList.size(); i++) {
//            xVals.add(paveSpeedCharList.get(i).getShijian());
//        }
//
//        ArrayList<Entry> yVals0 = new ArrayList<Entry>();
//
//        for (int i = 0; i < paveSpeedCharList.size(); i++) {
//
//            yVals0.add(new Entry(Float.parseFloat(paveSpeedCharList.get(i).getWendu()), i));
//        }
//
//        LineDataSet mLineDataSet0 = new LineDataSet(yVals0, "允许波动上线");
//
//        if (Utils.getSDKInt() >= 18) {
//            Drawable drawable0 = ContextCompat.getDrawable(_mActivity, R.drawable.fade_red);
//            mLineDataSet0.setFillDrawable(drawable0);
//
//        }
//        //设置样式
//        mLineDataSet0.enableDashedLine(10f, 5f, 0f);
//        mLineDataSet0.enableDashedHighlightLine(10f, 5f, 0f);
//        mLineDataSet0.setColor(Color.BLACK);
//        mLineDataSet0.setCircleColor(Color.BLUE);
//        mLineDataSet0.setLineWidth(1f);
//        mLineDataSet0.setCircleRadius(0f);
//        mLineDataSet0.setHighLightColor(Color.BLACK);
//        mLineDataSet0.setDrawCircleHole(true);
//        mLineDataSet0.setValueTextSize(7f);
//
//        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
//        dataSets.add(mLineDataSet0);
//        LineData data = new LineData(xVals, dataSets);
//        lineChart.setData(data);
//        KLog.e(TAG,"data=:"+data.toString());
//    }

    @Subscribe
    public void updateSearch(ParametersData mParametersData) {
        if (mParametersData != null) {
            if (mParametersData.fromTo == ConstantsUtils.PAVESPEEDFRAGMENT) {
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

//    @Subscribe
//    public void go2TopOrRefresh(EventData event) {
//        if (event.position == 2) {
//            mNestedScrollView.fullScroll(ScrollView.SCROLL_INDICATOR_TOP);
//        }
//    }

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
            sb.append(getString(R.string.asphalt) + " > ");
            sb.append(getString(R.string.pave_speed)).trimToSize();
            mToolbar.setTitle(sb.toString());
        }
    }
}
