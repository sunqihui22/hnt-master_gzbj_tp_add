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
import com.shtoone.shtw.activity.OverproofDetailActivity;
import com.shtoone.shtw.activity.PitchOverproofDetailActivity;
import com.shtoone.shtw.adapter.OnItemClickListener;
import com.shtoone.shtw.adapter.PitchOverproofFragmentViewPagerFragmentRecyclerViewAdapter;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.bean.PitchOverproofFragmentViewPagerFragmentItemData;
import com.shtoone.shtw.bean.PitchOverproofFragmentViewPagerFragmentListData;
import com.shtoone.shtw.event.EventData;
import com.shtoone.shtw.fragment.base.BaseFragment;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.URL;
import com.socks.library.KLog;
import com.squareup.otto.Subscribe;

import org.json.JSONArray;
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
public class PitchOverproofFragmentViewPagerFragment extends BaseFragment {
    private static final String TAG = PitchOverproofFragmentViewPagerFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private PageStateLayout mPageStateLayout;
    private PtrFrameLayout mPtrFrameLayout;
    private boolean isRegistered = false;
    private ParametersData mParametersData;
    private int lastVisibleItemPosition;
    private PitchOverproofFragmentViewPagerFragmentRecyclerViewAdapter mAdapter;
    private PitchOverproofFragmentViewPagerFragmentListData itemsData;
    private boolean isLoading;
    private Gson mGson;

    private LinearLayoutManager mLinearLayoutManager;
    private ScaleInAnimationAdapter mScaleInAnimationAdapter;
    private List<PitchOverproofFragmentViewPagerFragmentListData> lists;

