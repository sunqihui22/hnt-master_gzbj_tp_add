package com.shtoone.shtw.fragment.pitchactivity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.adapter.PitchStatisticFragmentRecyclerViewAdapter;
import com.shtoone.shtw.adapter.shengchanchaobiaolvAdapter;
import com.shtoone.shtw.adapter.zongchanliangRecyclerviewAdapter;
import com.shtoone.shtw.bean.MaterialStatisticFragmentData;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.bean.PitchStatisticFragmentData;
import com.shtoone.shtw.bean.SC_cailiaoyongliang;
import com.shtoone.shtw.bean.SC_zongchanliang;
import com.shtoone.shtw.event.EventData;
import com.shtoone.shtw.fragment.base.BaseLazyFragment;
import com.shtoone.shtw.ui.MyMarkerView;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.DateUtils;
import com.shtoone.shtw.utils.DensityUtils;
import com.shtoone.shtw.utils.HttpUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.URL;
import com.socks.library.KLog;
import com.squareup.otto.Subscribe;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;


/**
 * Created by gesangdianzi on 2016/8/30.
 */
public class PitchStatisticFragment extends BaseLazyFragment implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener {
    private static final String TAG = PitchStatisticFragment.class.getSimpleName();
    private Toolbar mToolbar;
    private NestedScrollView mNestedScrollView;
    private PageStateLayout mPageStateLayout;
    private PageStateLayout mPageStateLayout2;
    private PtrFrameLayout mPtrFrameLayout;
    private LinearLayout recyclerview1_title, recyclerview1_mpchart;
    private Typeface mTf;
    private PitchStatisticFragmentRecyclerViewAdapter mAdapter;
    private zongchanliangRecyclerviewAdapter nAdapter;
    private shengchanchaobiaolvAdapter xAdapter;
    SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter;

    private RecyclerView mRecyclerView0;
    private RecyclerView mRecyclerView1;
    private boolean isStartDateTime;
    Button btnSC_zongchanliang_ji;
    Button btnSC_zongchanliang_yue;
    Button btnSC_zongchanliang_zhou;
    Button btnSC_zongchanliang_ri;
    Button btnSC_zongchanliang_cl;
    Button btnSC_zongchanliang_wc;
    Button btnSC_zongchanliang_search;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private TextView tv_title;
    private boolean isRegistered = false;
    private BarChart mBarChart0, mBarChart1, chart_cl;
    private LineChart chart_wc;
    private MaterialStatisticFragmentData data;
    private ParametersData mParametersData;
    private PitchStatisticFragmentData pitchStatisticFragmentData;
    private Gson mGson;
    private LinearLayoutManager mLinearLayoutManager;
    private JSONObject jsonObject = null;
    private JsonObject jsObject;
    List<SC_cailiaoyongliang> datas;
    List<SC_zongchanliang> Datas;
    private String current_Cycle = "2";   //1:季  2:月 3：周 4：日
    private String current_Type = "CL";     //CL WC 默认类型为产量
    private String userGroupID = "";
    private String startDateTime = "";
    private String endDateTime = "";
    private String equipmentID = "";
    private String title = "月";

