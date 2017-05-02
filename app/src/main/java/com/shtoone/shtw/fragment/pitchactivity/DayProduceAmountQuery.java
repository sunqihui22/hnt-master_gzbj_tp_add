package com.shtoone.shtw.fragment.pitchactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.DayProduceQueryDetailActivity;
import com.shtoone.shtw.adapter.PitchDayProdeceRecyclerViewAdapter;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.bean.PitchDayProdeceRecyclerViewItemData;
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
 * Created by gesangdianzi on 2016/9/2.
 */
public class DayProduceAmountQuery extends BaseFragment {
    private static final String TAG = DayProduceAmountQuery.class.getSimpleName();
    private RecyclerView mRecyclerView;

    private PitchDayProdeceRecyclerViewAdapter mAdapter;
    private PageStateLayout mPageStateLayout;
    private PtrFrameLayout mPtrFrameLayout;
    private boolean isRegistered = false;
    private PitchDayProdeceRecyclerViewItemData itemData;
    private ParametersData mParametersData;
    private int lastVisibleItemPosition;
    private boolean isLoading;
    private List<PitchDayProdeceRecyclerViewItemData.DataBean> listData;
    private Gson mGson;
    private LinearLayoutManager mLinearLayoutManager;
    private ScaleInAnimationAdapter mScaleInAnimationAdapter;
    private SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter;

    public static DayProduceAmountQuery newInstance(ParametersData mParametersData) {
        Bundle args = new Bundle();
        args.putSerializable(ConstantsUtils.PARAMETERS, mParametersData);
        DayProduceAmountQuery fragment = new DayProduceAmountQuery();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mParametersData = (ParametersData) args.getSerializable(ConstantsUtils.PARAMETERS);//得到参数bean
        }
    }

    @Nullable
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
        listData = new ArrayList<>();
        itemData=new PitchDayProdeceRecyclerViewItemData();
        mLinearLayoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new PitchDayProdeceRecyclerViewAdapter(_mActivity, listData));