    public static PitchOverproofFragmentViewPagerFragment newInstance(ParametersData mParametersData) {
        Bundle args = new Bundle();
        args.putSerializable(ConstantsUtils.PARAMETERS, mParametersData);
        PitchOverproofFragmentViewPagerFragment fragment = new PitchOverproofFragmentViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mParametersData = (ParametersData) args.getSerializable(ConstantsUtils.PARAMETERS);
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
        lists = new ArrayList<>();
        mLinearLayoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        //设置动画与适配器
        SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new PitchOverproofFragmentViewPagerFragmentRecyclerViewAdapter(_mActivity, lists));
        mSlideInLeftAnimationAdapter.setDuration(500);
        mSlideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator(.5f));
        mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mSlideInLeftAnimationAdapter);
        mRecyclerView.setAdapter(mScaleInAnimationAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                KLog.e(TAG,"onitemclick000000000");
                // 实现局部界面刷新, 这个view就是被点击的item布局对象
                changeReadedState(view);
                jump2OverproofDetailActivity(position);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //还有一个不完美的地方就是当规定的item个数时，最后一个item在屏幕外滑到可见时，其底部没有footview，这点以后再解决。
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == mAdapter.getItemCount() && lists.size() >= 4) {

                    if (!isLoading) {
                        isLoading = true;
                        mRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                KLog.e("第" + mParametersData.currentPage + "页");
                                loadMore();
                                KLog.e("上拉加载更多mParametersData.currentPage=" + mParametersData.currentPage);
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
                    BaseApplication.bus.post(new EventData(ConstantsUtils.OVERPROOFFABHIDE));
                } else if (dy < -5) {
                    BaseApplication.bus.post(new EventData(ConstantsUtils.OVERPROOFFABSHOW));
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
        String userGroupID = "";
        String startDateTime = "";
        String endDateTime = "";
        String alarmLevel = "";
        String handleType = "";
        String currentPage = "";
        String equipmentID = "";
        if (null != mParametersData) {
            userGroupID = mParametersData.userGroupID;
            startDateTime = mParametersData.startDateTime;
            endDateTime = mParametersData.endDateTime;
            alarmLevel = mParametersData.alarmLevel;
            handleType = mParametersData.handleType;
            mParametersData.currentPage = "1";//默认都是第一页
            currentPage = mParametersData.currentPage;
            equipmentID = mParametersData.equipmentID;
        }

        if (null != lists) {
            lists.clear();
        }

        return URL.getPitchChaobiaoListData(alarmLevel, handleType, currentPage,  equipmentID,userGroupID, startDateTime,endDateTime);
    }

    @Override
    public String createLoadMoreULR() {
        String userGroupID = "";
        String startDateTime = "";
        String endDateTime = "";
        String alarmLevel = "";
        String handleType = "";
        String currentPage = "";
        String equipmentID = "";
        if (null != mParametersData) {
            userGroupID = mParametersData.userGroupID;
            startDateTime = mParametersData.startDateTime;
            endDateTime = mParametersData.endDateTime;
            alarmLevel = mParametersData.alarmLevel;
            handleType = mParametersData.handleType;
            mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) + 1) + "";//默认都是第一页
            currentPage = mParametersData.currentPage;
            equipmentID = mParametersData.equipmentID;
        }

        return URL.getPitchChaobiaoListData(alarmLevel, handleType, currentPage,  equipmentID,userGroupID, startDateTime,endDateTime);
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
                try {
                    KLog.e(response);
                    getOverProofDataFromString(response);
                }catch( Exception e){
                    e.printStackTrace();
                }
                        if (null != lists&&!lists.isEmpty()) {
                            if (lists.size() > 0) {
                                mPageStateLayout.showContent();
                                mAdapter.notifyDataSetChanged();
                            //    mRecyclerView.setAdapter(mScaleInAnimationAdapter);
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
                try {
                    KLog.e(response);
                    getOverProofDataFromString(response);
                }catch( Exception e){
                    e.printStackTrace();
                }
                        if (null != lists&&!lists.isEmpty()) {
                            if (lists.size() > 0) {
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

                }
             else {
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

    }

    private void changeReadedState(View view) {
        //此处可以做一些修改点击过的item的样式，方便用户看出哪些已经点击查看过
    }

    //进入PitchOverproofDetailActivity
    private void jump2OverproofDetailActivity(int position) {
        Intent intent = new Intent(_mActivity, PitchOverproofDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("pitchoverproofdetail", lists.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Subscribe
    public void updateSearch(ParametersData mParametersData) {
        if (mParametersData != null) {
            if (mParametersData.fromTo == ConstantsUtils.PITCHOVERPROOFFRAGMENT) {
                //后期优化考虑的时候，看这里需不需要克隆，应该只要直接复制即可
                this.mParametersData.startDateTime = mParametersData.startDateTime;
                this.mParametersData.endDateTime = mParametersData.endDateTime;
                this.mParametersData.equipmentID = mParametersData.equipmentID;
                this.mParametersData.handleType = mParametersData.handleType;

                KLog.e("mParametersData:" + mParametersData.startDateTime);
                KLog.e("mParametersData:" + mParametersData.endDateTime);
                KLog.e("mParametersData:" + mParametersData.equipmentID);
                KLog.e("mParametersData:" + mParametersData.handleType);
                mPtrFrameLayout.autoRefresh(true);
            }
        }
    }

    @Subscribe
    public void go2TopOrRefresh(EventData event) {
        if (event.position == 1) {
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
        BaseApplication.bus.post(new EventData(ConstantsUtils.OVERPROOFFABSHOW));
    }

    @Override
    public void onPause() {
        super.onPause();
        BaseApplication.bus.post(new EventData(ConstantsUtils.OVERPROOFFABSHOW));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.bus.unregister(this);
        if (null != mAdapter) {
            mAdapter.unRegister();
        }
    }


    public void getOverProofDataFromString(String string) throws  Exception{
        JSONObject jsonObject = new JSONObject(string);
        if (jsonObject.getBoolean("success")) {
            JSONArray array = jsonObject.getJSONArray("data");
            JSONObject joIsshow = jsonObject.getJSONObject("Isshow");
            JSONObject joFields = jsonObject.getJSONObject("Fields");
            for (int i = 0; i < array.length(); i++) {
                PitchOverproofFragmentViewPagerFragmentListData c = new PitchOverproofFragmentViewPagerFragmentListData();
                JSONObject joData = array.getJSONObject(i);
                c.setShijian(joData.getString("shijian"));
                c.setBianhao(joData.getInt("bianhao"));
                c.setShebeibianhao(joData.getString("shebeibianhao"));
                c.setChuli(joData.getString("chuli"));
                Log.e("沥青超标列表页：", c.getBianhao() + "***" + c.getShebeibianhao());
                List<PitchOverproofFragmentViewPagerFragmentItemData> l = new ArrayList<PitchOverproofFragmentViewPagerFragmentItemData>();
                if ("1".equals(joIsshow.getString("sjf1"))) {
                    PitchOverproofFragmentViewPagerFragmentItemData s = new PitchOverproofFragmentViewPagerFragmentItemData();
                    s.setName(joFields.getString("sjf1"));
                    s.setData(joData.getString("wsjf1"));
                    s.setCb(joData.getString("cbsjf1"));
                    l.add(s);
                }
                if ("1".equals(joIsshow.getString("sjf2"))) {
                    PitchOverproofFragmentViewPagerFragmentItemData s = new PitchOverproofFragmentViewPagerFragmentItemData();
                    s.setName(joFields.getString("sjf2"));
                    s.setData(joData.getString("wsjf2"));
                    s.setCb(joData.getString("cbsjf2"));
                    l.add(s);
                }
                if ("1".equals(joIsshow.getString("sjg1"))) {
                    PitchOverproofFragmentViewPagerFragmentItemData s = new PitchOverproofFragmentViewPagerFragmentItemData();
                    s.setName(joFields.getString("sjg1"));
                    s.setData(joData.getString("wsjg1"));
                    s.setCb(joData.getString("cbsjg1"));
                    l.add(s);
                }
                if ("1".equals(joIsshow.getString("sjg2"))) {
                    PitchOverproofFragmentViewPagerFragmentItemData s = new PitchOverproofFragmentViewPagerFragmentItemData();
                    s.setName(joFields.getString("sjg2"));
                    s.setData(joData.getString("wsjg2"));
                    s.setCb(joData.getString("cbsjg2"));
                    l.add(s);
                }
                if ("1".equals(joIsshow.getString("sjg3"))) {
                    PitchOverproofFragmentViewPagerFragmentItemData s = new PitchOverproofFragmentViewPagerFragmentItemData();
                    s.setName(joFields.getString("sjg3"));
                    s.setData(joData.getString("wsjg3"));
                    s.setCb(joData.getString("cbsjg3"));
                    l.add(s);
                }
                if ("1".equals(joIsshow.getString("sjg4"))) {
                    PitchOverproofFragmentViewPagerFragmentItemData s = new PitchOverproofFragmentViewPagerFragmentItemData();
                    s.setName(joFields.getString("sjg4"));
                    s.setData(joData.getString("wsjg4"));
                    s.setCb(joData.getString("cbsjg4"));
                    l.add(s);
                }
                if ("1".equals(joIsshow.getString("sjg5"))) {
                    PitchOverproofFragmentViewPagerFragmentItemData s = new PitchOverproofFragmentViewPagerFragmentItemData();
                    s.setName(joFields.getString("sjg5"));
                    s.setData(joData.getString("wsjg5"));
                    s.setCb(joData.getString("cbsjg5"));
                    l.add(s);
                }
                if ("1".equals(joIsshow.getString("sjg6"))) {
                    PitchOverproofFragmentViewPagerFragmentItemData s = new PitchOverproofFragmentViewPagerFragmentItemData();
                    s.setName(joFields.getString("sjg6"));
                    s.setData(joData.getString("wsjg6"));
                    s.setCb(joData.getString("cbsjg6"));
                    l.add(s);
                }
                if ("1".equals(joIsshow.getString("sjg7"))) {
                    PitchOverproofFragmentViewPagerFragmentItemData s = new PitchOverproofFragmentViewPagerFragmentItemData();
                    s.setName(joFields.getString("sjg7"));
                    s.setData(joData.getString("wsjg7"));
                    s.setCb(joData.getString("cbsjg7"));
                    l.add(s);
                }
                if ("1".equals(joIsshow.getString("sjlq"))) {
                    PitchOverproofFragmentViewPagerFragmentItemData s = new PitchOverproofFragmentViewPagerFragmentItemData();
                    s.setName(joFields.getString("sjlq"));
                    s.setData(joData.getString("wsjlq"));
                    s.setCb(joData.getString("cbsjlq"));
                    l.add(s);
                }
                if ("1".equals(joIsshow.getString("sjtjj"))) {
                    PitchOverproofFragmentViewPagerFragmentItemData s = new PitchOverproofFragmentViewPagerFragmentItemData();
                    s.setName(joFields.getString("sjtjj"));
                    s.setData(joData.getString("wsjtjj"));
                    s.setCb(joData.getString("cbsjtjj"));
                    l.add(s);
                }
                if ("1".equals(joIsshow.getString("sjysb"))) {
                    PitchOverproofFragmentViewPagerFragmentItemData s = new PitchOverproofFragmentViewPagerFragmentItemData();
                    s.setName(joFields.getString("sjysb"));
                    s.setData(joData.getString("wsjysb"));
                    s.setCb(joData.getString("cbsjysb"));
                    l.add(s);
                }
                c.setLists(l);
                lists.add(c);
            }
        }
    }
}