    public static PitchStatisticFragment newInstance() {
        return new PitchStatisticFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!isRegistered) {
            BaseApplication.bus.register(this);
            isRegistered = true;
        }
        View view = inflater.inflate(R.layout.fragment_pitch_material_statistic, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tv_title = (TextView) view.findViewById(R.id.textview);
        tv_start_date = (TextView) view.findViewById(R.id.tv_start_date_laboratory_statistic_fragment);
        tv_end_date = (TextView) view.findViewById(R.id.tv_end_date_laboratory_statistic_fragment);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_toolbar);
        mNestedScrollView = (NestedScrollView) view.findViewById(R.id.nsv_material_statistic_fragment);
        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.ptr_material_statistic_fragment);
        mPageStateLayout = (PageStateLayout) view.findViewById(R.id.psl_material_statistic_fragment);
        mPageStateLayout2= (PageStateLayout) view.findViewById(R.id.psl_material_statistic_fragment2);
        recyclerview1_title = (LinearLayout) view.findViewById(R.id.recyclerview1_title);
        //chart_cl= (BarChart) view.findViewById(R.id.barchart2_material_statistic_fragment);
       // chart_wc = (LineChart) view.findViewById(R.id.lineChart_pitch_statics);
        recyclerview1_mpchart = (LinearLayout) view.findViewById(R.id.recyclerview1_mpchart);
        mBarChart0 = (BarChart) view.findViewById(R.id.barchart0_material_statistic_fragment);
        mBarChart1 = (BarChart) view.findViewById(R.id.barchart1_material_statistic_fragment);
        mRecyclerView0 = (RecyclerView) view.findViewById(R.id.rv0_material_statistic_fragment);
        mRecyclerView1 = (RecyclerView) view.findViewById(R.id.rv1_material_statistic_fragment);

        //周期按钮
        btnSC_zongchanliang_ji = (Button) view.findViewById(R.id.btnSC_zongchanliang_ji);
        btnSC_zongchanliang_yue = (Button) view.findViewById(R.id.btnSC_zongchanliang_yue);
        btnSC_zongchanliang_zhou = (Button) view.findViewById(R.id.btnSC_zongchanliang_zhou);
        btnSC_zongchanliang_ri = (Button) view.findViewById(R.id.btnSC_zongchanliang_ri);
        //类型按钮
        btnSC_zongchanliang_cl = (Button) view.findViewById(R.id.btnSC_zongchanliang_cl);
        btnSC_zongchanliang_wc = (Button) view.findViewById(R.id.btnSC_zongchanliang_wc);

        btnSC_zongchanliang_search = (Button) view.findViewById(R.id.bt_search_laboratory_statistic_fragment);

    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        mGson = new Gson();
        mParametersData = (ParametersData) BaseApplication.parametersData.clone();
        mParametersData.userGroupID = BaseApplication.mDepartmentData.departmentID;
        mParametersData.fromTo = ConstantsUtils.MATERIALSTATISTICFRAGMENT;
        if (null != mParametersData) {
            userGroupID = mParametersData.userGroupID;
            startDateTime = mParametersData.startDateTime;
            endDateTime = mParametersData.endDateTime;
            equipmentID = mParametersData.equipmentID;
        }
        setToolbarTitle();
        initToolbarBackNavigation(mToolbar);
        LoadList();
