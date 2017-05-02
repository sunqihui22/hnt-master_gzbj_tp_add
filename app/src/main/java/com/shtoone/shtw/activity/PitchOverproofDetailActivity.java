package com.shtoone.shtw.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.adapter.PitchOverproofDetailActivityRecyclerViewAdapter;
import com.shtoone.shtw.bean.PitchOverproofDetailActivityData;
import com.shtoone.shtw.bean.PitchOverproofFragmentViewPagerFragmentItemData;
import com.shtoone.shtw.bean.PitchOverproofFragmentViewPagerFragmentListData;
import com.shtoone.shtw.bean.SC_chaxunItem_xq;
import com.shtoone.shtw.bean.SC_chaxunItem_xq_data;
import com.shtoone.shtw.bean.UserInfoData;
import com.shtoone.shtw.event.EventData;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.DateUtils;
import com.shtoone.shtw.utils.HttpHelper;
import com.shtoone.shtw.utils.KeyBoardUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.URL;
import com.socks.library.KLog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import in.srain.cube.views.ptr.PtrFrameLayout;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by gesangdianzi on 2016/9/6.
 */
public class PitchOverproofDetailActivity extends BaseActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private static final String TAG = PitchOverproofDetailActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private NestedScrollView mNestedScrollView;
    private PageStateLayout mPageStateLayout;
    private PtrFrameLayout mPtrFrameLayout;
    private TextView tv_date;
    private TextView tv_sjysb;
    private TextView tv_llysb;
    private TextView tv_wcysb;
    private TextView tv_lqwd;
    private TextView tv_slwd;
    private TextView tv_clwd;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView1;
    private ImageView iv_photo_select;
    private ImageView iv_camera_select;
    private ImageView iv_album_select;
    private TextInputLayout et_handle_person;
    private TextInputLayout et_handle_time;
    private TextInputLayout et_handle_reason;
    private TextInputLayout et_handle_way;
    private TextInputLayout et_handle_result;
    private Button bt_handle_submit;
    private Button bt_handle_reset;

    private LinearLayout ll_camera_album;
    private UserInfoData mUserInfoData;
    private String handlePerson;
    private String handleTime;
    private String handleReason;
    private String handleWay;
    private String handleResult;
    private Bitmap bitmap;
    private PitchOverproofDetailActivityRecyclerViewAdapter mAdapter;
    private PitchOverproofFragmentViewPagerFragmentItemData mDataBean;
    private PitchOverproofFragmentViewPagerFragmentListData mlistData;
    private PitchOverproofDetailActivityData data;
    private String confirmTime;
    private String approveTime;
    int xxid;
    private String chuli;
    private boolean isHandleDateTime;
    private boolean isConfirmDateTime;
    private boolean isApproveDateTime;
    SC_chaxunItem_xq xqData = null;
    List<SC_chaxunItem_xq_data> lists = null;
    private Gson mGson;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitch_overproof_detail);
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
        mRecyclerView1 = (RecyclerView) findViewById(R.id.rv_overproof_detail_activity);
        //处置部分
        iv_photo_select = (ImageView) findViewById(R.id.iv_photo_select_overproof_detail_activity);
        iv_camera_select = (ImageView) findViewById(R.id.iv_camera_select_overproof_detail_activity);
        iv_album_select = (ImageView) findViewById(R.id.iv_album_select_overproof_detail_activity);
        ll_camera_album = (LinearLayout) findViewById(R.id.ll_camera_album_overproof_detail_activity);
        et_handle_person = (TextInputLayout) findViewById(R.id.et_handle_person_overproof_detail_activity);
        et_handle_time = (TextInputLayout) findViewById(R.id.et_handle_time_overproof_detail_activity);
        et_handle_reason = (TextInputLayout) findViewById(R.id.et_handle_reason_overproof_detail_activity);
        et_handle_way = (TextInputLayout) findViewById(R.id.et_handle_way_overproof_detail_activity);
        et_handle_result = (TextInputLayout) findViewById(R.id.et_handle_result_overproof_detail_activity);
        et_handle_person.getEditText().setInputType(InputType.TYPE_NULL);
        et_handle_time.getEditText().setInputType(InputType.TYPE_NULL);
        bt_handle_submit = (Button) findViewById(R.id.bt_handle_submit_overproof_detail_activity);
        bt_handle_reset = (Button) findViewById(R.id.bt_handle_reset_overproof_detail_activity);


    }

    private void initDate() {

        mUserInfoData = BaseApplication.mUserInfoData;
        mlistData = (PitchOverproofFragmentViewPagerFragmentListData) getIntent().getSerializableExtra("pitchoverproofdetail");
        xxid = mlistData.getBianhao();
        chuli = mlistData.getChuli();
        Log.e("chuli",chuli);

        mGson = new Gson();
        setToolbarTitle();
        initToolbarBackNavigation(mToolbar);
        setSupportActionBar(mToolbar);
        xqData = new SC_chaxunItem_xq();
        et_handle_time.getEditText().setEnabled(false);
//        et_handle_time.getEditText().setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        isHandleDateTime = true;
//                        isConfirmDateTime = false;
//                        isApproveDateTime = false;
//                        showDatePicker();
//                        break;
//                }
//                return true;
//            }
//        });


        iv_photo_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = (ll_camera_album.getLeft() + view.getRight()) / 2;
                    int cy = (ll_camera_album.getTop() + view.getBottom()) / 2;
                    int radius = Math.max(view.getWidth(), ll_camera_album.getHeight());
                    Animator mAnimator = ViewAnimationUtils.createCircularReveal(ll_camera_album, cx, cy, 0, radius);
                    mAnimator.setDuration(500);
                    mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    mAnimator.addListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            ll_camera_album.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            iv_photo_select.setVisibility(View.INVISIBLE);
                        }
                    });
                    mAnimator.start();
                } else {
                    iv_photo_select.setVisibility(View.GONE);
                    ll_camera_album.setVisibility(View.VISIBLE);
                }
            }
        });

        iv_camera_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Android6.0以上动态请求打开相机权限
