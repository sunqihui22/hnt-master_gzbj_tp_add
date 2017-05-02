package com.shtoone.shtw.activity;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.adapter.PitchProduceQueryDetailActivityRecyclerViewAdapter;
import com.shtoone.shtw.bean.PitchProduceQueryDetailActivityData;
import com.shtoone.shtw.bean.PitchProduceQueryFragmentListData;
import com.shtoone.shtw.bean.SC_chaxunItem_xq;
import com.shtoone.shtw.bean.SC_chaxunItem_xq_data;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.DateUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.URL;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;


public class PitchProduceQueryDetailActivity extends BaseActivity {
    private static final String TAG = PitchProduceQueryDetailActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private NestedScrollView mNestedScrollView;
    private PageStateLayout mPageStateLayout;
    private PtrFrameLayout mPtrFrameLayout;
    private PitchProduceQueryDetailActivityData data;
    private TextView tv_date;
    private TextView tv_sjysb ;
    private TextView tv_llysb;
    private TextView tv_wcysb;
    private TextView tv_lqwd;
    private TextView tv_slwd;
    private TextView tv_clwd;
    private int bianhao;
    private String shebeibianhao;
    private RecyclerView mRecyclerView;
    private PitchProduceQueryDetailActivityRecyclerViewAdapter mAdapter;
    private PitchProduceQueryFragmentListData.DataEntity mDataBean;
    private Gson mGson;
    private static String string;
    SC_chaxunItem_xq xqData = null;
    List<SC_chaxunItem_xq_data> lists = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitch_produce_query_detail);
        initView();
        initDate();
    }

    private void initView() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar_toolbar);
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nsv_produce_query_detail_activity);
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.ptr_produce_query_detail_activity);
        mPageStateLayout = (PageStateLayout) findViewById(R.id.psl_produce_query_detail_activity);
        tv_date = (TextView) this.findViewById(R.id.scchaxun_xq_date);
        tv_sjysb = (TextView) this.findViewById(R.id.scchaxun_xq_sjysb);
        tv_llysb = (TextView) this.findViewById(R.id.scchaxun_xq_llysb);
        tv_wcysb = (TextView) this.findViewById(R.id.scchaxun_xq_wcysb);
        tv_lqwd = (TextView) this.findViewById(R.id.scchaxun_xq_liqinwendu);
        tv_slwd = (TextView) this.findViewById(R.id.scchaxun_xq_shiliaowendu);
        tv_clwd = (TextView) this.findViewById(R.id.scchaxun_xq_chuliaowendu);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_produce_query_detail_activity);
    }

    private void initDate() {
        mGson = new Gson();
        mDataBean = (PitchProduceQueryFragmentListData.DataEntity) getIntent().getSerializableExtra("liqingdetail");
        bianhao= mDataBean.getId();
        shebeibianhao=mDataBean.getShebeibianhao();
        xqData=new SC_chaxunItem_xq();
        setToolbarTitle();
        initToolbarBackNavigation(mToolbar);
        setSupportActionBar(mToolbar);
        initPageStateLayout(mPageStateLayout);
        initPtrFrameLayout(mPtrFrameLayout);

    }

    @Override
    public boolean isCanDoRefresh() {
        //判断是哪种状态的页面，都让其可下拉
        if (mNestedScrollView.getScrollY() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public String createRefreshULR() {
        mPageStateLayout.showLoading();
        return URL.getPitchProduceDetailData(shebeibianhao,bianhao);
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
                data = mGson.fromJson(response, PitchProduceQueryDetailActivityData.class);
                try {
                    KLog.e(response);
                    getXqDataFromString(response);
                }catch( Exception e){
                    e.printStackTrace();
                }
                if (null != data) {
                    if (data.isSuccess()) {
                        mPageStateLayout.showContent();
                       // mAdapter.notifyDataSetChanged();
                          setViewData();
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



    private  void setViewData()
    {
        // 设置显示数据
        tv_date.setText(data.getData().getShijian());//
        tv_sjysb.setText(data.getData().getSjysb()+"%");//
        tv_llysb.setText(data.getData().getLlysb()+"%");//
        tv_wcysb.setText(data.getData().getSjysb()+"%");//
        tv_lqwd.setText(data.getData().getLqwd()+"℃");//
        tv_slwd.setText(data.getData().getGlwd()+"℃");//
        tv_clwd.setText(data.getData().getClwd()+"℃");//
    }
    //还是不能这样搞，可能会内存泄漏，重复创建Adapyer对象。后面解决
    private void setAdapter() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        KLog.e(TAG,"lisst=:"+xqData.getLists());
        //设置动画与适配器
        SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new PitchProduceQueryDetailActivityRecyclerViewAdapter(this, xqData.getLists()));
        mSlideInLeftAnimationAdapter.setFirstOnly(true);
        mSlideInLeftAnimationAdapter.setDuration(500);
        mSlideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator(0.5f));
        ScaleInAnimationAdapter mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mSlideInLeftAnimationAdapter);
        mScaleInAnimationAdapter.setFirstOnly(true);
        mRecyclerView.setAdapter(mScaleInAnimationAdapter);
    }

    private void setToolbarTitle() {
        if (null != mToolbar && null != BaseApplication.mDepartmentData && !TextUtils.isEmpty(BaseApplication.mDepartmentData.departmentName)) {
            StringBuffer sb = new StringBuffer(BaseApplication.mDepartmentData.departmentName + " > ");
            sb.append(getString(R.string.asphalt) + " > ");
            sb.append(getString(R.string.produce_query) + " > ");
            sb.append(getString(R.string.detail)).trimToSize();
            mToolbar.setTitle(sb.toString());
        }
    }




    private void getXqDataFromString(String string) throws  Exception{
        JSONObject jsonObject = new JSONObject(string);
        if (jsonObject.getBoolean("success")) {
            xqData = new SC_chaxunItem_xq();
            lists = new ArrayList<SC_chaxunItem_xq_data>();
            JSONObject joData = jsonObject.getJSONObject("data"); //获取 data JSON
            JSONObject joFields = jsonObject.getJSONObject("Fields"); //获取 Fields JSON
            JSONObject joIsShow = jsonObject.getJSONObject("Isshow"); //获取 Isshow JSON

            if ("1".equals(joIsShow.getString("sjf1"))) {
                SC_chaxunItem_xq_data data = new SC_chaxunItem_xq_data();
                data.setName(joFields.getString("sjf1"));
                data.setPeibi(joData.getString("llf1"));
                data.setShiji(joData.getString("persjf1"));
                data.setWucha(joData.getString("wsjf1"));
                data.setYongliang(joData.getString("sjf1"));
                lists.add(data);
            }
            if ("1".equals(joIsShow.getString("sjf2"))) {
                SC_chaxunItem_xq_data data = new SC_chaxunItem_xq_data();
                data.setName(joFields.getString("sjf2"));
                data.setPeibi(joData.getString("llf2"));
                data.setShiji(joData.getString("persjf2"));
                data.setWucha(joData.getString("wsjf2"));
                data.setYongliang(joData.getString("sjf2"));
                lists.add(data);
            }
            if ("1".equals(joIsShow.getString("sjg1"))) {
                SC_chaxunItem_xq_data data = new SC_chaxunItem_xq_data();
                data.setName(joFields.getString("sjg1"));
                data.setPeibi(joData.getString("llg1"));
                data.setShiji(joData.getString("persjg1"));
                data.setWucha(joData.getString("wsjg1"));
                data.setYongliang(joData.getString("sjg1"));
                lists.add(data);
            }
            if ("1".equals(joIsShow.getString("sjg2"))) {
                SC_chaxunItem_xq_data data = new SC_chaxunItem_xq_data();
                data.setName(joFields.getString("sjg2"));
                data.setPeibi(joData.getString("llg2"));
                data.setShiji(joData.getString("persjg2"));
                data.setWucha(joData.getString("wsjg2"));
                data.setYongliang(joData.getString("sjg2"));
                lists.add(data);
            }
            if ("1".equals(joIsShow.getString("sjg3"))) {
                SC_chaxunItem_xq_data data = new SC_chaxunItem_xq_data();
                data.setName(joFields.getString("sjg3"));
                data.setPeibi(joData.getString("llg3"));
                data.setShiji(joData.getString("persjg3"));
                data.setWucha(joData.getString("wsjg3"));
                data.setYongliang(joData.getString("sjg3"));
                lists.add(data);
            }
            if ("1".equals(joIsShow.getString("sjg4"))) {
                SC_chaxunItem_xq_data data = new SC_chaxunItem_xq_data();
                data.setName(joFields.getString("sjg4"));
                data.setPeibi(joData.getString("llg4"));
                data.setShiji(joData.getString("persjg4"));
                data.setWucha(joData.getString("wsjg4"));
                data.setYongliang(joData.getString("sjg4"));
                lists.add(data);
            }
            if ("1".equals(joIsShow.getString("sjg5"))) {
                SC_chaxunItem_xq_data data = new SC_chaxunItem_xq_data();
                data.setName(joFields.getString("sjg5"));
                data.setPeibi(joData.getString("llg5"));
                data.setShiji(joData.getString("persjg5"));
                data.setWucha(joData.getString("wsjg5"));
                data.setYongliang(joData.getString("sjg5"));
                lists.add(data);
            }
            if ("1".equals(joIsShow.getString("sjg6"))) {
                SC_chaxunItem_xq_data data = new SC_chaxunItem_xq_data();
                data.setName(joFields.getString("sjg6"));
                data.setPeibi(joData.getString("llg6"));
                data.setShiji(joData.getString("persjg6"));
                data.setWucha(joData.getString("wsjg6"));
                data.setYongliang(joData.getString("sjg6"));
                lists.add(data);
            }
            if ("1".equals(joIsShow.getString("sjg7"))) {
                SC_chaxunItem_xq_data data = new SC_chaxunItem_xq_data();
                data.setName(joFields.getString("sjg7"));
                data.setPeibi(joData.getString("llg7"));
                data.setShiji(joData.getString("persjg7"));
                data.setWucha(joData.getString("wsjg7"));
                data.setYongliang(joData.getString("sjg7"));
                lists.add(data);
            }
            if ("1".equals(joIsShow.getString("sjlq"))) {
                SC_chaxunItem_xq_data data = new SC_chaxunItem_xq_data();
                data.setName(joFields.getString("sjlq"));
                data.setPeibi(joData.getString("lllq"));
                data.setShiji(joData.getString("persjlq"));
                data.setWucha(joData.getString("wsjlq"));
                data.setYongliang(joData.getString("sjlq"));
                lists.add(data);
            }
            if ("1".equals(joIsShow.getString("sjtjj"))) {
                SC_chaxunItem_xq_data data = new SC_chaxunItem_xq_data();
                data.setName(joFields.getString("sjtjj"));
                data.setPeibi(joData.getString("lltjj"));
                data.setShiji(joData.getString("persjtjj"));
                data.setWucha(joData.getString("wsjtjj"));
                data.setYongliang(joData.getString("sjtjj"));
                lists.add(data);
            }

            //1. 时间,实际油石比,理论油石比,误差实际油石比,沥青温度,骨料温度,出料温度
            xqData.setBanhezhanmingchen(joData.getString("banhezhanminchen"));
            xqData.setChuliaoshijian(DateUtils.subTime(joData.getString("shijian")));
            xqData.setShijiyoushibi(joData.getString("sjysb"));
            xqData.setLilunyoushibi(joData.getString("llysb"));
            xqData.setYoushibiwucha(joData.getString("wsjysb"));
            xqData.setLiqingwendu(joData.getString("lqwd"));
            xqData.setShiliaowend(joData.getString("glwd"));
            xqData.setChuliaowendu(joData.getString("clwd"));

            xqData.setLists(lists);
        }
    }
}