//        initToolbarMenu(mToolbar);
        tv_start_date.setText(mParametersData.startDateTime);
        tv_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStartDateTime();
            }
        });
        tv_end_date.setText(mParametersData.endDateTime);
        tv_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEndDateTime();
            }
        });

        btnSC_zongchanliang_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPtrFrameLayout.autoRefresh(true);
                LoadList();
            }
        });

        btnSC_zongchanliang_cl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (current_Type == "CL") {
                    return;
                }
                tv_title.setText("沥青混合料总生产量统计（单位：T)");
                btnSC_zongchanliang_wc.setBackgroundColor(Color.rgb(220, 220, 220));
                ((Button) v).setBackgroundColor(Color.rgb(154, 205, 50));
                current_Type = "CL";
                LoadList();
            }
        });
        btnSC_zongchanliang_ji.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (current_Cycle == "1")
                    return;
                //  mypDialog.show();
                title = "季";
                btnSC_zongchanliang_yue.setBackgroundColor(Color.rgb(220, 220, 220));
                btnSC_zongchanliang_zhou.setBackgroundColor(Color.rgb(220, 220, 220));
                btnSC_zongchanliang_ri.setBackgroundColor(Color.rgb(220, 220, 220));
                current_Cycle = "1";
                ((Button) v).setBackgroundColor(Color.rgb(154, 205, 50));
                LoadList();
            }
        });
        btnSC_zongchanliang_yue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (current_Cycle == "2")
                    return;
                //  mypDialog.show();
                title = "月";
                btnSC_zongchanliang_ji.setBackgroundColor(Color.rgb(220, 220, 220));
                btnSC_zongchanliang_zhou.setBackgroundColor(Color.rgb(220, 220, 220));
                btnSC_zongchanliang_ri.setBackgroundColor(Color.rgb(220, 220, 220));
                current_Cycle = "2";
                ((Button) v).setBackgroundColor(Color.rgb(154, 205, 50));
                LoadList();
            }
        });
        btnSC_zongchanliang_zhou.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (current_Cycle == "3")
                    return;
                // mypDialog.show();
                title = "周";

                btnSC_zongchanliang_yue.setBackgroundColor(Color.rgb(220, 220, 220));
                btnSC_zongchanliang_ji.setBackgroundColor(Color.rgb(220, 220, 220));
                btnSC_zongchanliang_ri.setBackgroundColor(Color.rgb(220, 220, 220));
                current_Cycle = "3";
                ((Button) v).setBackgroundColor(Color.rgb(154, 205, 50));
                LoadList();
            }
        });
        btnSC_zongchanliang_ri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (current_Cycle == "4")
                    return;
                if (Math.abs(DateUtils.getDaySub(startDateTime, endDateTime)) > 7) {
                    Toast.makeText(getContext(), "按日统计时请统计周期请小于1周。", Toast.LENGTH_SHORT).show();
                    return;
                }
                title = "日";
                //  mypDialog.show();
                btnSC_zongchanliang_yue.setBackgroundColor(Color.rgb(220, 220, 220));
                btnSC_zongchanliang_zhou.setBackgroundColor(Color.rgb(220, 220, 220));
                btnSC_zongchanliang_ji.setBackgroundColor(Color.rgb(220, 220, 220));

                current_Cycle = "4";
                ((Button) v).setBackgroundColor(Color.rgb(154, 205, 50));
                LoadList();
            }
        });
        btnSC_zongchanliang_wc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (current_Type == "WC") {
                    return;
                }
                tv_title.setText("沥青混合料生产超标率（%)");
                //  mypDialog.show();
                btnSC_zongchanliang_cl.setBackgroundColor(Color.rgb(220, 220, 220));
                ((Button) v).setBackgroundColor(Color.rgb(154, 205, 50));

                current_Type = "WC";
                LoadList();
            }
        });
        mPageStateLayout.setPadding(0, 0, 0, DensityUtils.dp2px(_mActivity, 80));
        mPageStateLayout2.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadList();
            }
        });