//                if (ContextCompat.checkSelfPermission(PitchOverproofDetailActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(PitchOverproofDetailActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
//                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, ConstantsUtils.CAMERA);
//                }

//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), System.currentTimeMillis() + ".jpg");
//                uri = Uri.fromFile(file);
//                // intent.setDataAndType(Uri.fromFile(file), "image/*");
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                startActivityForResult(intent, ConstantsUtils.CAMERA);
            }
        });

        iv_album_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");// 相片类型
                startActivityForResult(intent, ConstantsUtils.ALBUM);
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("image/*");
//                startActivityForResult(intent, ConstantsUtils.ALBUM);
            }
        });

        et_handle_reason.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    et_handle_reason.getEditText().setError("处置原因不能为空");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_handle_way.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    et_handle_way.getEditText().setError("处置方式不能为空");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_handle_result.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    et_handle_result.getEditText().setError("处置结果不能为空");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        bt_handle_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyBoardUtils.hideKeybord(view, PitchOverproofDetailActivity.this);
                handlePerson = et_handle_person.getEditText().getText().toString().trim();
                handleTime = et_handle_time.getEditText().getText().toString().trim();
                handleReason = et_handle_reason.getEditText().getText().toString().trim();
                handleWay = et_handle_way.getEditText().getText().toString().trim();
                handleResult = et_handle_result.getEditText().getText().toString().trim();
                if (!TextUtils.isEmpty(handlePerson) && !TextUtils.isEmpty(handleTime) && !TextUtils.isEmpty(handleReason) && !TextUtils.isEmpty(handleWay) && !TextUtils.isEmpty(handleResult)) {
                    //弹出对话框，确定提交
                    new MaterialDialog.Builder(PitchOverproofDetailActivity.this)
                            .title("确认")
                            .content("请问您确定填写无误并提交吗？")
                            .positiveText("确定")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    MaterialDialog progressDialog = new MaterialDialog.Builder(PitchOverproofDetailActivity.this)
                                            .title("提交")
                                            .content("正在提交中，请稍等……")
                                            .progress(true, 0)
                                            .progressIndeterminateStyle(true)
                                            .cancelable(false)
                                            .show();
                                    handleSubmit1(progressDialog);
                                }
                            })
                            .negativeText("放弃")
                            .show();
                } else {
                    if (TextUtils.isEmpty(handlePerson)) {
                        et_handle_person.getEditText().setError("处置人不能为空");
                    } else if (TextUtils.isEmpty(handleTime)) {
                        et_handle_time.getEditText().setError("处置时间不能为空");
                    } else if (TextUtils.isEmpty(handleReason)) {
                        et_handle_reason.getEditText().setError("处置原因不能为空");
                    } else if (TextUtils.isEmpty(handleWay)) {
                        et_handle_way.getEditText().setError("处置方式不能为空");
                    } else if (TextUtils.isEmpty(handleResult)) {
                        et_handle_result.getEditText().setError("处置结果不能为空");
                    }
                }
            }
        });

        bt_handle_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出对话框,确定重置
                new MaterialDialog.Builder(PitchOverproofDetailActivity.this)
                        .title("确认")
                        .content("请问您确定要重置吗？那样您就要重新填写哟……")
                        .positiveText("确定")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //提交到服务器
                                TastyToast.makeText(getApplicationContext(), "已经重置!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                                et_handle_reason.getEditText().setText("");
                                et_handle_reason.setFocusable(false);
                                et_handle_way.getEditText().setText("");
                                et_handle_result.getEditText().setText("");
                                handleResult = "";
                                handleReason = "";
                                handleWay = "";
                                bitmap = null;
                                iv_photo_select.setImageResource(R.drawable.ic_camera_album);
                            }
                        })
                        .negativeText("放弃")
                        .show();
            }
        });

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
        return URL.getPitchOverProofDetailData(mlistData.getBianhao(), mlistData.getShebeibianhao());
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
                data = mGson.fromJson(response, PitchOverproofDetailActivityData.class);
                setViewData();
                try {
                    KLog.e(response);
                    getXqDataFromString(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (null != xqData && !xqData.getLists().isEmpty()) {
                    if (xqData.getLists().size() > 0) {
                        mPageStateLayout.showContent();
                        setAdapter();
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
        if (!NetworkUtils.isConnected(this)) {
            //提示网络异常,让用户点击设置网络
            mPageStateLayout.showNetError();
        } else {
            //服务器异常，展示错误页面，点击刷新
            mPageStateLayout.showError();
        }
    }

    private void handleSubmit(final MaterialDialog progressDialog) {
        String person = null, time = null, reason = null, way = null, result = null;
        try {
            person = URLEncoder.encode(handlePerson, "UTF-8");
            time = URLEncoder.encode(handleTime, "utf-8");
            reason = URLEncoder.encode(handleReason, "UTF-8");
            way = URLEncoder.encode(handleWay, "UTF-8");
            result = URLEncoder.encode(handleResult, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        final String url = URL.LIQING_CHAOBIAO_DETAIL_CHUZHI_SUBMIT_URL.replace("%1", xxid + "").replace("%2", reason).replace("%3", way).replace("%4", result).replace("%5", person).replace("%6", time);

        final Handler handler =
                new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    progressDialog.dismiss();
                    TastyToast.makeText(getApplicationContext(), "上传成功!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    BaseApplication.bus.post(new EventData(ConstantsUtils.REFRESH));
                } else {
                    progressDialog.dismiss();
                    TastyToast.makeText(getApplicationContext(), "上传失败，请重试！", TastyToast.LENGTH_SHORT, TastyToast.ERROR);

                }
            }
        };
        new Thread(new Runnable() {
            public void run() {
                boolean flag = uploadPic(url); //返回为 true 表示上传成功 ； false 上传失败
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (flag) {
                    handler.sendEmptyMessage(1);  // 上传成功 发送消息到 handler 关闭详情页并提示上传成功
                } else {
                    handler.sendEmptyMessage(2);  // 上传失败 则什么都不做 停留在此页面
                }
            }
        }).start();
    }

    private void setViewData() {
        // 设置显示数据
        tv_date.setText(data.getData().getShijian());//
        tv_sjysb.setText(data.getData().getSjysb() + "%");//
        tv_llysb.setText(data.getData().getLlysb() + "%");//
        tv_wcysb.setText(data.getData().getSjysb() + "%");//
        tv_lqwd.setText(data.getData().getLqwd() + "℃");//
        tv_slwd.setText(data.getData().getGlwd() + "℃");//
        tv_clwd.setText(data.getData().getClwd() + "℃");//
    }

    //还是不能这样搞，可能会内存泄漏，重复创建Adapyer对象。后面解决
    private void setAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(this));
        //设置动画与适配器
        KLog.e(TAG,"xqData.getLists()="+xqData.getLists().toString());
        SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new PitchOverproofDetailActivityRecyclerViewAdapter(this, xqData.getLists()));
        mSlideInLeftAnimationAdapter.setFirstOnly(true);
        mSlideInLeftAnimationAdapter.setDuration(500);
        mSlideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator(.5f));
        ScaleInAnimationAdapter mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mSlideInLeftAnimationAdapter);
        mScaleInAnimationAdapter.setFirstOnly(true);
        mRecyclerView.setAdapter(mScaleInAnimationAdapter);
        mRecyclerView1.setAdapter(mScaleInAnimationAdapter);



        //设置处置部分是否显示
        if (mUserInfoData.getQuanxian().isLqchaobiaoReal()) {
            if ("1".equals(chuli)) {
                bt_handle_submit.setEnabled(false);
                bt_handle_reset.setEnabled(false);
            } else if ("0".equals(chuli)) {
                bt_handle_submit.setEnabled(true);
                bt_handle_reset.setEnabled(true);
            }

            if (!TextUtils.isEmpty(data.getData().getFilepath())) {

                String imageURL = URL.BaseURL + data.getData().getFilepath();
                Glide.with(getApplicationContext()).load(imageURL).crossFade().into(iv_photo_select);
            }

            if (TextUtils.isEmpty(data.getData().getChuzhiren())) {
                et_handle_person.getEditText().setText(handlePerson = mUserInfoData.getUserFullName());
            } else {
                et_handle_person.getEditText().setText(data.getData().getChuzhiren());
            }
            if (TextUtils.isEmpty(data.getData().getChuzhishijian())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                et_handle_time.getEditText().setText(sdf.format(Calendar.getInstance().getTime()));
            } else {
                et_handle_time.getEditText().setText(data.getData().getChuzhishijian());
            }

            if (!TextUtils.isEmpty(data.getData().getChaobiaoyuanyin())) {
                et_handle_reason.getEditText().setText(data.getData().getChaobiaoyuanyin());
            }

            if (!TextUtils.isEmpty(data.getData().getChulifangshi())) {
                et_handle_way.getEditText().setText(data.getData().getChulifangshi());

            }

            if (!TextUtils.isEmpty(data.getData().getChulijieguo())) {
                et_handle_result.getEditText().setText(data.getData().getChulijieguo());
            }
        }


    }

    private void setToolbarTitle() {
        if (null != mToolbar && null != BaseApplication.mDepartmentData && !TextUtils.isEmpty(BaseApplication.mDepartmentData.departmentName)) {
            StringBuffer sb = new StringBuffer(BaseApplication.mDepartmentData.departmentName + " > ");
            sb.append(getString(R.string.asphalt) + " > ");
            sb.append(getString(R.string.overproof) + " > ");
            sb.append(getString(R.string.detail)).trimToSize();
            mToolbar.setTitle(sb.toString());
        }
    }

    private void showDatePicker() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.vibrate(true);
        dpd.dismissOnPause(false);
        dpd.setAccentColor(Color.parseColor("#3F51B5"));
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    private void showTimePicker() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false);
        tpd.vibrate(true);
        tpd.dismissOnPause(false);
        tpd.setAccentColor(Color.parseColor("#3F51B5"));
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onResume() {
        super.onResume();
        TimePickerDialog tpd = (TimePickerDialog) getFragmentManager().findFragmentByTag("Timepickerdialog");
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");

        if (tpd != null) tpd.setOnTimeSetListener(this);
        if (dpd != null) dpd.setOnDateSetListener(this);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        String timeString = hourString + ":" + minuteString + ":" + secondString;
        if (isHandleDateTime) {
            handleTime = handleTime + timeString;
            et_handle_time.getEditText().setText(handleTime);
        } else if (isConfirmDateTime) {
            confirmTime = confirmTime + timeString;
            //   et_confirm_date.getEditText().setText(confirmTime);
        } else if (isApproveDateTime) {
            approveTime = approveTime + timeString;
            //et_approve_date.getEditText().setText(approveTime);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String monthString = (++monthOfYear) < 10 ? "0" + (monthOfYear) : "" + (monthOfYear);
        String dayString = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
        String dateString = year + "-" + monthString + "-" + dayString + " ";

        if (isHandleDateTime) {
            handleTime = dateString;
        } else if (isConfirmDateTime) {
            confirmTime = dateString;
        } else if (isApproveDateTime) {
            approveTime = dateString;
        }

        showTimePicker();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == ConstantsUtils.CAMERA) { // 表示是从相机拍照页返回
            // 判断内存卡是否可用
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                KLog.e("SD卡不可用");
                TastyToast.makeText(getApplicationContext(), "SD卡不可用！", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                return;
            }
            //对 返回的 bitmap 进行对应的保存操作
            String photoName = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
            TastyToast.makeText(getApplicationContext(), photoName, TastyToast.LENGTH_SHORT, TastyToast.INFO);
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");

            FileOutputStream b = null;
            File file = new File("/sdcard/shtw/");
            file.mkdirs();
            String fileName = "/sdcard/shtw/" + photoName;

            try {
                b = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    b.flush();
                    b.close();
                } catch (Exception e2) {
                }
            }
        } else if (requestCode == ConstantsUtils.ALBUM) { // 表示是从相册选择图片返回
            Uri uri = data.getData(); //得到图片 uri
            ContentResolver resolver = getContentResolver(); //处理器
            try {
                bitmap = MediaStore.Images.Media.getBitmap(resolver, uri); //  将对应 uri 通过处理器转化为 bitmap
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (bitmap != null) {
            ll_camera_album.setVisibility(View.GONE);
            iv_photo_select.setVisibility(View.VISIBLE);
            iv_photo_select.setImageBitmap(bitmap);
        }
    }



    private void handleSubmit1(final MaterialDialog progressDialog) {

        Map<String, String> options = new HashMap<>();
        Map<String, RequestBody> params=new HashMap<String, RequestBody>();
        options.put("xxid",xxid+"");
        options.put("chuzhiren",handlePerson);
        options.put("chuzhishijian", DateUtils.ChangeTimeToLong(handleTime));
        options.put("chaobiaoyuanyin",handleReason);
        options.put("chuzhifangshi",handleWay);
        options.put("chuzhijieguo",handleResult);

        KLog.e(TAG,options.toString());

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        if(null!=bitmap){
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);//图片压缩
            RequestBody photo = RequestBody.create(MediaType.parse("multipart/form-data"), os.toByteArray());
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", "1111", photo);
//               params.put("file",  photo);

            KLog.e(TAG,params.toString());
//            params.put("fileName=\""+photoName , RequestBody.create(MediaType.parse("image/png"),fileName));
            HttpHelper.getInstance().initService().uploadPendingTreatResult(options,body)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<Object>() {
                        @Override
                        public void onCompleted() {

                            progressDialog.dismiss();
//                            TastyToast.makeText(getApplicationContext(), "上传成功!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

                        }

                        @Override
                        public void onError(Throwable e) {
                            progressDialog.dismiss();

                            TastyToast.makeText(getApplicationContext(), "上传失败!", TastyToast.LENGTH_SHORT, TastyToast.ERROR);

                            KLog.e(TAG,e.toString());
                        }

                        @Override
                        public void onNext(Object o) {
                            TastyToast.makeText(getApplicationContext(), "上传成功!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                            finish();
                        }
                    });
        }
    }
    /**
     * 上传图片的方法
     *
     * @param path
     * @return
     */
    private boolean uploadPic(String path) {
        try {
            java.net.URL url = new java.net.URL(path);
            KLog.e(TAG, url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
            // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
            httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
            // 允许输入输出流
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "text/html");
            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
            // 将要上传的内容写入流中
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (bitmap != null) {

                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
            }

            InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());

            byte[] buffer = new byte[8192]; // 8k
            int length = 0;
            // 读取流 并写入到 上传流中
            while ((length = inputStream.read(buffer)) != -1) {
                dos.write(buffer, 0, length);
                KLog.e("buffer", buffer.toString());
            }
            baos.close();
            inputStream.close();
            dos.flush();

            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String sl;
            String result = "";
            while ((sl = br.readLine()) != null)
                result = result + sl;
            JSONObject jo = new JSONObject(result);
            KLog.e(jo.toString());
            br.close();
            is.close();
            if (jo.getBoolean("success")) { //服务器返回json success 为 true 表示上传成功
                return true;
            } else {
                return false;
            }
            // dos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 超标查询单条信息详情
     *
     * @throws Exception
     */
    private void getXqDataFromString(String string) throws Exception {

        JSONObject jsonObject = new JSONObject(string);
        if (jsonObject.getBoolean("success")) {
            lists = new ArrayList<SC_chaxunItem_xq_data>();
            JSONObject joData = jsonObject.getJSONObject("data"); // 获取 data
            // JSON
            JSONObject joFields = jsonObject.getJSONObject("Fields"); // 获取
            // Fields
            // JSON
            JSONObject joIsShow = jsonObject.getJSONObject("Isshow"); // 获取
            // Isshow
            // JSON

            // 1. 时间,实际油石比,理论油石比,误差实际油石比,沥青温度,骨料温度,出料温度
            xqData.setBanhezhanmingchen(joData.getString("banhezhanminchen"));
            xqData.setChuliaoshijian(joData.getString("shijian"));
            xqData.setShijiyoushibi(joData.getString("sjysb"));
            xqData.setLilunyoushibi(joData.getString("llysb"));
            xqData.setYoushibiwucha(joData.getString("wsjysb"));
            xqData.setLiqingwendu(joData.getString("lqwd"));
            xqData.setShiliaowend(joData.getString("glwd"));
            xqData.setChuliaowendu(joData.getString("clwd"));
            xqData.setUsername(joData.getString("chuzhiren"));
            xqData.setReason(joData.getString("chaobiaoyuanyin"));
            xqData.setResult(joData.getString("chulijieguo"));
            xqData.setType(joData.getString("chulifangshi"));
            xqData.setTime(joData.getString("chuzhishijian"));

            // 2. 根据字段名称 在 isshow 里面获取值 值为1 则表示显示 0 表示不显示
            String str = "sj";
            String strll = "ll";
            String tempStrsj = "";
            String tempstrll = "";
            for (int i = 1; i <= 9; i++) {
                if (i <= 7) {
                    tempStrsj = str + "g" + i;
                    tempstrll = strll + "g" + i;
                } else if (i == 8) {
                    tempStrsj = str + "lq";
                    tempstrll = strll + "lq";
                } else {
                    tempStrsj = str + "tjj";
                    tempstrll = strll + "tjj";
                }
                if ("1".equals(joIsShow.getString(tempStrsj))) {
                    SC_chaxunItem_xq_data row = new SC_chaxunItem_xq_data();
                    row.setName(joFields.getString(tempStrsj));
                    row.setYongliang(joData.getString(tempStrsj));
                    row.setShiji(joData.getString("per" + tempStrsj));
                    row.setPeibi(joData.getString(tempstrll));
                    row.setWucha(joData.getString("w" + tempStrsj));
                    row.setCb(joData.getString("cb" + tempStrsj));
                    lists.add(row);
                }
            }
            if ("1".equals(joIsShow.getString("sjf1"))) {
                SC_chaxunItem_xq_data row = new SC_chaxunItem_xq_data();
                row.setName(joFields.getString("sjf1"));
                row.setYongliang(joData.getString("sjf1"));
                row.setShiji(joData.getString("persjf1"));
                row.setPeibi(joData.getString("llf1"));
                row.setWucha(joData.getString("wsjf1"));
                row.setCb(joData.getString("cbsjf1"));
                lists.add(row);
            }
            if ("1".equals(joIsShow.getString("sjf2"))) {
                SC_chaxunItem_xq_data row = new SC_chaxunItem_xq_data();
                row.setName(joFields.getString("sjf2"));
                row.setYongliang(joData.getString("sjf2"));
                row.setShiji(joData.getString("persjf2"));
                row.setPeibi(joData.getString("llf2"));
                row.setWucha(joData.getString("wsjf2"));
                row.setCb(joData.getString("cbsjf2"));
                lists.add(row);
            }
            xqData.setLists(lists);
        }
    }

//    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
//    /*
//    使用原生方式Android6.0以上动态请求打开相机权限
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//
//        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, ConstantsUtils.CAMERA);
//            } else {
//                // Permission Denied
//                Toast.makeText(PitchOverproofDetailActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//            return;
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

}