//        mAdapter.setOnItemClickListener(new OnItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position) {
//                    // 实现局部界面刷新, 这个view就是被点击的item布局对象
//                    changeItemState(view);
//                    jumpToLiQingDetailActivity(position);
//
//                }
//            });
        mSlideInLeftAnimationAdapter.setDuration(500);
        mSlideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator(.5f));
        mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mSlideInLeftAnimationAdapter);
        mRecyclerView.setAdapter(mScaleInAnimationAdapter);
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
    public boolean isCanDoRefresh() {
        //判断是哪种状态的页面，都让其可下拉
        if (mPageStateLayout.isShowContent) {
            //判断RecyclerView是否在在顶部，在顶部则允许滑动下拉刷新
            if (null != mRecyclerView) {
                if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    LinearLayoutManager lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    int position = lm.findFirstVisibleItemPosition();
                    if (position >= 0) {
                        if (lm.findViewByPosition(position).getTop() > 0 && position == 0 && BaseApplication.isExpand) {
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
        mParametersData.currentPage = "1";//默认都是第一页
        String userGroupID = "";
        String startDateTime = "2015-03-01 00:00:00";
        String endDateTime = "2016-06-01 00:00:00";
        String currentPage = "";
        String equipmentID = "";
        String isQualified = "";
        String isReal = "";
        String testType = "";
        if (null != mParametersData) {
            userGroupID = mParametersData.userGroupID;
            startDateTime = mParametersData.startDateTime;
            endDateTime = mParametersData.endDateTime;
            currentPage = mParametersData.currentPage;
            equipmentID = mParametersData.equipmentID;
            isQualified = mParametersData.isQualified;
            isReal = mParametersData.isReal;
            testType = mParametersData.testTypeID;
        }

        if (null != listData) {
            listData.clear();
        }

        Log.e("时间", "开始时间: "+ startDateTime+"结束时间"+endDateTime);
        Log.e(TAG,"日产量url=:"+URL.getDayLQProduceData(userGroupID, startDateTime, endDateTime, currentPage, equipmentID));
        return URL.getDayLQProduceData(userGroupID, startDateTime, endDateTime, currentPage, equipmentID);

    }

    @Override
    public String createLoadMoreULR() {
        mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) + 1) + "";//默认都是第一页
        String userGroupID = "";
        String startDateTime = "";
        String endDateTime = "";
        String currentPage = "";
        String equipmentID = "";
        String isQualified = "";
        String isReal = "";
        String testType = "";
        if (null != mParametersData) {
            userGroupID = mParametersData.userGroupID;
            startDateTime = mParametersData.startDateTime;
            endDateTime = mParametersData.endDateTime;
            currentPage = mParametersData.currentPage;
            equipmentID = mParametersData.equipmentID;
            isQualified = mParametersData.isQualified;
            isReal = mParametersData.isReal;
            testType = mParametersData.testTypeID;
        }

        return URL.getDayLQProduceData(userGroupID, startDateTime, endDateTime, currentPage, equipmentID);


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
                    itemData = mGson.fromJson(response, PitchDayProdeceRecyclerViewItemData.class);
                    if (null != itemData) {
                        if (itemData.isSuccess() && itemData.getData().size() > 0) {
                            listData.addAll(itemData.getData());
                            if (null != listData) {
                                if (listData.size() > 0) {
                                    mPageStateLayout.showContent();
                                    mRecyclerView.setAdapter(mScaleInAnimationAdapter);
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
                itemData = mGson.fromJson(response, PitchDayProdeceRecyclerViewItemData.class);
                if (null != itemData) {
                    if (itemData.isSuccess() && itemData.getData().size() > 0) {
                        if (null != listData) {
                            listData.addAll(itemData.getData());
                            if (listData.size() > 0) {
                                mPageStateLayout.showContent();
                                mAdapter.notifyDataSetChanged();
                                isLoading = false;
                            } else {
                                TastyToast.makeText(_mActivity.getApplicationContext(), "无更多数据!", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                                mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
                                mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                                isLoading = false;
                            }
                        } else {
                            TastyToast.makeText(_mActivity.getApplicationContext(), "数据异常!", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                            mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
                            mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                            isLoading = false;
                        }
                    } else {
                        TastyToast.makeText(_mActivity.getApplicationContext(), "无更多数据!", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                        mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                        isLoading = false;
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
                isLoading = false;
            }
        } else {
            //提示返回数据异常，展示错误页面
            TastyToast.makeText(_mActivity.getApplicationContext(), "数据异常!", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
            mAdapter.notifyItemRemoved(mAdapter.getItemCount());
            isLoading = false;
        }
    }

    @Override
    public void onLoadMoreFailed(VolleyError error) {
        super.onLoadMoreFailed(error);
        mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
    }

    private void changeItemState(View view) {
//        ((CardView) view).setCardBackgroundColor(Color.parseColor("#0a000000"));
    }

    //进入YaLiJiDetailActivity
    private void jumpToLiQingDetailActivity(int position) {
        Intent intent = new Intent(_mActivity, DayProduceQueryDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("chaxunitem", listData.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Subscribe
    public void updateSearch(ParametersData mParametersData) {
        if (mParametersData != null) {
            if (mParametersData.fromTo == ConstantsUtils.PITCHPRODUCEQUERYFRAGMENT) {
                this.mParametersData.startDateTime = mParametersData.startDateTime;
                this.mParametersData.endDateTime = mParametersData.endDateTime;
                this.mParametersData.equipmentID = mParametersData.equipmentID;
                this.mParametersData.testTypeID = mParametersData.testTypeID;
                KLog.e("mParametersData:" + mParametersData.startDateTime);
                KLog.e("mParametersData:" + mParametersData.endDateTime);
                KLog.e("mParametersData:" + mParametersData.equipmentID);
                KLog.e("mParametersData:" + mParametersData.testTypeID);
                mPtrFrameLayout.autoRefresh(true);
            }
        }
    }

    @Subscribe
    public void go2TopOrRefresh(EventData event) {
        if (event.position == 0) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    @Subscribe
    public void handleRefresh(EventData event) {
        if (event.position == ConstantsUtils.REFRESH) {
            mPtrFrameLayout.autoRefresh(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        BaseApplication.bus.post(new EventData(ConstantsUtils.YALIJIFABSHOW));
    }

    @Override
    public void onPause() {
        super.onPause();
        BaseApplication.bus.post(new EventData(ConstantsUtils.YALIJIFABSHOW));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.bus.unregister(this);
    }

}