//        mPageStateLayout2.setOnNetErrorClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPageStateLayout.showEmpty();
//                NetworkUtils.openSetting(_mActivity);
//            }
//        });

        initPageStateLayout(mPageStateLayout);
        initPtrFrameLayout(mPtrFrameLayout);
    }


    private void LoadList() {
        query(equipmentID, startDateTime, endDateTime, userGroupID, current_Cycle);

    }

    private void setStartDateTime() {
        isStartDateTime = true;
        showDatePicker();
    }

    private void setEndDateTime() {
        isStartDateTime = false;
        showDatePicker();
    }

    private void showDatePicker() {
        Calendar now = Calendar.getInstance();
        if (isStartDateTime) {
            now.add(Calendar.MONTH, -3);
        }
        DatePickerDialog dpd = DatePickerDialog.newInstance(this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.vibrate(true);
        dpd.dismissOnPause(false);
        dpd.setAccentColor(getResources().getColor(R.color.base_color));
        dpd.show(_mActivity.getFragmentManager(), "Datepickerdialog");
    }

    private void showTimePicker() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false);
        tpd.vibrate(true);
        tpd.dismissOnPause(false);
        tpd.setAccentColor(Color.parseColor("#3F51B5"));
        tpd.show(_mActivity.getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String monthString = (++monthOfYear) < 10 ? "0" + (monthOfYear) : "" + (monthOfYear);
        String dayString = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
        String dateString = year + "-" + monthString + "-" + dayString + " ";
        if (isStartDateTime) {
            startDateTime = dateString;
        } else {
            endDateTime = dateString;
        }
        showTimePicker();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        String timeString = hourString + ":" + minuteString + ":" + secondString;
        if (isStartDateTime) {
            startDateTime = startDateTime + timeString;
            tv_start_date.setText(startDateTime);
            mParametersData.startDateTime = startDateTime;
        } else {
            endDateTime = endDateTime + timeString;
            tv_end_date.setText(endDateTime);
            mParametersData.endDateTime = endDateTime;
        }
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
        String userGroupID = "";
        String startDateTime = "";
        String endDateTime = "";
        String equipmentID = "";
        if (null != mParametersData) {
            userGroupID = mParametersData.userGroupID;
            startDateTime = mParametersData.startDateTime;
            endDateTime = mParametersData.endDateTime;
            equipmentID = mParametersData.equipmentID;
        }
        return URL.getPitchStatisticUrl(equipmentID, userGroupID, startDateTime, endDateTime);
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
                    getData(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (null != datas) {
                    if (!datas.isEmpty() && datas.size() > 0) {
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

    private void setViewData() {
        ArrayList<String> x = new ArrayList<String>();
        ArrayList<BarEntry> y0 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> y1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> y2 = new ArrayList<BarEntry>();

        for (int i = 0; i < datas.size(); i++) {
            x.add(datas.get(i).getCailiaomingcheng());
            y0.add(new BarEntry(Float.parseFloat(String.valueOf(datas.get(i).getShijiKg())), i));
            y1.add(new BarEntry(Float.parseFloat(String.valueOf(datas.get(i).getpeibiKg())), i));
            y2.add(new BarEntry(Float.parseFloat(String.valueOf(datas.get(i).getWuchaPst())), i));
        }

        setChart(mBarChart0);
        setChart(mBarChart1);

        setChartData(mBarChart0, x, y0);
        setChartData(mBarChart1, x, y2);

        mLinearLayoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView0.setLayoutManager(mLinearLayoutManager);
        //设置动画与适配器
        SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new PitchStatisticFragmentRecyclerViewAdapter(_mActivity, datas));
        mSlideInLeftAnimationAdapter.setFirstOnly(true);
        mSlideInLeftAnimationAdapter.setDuration(500);
        mSlideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator(.5f));
        ScaleInAnimationAdapter mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mSlideInLeftAnimationAdapter);
        mScaleInAnimationAdapter.setFirstOnly(true);
        mRecyclerView0.setAdapter(mScaleInAnimationAdapter);
    }

    private void setChartData(BarChart mBarChart, ArrayList<String> x, ArrayList<BarEntry> y) {
        BarDataSet mBarDataSet;
        mBarDataSet = new BarDataSet(y, "材料类型");
        mBarDataSet.setBarSpacePercent(35f);
        mBarDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(mBarDataSet);
        BarData data = new BarData(x, dataSets);
        data.setValueTextSize(10f);
        data.setValueTypeface(mTf);
        mBarChart.setData(data);
    }

    private void setChart(BarChart mBarChart) {
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(true);
        mBarChart.setDescription("");
        mBarChart.setMaxVisibleValueCount(60);
        mBarChart.setPinchZoom(false);
        mBarChart.animateXY(2000, 2000);
        mBarChart.setDrawGridBackground(false);
        mTf = Typeface.createFromAsset(_mActivity.getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(0);

        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setStartAtZero(false);
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(8, false);
       leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
      //  leftAxis.setAxisMinValue(-20f);  //设置y轴最小值，设置之后，超出范围的不显示，不设置，则根据值得范围自动生成


        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setEnabled(false);

        Legend l = mBarChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
    }

    private void changeReadedState(View view) {
        //此处可以做一些修改点击过的item的样式，方便用户看出哪些已经点击查看过
    }

    @Subscribe
    public void updateSearch(ParametersData mParametersData) {
        if (mParametersData != null) {
            if (mParametersData.fromTo == ConstantsUtils.MATERIALSTATISTICFRAGMENT) {
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

    @Subscribe
    public void go2TopOrRefresh(EventData event) {
        if (event.position == 2) {
            mNestedScrollView.fullScroll(ScrollView.SCROLL_INDICATOR_TOP);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) _mActivity.getFragmentManager().findFragmentByTag("Datepickerdialog");
        if (dpd != null) dpd.setOnDateSetListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        //防止屏幕旋转后重画时fab显示
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
            sb.append(getString(R.string.total_Statistics)).trimToSize();
            mToolbar.setTitle(sb.toString());
        }
    }

    /**
     * 材料查询数据
     *
     * @param response
     * @throws Exception
     */
    private void getData(String response) throws Exception {
        jsonObject = new JSONObject(response);
        if (jsonObject.getBoolean("success")) {
            datas = new ArrayList<SC_cailiaoyongliang>();
            JSONObject joData = jsonObject.getJSONObject("data");
            JSONObject joFields = jsonObject.getJSONObject("Fields");

            SC_cailiaoyongliang c1 = new SC_cailiaoyongliang();
            c1.setCailiaomingcheng(joFields.getString("sjf1"));
            c1.setShijiKg(joData.getDouble("sjf1"));
            c1.setpeibiKg(joData.getDouble("llf1"));
            c1.setWuchaKg(c1.getShijiKg() - c1.getpeibiKg());
            c1.setWuchaPst(joData.getDouble("wsjf1"));
            datas.add(c1);

            SC_cailiaoyongliang c2 = new SC_cailiaoyongliang();
            c2.setCailiaomingcheng(joFields.getString("sjf2"));
            c2.setShijiKg(joData.getDouble("sjf2"));
            c2.setpeibiKg(joData.getDouble("llf2"));
            c2.setWuchaKg(c2.getShijiKg() - c2.getpeibiKg());
            c2.setWuchaPst(joData.getDouble("wsjf2"));
            datas.add(c2);

            SC_cailiaoyongliang c3 = new SC_cailiaoyongliang();
            c3.setCailiaomingcheng(joFields.getString("sjg1"));
            c3.setShijiKg(joData.getDouble("sjg1"));
            c3.setpeibiKg(joData.getDouble("llg1"));
            c3.setWuchaKg(c3.getShijiKg() - c3.getpeibiKg());
            c3.setWuchaPst(joData.getDouble("wsjg1"));
            datas.add(c3);

            SC_cailiaoyongliang c4 = new SC_cailiaoyongliang();
            c4.setCailiaomingcheng(joFields.getString("sjg2"));
            c4.setShijiKg(joData.getDouble("sjg2"));
            c4.setpeibiKg(joData.getDouble("llg2"));
            c4.setWuchaKg(c4.getShijiKg() - c4.getpeibiKg());
            c4.setWuchaPst(joData.getDouble("wsjg2"));
            datas.add(c4);

            SC_cailiaoyongliang c5 = new SC_cailiaoyongliang();
            c5.setCailiaomingcheng(joFields.getString("sjg3"));
            c5.setShijiKg(joData.getDouble("sjg3"));
            c5.setpeibiKg(joData.getDouble("llg3"));
            c5.setWuchaKg(c5.getShijiKg() - c5.getpeibiKg());
            c5.setWuchaPst(joData.getDouble("wsjg3"));
            datas.add(c5);

            SC_cailiaoyongliang c6 = new SC_cailiaoyongliang();
            c6.setCailiaomingcheng(joFields.getString("sjg4"));
            c6.setShijiKg(joData.getDouble("sjg4"));
            c6.setpeibiKg(joData.getDouble("llg4"));
            c6.setWuchaKg(c6.getShijiKg() - c6.getpeibiKg());
            c6.setWuchaPst(joData.getDouble("wsjg4"));
            datas.add(c6);

            SC_cailiaoyongliang c7 = new SC_cailiaoyongliang();
            c7.setCailiaomingcheng(joFields.getString("sjg5"));
            c7.setShijiKg(joData.getDouble("sjg5"));
            c7.setpeibiKg(joData.getDouble("llg5"));
            c7.setWuchaKg(c7.getShijiKg() - c7.getpeibiKg());
            c7.setWuchaPst(joData.getDouble("wsjg5"));
            datas.add(c7);

            SC_cailiaoyongliang c8 = new SC_cailiaoyongliang();
            c8.setCailiaomingcheng(joFields.getString("sjg6"));
            c8.setShijiKg(joData.getDouble("sjg6"));
            c8.setpeibiKg(joData.getDouble("llg6"));
            c8.setWuchaKg(c8.getShijiKg() - c8.getpeibiKg());
            c8.setWuchaPst(joData.getDouble("wsjg6"));
            datas.add(c8);

            SC_cailiaoyongliang c9 = new SC_cailiaoyongliang();
            c9.setCailiaomingcheng(joFields.getString("sjg7"));
            c9.setShijiKg(joData.getDouble("sjg7"));
            c9.setpeibiKg(joData.getDouble("llg7"));
            c9.setWuchaKg(c9.getShijiKg() - c9.getpeibiKg());
            c9.setWuchaPst(joData.getDouble("wsjg7"));
            datas.add(c9);

            SC_cailiaoyongliang c10 = new SC_cailiaoyongliang();
            c10.setCailiaomingcheng(joFields.getString("sjlq"));
            c10.setShijiKg(joData.getDouble("sjlq"));
            c10.setpeibiKg(joData.getDouble("lllq"));
            c10.setWuchaKg(c10.getShijiKg() - c10.getpeibiKg());
            c10.setWuchaPst(joData.getDouble("wsjlq"));
            datas.add(c10);

            SC_cailiaoyongliang c11 = new SC_cailiaoyongliang();
            c11.setCailiaomingcheng(joFields.getString("sjtjj"));
            c11.setShijiKg(joData.getDouble("sjtjj"));
            c11.setpeibiKg(joData.getDouble("lltjj"));
            c11.setWuchaKg(c11.getShijiKg() - c11.getpeibiKg());
            c11.setWuchaPst(joData.getDouble("wsjtjj"));
            datas.add(c11);

        }
    }


    /**
     * 总生产量和超标率数据
     *
     * @return
     * @throws Exception
     */
    public void getchanliangData(String response) {
        try {
            jsonObject = new JSONObject(response);
            Datas = new ArrayList<SC_zongchanliang>();
            if (jsonObject.getBoolean("success")) {

                JSONArray arrs = jsonObject.getJSONArray("data");
                for (int i = 0; i < arrs.length(); i++) {
                    JSONObject jo = (JSONObject) arrs.get(i);
                    SC_zongchanliang data = new SC_zongchanliang();
                    data.setDate(jo.getString("xa") + "-" + jo.getString("xb"));
                    data.setPrimarylv(Double.valueOf(jo.getString("primaryPer")));   //转换为百分比
                    data.setMiddlelv(Double.valueOf(jo.getString("middlePer")));     //转换为百分比
                    data.setHighlv(Double.valueOf(jo.getString("highPer")));         //转换为百分比
                    data.setChangliang(Double.valueOf(jo.getString("changliang")) / 1000); //转换为t
                    Datas.add(data);
                }
            }
        } catch (Exception e) {
            KLog.e(TAG, e.toString());

        }
    }



    private void query(String shebeibianhao, String StartTime, String EndTime, String userGroupID, String Type) {
        // 定义发送请求的URL
        StartTime = DateUtils.ChangeTimeToLong(StartTime);
        EndTime = DateUtils.ChangeTimeToLong(EndTime);

        String url = URL.LIQING_ZONGCHANLIANGTONGJI_URL.replace("%1", shebeibianhao).replace("%2", StartTime).replace("%3", EndTime).replace("%4", userGroupID).replace("%5", Type);
        Log.e("总生产量统计url：", url);
        // 发送请求
        mPageStateLayout2.showLoading();
        HttpUtils.getRequest(url, new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                mPageStateLayout2.showContent();
                getchanliangData(response);

                ArrayList<String> x = new ArrayList<String>();
                ArrayList<BarEntry> y0 = new ArrayList<BarEntry>();
                ArrayList<BarEntry> y1 = new ArrayList<BarEntry>();
                ArrayList<BarEntry> y2 = new ArrayList<BarEntry>();


//                if(Datas.isEmpty())
//                {
//                    return;
//                }
                for (int i = 0; i < Datas.size(); i++) {
                    x.add(Datas.get(i).getDate());
                    y0.add(new BarEntry(Float.parseFloat(String.valueOf(Datas.get(i).getChangliang())), i));
                }

                //设置动画与适配器
                if (current_Type == "CL") {
                    recyclerview1_mpchart.removeAllViews();
                    chart_cl = new BarChart(getContext());

                    chart_cl.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DensityUtils.dp2px(_mActivity, 300)));
                    setChart(chart_cl);
                    setChartData(chart_cl, x, y0);
                    recyclerview1_mpchart.addView(chart_cl);
                    LinearLayout.LayoutParams lll = new LinearLayout.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT, 45);
                    lll.weight = (float) 0.5; //设置宽度
                    recyclerview1_title.removeAllViews();
                    addViews(title, lll);
                    addViews("产量(t)", lll);

                    mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(nAdapter = new zongchanliangRecyclerviewAdapter(_mActivity, Datas));
                } else if (current_Type == "WC") {
                    recyclerview1_mpchart.removeAllViews();
                    chart_wc = new LineChart(getContext());
                    chart_wc.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(_mActivity, 300)));
                    setChart();
                    setChartData();
                    recyclerview1_mpchart.addView(chart_wc);
                    LinearLayout.LayoutParams lll = new LinearLayout.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT, 45);
                    lll.weight = (float) 0.5; //设置宽度
                    recyclerview1_title.removeAllViews();
                    addViews(title, lll);
                    addViews("低超标(%)", lll);
                    addViews("中超标(%)", lll);
                    addViews("高超标(%)", lll);

                    mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(xAdapter = new shengchanchaobiaolvAdapter(_mActivity, Datas));
                }
                mLinearLayoutManager = new LinearLayoutManager(_mActivity);
                mRecyclerView1.setLayoutManager(mLinearLayoutManager);
                mSlideInLeftAnimationAdapter.setFirstOnly(true);
                mSlideInLeftAnimationAdapter.setDuration(500);
//                mSlideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator(.5f));
                ScaleInAnimationAdapter mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mSlideInLeftAnimationAdapter);
                mScaleInAnimationAdapter.setFirstOnly(true);
                mRecyclerView1.setAdapter(mScaleInAnimationAdapter);

            }

            @Override
            public void onFailed(VolleyError error) {

                mPageStateLayout2.showError();
            }
        });
    }


    private void addViews(String string, LinearLayout.LayoutParams params) {

        //周期
        TextView txt = new TextView(getContext());
        txt.setText(string);
        txt.setTextColor(Color.rgb(255, 165, 0));
        txt.setTextSize(12);

        txt.setLayoutParams(params);
        recyclerview1_title.addView(txt);

    }


    private void setChart() {

        chart_wc.setDescription("");
        chart_wc.setDrawGridBackground(true);
        chart_wc.setNoDataTextDescription("暂无数据表……");
        chart_wc.setTouchEnabled(true);
        chart_wc.setDragEnabled(true);
        chart_wc.setScaleEnabled(true);
        chart_wc.setPinchZoom(true);
        chart_wc.animateX(1500);
        chart_wc.getAxisRight().setEnabled(false);

        MyMarkerView mv = new MyMarkerView(_mActivity, R.layout.custom_marker_view);
        chart_wc.setMarkerView(mv);

        Typeface tf = Typeface.createFromAsset(_mActivity.getAssets(), "OpenSans-Light.ttf");
        Legend l = chart_wc.getLegend();
        l.setTypeface(tf);
        YAxis leftAxis = chart_wc.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setAxisMinValue(0f);
        leftAxis.removeAllLimitLines();
        leftAxis.setTypeface(tf);
        leftAxis.setTextColor(Color.RED);

        XAxis xAxis = chart_wc.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setTypeface(tf);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLUE);
    }

    private void setChartData() {
        ArrayList<String> xVals = new ArrayList<String>();
        //   String[] x = data.getX();
        for (int i = 0; i < Datas.size(); i++) {
            xVals.add(Datas.get(i).getDate());
        }

        ArrayList<Entry> yVals0 = new ArrayList<Entry>();

        for (int i = 0; i < Datas.size(); i++) {

            yVals0.add(new Entry(Float.parseFloat(Datas.get(i).getPrimarylv().toString()), i));
        }

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < Datas.size(); i++) {

            yVals1.add(new Entry(Float.parseFloat(Datas.get(i).getMiddlelv().toString()), i));
        }

        ArrayList<Entry> yVals2 = new ArrayList<Entry>();

        for (int i = 0; i < Datas.size(); i++) {

            yVals2.add(new Entry(Float.parseFloat(Datas.get(i).getHighlv().toString()), i));
        }

        LineDataSet mLineDataSet0 = new LineDataSet(yVals0, "低");
        LineDataSet mLineDataSet1 = new LineDataSet(yVals1, "中");
        LineDataSet mLineDataSet2 = new LineDataSet(yVals2, "高");

        if (Utils.getSDKInt() >= 18) {
            Drawable drawable0 = ContextCompat.getDrawable(_mActivity, R.drawable.fade_red);
            Drawable drawable1 = ContextCompat.getDrawable(_mActivity, R.drawable.fade_blue);
            Drawable drawable2 = ContextCompat.getDrawable(_mActivity, R.drawable.fade_green);
            mLineDataSet0.setFillDrawable(drawable0);
            mLineDataSet1.setFillDrawable(drawable1);
            mLineDataSet2.setFillDrawable(drawable2);
        }
        //设置样式
        mLineDataSet0.enableDashedLine(10f, 5f, 0f);
        mLineDataSet0.enableDashedHighlightLine(10f, 5f, 0f);
        mLineDataSet0.setColor(Color.BLACK);
        mLineDataSet0.setCircleColor(Color.BLUE);
        mLineDataSet0.setLineWidth(1f);
        mLineDataSet0.setCircleRadius(0f);
        mLineDataSet0.setHighLightColor(Color.BLACK);
        mLineDataSet0.setDrawCircleHole(true);
        mLineDataSet0.setValueTextSize(7f);
        mLineDataSet0.setDrawFilled(true);

        mLineDataSet1.enableDashedLine(10f, 5f, 0f);
        mLineDataSet1.enableDashedHighlightLine(10f, 5f, 0f);
        mLineDataSet1.setColor(Color.YELLOW);
        mLineDataSet1.setCircleColor(Color.BLUE);
        mLineDataSet1.setLineWidth(1f);
        mLineDataSet1.setCircleRadius(0f);
        mLineDataSet1.setHighLightColor(Color.YELLOW);
        mLineDataSet1.setDrawCircleHole(true);
        mLineDataSet1.setValueTextSize(7f);
        mLineDataSet1.setDrawFilled(true);

        mLineDataSet2.enableDashedLine(10f, 5f, 0f);
        mLineDataSet2.enableDashedHighlightLine(10f, 5f, 0f);
        mLineDataSet2.setColor(Color.RED);
        mLineDataSet2.setCircleColor(Color.BLUE);
        mLineDataSet2.setLineWidth(1f);
        mLineDataSet2.setCircleRadius(0f);
        mLineDataSet2.setHighLightColor(Color.RED);
        mLineDataSet2.setDrawCircleHole(true);
        mLineDataSet2.setValueTextSize(7f);
        mLineDataSet2.setDrawFilled(true);
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(mLineDataSet0);
        dataSets.add(mLineDataSet1);
        dataSets.add(mLineDataSet2);
        LineData data = new LineData(xVals, dataSets);
        chart_wc.setData(data);
    }

}
